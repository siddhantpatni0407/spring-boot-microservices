package com.sid.spring.batch.app.listener;

import com.sid.spring.batch.app.entity.Customer;
import com.sid.spring.batch.app.utils.ApplicationUtils;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.SkipListener;

@Slf4j
public class StepSkipListener implements SkipListener<Customer, Number> {

    @Override // item reader
    public void onSkipInRead(Throwable throwable) {
        log.info("onSkipInRead() : failure on read {} ", throwable.getMessage());
    }

    @Override // item writer
    public void onSkipInWrite(Number item, Throwable throwable) {
        log.info("onSkipInWrite() : failure on write {} , {}", throwable.getMessage(), item);
    }

    @SneakyThrows
    @Override // item processor
    public void onSkipInProcess(Customer customer, Throwable throwable) {
        log.info("onSkipInProcess() : Item {}  was skipped due to the exception  {}", ApplicationUtils.getJSONString(customer),
                throwable.getMessage());
    }

}