package com.example.ui.components

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.widget.Toast
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.SalaryCalculationResult
import com.example.data.model.SalaryInputState
import com.example.ui.theme.HighDensityBorder
import com.example.ui.theme.HighDensityBorderSubtle
import com.example.ui.theme.HighDensityEmeraldText
import com.example.ui.theme.HighDensityPrimary
import com.example.ui.theme.HighDensityPrimaryContainer
import com.example.ui.theme.HighDensityPrimaryDark
import com.example.ui.theme.HighDensityRoseText
import com.example.ui.theme.HighDensitySecondaryContainer
import com.example.ui.theme.HighDensitySurface
import com.example.ui.theme.HighDensityTextPrimary
import com.example.ui.theme.HighDensityTextSecondary

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PayslipDialog(
    state: SalaryInputState,
    result: SalaryCalculationResult,
    onDismiss: () -> Unit,
    onExportJson: () -> String,
    onImportJson: (String) -> Boolean
) {
    val context = LocalContext.current
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    val formattedReport = buildString {
        appendLine("============================================")
        appendLine("           РОЗРАХУНКОВИЙ ЛИСТОК             ")
        appendLine("                  BADER                     ")
        appendLine("============================================")
        appendLine("Звітний період: ${state.salaryMonth}")
        appendLine("Працівник: ${state.employeeName.ifBlank { "—" }}")
        appendLine("Розряд: ${state.rank} | Робочих днів: ${state.workDays}")
        appendLine("Оклад (брутто): ${String.format(java.util.Locale.US, "%.2f ₴", state.baseRate / 0.77)}")
        appendLine("--------------------------------------------")
        appendLine("1. НАРАХОВАНО (БРУТТО):")
        appendLine("  • Оклад по годинах: ${String.format(java.util.Locale.US, "%.2f ₴", result.payShift1 / 0.77)}")
        if (result.payShift2 > 0) appendLine("  • Вечірні (+20%): ${String.format(java.util.Locale.US, "%.2f ₴", result.payShift2 / 0.77)}")
        if (result.payShift3 > 0) appendLine("  • Нічні (+40%): ${String.format(java.util.Locale.US, "%.2f ₴", result.payShift3 / 0.77)}")
        if (result.paySatShift1 > 0) appendLine("  • Субота 1зм (x2): ${String.format(java.util.Locale.US, "%.2f ₴", result.paySatShift1 / 0.77)}")
        if (result.paySatShift2 > 0) appendLine("  • Субота 2зм (x2): ${String.format(java.util.Locale.US, "%.2f ₴", result.paySatShift2 / 0.77)}")
        if (result.payOvertime > 0) appendLine("  • Понаднормові (x2): ${String.format(java.util.Locale.US, "%.2f ₴", result.payOvertime / 0.77)}")
        appendLine("  • Премія (якість): ${String.format(java.util.Locale.US, "%.2f ₴", (result.actualQualityPremium + result.overtimePremium) / 0.77)}")
        if (result.satPremium > 0) appendLine("  • Премія за суботи: ${String.format(java.util.Locale.US, "%.2f ₴", result.satPremium / 0.77)}")
        if (result.expBonus > 0) appendLine("  • Доплата за стаж: ${String.format(java.util.Locale.US, "%.2f ₴", state.experienceBonusBrutto)}")
        if (result.attBonus > 0) appendLine("  • Відвідування: ${String.format(java.util.Locale.US, "%.2f ₴", result.attBonus / 0.77)}")
        if (result.indexation > 0) appendLine("  • Індексація: ${String.format(java.util.Locale.US, "%.2f ₴", state.indexationBonusBrutto)}")
        if (result.extraPayments > 0) appendLine("  • Додаткові доплати: ${String.format(java.util.Locale.US, "%.2f ₴", result.extraPayments / 0.77)}")
        if (result.vacationPayment > 0) appendLine("  • Відпустка: ${String.format(java.util.Locale.US, "%.2f ₴", result.vacationPayment / 0.77)}")
        appendLine(">> ВСЬОГО НАРАХОВАНО (БРУТТО): ${String.format(java.util.Locale.US, "%.2f ₴", result.totalAccruedBrutto)}")
        appendLine(">> ВСЬОГО НАРАХОВАНО (НЕТТО): ${String.format(java.util.Locale.US, "%.2f ₴", result.totalAccrued)}")
        appendLine("--------------------------------------------")
        appendLine("2. УТРИМАНО (ПОДАТКИ):")
        appendLine("  • Військовий збір (5%): ${String.format(java.util.Locale.US, "%.2f ₴", result.militaryTax)}")
        appendLine("  • ПДФО (18%): ${String.format(java.util.Locale.US, "%.2f ₴", result.incomeTax)}")
        appendLine(">> ВСЬОГО УТРИМАНО: ${String.format(java.util.Locale.US, "%.2f ₴", result.totalDeductions)}")
        appendLine("--------------------------------------------")
        appendLine("3. ВИПЛАЧЕНО:")
        appendLine("  • Аванс: ${String.format(java.util.Locale.US, "%.2f ₴", result.advancePayment)}")
        if (result.regularPaymentDeduction > 0) appendLine("  • Чергова виплата: ${String.format(java.util.Locale.US, "%.2f ₴", result.regularPaymentDeduction)}")
        appendLine(">> ВСЬОГО ВИПЛАТ: ${String.format(java.util.Locale.US, "%.2f ₴", result.totalPaid)}")
        appendLine("============================================")
        appendLine("ДО ВИДАЧІ (НА РУКИ): ${String.format(java.util.Locale.US, "%.2f ₴", result.totalToPay)}")
        appendLine("============================================")
    }

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        containerColor = HighDensitySurface,
        dragHandle = null,
        shape = RoundedCornerShape(topStart = 28.dp, topEnd = 28.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(36.dp)
                            .background(HighDensitySecondaryContainer, shape = RoundedCornerShape(10.dp)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Description,
                            contentDescription = null,
                            tint = HighDensityPrimary,
                            modifier = Modifier.size(20.dp)
                        )
                    }

                    Column {
                        Text(
                            text = "Розрахунковий листок",
                            color = HighDensityPrimaryDark,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "За ${state.salaryMonth}",
                            color = HighDensityTextSecondary,
                            fontSize = 12.sp
                        )
                    }
                }

                IconButton(onClick = onDismiss) {
                    Icon(imageVector = Icons.Default.Close, contentDescription = "Закрити", tint = HighDensityTextSecondary)
                }
            }

            // Document View Card
            Surface(
                shape = RoundedCornerShape(18.dp),
                color = Color.White,
                border = androidx.compose.foundation.BorderStroke(1.dp, HighDensityBorder),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = "Розрахунковий листок за ${state.salaryMonth}",
                        color = Color.Black,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily.Serif
                    )

                    Text(
                        text = "Працівник: ${state.employeeName.ifBlank { "___________________" }}",
                        color = Color.Black,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold,
                        fontFamily = FontFamily.Serif
                    )

                    Text(
                        text = "Розмір тарифної ставки (окладу): ${String.format(java.util.Locale.US, "%.2f ₴", state.baseRate / 0.77)}",
                        color = Color.Black,
                        fontSize = 11.sp,
                        fontFamily = FontFamily.Serif
                    )

                    HorizontalDivider(color = Color.Black.copy(alpha = 0.15f), thickness = 1.dp)

                    // Section 1: Нараховано
                    Text(
                        text = "1. НАРАХОВАНО (Брутто)",
                        color = HighDensityPrimaryDark,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily.Serif
                    )

                    DocLine("Оклад по годинах (будні):", String.format(java.util.Locale.US, "%.2f ₴", result.payShift1 / 0.77))
                    if (result.payShift2 > 0) DocLine("Вечірні години (+20%):", String.format(java.util.Locale.US, "%.2f ₴", result.payShift2 / 0.77))
                    if (result.payShift3 > 0) DocLine("Нічні години (+40%):", String.format(java.util.Locale.US, "%.2f ₴", result.payShift3 / 0.77))
                    if (result.paySatShift1 > 0) DocLine("Субота 1 зміна (x2):", String.format(java.util.Locale.US, "%.2f ₴", result.paySatShift1 / 0.77))
                    if (result.paySatShift2 > 0) DocLine("Субота 2 зміна (x2):", String.format(java.util.Locale.US, "%.2f ₴", result.paySatShift2 / 0.77))
                    if (result.payOvertime > 0) DocLine("Понаднормові (x2):", String.format(java.util.Locale.US, "%.2f ₴", result.payOvertime / 0.77))
                    DocLine("Премія (прод. якість):", String.format(java.util.Locale.US, "%.2f ₴", (result.actualQualityPremium + result.overtimePremium) / 0.77))
                    if (result.satPremium > 0) DocLine("Премія за суботи:", String.format(java.util.Locale.US, "%.2f ₴", result.satPremium / 0.77))
                    if (result.expBonus > 0) DocLine("Доплата за стаж:", String.format(java.util.Locale.US, "%.2f ₴", state.experienceBonusBrutto))
                    if (result.attBonus > 0) DocLine("Доплата за відвідування:", String.format(java.util.Locale.US, "%.2f ₴", result.attBonus / 0.77))
                    if (result.indexation > 0) DocLine("Індексація:", String.format(java.util.Locale.US, "%.2f ₴", state.indexationBonusBrutto))
                    if (result.extraPayments > 0) DocLine("Додаткові доплати:", String.format(java.util.Locale.US, "%.2f ₴", result.extraPayments / 0.77))
                    if (result.vacationPayment > 0) DocLine("Відпустка / інші:", String.format(java.util.Locale.US, "%.2f ₴", result.vacationPayment / 0.77))

                    DocLine("Всього нараховано (Брутто):", String.format(java.util.Locale.US, "%.2f ₴", result.totalAccruedBrutto), isBold = true)
                    DocLine("Всього нараховано (Нетто):", String.format(java.util.Locale.US, "%.2f ₴", result.totalAccrued), isBold = true)

                    HorizontalDivider(color = Color.Black.copy(alpha = 0.15f), thickness = 1.dp)

                    // Section 2: Утримано
                    Text(
                        text = "2. УТРИМАНО (Податки)",
                        color = HighDensityRoseText,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily.Serif
                    )
                    DocLine("Військовий збір (5%):", String.format(java.util.Locale.US, "%.2f ₴", result.militaryTax))
                    DocLine("Податок з доходів фіз.осіб (18%):", String.format(java.util.Locale.US, "%.2f ₴", result.incomeTax))
                    DocLine("Всього утримано:", String.format(java.util.Locale.US, "%.2f ₴", result.totalDeductions), isBold = true)

                    HorizontalDivider(color = Color.Black.copy(alpha = 0.15f), thickness = 1.dp)

                    // Section 3: Виплачено
                    Text(
                        text = "3. ВИПЛАЧЕНО",
                        color = HighDensityEmeraldText,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily.Serif
                    )
                    DocLine("Аванс:", String.format(java.util.Locale.US, "%.2f ₴", result.advancePayment))
                    if (result.regularPaymentDeduction > 0) {
                        DocLine("Чергова виплата:", String.format(java.util.Locale.US, "%.2f ₴", result.regularPaymentDeduction))
                    }
                    DocLine("Всього виплат:", String.format(java.util.Locale.US, "%.2f ₴", result.totalPaid), isBold = true)

                    HorizontalDivider(color = Color.Black, thickness = 1.5.dp)

                    // Summary Line
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "Борг на кінець місяця (До видачі):",
                            color = Color.Black,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.ExtraBold,
                            fontFamily = FontFamily.Serif
                        )
                        Text(
                            text = String.format(java.util.Locale.US, "%.2f ₴", result.totalToPay),
                            color = HighDensityPrimaryDark,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.ExtraBold,
                            fontFamily = FontFamily.Monospace
                        )
                    }
                }
            }

            // Action Buttons
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Button(
                    onClick = {
                        val shareIntent = Intent(Intent.ACTION_SEND).apply {
                            type = "text/plain"
                            putExtra(Intent.EXTRA_SUBJECT, "Розрахунковий листок Bader - ${state.salaryMonth}")
                            putExtra(Intent.EXTRA_TEXT, formattedReport)
                        }
                        context.startActivity(Intent.createChooser(shareIntent, "Поділитися листком"))
                    },
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = HighDensityPrimary,
                        contentColor = Color.White
                    )
                ) {
                    Icon(imageVector = Icons.Default.Share, contentDescription = null, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(text = "Поділитись", fontSize = 13.sp, fontWeight = FontWeight.Bold)
                }

                OutlinedButton(
                    onClick = {
                        val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                        val clip = ClipData.newPlainText("Bader Payslip", formattedReport)
                        clipboard.setPrimaryClip(clip)
                        Toast.makeText(context, "Скопійовано в буфер!", Toast.LENGTH_SHORT).show()
                    },
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(16.dp),
                    border = androidx.compose.foundation.BorderStroke(1.dp, HighDensityPrimary),
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = HighDensityPrimary)
                ) {
                    Icon(imageVector = Icons.Default.ContentCopy, contentDescription = null, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(text = "Копіювати", fontSize = 13.sp, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}

@Composable
private fun DocLine(title: String, amount: String, isBold: Boolean = false) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = title,
            color = Color.Black,
            fontSize = 10.sp,
            fontWeight = if (isBold) FontWeight.Bold else FontWeight.Normal,
            fontFamily = FontFamily.Serif
        )
        Text(
            text = amount,
            color = Color.Black,
            fontSize = 10.sp,
            fontWeight = if (isBold) FontWeight.Bold else FontWeight.Normal,
            fontFamily = FontFamily.Monospace
        )
    }
}
