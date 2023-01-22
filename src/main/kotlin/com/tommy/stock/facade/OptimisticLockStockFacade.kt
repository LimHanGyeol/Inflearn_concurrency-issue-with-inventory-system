package com.tommy.stock.facade

import com.tommy.stock.service.OptimisticLockStockService
import org.springframework.stereotype.Service
import java.lang.Thread.sleep

@Service
class OptimisticLockStockFacade(
    private val optimisticLockStockService: OptimisticLockStockService,
) {

    fun decrease(id: Long, quantity: Long) {
        while(true) {
            try {
                optimisticLockStockService.decrease(id, quantity)

                break
            } catch (e: java.lang.Exception) {
                sleep(50)
            }
        }
    }
}
