package com.example.data.model

data class RankInfo(
    val rank: Int,
    val netto: Double,
    val coef: Double,
    val advance: Double
)

object RankPresets {
    val ranks: List<RankInfo> = listOf(
        RankInfo(1, 10510.5, 1.0, 7700.0),
        RankInfo(2, 10895.5, 1.0, 7800.0),
        RankInfo(3, 11396.0, 1.0, 8100.0),
        RankInfo(4, 11858.0, 1.0, 8400.0),
        RankInfo(5, 12358.5, 1.0, 8500.0),
        RankInfo(6, 12820.5, 1.1, 8800.0),
        RankInfo(7, 13552.0, 1.2, 9100.0),
        RankInfo(8, 14206.5, 1.3, 9800.0),
        RankInfo(9, 14938.0, 1.3, 10500.0),
        RankInfo(10, 15554.0, 1.4, 11200.0)
    )

    fun getRank(rankNumber: Int): RankInfo {
        return ranks.find { it.rank == rankNumber } ?: ranks[7] // Default Rank 8
    }
}

data class SalaryInputState(
    val employeeName: String = "",
    val salaryMonth: String = "2026-08",
    val rank: Int = 8,
    val baseRate: Double = 14206.5,
    val workDays: Int = 23,
    val experienceBonusBrutto: Double = 1402.0,
    val attendanceBonus: Boolean = true,
    val indexationBonusBrutto: Double = 0.0,
    val coefficient: Double = 1.3,
    val qualityPremiumBase: Double = 7068.0,
    val extraPayments: Double = 0.0,
    val vacationPayment: Double = 0.0,
    val regularPaymentDeduction: Double = 0.0,
    val shift1Days: Int = 9,
    val satShift1Days: Int = 2,
    val shift2Days: Int = 14,
    val satShift2Days: Int = 2,
    val shift3Days: Int = 0,
    val overtimeHours: Double = 0.0,
    val advancePayment: Double = 9800.0
)

data class BreakdownRow(
    val title: String,
    val hoursText: String,
    val netto: Double,
    val brutto: Double,
    val isHeader: Boolean = false,
    val isTotal: Boolean = false,
    val highlightNetto: Boolean = true
)

data class SalaryCalculationResult(
    val hourlyRate: Double = 0.0,
    val hoursShift1: Double = 0.0,
    val payShift1: Double = 0.0,
    val hoursShift2: Double = 0.0,
    val payShift2: Double = 0.0,
    val hoursShift3: Double = 0.0,
    val payShift3: Double = 0.0,
    val hoursSatShift1: Double = 0.0,
    val paySatShift1: Double = 0.0,
    val hoursSatShift2: Double = 0.0,
    val paySatShift2: Double = 0.0,
    val hoursOvertime: Double = 0.0,
    val payOvertime: Double = 0.0,
    val actualQualityPremium: Double = 0.0,
    val satPremium: Double = 0.0,
    val overtimePremium: Double = 0.0,
    val expBonus: Double = 0.0,
    val attBonus: Double = 0.0,
    val indexation: Double = 0.0,
    val extraPayments: Double = 0.0,
    val vacationPayment: Double = 0.0,
    val regularPaymentDeduction: Double = 0.0,
    val totalAccrued: Double = 0.0,
    val totalAccruedBrutto: Double = 0.0,
    val advancePayment: Double = 0.0,
    val totalToPay: Double = 0.0,
    val emoji: String = "😊",
    val militaryTax: Double = 0.0,
    val incomeTax: Double = 0.0,
    val totalDeductions: Double = 0.0,
    val totalPaid: Double = 0.0,
    val totalWorkedHours: Double = 0.0,
    val breakdownRows: List<BreakdownRow> = emptyList()
)

data class TimesheetParseResult(
    val employeeName: String?,
    val shift1Days: Int,
    val shift2Days: Int,
    val satShift1Days: Int,
    val satShift2Days: Int,
    val calculatedExtraPayments: Double,
    val hasAbsence: Boolean,
    val hasVacation: Boolean,
    val isCalendarAligned: Boolean,
    val summaryText: String
)
