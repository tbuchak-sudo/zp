package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CardGiftcard
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.RadioButtonUnchecked
import androidx.compose.material3.Icon
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
import com.example.data.model.SalaryInputState
import com.example.ui.theme.HighDensityAmberContainer
import com.example.ui.theme.HighDensityAmberText
import com.example.ui.theme.HighDensityBorder
import com.example.ui.theme.HighDensityBorderSubtle
import com.example.ui.theme.HighDensityEmeraldContainer
import com.example.ui.theme.HighDensityEmeraldText
import com.example.ui.theme.HighDensityPrimary
import com.example.ui.theme.HighDensityPrimaryContainer
import com.example.ui.theme.HighDensityPrimaryDark
import com.example.ui.theme.HighDensityRoseContainer
import com.example.ui.theme.HighDensityRoseText
import com.example.ui.theme.HighDensitySecondaryContainer
import com.example.ui.theme.HighDensitySurface
import com.example.ui.theme.HighDensitySurfaceVariant
import com.example.ui.theme.HighDensityTextPrimary
import com.example.ui.theme.HighDensityTextSecondary
import com.example.ui.theme.HighDensityTextTertiary

@Composable
fun BonusesAndDeductionsCard(
    state: SalaryInputState,
    onExpBonusChange: (Double) -> Unit,
    onAttendanceToggle: (Boolean) -> Unit,
    onIndexationChange: (Double) -> Unit,
    onCoefChange: (Double) -> Unit,
    onQualityBaseChange: (Double) -> Unit,
    onExtraPaymentsChange: (Double) -> Unit,
    onVacationChange: (Double) -> Unit,
    onRegularDeductionChange: (Double) -> Unit,
    onAdvanceChange: (Double) -> Unit,
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
            verticalArrangement = Arrangement.spacedBy(12.dp)
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
                        imageVector = Icons.Default.CardGiftcard,
                        contentDescription = null,
                        tint = HighDensityPrimary,
                        modifier = Modifier.size(16.dp)
                    )
                }
                Text(
                    text = "ПРЕМІЇ ТА НАДБАВКИ",
                    color = HighDensityPrimaryDark,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.ExtraBold,
                    letterSpacing = 1.sp
                )
            }

            // Row 1: Seniority (Стаж Брутто) & Attendance Bonus
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                OutlinedTextField(
                    value = if (state.experienceBonusBrutto == 0.0) "0" else if (state.experienceBonusBrutto % 1.0 == 0.0) state.experienceBonusBrutto.toInt().toString() else state.experienceBonusBrutto.toString(),
                    onValueChange = { input ->
                        val v = input.toDoubleOrNull() ?: 0.0
                        onExpBonusChange(v)
                    },
                    label = { Text("Стаж (Брутто)", color = HighDensityTextSecondary, fontSize = 11.sp) },
                    suffix = { Text("₴", color = HighDensityPrimary, fontSize = 11.sp, fontWeight = FontWeight.Bold) },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                    singleLine = true,
                    modifier = Modifier
                        .weight(1f)
                        .testTag("input_exp_bonus"),
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

                // Attendance bonus toggle card
                Surface(
                    shape = RoundedCornerShape(16.dp),
                    color = if (state.attendanceBonus) HighDensityEmeraldContainer else HighDensitySurfaceVariant,
                    border = androidx.compose.foundation.BorderStroke(
                        1.dp,
                        if (state.attendanceBonus) HighDensityEmeraldText.copy(alpha = 0.3f) else HighDensityBorder
                    ),
                    modifier = Modifier
                        .weight(1f)
                        .height(56.dp)
                        .clickable { onAttendanceToggle(!state.attendanceBonus) }
                        .testTag("toggle_attendance_bonus")
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 12.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column {
                            Text(
                                text = "Відвідування",
                                color = if (state.attendanceBonus) HighDensityEmeraldText else HighDensityTextSecondary,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                            Text(
                                text = "+1578 ₴",
                                color = if (state.attendanceBonus) HighDensityEmeraldText else HighDensityTextPrimary,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                fontFamily = FontFamily.Monospace
                            )
                        }

                        Icon(
                            imageVector = if (state.attendanceBonus) Icons.Default.CheckCircle else Icons.Default.RadioButtonUnchecked,
                            contentDescription = null,
                            tint = if (state.attendanceBonus) HighDensityEmeraldText else HighDensityTextSecondary,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }
            }

            // Row 2: Indexation (Індексація Брутто)
            OutlinedTextField(
                value = if (state.indexationBonusBrutto == 0.0) "0" else if (state.indexationBonusBrutto % 1.0 == 0.0) state.indexationBonusBrutto.toInt().toString() else state.indexationBonusBrutto.toString(),
                onValueChange = { input ->
                    val v = input.toDoubleOrNull() ?: 0.0
                    onIndexationChange(v)
                },
                label = { Text("Індексація (Брутто)", color = HighDensityTextSecondary, fontSize = 11.sp) },
                suffix = { Text("₴", color = HighDensityPrimary, fontSize = 11.sp, fontWeight = FontWeight.Bold) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("input_indexation"),
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

            // Row 3: Quality Premium Coef & Base
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                OutlinedTextField(
                    value = if (state.coefficient == 0.0) "1.0" else state.coefficient.toString(),
                    onValueChange = { input ->
                        val v = input.toDoubleOrNull() ?: 1.0
                        onCoefChange(v)
                    },
                    label = { Text("Коеф. премії", color = HighDensityTextSecondary, fontSize = 11.sp) },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                    singleLine = true,
                    modifier = Modifier
                        .weight(0.9f)
                        .testTag("input_premium_coef"),
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
                    value = if (state.qualityPremiumBase == 0.0) "0" else if (state.qualityPremiumBase % 1.0 == 0.0) state.qualityPremiumBase.toInt().toString() else state.qualityPremiumBase.toString(),
                    onValueChange = { input ->
                        val v = input.toDoubleOrNull() ?: 0.0
                        onQualityBaseChange(v)
                    },
                    label = { Text("База премії", color = HighDensityTextSecondary, fontSize = 11.sp) },
                    suffix = { Text("₴", color = HighDensityPrimary, fontSize = 11.sp, fontWeight = FontWeight.Bold) },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                    singleLine = true,
                    modifier = Modifier
                        .weight(1.1f)
                        .testTag("input_quality_base"),
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

            // Row 4: Extra Payments & Vacation
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                OutlinedTextField(
                    value = if (state.extraPayments == 0.0) "0" else if (state.extraPayments % 1.0 == 0.0) state.extraPayments.toInt().toString() else state.extraPayments.toString(),
                    onValueChange = { input ->
                        val v = input.toDoubleOrNull() ?: 0.0
                        onExtraPaymentsChange(v)
                    },
                    label = { Text("Додаткові доплати", color = HighDensityTextSecondary, fontSize = 11.sp) },
                    suffix = { Text("₴", color = HighDensityPrimary, fontSize = 11.sp, fontWeight = FontWeight.Bold) },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                    singleLine = true,
                    modifier = Modifier
                        .weight(1f)
                        .testTag("input_extra_payments"),
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
                    value = if (state.vacationPayment == 0.0) "0" else if (state.vacationPayment % 1.0 == 0.0) state.vacationPayment.toInt().toString() else state.vacationPayment.toString(),
                    onValueChange = { input ->
                        val v = input.toDoubleOrNull() ?: 0.0
                        onVacationChange(v)
                    },
                    label = { Text("Відпустка", color = HighDensityTextSecondary, fontSize = 11.sp) },
                    suffix = { Text("₴", color = HighDensityEmeraldText, fontSize = 11.sp, fontWeight = FontWeight.Bold) },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                    singleLine = true,
                    modifier = Modifier
                        .weight(1f)
                        .testTag("input_vacation_payment"),
                    shape = RoundedCornerShape(16.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = if (state.vacationPayment > 0) HighDensityEmeraldContainer else HighDensitySurface,
                        unfocusedContainerColor = if (state.vacationPayment > 0) HighDensityEmeraldContainer else HighDensitySurface,
                        focusedBorderColor = HighDensityEmeraldText,
                        unfocusedBorderColor = if (state.vacationPayment > 0) HighDensityEmeraldText.copy(alpha = 0.4f) else HighDensityBorder,
                        focusedTextColor = HighDensityEmeraldText,
                        unfocusedTextColor = if (state.vacationPayment > 0) HighDensityEmeraldText else HighDensityTextPrimary
                    )
                )
            }

            // Row 5: Regular Payment Deduction & Advance
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                OutlinedTextField(
                    value = if (state.regularPaymentDeduction == 0.0) "0" else if (state.regularPaymentDeduction % 1.0 == 0.0) state.regularPaymentDeduction.toInt().toString() else state.regularPaymentDeduction.toString(),
                    onValueChange = { input ->
                        val v = input.toDoubleOrNull() ?: 0.0
                        onRegularDeductionChange(v)
                    },
                    label = { Text("Чергова виплата", color = HighDensityAmberText, fontSize = 11.sp) },
                    suffix = { Text("₴", color = HighDensityAmberText, fontSize = 11.sp, fontWeight = FontWeight.Bold) },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                    singleLine = true,
                    modifier = Modifier
                        .weight(1f)
                        .testTag("input_regular_deduction"),
                    shape = RoundedCornerShape(16.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = HighDensityAmberContainer,
                        unfocusedContainerColor = HighDensityAmberContainer,
                        focusedBorderColor = HighDensityAmberText,
                        unfocusedBorderColor = HighDensityAmberText.copy(alpha = 0.4f),
                        focusedTextColor = HighDensityAmberText,
                        unfocusedTextColor = HighDensityAmberText
                    )
                )

                OutlinedTextField(
                    value = if (state.advancePayment == 0.0) "0" else if (state.advancePayment % 1.0 == 0.0) state.advancePayment.toInt().toString() else state.advancePayment.toString(),
                    onValueChange = { input ->
                        val v = input.toDoubleOrNull() ?: 0.0
                        onAdvanceChange(v)
                    },
                    label = { Text("Аванс (віднімається)", color = HighDensityRoseText, fontSize = 11.sp) },
                    suffix = { Text("₴", color = HighDensityRoseText, fontSize = 11.sp, fontWeight = FontWeight.Bold) },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                    singleLine = true,
                    modifier = Modifier
                        .weight(1f)
                        .testTag("input_advance_payment"),
                    shape = RoundedCornerShape(16.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = HighDensityRoseContainer,
                        unfocusedContainerColor = HighDensityRoseContainer,
                        focusedBorderColor = HighDensityRoseText,
                        unfocusedBorderColor = HighDensityRoseText.copy(alpha = 0.4f),
                        focusedTextColor = HighDensityRoseText,
                        unfocusedTextColor = HighDensityRoseText
                    )
                )
            }
        }
    }
}
