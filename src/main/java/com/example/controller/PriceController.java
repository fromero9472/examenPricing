package com.example.controller;

import com.example.model.Price;
import com.example.service.PriceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

@RestController
@Slf4j
public class PriceController {

    @Autowired
    private PriceService priceService;
    @PostMapping(value = "/getPriceList",  produces = "application/json", consumes = "application/json")
    public ResponseEntity<?> getPriceList(@RequestBody Map<String, Object> params) {
        try {
            // Obtener los parámetros del mapa
            Long brandId = getLongParameter(params, "brandId");
            Long productId = getLongParameter(params, "productId");
            LocalDateTime applicationDate = parseStringToLocalDateTime((String) params.get("applicationDate"));

            log.info("Received request for getPriceList. Brand ID: {}, Product ID: {}, Application Date: {}", brandId, productId, applicationDate);
            List<Price> prices = priceService.getPriceList(brandId, productId, applicationDate);
            log.info("Returning {} prices", ((List<?>) prices).size());

            return ResponseEntity.ok(prices);
        } catch (Exception e) {
            log.error("Error processing getPriceList request", e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid request parameters");
        }
    }
    public static LocalDateTime parseStringToLocalDateTime(String dateString) {
        // Definir un formato de fecha y hora sin la 'T' en el medio
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH.mm.ss");

        // Convertir la cadena a LocalDateTime
        return LocalDateTime.parse(dateString, formatter);
    }

    private Long getLongParameter(Map<String, Object> params, String paramName) {
        Object paramValue = params.get(paramName);
        if (paramValue instanceof Number) {
            return ((Number) paramValue).longValue();
        } else {
            throw new IllegalArgumentException(paramName + " must be a number");
        }
    }

}