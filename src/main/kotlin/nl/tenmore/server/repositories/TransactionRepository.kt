package nl.tenmore.server.repositories

import nl.tenmore.server.entities.Transaction
import org.springframework.data.jpa.repository.JpaRepository

interface TransactionRepository: JpaRepository<Transaction, String>