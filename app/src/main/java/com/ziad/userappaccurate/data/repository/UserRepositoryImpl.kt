package com.ziad.userappaccurate.data.repository

import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.logEvent
import com.ziad.userappaccurate.data.local.dao.UserDao
import com.ziad.userappaccurate.data.mapper.toDomain
import com.ziad.userappaccurate.data.mapper.toEntity
import com.ziad.userappaccurate.data.mapper.toRequest
import com.ziad.userappaccurate.data.remote.api.ApiService
import com.ziad.userappaccurate.domain.model.User
import com.ziad.userappaccurate.domain.repository.IUserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val userDao: UserDao,
    private val analytics: FirebaseAnalytics
) : IUserRepository {

    override fun getFilteredUsers(
        query: String,
        city: String,
        sortAscending: Boolean
    ): Flow<List<User>> =
        userDao.getFilteredUsers(query, city, sortAscending)
            .map { entities ->
                entities.map { it.toDomain() }
            }

    override suspend fun syncUsers(): Result<Unit> = runCatching {
        val users = apiService.getUsers()
        userDao.upsertUsers(users.map { it.toEntity() })
        analytics.logEvent("users_synced") {
            param("count", users.size.toLong())
        }
    }

    override suspend fun addUser(user: User): Result<User> = runCatching {
        val response = apiService.addUser(user.toRequest())
        userDao.insertUser(response.toEntity())
        analytics.logEvent("user_added") {
            param("city", user.city)
            param("gender", user.gender.toLong())
        }
        response.toDomain()
    }
}