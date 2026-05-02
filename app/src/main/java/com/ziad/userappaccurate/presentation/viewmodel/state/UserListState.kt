package com.ziad.userappaccurate.presentation.viewmodel.state

import com.ziad.userappaccurate.domain.model.User

sealed class UserListState {
    object Loading : UserListState()
    data class Success(val users: List<User>) : UserListState()
    data class Error(val message: String) : UserListState()
}
