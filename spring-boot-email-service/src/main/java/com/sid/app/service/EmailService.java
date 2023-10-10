package com.sid.app.service;

import com.sid.app.constant.AppConstants;
import com.sid.app.entity.EmailTransaction;
import com.sid.app.model.EmailRequest;
import com.sid.app.repository.EmailRepository;
import com.sid.app.utils.AppUtils;
import com.sid.app.utils.Status;
import com.sid.app.validation.AppServiceErrors;
import liquibase.repackaged.org.apache.commons.lang3.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import reactor.core.publisher.Mono;

import javax.activation.DataSource;
import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.util.ByteArrayDataSource;
import java.sql.Timestamp;
import java.util.*;

/**
 * @author Siddhant Patni
 */
@Service
@Slf4j
public class EmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private EmailRepository emailRepository;

    public Mono<EmailTransaction> sendEmail(EmailRequest emailRequestDTO, List<FilePart> attachment) {

        if (log.isInfoEnabled()) {
            log.info("sendEmail() : emailRequestDTO -> {}", AppUtils.getJSONString(emailRequestDTO));
        }

        return Mono.just(emailRequestDTO)
                .map(this::createEmailTransaction)
                .flatMap(emailTransaction -> createMimeMessage(emailRequestDTO, attachment, emailTransaction)
                        .flatMap(mimeMessage -> sendMimeMessage(mimeMessage)
                                .thenReturn(mimeMessage))
                        .flatMap(sentMessage -> saveEmailTransaction(emailTransaction, Status.SUCCESS, ""))
                        .onErrorResume(throwable -> handleSendEmailError(throwable, emailTransaction)));
    }

    public EmailTransaction createEmailTransaction(EmailRequest emailRequestDTO) {

        return EmailTransaction.builder()
                .correlationID(UUID.randomUUID().toString())
                .createdAt(new Timestamp(System.currentTimeMillis()))
                .to(String.join(",", emailRequestDTO.getTo()))
                .build();
    }

    public InternetAddress[] getInternetAddresses(List<String> emails) {

        return emails.stream()
                .map(email -> {
                    try {
                        return new InternetAddress(email, true);
                    } catch (AddressException e) {
                        log.error("Error while parsing the email string and creating its InternetAddress : {}", e.getCause().getMessage());
                        throw AppServiceErrors.MAILING_ERROR.newEx();
                    }
                })
                .toArray(InternetAddress[]::new);

    }

    public void addRecipients(MimeMessageHelper helper, EmailRequest emailRequestDTO) throws MessagingException {

        InternetAddress[] toAddresses = getInternetAddresses(emailRequestDTO.getTo());
        helper.setTo(toAddresses);
        helper.setFrom(AppConstants.EMAIL_FROM);
        if (!CollectionUtils.isEmpty(emailRequestDTO.getCc())) {
            InternetAddress[] ccAddresses = getInternetAddresses(emailRequestDTO.getCc());
            helper.setCc(ccAddresses);
        }
        if (!CollectionUtils.isEmpty(emailRequestDTO.getBcc())) {
            InternetAddress[] bccAddresses = getInternetAddresses(emailRequestDTO.getBcc());
            helper.setBcc(bccAddresses);
        }

    }

    public Mono<MimeMessage> createMimeMessage(EmailRequest emailRequestDTO, List<FilePart> attachment, EmailTransaction emailTransaction) {

        return Mono.fromCallable(() -> {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            addRecipients(helper, emailRequestDTO);
            helper.setSubject(emailRequestDTO.getSubject());
            helper.setText(emailRequestDTO.getBody(), true);
            if (!CollectionUtils.isEmpty(attachment)) {
                for (FilePart fp : attachment) {
                    String type = Objects.requireNonNull(fp.headers().getContentType()).toString();
                    Mono<DataBuffer> singleBuffer = DataBufferUtils.join(fp.content());
                    singleBuffer.subscribe(dataBuffer -> {
                        try {
                            DataSource dataSource = new ByteArrayDataSource(dataBuffer.asByteBuffer().array(), type);
                            helper.addAttachment(fp.filename(), dataSource);
                        } catch (MessagingException e) {
                            if (log.isErrorEnabled()) {
                                log.error("createMimeMessage() : Error while adding attachment to MimeMessage: {}", e.getCause().getMessage());
                            }
                            handleSendEmailError(e, emailTransaction);
                        }
                    });
                }
            }
            if (StringUtils.isNotBlank(emailRequestDTO.getPriority())) {
                message.setHeader("X-Priority", emailRequestDTO.getPriority());
            }
            return message;
        });

    }

    public Mono<Void> sendMimeMessage(MimeMessage mimeMessage) {

        Mono<Void> monoRes = Mono.fromRunnable(() -> javaMailSender.send(mimeMessage));
        if (log.isInfoEnabled()) {
            log.info("sendMimeMessage() : Request Processed with CorrelationId = {}", UUID.randomUUID().toString());
        }
        return monoRes;

    }

    public Mono<EmailTransaction> saveEmailTransaction(EmailTransaction emailTransaction, Status status, String failureDescription) {

        emailTransaction.setStatus(status);
        if (Status.FAILURE.equals(status)) {
            emailTransaction.setFailureDescription(failureDescription);
        }

        return Mono.just(emailRepository.save(emailTransaction))
                .doOnSuccess(saveEmailTransaction -> {
                    if (log.isInfoEnabled()) {
                        log.info("saveEmailTransaction() : Record saved with CorrelationId = {}", saveEmailTransaction.getCorrelationID());
                    }
                });

    }

    public Mono<EmailTransaction> handleSendEmailError(Throwable throwable, EmailTransaction emailTransaction) {

        String failureDescription = Optional.ofNullable(throwable.getCause())
                .map(Throwable::getMessage)
                .orElse("Internal Server Error");
        return saveEmailTransaction(emailTransaction, Status.FAILURE, failureDescription)
                .doOnNext(s -> log.error("{}, while sending the email with CorrelationId = {}", failureDescription, UUID.randomUUID()))
                .then(Mono.error(AppServiceErrors.MAILING_ERROR.newEx()));

    }

}