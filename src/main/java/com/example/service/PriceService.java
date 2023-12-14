package com.example.service;

import com.example.model.Price;
import com.example.repository.PriceRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class PriceService {

    @Autowired
    private PriceRepository priceRepository;

    public List<Price> getPriceList(Map<String, Object> params) {
        try {
            // Extraer parámetros del mapa
            Long brandId = getLongParameter(params, "brandId");
            Long productId = getLongParameter(params, "productId");
            LocalDateTime applicationDate = parseStringToLocalDateTime((String) params.get("applicationDate"));

            log.info("Getting price list for Brand ID {}, Product ID {}, Application Date {}", brandId, productId, applicationDate);

            // Delegar al repositorio
            List<Price> prices = priceRepository.findByBrandIdAndProductIdAndStartDate(brandId, productId, applicationDate);

            log.info("Found {} prices", prices.size());
            return prices;
        } catch (Exception e) {
            log.error("Error processing getPriceList request", e);
            throw new RuntimeException("Error processing getPriceList request", e);
        }
    }

    // Métodos de utilidad

    private Long getLongParameter(Map<String, Object> params, String paramName) {
        Object paramValue = params.get(paramName);
        if (paramValue instanceof Number) {
            return ((Number) paramValue).longValue();
        } else {
            throw new IllegalArgumentException(paramName + " must be a number");
        }
    }

    private LocalDateTime parseStringToLocalDateTime(String dateString) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH.mm.ss");
        return LocalDateTime.parse(dateString, formatter);
    }
}
