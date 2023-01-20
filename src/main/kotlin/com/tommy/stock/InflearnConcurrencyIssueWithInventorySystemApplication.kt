package com.tommy.stock

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class InflearnConcurrencyIssueWithInventorySystemApplication

fun main(args: Array<String>) {
    runApplication<InflearnConcurrencyIssueWithInventorySystemApplication>(*args)
}
