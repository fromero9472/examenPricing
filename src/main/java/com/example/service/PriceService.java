package com.example.service;

import com.example.model.Price;
import com.example.repository.PriceRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class PriceService {

    @Autowired
    private PriceRepository priceRepository;

    public List<Price> getPriceList(Long brandId, Long productId, LocalDateTime applicationDate) {
        log.info("Getting price list for Brand ID {}, Product ID {}, Application Date {}", brandId, productId, applicationDate);
        List<Price> prices = priceRepository.findByBrandIdAndProductIdAndStartDate(
                brandId, productId, applicationDate);
        log.info("Found {} prices", prices.size());
        return prices;
    }


}
