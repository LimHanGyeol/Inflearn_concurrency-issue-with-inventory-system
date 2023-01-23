package com.tommy.stock.facade

import com.tommy.stock.repository.LockRepository
import com.tommy.stock.service.StockService
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class NamedLockStockFacade(
    private val stockService: StockService,
    private val lockRepository: LockRepository,
) {

    @Transactional
    fun decrease(id: Long, quantity: Long) {
        val lockKey = id.toString()

        try {
            lockRepository.getLock(lockKey)
            stockService.decrease(id, quantity)
        } finally {
            lockRepository.releaseLock(lockKey)
        }
    }
}
