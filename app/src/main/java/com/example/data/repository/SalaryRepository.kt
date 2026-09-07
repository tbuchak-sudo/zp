package com.example.data.repository

import android.content.Context
import com.example.data.local.SalaryRecord
import com.example.data.local.SalaryRecordDao
import com.example.data.model.SalaryInputState
import kotlinx.coroutines.flow.Flow
import org.json.JSONObject

class SalaryRepository(
    private val dao: SalaryRecordDao,
    private val context: Context
) {
    val allRecords: Flow<List<SalaryRecord>> = dao.getAllRecords()

    suspend fun saveRecord(state: SalaryInputState, totalAccrued: Double, totalAccruedBrutto: Double, totalToPay: Double, workedHours: Double): Long {
        val jsonString = stateToJson(state)
        val record = SalaryRecord(
            month = state.salaryMonth,
            employeeName = state.employeeName.ifBlank { "Без імені" },
            rank = state.rank,
            baseRate = state.baseRate,
            workDays = state.workDays,
            totalAccrued = totalAccrued,
            totalAccruedBrutto = totalAccruedBrutto,
            totalToPay = totalToPay,
            advancePayment = state.advancePayment,
            workedHours = workedHours,
            jsonState = jsonString
        )
        return dao.insertRecord(record)
    }

    suspend fun deleteRecord(id: Long) {
        dao.deleteRecordById(id)
    }

    fun saveLastStateToPrefs(state: SalaryInputState) {
        val prefs = context.getSharedPreferences("bader_salary_prefs", Context.MODE_PRIVATE)
        prefs.edit().putString("last_state_json", stateToJson(state)).apply()
    }

    fun loadLastStateFromPrefs(): SalaryInputState? {
        val prefs = context.getSharedPreferences("bader_salary_prefs", Context.MODE_PRIVATE)
        val json = prefs.getString("last_state_json", null) ?: return null
        return jsonToState(json)
    }

    companion object {
        fun stateToJson(state: SalaryInputState): String {
            val obj = JSONObject()
            obj.put("employeeName", state.employeeName)
            obj.put("salaryMonth", state.salaryMonth)
            obj.put("rank", state.rank)
            obj.put("baseRate", state.baseRate)
            obj.put("workDays", state.workDays)
            obj.put("experienceBonusBrutto", state.experienceBonusBrutto)
            obj.put("attendanceBonus", state.attendanceBonus)
            obj.put("indexationBonusBrutto", state.indexationBonusBrutto)
            obj.put("coefficient", state.coefficient)
            obj.put("qualityPremiumBase", state.qualityPremiumBase)
            obj.put("extraPayments", state.extraPayments)
            obj.put("vacationPayment", state.vacationPayment)
            obj.put("regularPaymentDeduction", state.regularPaymentDeduction)
            obj.put("shift1Days", state.shift1Days)
            obj.put("satShift1Days", state.satShift1Days)
            obj.put("shift2Days", state.shift2Days)
            obj.put("satShift2Days", state.satShift2Days)
            obj.put("shift3Days", state.shift3Days)
            obj.put("overtimeHours", state.overtimeHours)
            obj.put("advancePayment", state.advancePayment)
            return obj.toString()
        }

        fun jsonToState(jsonStr: String): SalaryInputState {
            val obj = JSONObject(jsonStr)
            return SalaryInputState(
                employeeName = obj.optString("employeeName", ""),
                salaryMonth = obj.optString("salaryMonth", "2026-08"),
                rank = obj.optInt("rank", 8),
                baseRate = obj.optDouble("baseRate", 14206.5),
                workDays = obj.optInt("workDays", 23),
                experienceBonusBrutto = obj.optDouble("experienceBonusBrutto", 1402.0),
                attendanceBonus = obj.optBoolean("attendanceBonus", true),
                indexationBonusBrutto = obj.optDouble("indexationBonusBrutto", 0.0),
                coefficient = obj.optDouble("coefficient", 1.3),
                qualityPremiumBase = obj.optDouble("qualityPremiumBase", 7068.0),
                extraPayments = obj.optDouble("extraPayments", 0.0),
                vacationPayment = obj.optDouble("vacationPayment", 0.0),
                regularPaymentDeduction = obj.optDouble("regularPaymentDeduction", 0.0),
                shift1Days = obj.optInt("shift1Days", 9),
                satShift1Days = obj.optInt("satShift1Days", 2),
                shift2Days = obj.optInt("shift2Days", 14),
                satShift2Days = obj.optInt("satShift2Days", 2),
                shift3Days = obj.optInt("shift3Days", 0),
                overtimeHours = obj.optDouble("overtimeHours", 0.0),
                advancePayment = obj.optDouble("advancePayment", 9800.0)
            )
        }
    }
}
