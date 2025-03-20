package com.christianj98.market_data_service.kafka;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MarketDataProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;

    public void sendPriceUpdate(String symbol, double price) {
        String message = "{\"symbol\":\"" + symbol + "\", \"price\":" + price + "}";
        kafkaTemplate.send("stock-prices", symbol, message);
    }
}
