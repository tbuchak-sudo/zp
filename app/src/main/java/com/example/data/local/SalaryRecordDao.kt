package com.example.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface SalaryRecordDao {

    @Query("SELECT * FROM salary_records ORDER BY createdAt DESC")
    fun getAllRecords(): Flow<List<SalaryRecord>>

    @Query("SELECT * FROM salary_records WHERE id = :id LIMIT 1")
    suspend fun getRecordById(id: Long): SalaryRecord?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecord(record: SalaryRecord): Long

    @Query("DELETE FROM salary_records WHERE id = :id")
    suspend fun deleteRecordById(id: Long)

    @Query("DELETE FROM salary_records")
    suspend fun clearAll()
}
