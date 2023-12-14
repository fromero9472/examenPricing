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

import java.util.List;
import java.util.Map;

@RestController
@Slf4j
public class PriceController {

    @Autowired
    private PriceService priceService;

    @PostMapping(value = "/getPriceList", produces = "application/json", consumes = "application/json")
    public ResponseEntity<?> getPriceList(@RequestBody Map<String, Object> params) {
        try {
            // Delegar toda la lógica al servicio
            List<Price> prices = priceService.getPriceList(params);

            log.info("Returning {} prices", prices.size());

            return ResponseEntity.ok(prices);
        } catch (Exception e) {
            log.error("Error processing getPriceList request", e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid request parameters");
        }
    }
}
