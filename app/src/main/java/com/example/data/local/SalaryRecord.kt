package com.example.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "salary_records")
data class SalaryRecord(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val month: String,
    val employeeName: String,
    val rank: Int,
    val baseRate: Double,
    val workDays: Int,
    val totalAccrued: Double,
    val totalAccruedBrutto: Double,
    val totalToPay: Double,
    val advancePayment: Double,
    val workedHours: Double,
    val jsonState: String,
    val createdAt: Long = System.currentTimeMillis()
)
