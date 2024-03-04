package com.sid.spring.batch.app.service;

import com.sid.spring.batch.app.constant.AppConstants;
import com.sid.spring.batch.app.entity.Customer;
import com.sid.spring.batch.app.exception.CustomException;
import com.sid.spring.batch.app.repository.CustomerRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Locale;
import java.util.Random;

@Service
@Slf4j
@SuppressWarnings("PMD")
public class DataGeneratorService {

    @Autowired
    private CustomerRepository customerRepository;

    public Mono<String> generateCSVData() {
        String file = AppConstants.CSV_FILE_PATH;
        try (FileWriter writer = new FileWriter(file)) {
            writer.append("id,firstName,lastName,email,gender,contactNo,country,dob\n");

            for (int i = 1; i <= 1000; i++) {
                String id = Integer.toString(i);
                String firstName = generateRandomName();
                String lastName = generateRandomName();
                String email = generateRandomEmail(firstName, lastName);
                String gender = generateRandomGender();
                String contactNo = generateRandomContactNumber();
                String country = generateRandomCountry();
                String dob = generateRandomDateOfBirth().toString();

                writer.append(id).append(",")
                        .append(firstName).append(",")
                        .append(lastName).append(",")
                        .append(email).append(",")
                        .append(gender).append(",")
                        .append(contactNo).append(",")
                        .append(country).append(",")
                        .append(dob).append("\n");
            }
            if (log.isInfoEnabled()) {
                log.info("generateCSVData() : Customer data has been generated and saved to {}", file);
            }
            return Mono.just("Customer data has been generated and saved to database");
        } catch (IOException exception) {
            if (log.isErrorEnabled()) {
                log.error("generateCSVData() : Exception occurred while generating the CSV data. Exception -> {}", exception.getCause().getMessage());
            }
            return Mono.error(new CustomException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), "Exception occurred while generating the CSV data. Exception -> {}", exception));
        }
    }

    private static String generateRandomName() {
        String[] names = {"John", "Alice", "Bob", "Sarah", "Michael", "Emma", "David", "Olivia", "James", "Emily"};
        Random random = new Random();
        return names[random.nextInt(names.length)];
    }

    private static String generateRandomEmail(String firstName, String lastName) {
        Random random = new Random();
        String[] domains = {"google.com", "microsoft.com", "yahoo.com", "gmail.com"};
        String domain = domains[random.nextInt(domains.length)];
        int randomNumber = random.nextInt(10000);
        return firstName.toLowerCase(Locale.ROOT) + "." + lastName.toLowerCase(Locale.ROOT) + randomNumber + "@" + domain;
    }

    private static String generateRandomGender() {
        Random random = new Random();
        return random.nextBoolean() ? "Male" : "Female";
    }

    private static String generateRandomContactNumber() {
        Random random = new Random();
        int firstDigit = random.nextInt(9) + 1;
        StringBuilder contactNumberBuilder = new StringBuilder().append(firstDigit);
        for (int i = 0; i < 9; i++) {
            contactNumberBuilder.append(random.nextInt(10));
        }
        return contactNumberBuilder.toString();
    }

    private static String generateRandomCountry() {
        String[] countries = {"USA", "UK", "Canada", "Australia", "Germany", "France", "India", "Japan", "China", "Brazil"};
        Random random = new Random();
        return countries[random.nextInt(countries.length)];
    }

    private static LocalDate generateRandomDateOfBirth() {
        Random random = new Random();
        LocalDate startDate = LocalDate.of(1950, 1, 1); // Start date
        LocalDate endDate = LocalDate.now().minusYears(18); // End date (18 years ago from today)
        int randomDays = random.nextInt(startDate.until(endDate).getDays());
        return startDate.plusDays(randomDays);
    }

    public Mono<List<Customer>> getAllData() {

        return Mono.just(customerRepository.findAll());
    }
}