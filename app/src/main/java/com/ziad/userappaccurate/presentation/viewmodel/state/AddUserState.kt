package com.ziad.userappaccurate.presentation.viewmodel.state

sealed class AddUserState {
    object Idle : AddUserState()
    object Loading : AddUserState()
    object Success : AddUserState()
    data class Error(val message: String) : AddUserState()
}