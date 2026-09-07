package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.SalaryInputState
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

@Composable
fun ShiftsAndOvertimeCard(
    state: SalaryInputState,
    onShift1DaysChange: (Int) -> Unit,
    onSatShift1DaysChange: (Int) -> Unit,
    onShift2DaysChange: (Int) -> Unit,
    onSatShift2DaysChange: (Int) -> Unit,
    onShift3DaysChange: (Int) -> Unit,
    onOvertimeHoursChange: (Double) -> Unit,
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
                        imageVector = Icons.Default.AccessTime,
                        contentDescription = null,
                        tint = HighDensityPrimary,
                        modifier = Modifier.size(16.dp)
                    )
                }
                Text(
                    text = "ЗМІНИ ТА ПОНАДНОРМОВІ",
                    color = HighDensityPrimaryDark,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.ExtraBold,
                    letterSpacing = 1.sp
                )
            }

            // 1st Shift
            ShiftRowItem(
                title = "1 Зміна",
                subtitle = "денна (8 год)",
                weekdayValue = state.shift1Days,
                saturdayValue = state.satShift1Days,
                onWeekdayChange = onShift1DaysChange,
                onSaturdayChange = onSatShift1DaysChange,
                hasSaturday = true,
                badge = null,
                testTagPrefix = "shift1"
            )

            // 2nd Shift (+20%)
            ShiftRowItem(
                title = "2 Зміна",
                subtitle = "вечірні години (+20%)",
                weekdayValue = state.shift2Days,
                saturdayValue = state.satShift2Days,
                onWeekdayChange = onShift2DaysChange,
                onSaturdayChange = onSatShift2DaysChange,
                hasSaturday = true,
                badge = "+20%",
                badgeBgColor = HighDensityEmeraldContainer,
                badgeTextColor = HighDensityEmeraldText,
                testTagPrefix = "shift2"
            )

            // 3rd Shift (+40%)
            ShiftRowItem(
                title = "3 Зміна",
                subtitle = "нічні години (+40%)",
                weekdayValue = state.shift3Days,
                saturdayValue = 0,
                onWeekdayChange = onShift3DaysChange,
                onSaturdayChange = {},
                hasSaturday = false,
                badge = "+40%",
                badgeBgColor = HighDensityEmeraldContainer,
                badgeTextColor = HighDensityEmeraldText,
                testTagPrefix = "shift3"
            )

            // Overtime hours
            Surface(
                shape = RoundedCornerShape(16.dp),
                color = HighDensitySurfaceVariant,
                border = androidx.compose.foundation.BorderStroke(1.dp, HighDensityBorderSubtle),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 14.dp, vertical = 10.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = "Понаднормові (години)",
                                color = HighDensityTextPrimary,
                                fontSize = 13.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Surface(
                                shape = RoundedCornerShape(6.dp),
                                color = HighDensityRoseContainer
                            ) {
                                Text(
                                    text = "x2",
                                    color = HighDensityRoseText,
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.ExtraBold,
                                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                )
                            }
                        }
                        Text(
                            text = "Подвійна оплата + премія",
                            color = HighDensityTextSecondary,
                            fontSize = 11.sp
                        )
                    }

                    OutlinedTextField(
                        value = if (state.overtimeHours == 0.0) "0" else if (state.overtimeHours % 1.0 == 0.0) state.overtimeHours.toInt().toString() else state.overtimeHours.toString(),
                        onValueChange = { input ->
                            val v = input.toDoubleOrNull() ?: 0.0
                            onOvertimeHoursChange(v)
                        },
                        suffix = { Text("год", color = HighDensityPrimary, fontSize = 11.sp, fontWeight = FontWeight.Bold) },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                        singleLine = true,
                        modifier = Modifier
                            .width(100.dp)
                            .testTag("input_overtime_hours"),
                        shape = RoundedCornerShape(12.dp),
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
            }
        }
    }
}

@Composable
private fun ShiftRowItem(
    title: String,
    subtitle: String,
    weekdayValue: Int,
    saturdayValue: Int,
    onWeekdayChange: (Int) -> Unit,
    onSaturdayChange: (Int) -> Unit,
    hasSaturday: Boolean,
    badge: String?,
    badgeBgColor: Color = HighDensityEmeraldContainer,
    badgeTextColor: Color = HighDensityEmeraldText,
    testTagPrefix: String
) {
    Surface(
        shape = RoundedCornerShape(16.dp),
        color = HighDensitySurfaceVariant,
        border = androidx.compose.foundation.BorderStroke(1.dp, HighDensityBorderSubtle),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 14.dp, vertical = 10.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = title,
                        color = HighDensityTextPrimary,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                    if (badge != null) {
                        Spacer(modifier = Modifier.width(6.dp))
                        Surface(
                            shape = RoundedCornerShape(6.dp),
                            color = badgeBgColor
                        ) {
                            Text(
                                text = badge,
                                color = badgeTextColor,
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                            )
                        }
                    }
                }
                Text(
                    text = subtitle,
                    color = HighDensityTextSecondary,
                    fontSize = 11.sp
                )
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                // Weekdays input
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(text = "будні", color = HighDensityTextSecondary, fontSize = 10.sp)
                    OutlinedTextField(
                        value = if (weekdayValue == 0) "0" else weekdayValue.toString(),
                        onValueChange = { input ->
                            val v = input.toIntOrNull() ?: 0
                            onWeekdayChange(v)
                        },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        singleLine = true,
                        modifier = Modifier
                            .width(58.dp)
                            .testTag("input_${testTagPrefix}_weekdays"),
                        shape = RoundedCornerShape(12.dp),
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

                // Saturdays input
                if (hasSaturday) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(text = "суб(x2)", color = HighDensityRoseText, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                        OutlinedTextField(
                            value = if (saturdayValue == 0) "0" else saturdayValue.toString(),
                            onValueChange = { input ->
                                val v = input.toIntOrNull() ?: 0
                                onSaturdayChange(v)
                            },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            singleLine = true,
                            modifier = Modifier
                                .width(58.dp)
                                .testTag("input_${testTagPrefix}_saturdays"),
                            shape = RoundedCornerShape(12.dp),
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
                }
            }
        }
    }
}
