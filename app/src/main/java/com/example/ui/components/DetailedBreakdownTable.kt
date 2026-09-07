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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.TableChart
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.BreakdownRow
import com.example.data.model.SalaryCalculationResult
import com.example.ui.theme.HighDensityBorder
import com.example.ui.theme.HighDensityBorderSubtle
import com.example.ui.theme.HighDensityPrimary
import com.example.ui.theme.HighDensityPrimaryContainer
import com.example.ui.theme.HighDensityPrimaryDark
import com.example.ui.theme.HighDensitySecondaryContainer
import com.example.ui.theme.HighDensitySurface
import com.example.ui.theme.HighDensitySurfaceVariant
import com.example.ui.theme.HighDensityTextPrimary
import com.example.ui.theme.HighDensityTextSecondary
import com.example.ui.theme.HighDensityTextTertiary

@Composable
fun DetailedBreakdownTable(
    result: SalaryCalculationResult,
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
                        imageVector = Icons.Default.TableChart,
                        contentDescription = null,
                        tint = HighDensityPrimary,
                        modifier = Modifier.size(16.dp)
                    )
                }
                Text(
                    text = "ДЕТАЛІЗАЦІЯ НАРАХУВАНЬ",
                    color = HighDensityPrimaryDark,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.ExtraBold,
                    letterSpacing = 1.sp
                )
            }

            // Table header
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "СТАТТЯ ВИПЛАТИ",
                    color = HighDensityTextSecondary,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.ExtraBold,
                    letterSpacing = 0.5.sp,
                    modifier = Modifier.weight(1.3f)
                )

                Text(
                    text = "ГОД.",
                    color = HighDensityTextSecondary,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.ExtraBold,
                    textAlign = TextAlign.Center,
                    letterSpacing = 0.5.sp,
                    modifier = Modifier.width(48.dp)
                )

                Spacer(modifier = Modifier.width(4.dp))

                Text(
                    text = "НЕТТО",
                    color = HighDensityPrimary,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.ExtraBold,
                    textAlign = TextAlign.End,
                    letterSpacing = 0.5.sp,
                    modifier = Modifier.width(72.dp)
                )

                Spacer(modifier = Modifier.width(4.dp))

                Text(
                    text = "БРУТТО",
                    color = HighDensityTextSecondary,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.ExtraBold,
                    textAlign = TextAlign.End,
                    letterSpacing = 0.5.sp,
                    modifier = Modifier.width(72.dp)
                )
            }

            HorizontalDivider(color = HighDensityBorder, thickness = 1.dp)

            // Rows list
            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                result.breakdownRows.forEach { row ->
                    BreakdownRowView(row = row)
                }
            }

            HorizontalDivider(color = HighDensityBorder, thickness = 1.dp)

            // Subtotal Row: ВСЬОГО НАРАХОВАНО
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(HighDensitySecondaryContainer, shape = RoundedCornerShape(12.dp))
                    .padding(horizontal = 10.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Всього нараховано",
                    color = HighDensityPrimaryDark,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.ExtraBold,
                    modifier = Modifier.weight(1.3f)
                )

                Text(
                    text = "-",
                    color = HighDensityTextSecondary,
                    fontSize = 11.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.width(48.dp)
                )

                Spacer(modifier = Modifier.width(4.dp))

                Text(
                    text = String.format(java.util.Locale.US, "%.2f", result.totalAccrued),
                    color = HighDensityPrimaryDark,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.ExtraBold,
                    fontFamily = FontFamily.Monospace,
                    textAlign = TextAlign.End,
                    modifier = Modifier.width(72.dp)
                )

                Spacer(modifier = Modifier.width(4.dp))

                Text(
                    text = String.format(java.util.Locale.US, "%.2f", result.totalAccruedBrutto),
                    color = HighDensityTextPrimary,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.Monospace,
                    textAlign = TextAlign.End,
                    modifier = Modifier.width(72.dp)
                )
            }
        }
    }
}

@Composable
private fun BreakdownRowView(row: BreakdownRow) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 2.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = row.title,
            color = HighDensityTextPrimary,
            fontSize = 12.sp,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.weight(1.3f)
        )

        // Hours Chip
        Box(
            modifier = Modifier
                .width(48.dp)
                .background(HighDensitySurfaceVariant, shape = RoundedCornerShape(6.dp))
                .border(0.5.dp, HighDensityBorderSubtle, shape = RoundedCornerShape(6.dp))
                .padding(vertical = 3.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = row.hoursText,
                color = HighDensityTextSecondary,
                fontSize = 11.sp,
                fontFamily = FontFamily.Monospace,
                fontWeight = FontWeight.SemiBold,
                textAlign = TextAlign.Center
            )
        }

        Spacer(modifier = Modifier.width(4.dp))

        // Netto Chip
        Box(
            modifier = Modifier
                .width(72.dp)
                .background(HighDensityPrimaryContainer, shape = RoundedCornerShape(6.dp))
                .border(0.5.dp, HighDensityPrimary.copy(alpha = 0.2f), shape = RoundedCornerShape(6.dp))
                .padding(vertical = 3.dp, horizontal = 4.dp),
            contentAlignment = Alignment.CenterEnd
        ) {
            Text(
                text = String.format(java.util.Locale.US, "%.2f", row.netto),
                color = HighDensityPrimaryDark,
                fontSize = 11.sp,
                fontFamily = FontFamily.Monospace,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.End
            )
        }

        Spacer(modifier = Modifier.width(4.dp))

        // Brutto Chip
        Box(
            modifier = Modifier
                .width(72.dp)
                .background(HighDensitySurfaceVariant, shape = RoundedCornerShape(6.dp))
                .border(0.5.dp, HighDensityBorderSubtle, shape = RoundedCornerShape(6.dp))
                .padding(vertical = 3.dp, horizontal = 4.dp),
            contentAlignment = Alignment.CenterEnd
        ) {
            Text(
                text = String.format(java.util.Locale.US, "%.2f", row.brutto),
                color = HighDensityTextSecondary,
                fontSize = 11.sp,
                fontFamily = FontFamily.Monospace,
                fontWeight = FontWeight.Normal,
                textAlign = TextAlign.End
            )
        }
    }
}
