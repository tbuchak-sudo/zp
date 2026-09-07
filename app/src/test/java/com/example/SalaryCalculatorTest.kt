package com.example

import com.example.data.calculator.SalaryCalculator
import com.example.data.model.SalaryInputState
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class SalaryCalculatorTest {

    @Test
    fun testDefaultRank8Calculation() {
        val state = SalaryInputState(
            rank = 8,
            baseRate = 14206.5,
            workDays = 23,
            shift1Days = 9,
            satShift1Days = 2,
            shift2Days = 14,
            satShift2Days = 2,
            shift3Days = 0,
            experienceBonusBrutto = 1402.0,
            attendanceBonus = true,
            coefficient = 1.3,
            qualityPremiumBase = 7068.0,
            advancePayment = 9800.0
        )

        val result = SalaryCalculator.calculate(state)
        assertTrue(result.hourlyRate > 0)
        assertTrue(result.totalAccrued > 0)
        assertTrue(result.totalToPay > 0)
        assertTrue(result.emoji.isNotEmpty())
    }

    @Test
    fun testTimesheetParser() {
        val raw = "Тест Працівник\t8,0\t8,0\t8,1\t8,1\t16,1\tВ"
        val parsed = SalaryCalculator.parseTimesheet(raw, "2026-08")
        assertTrue(parsed.shift1Days >= 1 || parsed.satShift1Days >= 1)
        assertTrue(parsed.hasVacation)
    }
}
