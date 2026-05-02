package com.ziad.userappaccurate.domain.repository

import com.ziad.userappaccurate.domain.model.User
import kotlinx.coroutines.flow.Flow

interface IUserRepository {

    fun getFilteredUsers(
        query: String,
        city: String,
        sortAscending: Boolean
    ): Flow<List<User>>

    suspend fun syncUsers(): Result<Unit>
    suspend fun addUser(user: User): Result<User>
}