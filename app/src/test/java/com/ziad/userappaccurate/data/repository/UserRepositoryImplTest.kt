package com.ziad.userappaccurate.data.repository

import com.google.common.truth.Truth.assertThat
import com.google.firebase.analytics.FirebaseAnalytics
import com.ziad.userappaccurate.data.local.dao.UserDao
import com.ziad.userappaccurate.data.remote.api.ApiService
import com.ziad.userappaccurate.data.remote.dto.UserDto
import com.ziad.userappaccurate.domain.model.User
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class UserRepositoryImplTest {

    private lateinit var userRepositoryImpl: UserRepositoryImpl
    private lateinit var apiService: ApiService
    private lateinit var userDao: UserDao
    private lateinit var analytics: FirebaseAnalytics

    private val fakeUserDto = UserDto(
        id = "1",
        name = "Ziad",
        address = "Jakarta",
        email = "ziadsyahrulm@gmail.com",
        phoneNumber = "08123456789",
        city = "Jakarta",
        gender = 0
    )

    private val fakeUser = User(
        id = "",
        name = "Ziad",
        address = "Jakarta",
        email = "ziadsyahrulm@gmail.com",
        phoneNumber = "08123456789",
        city = "Jakarta",
        gender = 0
    )

    @Before
    fun setup() {
        apiService = mockk()
        userDao = mockk(relaxed = true)
        analytics = mockk(relaxed = true)
        userRepositoryImpl = UserRepositoryImpl(apiService, userDao, analytics)
    }

    @Test
    fun `syncUsers success, should save to database`() = runTest {
        coEvery { apiService.getUsers() } returns listOf(fakeUserDto)
        coEvery { userDao.upsertUsers(any()) } just runs

        val result = userRepositoryImpl.syncUsers()

        coVerify { userDao.upsertUsers(any()) }
    }

    @Test
    fun `syncUsers failed, should return failure`() = runTest {
        coEvery { apiService.getUsers() } throws Exception("No internet connection")

        val result = userRepositoryImpl.syncUsers()

        assertThat(result.isFailure).isTrue()
        assertThat(result.exceptionOrNull()?.message).isEqualTo("No internet connection")
    }

    @Test
    fun `addUser success, should save to database`() = runTest {
        coEvery { apiService.addUser(any()) } returns fakeUserDto
        coEvery { userDao.insertUser(any()) } just runs

        val result = userRepositoryImpl.addUser(fakeUser)

        coVerify { userDao.insertUser(any()) }
    }
}