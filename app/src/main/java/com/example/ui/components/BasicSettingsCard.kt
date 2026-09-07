package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronLeft
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Tune
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.RankPresets
import com.example.data.model.SalaryInputState
import com.example.ui.theme.HighDensityBorder
import com.example.ui.theme.HighDensityBorderSubtle
import com.example.ui.theme.HighDensityPrimary
import com.example.ui.theme.HighDensityPrimaryContainer
import com.example.ui.theme.HighDensityPrimaryDark
import com.example.ui.theme.HighDensityPrimaryLight
import com.example.ui.theme.HighDensitySecondaryContainer
import com.example.ui.theme.HighDensitySurface
import com.example.ui.theme.HighDensitySurfaceVariant
import com.example.ui.theme.HighDensityTextPrimary
import com.example.ui.theme.HighDensityTextSecondary
import com.example.ui.theme.HighDensityTextTertiary
import java.time.YearMonth
import java.time.format.DateTimeFormatter

@Composable
fun BasicSettingsCard(
    state: SalaryInputState,
    hourlyRate: Double,
    onNameChange: (String) -> Unit,
    onMonthChange: (String) -> Unit,
    onRankSelect: (Int) -> Unit,
    onBaseRateChange: (Double) -> Unit,
    onWorkDaysChange: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 4.dp),
        shape = RoundedCornerShape(24.dp),
        color = HighDensitySurface,
        border = androidx.compose.foundation.BorderStroke(1.dp, HighDensityBorder),
        tonalElevation = 2.dp,
        shadowElevation = 1.dp
    ) {
        Column(
            modifier = Modifier.padding(18.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            // Header
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(28.dp)
                        .background(HighDensitySecondaryContainer, shape = RoundedCornerShape(8.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Tune,
                        contentDescription = null,
                        tint = HighDensityPrimary,
                        modifier = Modifier.size(16.dp)
                    )
                }
                Text(
                    text = "БАЗОВІ НАЛАШТУВАННЯ",
                    color = HighDensityPrimaryDark,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.ExtraBold,
                    letterSpacing = 1.sp
                )
            }

            // Employee Name Input
            OutlinedTextField(
                value = state.employeeName,
                onValueChange = onNameChange,
                label = { Text("ПІБ працівника", color = HighDensityTextSecondary, fontSize = 12.sp) },
                placeholder = { Text("Прізвище Ім'я", color = HighDensityTextTertiary, fontSize = 13.sp) },
                leadingIcon = {
                    Icon(imageVector = Icons.Default.Person, contentDescription = null, tint = HighDensityPrimary)
                },
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("input_employee_name"),
                shape = RoundedCornerShape(16.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = HighDensitySurface,
                    unfocusedContainerColor = HighDensitySurface,
                    focusedBorderColor = HighDensityPrimary,
                    unfocusedBorderColor = HighDensityBorder,
                    focusedTextColor = HighDensityTextPrimary,
                    unfocusedTextColor = HighDensityTextPrimary,
                    cursorColor = HighDensityPrimary
                )
            )

            // Salary Month with prev/next buttons
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "Звітний місяць",
                        fontSize = 13.sp,
                        color = HighDensityTextPrimary,
                        fontWeight = FontWeight.SemiBold
                    )
                    Text(
                        text = "Для розрахунку робочих днів і субот",
                        fontSize = 11.sp,
                        color = HighDensityTextSecondary
                    )
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .background(HighDensitySurfaceVariant, shape = RoundedCornerShape(14.dp))
                        .border(1.dp, HighDensityBorder, shape = RoundedCornerShape(14.dp))
                        .padding(horizontal = 4.dp, vertical = 2.dp)
                ) {
                    IconButton(
                        onClick = {
                            try {
                                val current = YearMonth.parse(state.salaryMonth, DateTimeFormatter.ofPattern("yyyy-MM"))
                                val prev = current.minusMonths(1)
                                onMonthChange(prev.format(DateTimeFormatter.ofPattern("yyyy-MM")))
                            } catch (e: Exception) {
                                onMonthChange("2026-07")
                            }
                        },
                        modifier = Modifier.size(32.dp)
                    ) {
                        Icon(imageVector = Icons.Default.ChevronLeft, contentDescription = "Попередній місяць", tint = HighDensityPrimary)
                    }

                    Text(
                        text = state.salaryMonth,
                        color = HighDensityPrimaryDark,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily.Monospace,
                        modifier = Modifier.padding(horizontal = 6.dp)
                    )

                    IconButton(
                        onClick = {
                            try {
                                val current = YearMonth.parse(state.salaryMonth, DateTimeFormatter.ofPattern("yyyy-MM"))
                                val next = current.plusMonths(1)
                                onMonthChange(next.format(DateTimeFormatter.ofPattern("yyyy-MM")))
                            } catch (e: Exception) {
                                onMonthChange("2026-09")
                            }
                        },
                        modifier = Modifier.size(32.dp)
                    ) {
                        Icon(imageVector = Icons.Default.ChevronRight, contentDescription = "Наступний місяць", tint = HighDensityPrimary)
                    }
                }
            }

            // Rank Selector (1 to 10)
            Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Розряд (Тарифна сітка)",
                        fontSize = 13.sp,
                        color = HighDensityTextPrimary,
                        fontWeight = FontWeight.SemiBold
                    )
                    Text(
                        text = "Розряд ${state.rank} (коеф: ${state.coefficient})",
                        fontSize = 12.sp,
                        color = HighDensityPrimary,
                        fontWeight = FontWeight.Bold
                    )
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .horizontalScroll(rememberScrollState()),
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    for (rankNum in 1..10) {
                        val isSelected = state.rank == rankNum
                        val rankData = RankPresets.getRank(rankNum)
                        Box(
                            modifier = Modifier
                                .background(
                                    color = if (isSelected) HighDensityPrimary else HighDensitySurfaceVariant,
                                    shape = RoundedCornerShape(12.dp)
                                )
                                .border(
                                    width = 1.dp,
                                    color = if (isSelected) HighDensityPrimary else HighDensityBorder,
                                    shape = RoundedCornerShape(12.dp)
                                )
                                .clickable { onRankSelect(rankNum) }
                                .padding(horizontal = 12.dp, vertical = 8.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text(
                                    text = "Р-$rankNum",
                                    color = if (isSelected) Color.White else HighDensityTextPrimary,
                                    fontSize = 12.sp,
                                    fontWeight = if (isSelected) FontWeight.ExtraBold else FontWeight.Medium
                                )
                                Text(
                                    text = "${rankData.netto.toInt()}₴",
                                    color = if (isSelected) HighDensityPrimaryLight else HighDensityTextSecondary,
                                    fontSize = 9.sp,
                                    fontFamily = FontFamily.Monospace
                                )
                            }
                        }
                    }
                }
            }

            // Base Rate and Work Days
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                OutlinedTextField(
                    value = if (state.baseRate == 0.0) "" else if (state.baseRate % 1.0 == 0.0) state.baseRate.toInt().toString() else state.baseRate.toString(),
                    onValueChange = { input ->
                        val value = input.toDoubleOrNull() ?: 0.0
                        onBaseRateChange(value)
                    },
                    label = { Text("Ставка / розряд", color = HighDensityTextSecondary, fontSize = 11.sp) },
                    suffix = { Text("₴", color = HighDensityPrimary, fontSize = 12.sp, fontWeight = FontWeight.Bold) },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                    singleLine = true,
                    modifier = Modifier
                        .weight(1.2f)
                        .testTag("input_base_rate"),
                    shape = RoundedCornerShape(16.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = HighDensitySurface,
                        unfocusedContainerColor = HighDensitySurface,
                        focusedBorderColor = HighDensityPrimary,
                        unfocusedBorderColor = HighDensityBorder,
                        focusedTextColor = HighDensityTextPrimary,
                        unfocusedTextColor = HighDensityTextPrimary
                    )
                )

                OutlinedTextField(
                    value = if (state.workDays == 0) "" else state.workDays.toString(),
                    onValueChange = { input ->
                        val value = input.toIntOrNull() ?: 0
                        onWorkDaysChange(value)
                    },
                    label = { Text("Робочих днів", color = HighDensityTextSecondary, fontSize = 11.sp) },
                    suffix = { Text("дн", color = HighDensityPrimaryDark, fontSize = 12.sp, fontWeight = FontWeight.Bold) },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    singleLine = true,
                    modifier = Modifier
                        .weight(0.8f)
                        .testTag("input_work_days"),
                    shape = RoundedCornerShape(16.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = HighDensitySurface,
                        unfocusedContainerColor = HighDensitySurface,
                        focusedBorderColor = HighDensityPrimary,
                        unfocusedBorderColor = HighDensityBorder,
                        focusedTextColor = HighDensityTextPrimary,
                        unfocusedTextColor = HighDensityTextPrimary
                    )
                )
            }

            // Hourly rate calculated display badge
            Surface(
                shape = RoundedCornerShape(12.dp),
                color = HighDensityPrimaryContainer,
                border = androidx.compose.foundation.BorderStroke(1.dp, HighDensityPrimary.copy(alpha = 0.15f)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 14.dp, vertical = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Годинна ставка (${state.workDays} дн × 8 год):",
                        fontSize = 12.sp,
                        color = HighDensityPrimaryDark
                    )
                    Text(
                        text = String.format(java.util.Locale.US, "%.2f ₴/год", hourlyRate),
                        color = HighDensityPrimaryDark,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily.Monospace
                    )
                }
            }
        }
    }
}

