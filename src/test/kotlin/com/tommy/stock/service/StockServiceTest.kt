package com.tommy.stock.service

import com.tommy.stock.domain.Stock
import com.tommy.stock.repository.StockRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.repository.findByIdOrNull

@SpringBootTest
class StockServiceTest @Autowired constructor(
    private val stockService: StockService,
    private val stockRepository: StockRepository,
) {

    @BeforeEach
    fun setUp() {
        stockRepository.deleteAll()
    }

    @Test
    fun decrease() {
        val stock = Stock(1L, 100L)
        stockRepository.save(stock)

        stockService.decrease(1L, 1L)

        val actual = stockRepository.findByIdOrNull(1L) ?: throw RuntimeException()

        assertThat(actual.quantity).isEqualTo(99L)
    }
}
