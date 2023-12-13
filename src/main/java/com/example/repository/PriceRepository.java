package com.example.repository;

import com.example.model.Price;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

public interface PriceRepository extends JpaRepository<Price, Long> {

    List<Price> findByBrandIdAndProductIdAndStartDate(
            Long brandId, Long productId, LocalDateTime startDate);
}