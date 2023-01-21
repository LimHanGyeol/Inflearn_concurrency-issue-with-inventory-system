package com.tommy.stock.repository

import com.tommy.stock.domain.Stock
import org.springframework.data.jpa.repository.JpaRepository

interface StockRepository : JpaRepository<Stock, Long> {
}
