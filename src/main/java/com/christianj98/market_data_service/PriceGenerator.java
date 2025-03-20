package com.christianj98.market_data_service;

import com.christianj98.market_data_service.kafka.MarketDataProducer;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Random;

@Slf4j
@Component
public class PriceGenerator {

    private final MarketDataProducer producer;
    private final Random random = new Random();
    private final String[] symbols = {"AAPL", "GOOG", "AMZN", "TSLA"};

    public PriceGenerator(MarketDataProducer producer) {
        this.producer = producer;
    }

    @PostConstruct
    public void startGenerating() {
        new Thread(() -> {
            while (true) {
                for (String symbol : symbols) {
                    double price = 100 + random.nextDouble() * 1000;
                    log.info("Generating price: {} -> {}", symbol, price);
                    producer.sendPriceUpdate(symbol, price);
                }
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }).start();
    }
}
