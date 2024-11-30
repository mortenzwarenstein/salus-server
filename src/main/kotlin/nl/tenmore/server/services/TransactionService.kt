package nl.tenmore.server.services

import nl.tenmore.server.entities.Transaction
import nl.tenmore.server.repositories.TransactionRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class TransactionService(
    @Autowired private val transactionRepository: TransactionRepository
) {
    fun getAllTransactions(): List<Transaction> {
        return transactionRepository.findAll();
    }


}