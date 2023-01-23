# Inflearn-concurrency-issue-with-inventory-system
## 재고 시스템으로 알아보는 동시성이슈 해결방법 - 최상용
---
### Race Condition
두개 이상의 프로세스가 공통 자원을 병행적으로 쓰기 또는 읽기 작업을 하며 발생하는 문제이다.

### Race Condition 해결방안

**Application Level**

- `Synchronized` 키워드를 사용하여 하나의 메서드가 실행중일때는 접근하지 못하도록 막을 수 있다.

**Database Lock (MySql)**

- Pessimistic Lock
    - 실제로 데이터에 Lock 을 걸어 정합성을 맞추는 방법
    - exclusive lock 을 걸게되면 다른 트랜잭션에서는 lock 이 해체되기전에는 가져갈 수 없다.
- Optimistic Lock
    - 버전을 이용하여 정합성을 맞출 수 있다.
        - 장점
            - 별도의 lock 을 잡지 않아 성능상 이점이 있다.
        - 단점
            - 동시성 문제가 발생했을시, 재시도와 같은 로직을 개발자가 직접 코드를 작성하여야 한다.
- Named Lock
    - 이름을 가진 matadata locking 방법
    - 이름을 가진 lock 을 획득한 후 해체될때까지 다른 세션은 이 lock 을 획득할 수 없도록한다.
    - 주의할점은 transaction 이 종료될 때 lock 이 자동으로 해체되지 않는다.

**Redis Distributed Lock**

- Lettuce
    - redis 의 setnx 명령어 활용
    - spin lock 방식
        - redis 에 지속적으로 lock 여부를 물어보고, lock 이 안걸렸을때 수행
          → 구현은 간단하지만, redis 에 부하를 줄 수 있다.
- Redisson
    - pub-sub 기반으로 Lock 구현 제공
        - 구현은 다소 복잡하지만, redis 의 부하를 줄일 수 있다.

보통, 재시도가 필요하지 않은 경우에는 lettuce 를 사용하고, 재시도가 필요한 경우에는 Redisson 을 사용한다.
