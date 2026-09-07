package com.example.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.calculator.SalaryCalculator
import com.example.data.local.AppDatabase
import com.example.data.local.SalaryRecord
import com.example.data.model.RankPresets
import com.example.data.model.SalaryCalculationResult
import com.example.data.model.SalaryInputState
import com.example.data.model.TimesheetParseResult
import com.example.data.repository.SalaryRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.time.YearMonth
import java.time.format.DateTimeFormatter

class SalaryViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: SalaryRepository
    val savedRecords: StateFlow<List<SalaryRecord>>

    private val _inputState = MutableStateFlow(SalaryInputState())
    val inputState: StateFlow<SalaryInputState> = _inputState.asStateFlow()

    private val _calculationResult = MutableStateFlow(SalaryCalculationResult())
    val calculationResult: StateFlow<SalaryCalculationResult> = _calculationResult.asStateFlow()

    private val _lastParsedTimesheet = MutableStateFlow<TimesheetParseResult?>(null)
    val lastParsedTimesheet: StateFlow<TimesheetParseResult?> = _lastParsedTimesheet.asStateFlow()

    private val _snackbarMessage = MutableStateFlow<String?>(null)
    val snackbarMessage: StateFlow<String?> = _snackbarMessage.asStateFlow()

    init {
        val db = AppDatabase.getDatabase(application)
        repository = SalaryRepository(db.salaryRecordDao(), application)
        savedRecords = repository.allRecords.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

        // Load previous state or initialize current month
        val saved = repository.loadLastStateFromPrefs()
        if (saved != null) {
            _inputState.value = saved
        } else {
            val currentMonth = YearMonth.now().format(DateTimeFormatter.ofPattern("yyyy-MM"))
            _inputState.value = SalaryInputState(salaryMonth = currentMonth)
        }
        recalculate()
    }

    private fun recalculate() {
        val res = SalaryCalculator.calculate(_inputState.value)
        _calculationResult.value = res
        repository.saveLastStateToPrefs(_inputState.value)
    }

    fun updateEmployeeName(name: String) {
        _inputState.value = _inputState.value.copy(employeeName = name)
        recalculate()
    }

    fun updateSalaryMonth(month: String) {
        _inputState.value = _inputState.value.copy(salaryMonth = month)
        recalculate()
    }

    fun selectRank(rankNumber: Int) {
        val rankInfo = RankPresets.getRank(rankNumber)
        _inputState.value = _inputState.value.copy(
            rank = rankNumber,
            baseRate = rankInfo.netto,
            coefficient = rankInfo.coef,
            advancePayment = rankInfo.advance
        )
        recalculate()
    }

    fun updateBaseRate(rate: Double) {
        _inputState.value = _inputState.value.copy(baseRate = rate)
        recalculate()
    }

    fun updateWorkDays(days: Int) {
        _inputState.value = _inputState.value.copy(workDays = days)
        recalculate()
    }

    fun updateExperienceBonusBrutto(amount: Double) {
        _inputState.value = _inputState.value.copy(experienceBonusBrutto = amount)
        recalculate()
    }

    fun toggleAttendanceBonus(enabled: Boolean) {
        _inputState.value = _inputState.value.copy(attendanceBonus = enabled)
        recalculate()
    }

    fun updateIndexationBonusBrutto(amount: Double) {
        _inputState.value = _inputState.value.copy(indexationBonusBrutto = amount)
        recalculate()
    }

    fun updateCoefficient(coef: Double) {
        _inputState.value = _inputState.value.copy(coefficient = coef)
        recalculate()
    }

    fun updateQualityPremiumBase(base: Double) {
        _inputState.value = _inputState.value.copy(qualityPremiumBase = base)
        recalculate()
    }

    fun updateExtraPayments(amount: Double) {
        _inputState.value = _inputState.value.copy(extraPayments = amount)
        recalculate()
    }

    fun updateVacationPayment(amount: Double) {
        _inputState.value = _inputState.value.copy(vacationPayment = amount)
        recalculate()
    }

    fun updateRegularPaymentDeduction(amount: Double) {
        _inputState.value = _inputState.value.copy(regularPaymentDeduction = amount)
        recalculate()
    }

    fun updateShift1Days(days: Int) {
        _inputState.value = _inputState.value.copy(shift1Days = days)
        recalculate()
    }

    fun updateSatShift1Days(days: Int) {
        _inputState.value = _inputState.value.copy(satShift1Days = days)
        recalculate()
    }

    fun updateShift2Days(days: Int) {
        _inputState.value = _inputState.value.copy(shift2Days = days)
        recalculate()
    }

    fun updateSatShift2Days(days: Int) {
        _inputState.value = _inputState.value.copy(satShift2Days = days)
        recalculate()
    }

    fun updateShift3Days(days: Int) {
        _inputState.value = _inputState.value.copy(shift3Days = days)
        recalculate()
    }

    fun updateOvertimeHours(hours: Double) {
        _inputState.value = _inputState.value.copy(overtimeHours = hours)
        recalculate()
    }

    fun updateAdvancePayment(amount: Double) {
        _inputState.value = _inputState.value.copy(advancePayment = amount)
        recalculate()
    }

    fun parseAndApplyTimesheet(rawText: String): TimesheetParseResult {
        val result = SalaryCalculator.parseTimesheet(rawText, _inputState.value.salaryMonth)
        _lastParsedTimesheet.value = result

        var updated = _inputState.value.copy(
            shift1Days = result.shift1Days,
            shift2Days = result.shift2Days,
            satShift1Days = result.satShift1Days,
            satShift2Days = result.satShift2Days,
            extraPayments = result.calculatedExtraPayments,
            attendanceBonus = !result.hasAbsence
        )

        if (!result.employeeName.isNullOrBlank()) {
            updated = updated.copy(employeeName = result.employeeName)
        }

        _inputState.value = updated
        recalculate()
        _snackbarMessage.value = "Табель успішно оброблено!"
        return result
    }

    fun saveCurrentCalculation() {
        viewModelScope.launch {
            val state = _inputState.value
            val res = _calculationResult.value
            repository.saveRecord(
                state = state,
                totalAccrued = res.totalAccrued,
                totalAccruedBrutto = res.totalAccruedBrutto,
                totalToPay = res.totalToPay,
                workedHours = res.totalWorkedHours
            )
            _snackbarMessage.value = "Звіт збережено в історію"
        }
    }

    fun loadSavedRecord(record: SalaryRecord) {
        try {
            val state = SalaryRepository.jsonToState(record.jsonState)
            _inputState.value = state
            recalculate()
            _snackbarMessage.value = "Завантажено звіт за ${record.month}"
        } catch (e: Exception) {
            _snackbarMessage.value = "Помилка завантаження звіту"
        }
    }

    fun deleteSavedRecord(id: Long) {
        viewModelScope.launch {
            repository.deleteRecord(id)
            _snackbarMessage.value = "Звіт видалено з історії"
        }
    }

    fun clearSnackbarMessage() {
        _snackbarMessage.value = null
    }

    fun exportCurrentJson(): String {
        return SalaryRepository.stateToJson(_inputState.value)
    }

    fun importJson(json: String): Boolean {
        return try {
            val state = SalaryRepository.jsonToState(json)
            _inputState.value = state
            recalculate()
            _snackbarMessage.value = "Дані успішно імпортовано!"
            true
        } catch (e: Exception) {
            _snackbarMessage.value = "Помилка формату JSON!"
            false
        }
    }

    fun resetToDefaults() {
        val currentMonth = YearMonth.now().format(DateTimeFormatter.ofPattern("yyyy-MM"))
        val rank8 = RankPresets.getRank(8)
        _inputState.value = SalaryInputState(
            salaryMonth = currentMonth,
            rank = 8,
            baseRate = rank8.netto,
            coefficient = rank8.coef,
            advancePayment = rank8.advance
        )
        recalculate()
        _snackbarMessage.value = "Скинуто до початкових налаштувань"
    }
}
