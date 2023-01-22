package com.tommy.stock.service

import com.tommy.stock.repository.StockRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class StockService(
    private val stockRepository: StockRepository,
) {

    @Transactional
    fun decrease(id: Long, quantity: Long) {
        val stock = stockRepository.findByIdOrNull(id) ?: throw RuntimeException("not found stock. stock id is $id")
        stock.decrease(quantity)
    }
}
