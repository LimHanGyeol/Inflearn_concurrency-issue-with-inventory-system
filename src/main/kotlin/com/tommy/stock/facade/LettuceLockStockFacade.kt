package com.tommy.stock.facade

import com.tommy.stock.repository.RedisLockRepository
import com.tommy.stock.service.StockService
import org.springframework.stereotype.Component
import java.lang.Thread.sleep

@Component
class LettuceLockStockFacade(
    private val stockService: StockService,
    private val redisLockRepository: RedisLockRepository,
) {

    fun decrease(key: Long, quantity: Long) {
        while (!redisLockRepository.lock(key)) {
            sleep(100)
        }

        try {
            stockService.decrease(key, quantity)
        } finally {
            redisLockRepository.unlock(key)
        }
    }
}
