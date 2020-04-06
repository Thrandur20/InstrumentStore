package com.tudor.dodonete.mizuho.InstrumentStore.utilis;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Date;

@Configuration
public class DateUtility {
    @Bean
    public Date getCurrentDate() {
        return new Date();
    }
}
