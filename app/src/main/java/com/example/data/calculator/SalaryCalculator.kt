package com.example.data.calculator

import com.example.data.model.BreakdownRow
import com.example.data.model.SalaryCalculationResult
import com.example.data.model.SalaryInputState
import com.example.data.model.TimesheetParseResult
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.DateTimeFormatter

object SalaryCalculator {

    fun calculate(state: SalaryInputState): SalaryCalculationResult {
        val workDays = if (state.workDays > 0) state.workDays else 1
        val baseRate = state.baseRate
        val hourlyRate = baseRate / (workDays * 8.0)

        val expBonus = state.experienceBonusBrutto * 0.77
        val attBonus = if (state.attendanceBonus) 1578.0 else 0.0
        val indexation = state.indexationBonusBrutto * 0.77

        val totalWeekdayShifts = state.shift1Days + state.shift2Days + state.shift3Days
        val totalSaturdays = state.satShift1Days + state.satShift2Days

        val actualQualityPremium = (state.qualityPremiumBase / workDays.toDouble()) * totalWeekdayShifts * state.coefficient
        val satPremium = (state.qualityPremiumBase / workDays.toDouble()) * totalSaturdays
        val overtimePremium = (state.qualityPremiumBase / (workDays * 8.0)) * state.overtimeHours

        val hoursShift1 = totalWeekdayShifts * 8.0
        val hoursShift2 = state.shift2Days * 3.5
        val hoursShift3 = (state.shift2Days * 2.0) + (state.shift3Days * 6.5)
        val hoursSatShift1 = state.satShift1Days * 8.0
        val hoursSatShift2 = state.satShift2Days * 8.0
        val hoursOvertime = state.overtimeHours

        val payShift1 = hoursShift1 * hourlyRate
        val payShift2 = hoursShift2 * 0.2 * hourlyRate
        val payShift3 = hoursShift3 * 0.4 * hourlyRate
        val paySatShift1 = hoursSatShift1 * hourlyRate * 2.0
        val paySatShift2 = hoursSatShift2 * hourlyRate * 2.0
        val payOvertime = hoursOvertime * hourlyRate * 2.0

        val totalAccrued = payShift1 + payShift2 + payShift3 + paySatShift1 + paySatShift2 +
                payOvertime + actualQualityPremium + satPremium + overtimePremium +
                expBonus + attBonus + indexation + state.extraPayments +
                state.vacationPayment + state.regularPaymentDeduction

        val totalAccruedBrutto = totalAccrued / 0.77
        val totalToPay = totalAccrued - state.advancePayment - state.vacationPayment - state.regularPaymentDeduction

        val emoji = when {
            totalAccrued < 25000.0 -> "😢"
            totalAccrued < 35000.0 -> "😊"
            totalAccrued <= 50000.0 -> "💵🔥"
            else -> "🤩🎉"
        }

        val militaryTax = totalAccruedBrutto * 0.05
        val incomeTax = totalAccruedBrutto * 0.18
        val totalDeductions = militaryTax + incomeTax
        val totalPaid = state.advancePayment + state.regularPaymentDeduction
        val totalWorkedHours = hoursShift1 + hoursSatShift1 + hoursSatShift2 + hoursOvertime

        val rows = listOf(
            BreakdownRow("Премія (прод. якість)", "-", actualQualityPremium, actualQualityPremium / 0.77),
            BreakdownRow("Оклад по годинах (будні)", formatHours(hoursShift1), payShift1, payShift1 / 0.77),
            BreakdownRow("Вечірні години (+20%)", formatHours(hoursShift2), payShift2, payShift2 / 0.77),
            BreakdownRow("Нічні години (+40%)", formatHours(hoursShift3), payShift3, payShift3 / 0.77),
            BreakdownRow("Субота 1 зміна (x2)", formatHours(hoursSatShift1), paySatShift1, paySatShift1 / 0.77),
            BreakdownRow("Субота 2 зміна (x2)", formatHours(hoursSatShift2), paySatShift2, paySatShift2 / 0.77),
            BreakdownRow("Понаднормові (x2)", formatHours(hoursOvertime), payOvertime, payOvertime / 0.77),
            BreakdownRow("Премія за суботи", "-", satPremium, satPremium / 0.77),
            BreakdownRow("Премія за понаднорм.", "-", overtimePremium, overtimePremium / 0.77),
            BreakdownRow("Доплата за стаж", "-", expBonus, state.experienceBonusBrutto),
            BreakdownRow("Доплата за відвідування", "-", attBonus, attBonus / 0.77),
            BreakdownRow("Індексація", "-", indexation, state.indexationBonusBrutto),
            BreakdownRow("Додаткові доплати", "-", state.extraPayments, state.extraPayments / 0.77),
            BreakdownRow("Чергова виплата", "-", state.regularPaymentDeduction, state.regularPaymentDeduction / 0.77)
        )

        return SalaryCalculationResult(
            hourlyRate = hourlyRate,
            hoursShift1 = hoursShift1,
            payShift1 = payShift1,
            hoursShift2 = hoursShift2,
            payShift2 = payShift2,
            hoursShift3 = hoursShift3,
            payShift3 = payShift3,
            hoursSatShift1 = hoursSatShift1,
            paySatShift1 = paySatShift1,
            hoursSatShift2 = hoursSatShift2,
            paySatShift2 = paySatShift2,
            hoursOvertime = hoursOvertime,
            payOvertime = payOvertime,
            actualQualityPremium = actualQualityPremium,
            satPremium = satPremium,
            overtimePremium = overtimePremium,
            expBonus = expBonus,
            attBonus = attBonus,
            indexation = indexation,
            extraPayments = state.extraPayments,
            vacationPayment = state.vacationPayment,
            regularPaymentDeduction = state.regularPaymentDeduction,
            totalAccrued = totalAccrued,
            totalAccruedBrutto = totalAccruedBrutto,
            advancePayment = state.advancePayment,
            totalToPay = totalToPay,
            emoji = emoji,
            militaryTax = militaryTax,
            incomeTax = incomeTax,
            totalDeductions = totalDeductions,
            totalPaid = totalPaid,
            totalWorkedHours = totalWorkedHours,
            breakdownRows = rows
        )
    }

    private fun formatHours(hours: Double): String {
        return if (hours % 1.0 == 0.0) {
            hours.toInt().toString()
        } else {
            String.format(java.util.Locale.US, "%.1f", hours)
        }
    }

    fun parseTimesheet(rawText: String, salaryMonth: String): TimesheetParseResult {
        val trimmed = rawText.trim()
        if (trimmed.isEmpty()) {
            return TimesheetParseResult(
                employeeName = null,
                shift1Days = 0,
                shift2Days = 0,
                satShift1Days = 0,
                satShift2Days = 0,
                calculatedExtraPayments = 0.0,
                hasAbsence = false,
                hasVacation = false,
                isCalendarAligned = false,
                summaryText = "Порожні дані для імпорту"
            )
        }

        // Parse year and month
        val ym = try {
            YearMonth.parse(salaryMonth, DateTimeFormatter.ofPattern("yyyy-MM"))
        } catch (e: Exception) {
            YearMonth.now()
        }

        val year = ym.year
        val month = ym.monthValue

        var cells = trimmed.split("\t")
        var useCalendar = true

        if (cells.size < 20) {
            cells = trimmed.split(Regex("[\\s\\t]+"))
            useCalendar = false
        }

        var startIndex = -1
        val nameParts = mutableListOf<String>()
        val absenceCodes = setOf("нз", "ДВ", "тн", "пр", "на")

        for (i in cells.indices) {
            val v = cells[i].trim()
            if (v.matches(Regex("^(\\d+[.,]\\d+|[А-Яа-яІіЇїЄєҐґ]+)$"))) {
                if (v.matches(Regex("^\\d+[.,]\\d+$")) || absenceCodes.contains(v)) {
                    startIndex = i
                    break
                }
            }
            if (v.isNotEmpty() && !v.matches(Regex("^\\d+$"))) {
                nameParts.add(v)
            }
        }

        val employeeName = if (nameParts.isNotEmpty()) nameParts.joinToString(" ") else null

        if (startIndex == -1) {
            startIndex = minOf(cells.size, 5)
        }

        var shift1 = 0
        var shift2 = 0
        var satShift1 = 0
        var satShift2 = 0
        var calculatedExtraPayments = 0.0
        var hasAbsence = false
        var hasVacation = false

        if (useCalendar) {
            for (day in 1..31) {
                val cellIndex = startIndex + (day - 1)
                if (cellIndex >= cells.size) break

                val c = cells[cellIndex].trim().replace('.', ',')
                if (c.isEmpty()) continue

                val date = try {
                    LocalDate.of(year, month, day)
                } catch (e: Exception) {
                    continue
                }

                val dayOfWeek = date.dayOfWeek
                val isSaturday = (dayOfWeek == DayOfWeek.SATURDAY)
                val isSunday = (dayOfWeek == DayOfWeek.SUNDAY)

                if (absenceCodes.contains(c)) hasAbsence = true
                if (c == "В" || c == "в") hasVacation = true

                when (c) {
                    "8,0", "8" -> {
                        if (isSaturday) {
                            satShift1++
                        } else if (!isSunday) {
                            shift1++
                        }
                    }
                    "8,1" -> {
                        if (isSaturday) {
                            satShift2++
                            calculatedExtraPayments += 2000.0
                        } else if (!isSunday) {
                            shift2++
                        }
                    }
                    "16,1", "16" -> {
                        if (isSaturday) {
                            satShift1++
                            satShift2++
                            calculatedExtraPayments += 4000.0
                        } else if (!isSunday) {
                            shift1++
                            satShift1++
                            calculatedExtraPayments += 2000.0
                        }
                    }
                }
            }
        } else {
            cells.forEach { cell ->
                val c = cell.trim().replace('.', ',')
                if (absenceCodes.contains(c)) hasAbsence = true
                if (c == "В" || c == "в") hasVacation = true

                when (c) {
                    "8,0", "8" -> shift1++
                    "8,1" -> shift2++
                    "16,1", "16" -> {
                        shift1++
                        satShift1++
                        calculatedExtraPayments += 2000.0
                    }
                }
            }
        }

        val summary = buildString {
            append("1 зміна (будні): $shift1 дн, ")
            append("2 зміна (будні): $shift2 дн, ")
            append("Суботи 1зм: $satShift1, 2зм: $satShift2. ")
            if (calculatedExtraPayments > 0) {
                append("Доплати: ${calculatedExtraPayments.toInt()} ₴. ")
            }
            if (hasAbsence) {
                append("Відвідування: не зараховано (є пропуски). ")
            } else {
                append("Відвідування: зараховано (+1578 ₴). ")
            }
            if (hasVacation) {
                append("Виявлено дні відпустки (В).")
            }
        }

        return TimesheetParseResult(
            employeeName = employeeName,
            shift1Days = shift1,
            shift2Days = shift2,
            satShift1Days = satShift1,
            satShift2Days = satShift2,
            calculatedExtraPayments = calculatedExtraPayments,
            hasAbsence = hasAbsence,
            hasVacation = hasVacation,
            isCalendarAligned = useCalendar,
            summaryText = summary
        )
    }
}
