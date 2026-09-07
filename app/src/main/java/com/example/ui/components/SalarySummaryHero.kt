package com.example.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.SalaryCalculationResult
import com.example.ui.theme.HighDensityAmberContainer
import com.example.ui.theme.HighDensityAmberText
import com.example.ui.theme.HighDensityBorder
import com.example.ui.theme.HighDensityBorderSubtle
import com.example.ui.theme.HighDensityEmeraldContainer
import com.example.ui.theme.HighDensityEmeraldText
import com.example.ui.theme.HighDensityPrimary
import com.example.ui.theme.HighDensityPrimaryContainer
import com.example.ui.theme.HighDensityPrimaryDark
import com.example.ui.theme.HighDensityPrimaryLight
import com.example.ui.theme.HighDensityRoseContainer
import com.example.ui.theme.HighDensityRoseText
import com.example.ui.theme.HighDensitySecondaryContainer
import com.example.ui.theme.HighDensitySurface
import com.example.ui.theme.HighDensitySurfaceVariant
import com.example.ui.theme.HighDensityTextPrimary
import com.example.ui.theme.HighDensityTextSecondary
import com.example.ui.theme.HighDensityTextTertiary

@Composable
fun SalarySummaryHero(
    result: SalaryCalculationResult,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 4.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        // High Density Main Hero Card (Lavender/Lilac container #E8DEF8)
        Surface(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(26.dp),
            color = HighDensitySecondaryContainer,
            border = androidx.compose.foundation.BorderStroke(1.dp, HighDensityPrimary.copy(alpha = 0.15f)),
            tonalElevation = 2.dp,
            shadowElevation = 1.dp
        ) {
            Column(
                modifier = Modifier.padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // Header with badge
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Top
                ) {
                    Column {
                        Text(
                            text = "Поточний розрахунок",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Medium,
                            color = HighDensityTextSecondary
                        )
                        Spacer(modifier = Modifier.height(2.dp))
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Text(
                                text = "До видачі на руки",
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                color = HighDensityPrimaryDark
                            )
                            Text(
                                text = result.emoji,
                                fontSize = 18.sp
                            )
                        }
                    }

                    Surface(
                        shape = RoundedCornerShape(99.dp),
                        color = Color.White,
                        border = androidx.compose.foundation.BorderStroke(1.dp, HighDensityPrimary.copy(alpha = 0.15f))
                    ) {
                        Text(
                            text = "АКТИВНО",
                            color = HighDensityPrimary,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.ExtraBold,
                            letterSpacing = 0.5.sp,
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                        )
                    }
                }

                // Big Payout Display
                Text(
                    text = String.format(java.util.Locale.US, "%.2f ₴", result.totalToPay),
                    color = HighDensityPrimaryDark,
                    fontSize = 34.sp,
                    fontWeight = FontWeight.ExtraBold,
                    fontFamily = FontFamily.Monospace,
                    letterSpacing = (-0.5).sp,
                    modifier = Modifier.testTag("text_total_to_pay")
                )

                // High Density Progress Bar indicator
                Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(8.dp)
                            .background(HighDensitySurfaceVariant, shape = RoundedCornerShape(99.dp))
                    ) {
                        val progressFraction = if (result.totalAccruedBrutto > 0) {
                            (result.totalToPay / result.totalAccruedBrutto).toFloat().coerceIn(0.1f, 1f)
                        } else 0.8f
                        Box(
                            modifier = Modifier
                                .fillMaxWidth(progressFraction)
                                .height(8.dp)
                                .background(HighDensityPrimary, shape = RoundedCornerShape(99.dp))
                        )
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "Відпрацьовано: " + if (result.totalWorkedHours % 1.0 == 0.0) {
                                "${result.totalWorkedHours.toInt()} год"
                            } else {
                                String.format(java.util.Locale.US, "%.1f год", result.totalWorkedHours)
                            },
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Medium,
                            color = HighDensityTextSecondary
                        )
                        Text(
                            text = "Ставка: ${String.format(java.util.Locale.US, "%.2f ₴/год", result.hourlyRate)}",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Medium,
                            color = HighDensityTextSecondary
                        )
                    }
                }
            }
        }

        // 3 High Density Metric Cards: Netto, Brutto, Advance
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            // Netto Card (EADDFF container)
            HighDensityTile(
                title = "НЕТТО",
                value = String.format(java.util.Locale.US, "%.2f ₴", result.totalAccrued),
                bgColor = HighDensityPrimaryContainer,
                textColor = HighDensityPrimaryDark,
                modifier = Modifier.weight(1f)
            )

            // Brutto Card (FEF7FF container with border)
            HighDensityTile(
                title = "БРУТТО",
                value = String.format(java.util.Locale.US, "%.2f ₴", result.totalAccruedBrutto),
                bgColor = HighDensitySurface,
                textColor = HighDensityTextPrimary,
                borderColor = HighDensityBorder,
                modifier = Modifier.weight(1f)
            )

            // Advance Card (FFD8E4 Rose container)
            HighDensityTile(
                title = "АВАНС",
                value = String.format(java.util.Locale.US, "-%.0f ₴", result.advancePayment),
                bgColor = HighDensityRoseContainer,
                textColor = HighDensityRoseText,
                modifier = Modifier.weight(1f)
            )
        }

        // Conditional Vacation / Regular Deduction banners
        AnimatedVisibility(
            visible = result.vacationPayment > 0 || result.regularPaymentDeduction > 0,
            enter = fadeIn(),
            exit = fadeOut()
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                if (result.vacationPayment > 0) {
                    Surface(
                        shape = RoundedCornerShape(16.dp),
                        color = HighDensityEmeraldContainer,
                        border = androidx.compose.foundation.BorderStroke(1.dp, HighDensityEmeraldText.copy(alpha = 0.2f)),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 14.dp, vertical = 10.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "🏖 Відпустка (виплачена окремо)",
                                fontSize = 12.sp,
                                color = HighDensityEmeraldText,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = String.format(java.util.Locale.US, "%.2f ₴", result.vacationPayment),
                                fontSize = 13.sp,
                                color = HighDensityEmeraldText,
                                fontWeight = FontWeight.ExtraBold,
                                fontFamily = FontFamily.Monospace
                            )
                        }
                    }
                }

                if (result.regularPaymentDeduction > 0) {
                    Surface(
                        shape = RoundedCornerShape(16.dp),
                        color = HighDensityAmberContainer,
                        border = androidx.compose.foundation.BorderStroke(1.dp, HighDensityAmberText.copy(alpha = 0.2f)),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 14.dp, vertical = 10.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "🧾 Чергова виплата (віднімається)",
                                fontSize = 12.sp,
                                color = HighDensityAmberText,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = String.format(java.util.Locale.US, "-%.2f ₴", result.regularPaymentDeduction),
                                fontSize = 13.sp,
                                color = HighDensityAmberText,
                                fontWeight = FontWeight.ExtraBold,
                                fontFamily = FontFamily.Monospace
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun HighDensityTile(
    title: String,
    value: String,
    bgColor: Color,
    textColor: Color,
    borderColor: Color? = null,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(18.dp),
        color = bgColor,
        border = borderColor?.let { androidx.compose.foundation.BorderStroke(1.dp, it) }
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 12.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                text = title,
                color = textColor.copy(alpha = 0.8f),
                fontSize = 10.sp,
                fontWeight = FontWeight.ExtraBold,
                letterSpacing = 0.5.sp
            )
            Text(
                text = value,
                color = textColor,
                fontSize = 13.sp,
                fontWeight = FontWeight.ExtraBold,
                fontFamily = FontFamily.Monospace,
                maxLines = 1
            )
        }
    }
}

