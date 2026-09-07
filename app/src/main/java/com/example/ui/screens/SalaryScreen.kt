package com.example.ui.screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ui.components.BaderTopBar
import com.example.ui.components.BasicSettingsCard
import com.example.ui.components.BonusesAndDeductionsCard
import com.example.ui.components.DetailedBreakdownTable
import com.example.ui.components.FloatingBottomBar
import com.example.ui.components.HistoryDialog
import com.example.ui.components.PayslipDialog
import com.example.ui.components.SalarySummaryHero
import com.example.ui.components.ShiftsAndOvertimeCard
import com.example.ui.components.TimesheetImportDialog
import com.example.ui.theme.HighDensityBg
import com.example.ui.theme.HighDensitySurfaceVariant
import com.example.ui.viewmodel.SalaryViewModel

@Composable
fun SalaryScreen(
    viewModel: SalaryViewModel = viewModel()
) {
    val context = LocalContext.current
    val inputState by viewModel.inputState.collectAsStateWithLifecycle()
    val calculationResult by viewModel.calculationResult.collectAsStateWithLifecycle()
    val savedRecords by viewModel.savedRecords.collectAsStateWithLifecycle()
    val snackbarMsg by viewModel.snackbarMessage.collectAsStateWithLifecycle()

    val snackbarHostState = remember { SnackbarHostState() }

    var showTimesheetModal by remember { mutableStateOf(false) }
    var showPayslipModal by remember { mutableStateOf(false) }
    var showHistoryModal by remember { mutableStateOf(false) }

    LaunchedEffect(snackbarMsg) {
        snackbarMsg?.let {
            snackbarHostState.showSnackbar(it)
            viewModel.clearSnackbarMessage()
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = HighDensityBg,
        contentWindowInsets = WindowInsets.safeDrawing,
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        bottomBar = {
            FloatingBottomBar(result = calculationResult)
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(HighDensityBg)
                .padding(innerPadding)
        ) {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(bottom = 16.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                item {
                    BaderTopBar(
                        onOpenTimesheetModal = { showTimesheetModal = true },
                        onOpenPayslipModal = { showPayslipModal = true },
                        onOpenHistoryModal = { showHistoryModal = true },
                        onSaveCalculation = { viewModel.saveCurrentCalculation() }
                    )
                }

                item {
                    SalarySummaryHero(result = calculationResult)
                }

                item {
                    BasicSettingsCard(
                        state = inputState,
                        hourlyRate = calculationResult.hourlyRate,
                        onNameChange = { viewModel.updateEmployeeName(it) },
                        onMonthChange = { viewModel.updateSalaryMonth(it) },
                        onRankSelect = { viewModel.selectRank(it) },
                        onBaseRateChange = { viewModel.updateBaseRate(it) },
                        onWorkDaysChange = { viewModel.updateWorkDays(it) }
                    )
                }

                item {
                    ShiftsAndOvertimeCard(
                        state = inputState,
                        onShift1DaysChange = { viewModel.updateShift1Days(it) },
                        onSatShift1DaysChange = { viewModel.updateSatShift1Days(it) },
                        onShift2DaysChange = { viewModel.updateShift2Days(it) },
                        onSatShift2DaysChange = { viewModel.updateSatShift2Days(it) },
                        onShift3DaysChange = { viewModel.updateShift3Days(it) },
                        onOvertimeHoursChange = { viewModel.updateOvertimeHours(it) }
                    )
                }

                item {
                    BonusesAndDeductionsCard(
                        state = inputState,
                        onExpBonusChange = { viewModel.updateExperienceBonusBrutto(it) },
                        onAttendanceToggle = { viewModel.toggleAttendanceBonus(it) },
                        onIndexationChange = { viewModel.updateIndexationBonusBrutto(it) },
                        onCoefChange = { viewModel.updateCoefficient(it) },
                        onQualityBaseChange = { viewModel.updateQualityPremiumBase(it) },
                        onExtraPaymentsChange = { viewModel.updateExtraPayments(it) },
                        onVacationChange = { viewModel.updateVacationPayment(it) },
                        onRegularDeductionChange = { viewModel.updateRegularPaymentDeduction(it) },
                        onAdvanceChange = { viewModel.updateAdvancePayment(it) }
                    )
                }

                item {
                    DetailedBreakdownTable(result = calculationResult)
                }

                item {
                    Spacer(modifier = Modifier.height(12.dp))
                }
            }
        }
    }

    // Modal Sheets
    if (showTimesheetModal) {
        TimesheetImportDialog(
            salaryMonth = inputState.salaryMonth,
            onDismiss = { showTimesheetModal = false },
            onApplyTimesheet = { rawText ->
                viewModel.parseAndApplyTimesheet(rawText)
            }
        )
    }

    if (showPayslipModal) {
        PayslipDialog(
            state = inputState,
            result = calculationResult,
            onDismiss = { showPayslipModal = false },
            onExportJson = { viewModel.exportCurrentJson() },
            onImportJson = { json -> viewModel.importJson(json) }
        )
    }

    if (showHistoryModal) {
        HistoryDialog(
            records = savedRecords,
            onLoadRecord = { record -> viewModel.loadSavedRecord(record) },
            onDeleteRecord = { id -> viewModel.deleteSavedRecord(id) },
            onDismiss = { showHistoryModal = false }
        )
    }
}
