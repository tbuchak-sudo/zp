package com.example.ui.components

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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.PostAdd
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.calculator.SalaryCalculator
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimesheetImportDialog(
    salaryMonth: String,
    onDismiss: () -> Unit,
    onApplyTimesheet: (String) -> Unit
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    var textInput by remember { mutableStateOf("") }

    val sampleTimesheet = "Іваненко Іван Іванович\t8,0\t8,0\t8,0\t8,0\t8,0\t8,0\t\t8,1\t8,1\t8,1\t8,1\t8,1\t8,1\t\t8,0\t8,0\t8,0\t8,0\t8,0\t\t\t8,1\t8,1\t8,1\t8,1\t8,1\t8,1\t\t8,0\t8,0"

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
                .padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Title Bar
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
                            imageVector = Icons.Default.PostAdd,
                            contentDescription = null,
                            tint = HighDensityPrimary,
                            modifier = Modifier.size(20.dp)
                        )
                    }

                    Column {
                        Text(
                            text = "Імпорт з табеля Excel",
                            color = HighDensityPrimaryDark,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "Звітний місяць: $salaryMonth",
                            color = HighDensityTextSecondary,
                            fontSize = 12.sp
                        )
                    }
                }

                IconButton(onClick = onDismiss) {
                    Icon(imageVector = Icons.Default.Close, contentDescription = "Закрити", tint = HighDensityTextSecondary)
                }
            }

            // Description
            Text(
                text = "Скопіюйте рядок працівника з Excel (разом з відмітками 8,0 / 8,1 / 16,1 / В) і вставте сюди. Додаток автоматично розпізнає суботи за календарем та нарахує доплати.",
                color = HighDensityTextSecondary,
                fontSize = 13.sp,
                lineHeight = 18.sp
            )

            // Sample button
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                OutlinedButton(
                    onClick = { textInput = sampleTimesheet },
                    shape = RoundedCornerShape(12.dp),
                    border = androidx.compose.foundation.BorderStroke(1.dp, HighDensityPrimary),
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = HighDensityPrimary)
                ) {
                    Icon(imageVector = Icons.Default.AutoAwesome, contentDescription = null, modifier = Modifier.size(14.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(text = "Вставити приклад", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                }
            }

            // Text Input
            OutlinedTextField(
                value = textInput,
                onValueChange = { textInput = it },
                placeholder = { Text("Вставте рядок з Excel тут...", color = HighDensityTextSecondary, fontSize = 13.sp) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(130.dp)
                    .testTag("input_timesheet_area"),
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

            // Live Preview Card
            if (textInput.isNotBlank()) {
                val preview = remember(textInput, salaryMonth) {
                    SalaryCalculator.parseTimesheet(textInput, salaryMonth)
                }
                Surface(
                    shape = RoundedCornerShape(14.dp),
                    color = HighDensitySurfaceVariant,
                    border = androidx.compose.foundation.BorderStroke(1.dp, HighDensityBorderSubtle),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.padding(12.dp),
                        verticalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Text(
                            text = "Результат розпізнавання:",
                            color = HighDensityPrimaryDark,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold
                        )
                        if (!preview.employeeName.isNullOrBlank()) {
                            Text(
                                text = "Працівник: ${preview.employeeName}",
                                color = HighDensityTextPrimary,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                        Text(
                            text = preview.summaryText,
                            color = HighDensityTextSecondary,
                            fontSize = 11.sp
                        )
                    }
                }
            }

            // Submit Button
            Button(
                onClick = {
                    if (textInput.isNotBlank()) {
                        onApplyTimesheet(textInput)
                        onDismiss()
                    }
                },
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = HighDensityPrimary,
                    contentColor = Color.White
                ),
                enabled = textInput.isNotBlank(),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp)
                    .testTag("btn_submit_timesheet")
            ) {
                Text(
                    text = "Обчислити та перенести в розрахунок",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}
