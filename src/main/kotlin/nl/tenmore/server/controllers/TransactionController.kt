package nl.tenmore.server.controllers

import nl.tenmore.server.entities.Transaction
import nl.tenmore.server.services.TransactionService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/transaction")
class TransactionController(
    @Autowired private val transactionService: TransactionService
) {

    @GetMapping
    fun getAll(): ResponseEntity<List<Transaction>> {
        return ResponseEntity.ok(transactionService.getAllTransactions())
    }
}