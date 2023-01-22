package com.tommy.stock.facade

import com.tommy.stock.domain.Stock
import com.tommy.stock.repository.StockRepository
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.repository.findByIdOrNull
import java.util.concurrent.CountDownLatch
import java.util.concurrent.Executors

@SpringBootTest
internal class OptimisticLockStockFacadeTest @Autowired constructor(
    private val optimisticLockStockFacade: OptimisticLockStockFacade,
    private val stockRepository: StockRepository,
) {

    @BeforeEach
    fun setUp() {
        stockRepository.deleteAll()
    }

    @Test
    @DisplayName("동시에 100개의 요청을 보내 재고 개수를 감소한다.")
    fun `sut should stock quantity decrease when 100 request is given`() {
        // Arrange
        val stock = Stock(1L, 100L)
        stockRepository.save(stock)

        val threadCount = 100
        val executorService = Executors.newFixedThreadPool(32)
        val latch = CountDownLatch(threadCount) // 다른 스레드에서 실행되는 작업이 종료될 때까지 대기

        // Act
        for (i in 0 until threadCount) {
            executorService.submit {
                try {
                    optimisticLockStockFacade.decrease(1L, 1L)
                } finally {
                    latch.countDown()
                }
            }
        }

        latch.await()

        // Assert
        val actual = stockRepository.findByIdOrNull(1L) ?: throw RuntimeException()

        Assertions.assertThat(actual.quantity).isEqualTo(0)
    }
}

