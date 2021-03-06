package com.arthurnagy.staysafe.feature.newdocument.statement.personaldata

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import com.arthurnagy.staysafe.feature.newdocument.NewDocumentViewModel
import com.arthurnagy.staysafe.feature.shared.formatToDate
import com.arthurnagy.staysafe.feature.shared.mediatorLiveData

class StatementPersonalDataViewModel(private val newDocumentViewModel: NewDocumentViewModel) : ViewModel() {

    private val pendingStatement: LiveData<NewDocumentViewModel.PendingStatement> get() = newDocumentViewModel.pendingStatement
    val lastName = mediatorLiveData("", pendingStatement) { currentValue, pendingStatement ->
        if (pendingStatement.lastName != currentValue) {
            pendingStatement.lastName
        } else {
            currentValue
        }
    }
    val firstName = mediatorLiveData("", pendingStatement) { currentValue, pendingStatement ->
        if (pendingStatement.firstName != currentValue) {
            pendingStatement.firstName
        } else {
            currentValue
        }
    }
    val address = mediatorLiveData("", pendingStatement) { currentValue, pendingStatement ->
        if (pendingStatement.address != currentValue) {
            pendingStatement.address
        } else {
            currentValue
        }
    }
    val birthDate: LiveData<Long?> = pendingStatement.map { it.birthDate }
    val birthDateFormatted: LiveData<String> = birthDate.map { it?.let { formatToDate(it) } ?: "" }
    val isNextEnabled: LiveData<Boolean> = pendingStatement.map(::areStatementPersonalDataValid)

    init {
        lastName.observeForever {
            if (pendingStatement.value?.lastName != it) {
                newDocumentViewModel.updateStatement { copy(lastName = it) }
            }
        }
        firstName.observeForever {
            if (pendingStatement.value?.firstName != it) {
                newDocumentViewModel.updateStatement { copy(firstName = it) }
            }
        }
        address.observeForever {
            if (pendingStatement.value?.address != it) {
                newDocumentViewModel.updateStatement { copy(address = it) }
            }
        }
    }

    private fun areStatementPersonalDataValid(pendingStatement: NewDocumentViewModel.PendingStatement) =
        !pendingStatement.firstName.isNullOrEmpty() && !pendingStatement.lastName.isNullOrEmpty() &&
            !pendingStatement.address.isNullOrBlank() && pendingStatement.birthDate != null

    fun onBirthDateSelected(timestamp: Long) {
        newDocumentViewModel.updateStatement { copy(birthDate = timestamp) }
    }
}