package com.tommy.stock.service

import com.tommy.stock.repository.StockRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional

@Service
class StockService(
    private val stockRepository: StockRepository,
) {

    /**
     * Synchronized 는 각 프로세스 안에서만 보장되기 때문에 여러 스레드에서 동시에 데이터에 접근하게 되면 Race Condition 이 발생하게 된다.
     * 운영 서비스는 보통 2대 이상의 서버를 사용하기 때문에 Synchronized 를 사용하지 않는다.
     *
     * @Transactional(propagation = Propagation.REQUIRES_NEW)
     * NamedLock 구현 시 별도의 트랜잭션을 발행하기 위해 전파레벨을 설정한다.
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Synchronized
    fun decrease(id: Long, quantity: Long) {
        val stock = stockRepository.findByIdOrNull(id) ?: throw RuntimeException("not found stock. stock id is $id")
        stock.decrease(quantity)
    }
}
