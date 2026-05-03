package com.ziad.userappaccurate.domain.usecase

import com.google.common.truth.Truth.assertThat
import com.ziad.userappaccurate.domain.model.User
import com.ziad.userappaccurate.domain.repository.IUserRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class AddUserUseCaseTest {

    private lateinit var  addUserUseCase: AddUserUseCase
    private lateinit var  userRepository: IUserRepository

    @Before
    fun setup() {
        userRepository = mockk()
        addUserUseCase = AddUserUseCase(userRepository)
    }

    @Test
    fun `when name is empty, should return failure`() = runTest {
        val user = User(
            id = "",
            name = "",  // empty
            address = "Jakarta",
            email = "test@gmail.com",
            phoneNumber = "08123456789",
            city = "Jakarta",
            gender = 0
        )

        val result = addUserUseCase(user)

        assertThat(result.isFailure).isTrue()
        assertThat(result.exceptionOrNull()?.message).isEqualTo("Name cannot be empty")
    }

    @Test
    fun `when email is empty, should return failure`() = runTest {
        val user = User(
            id = "",
            name = "Ziad",
            address = "Jakarta",
            email = "",  // empty
            phoneNumber = "08123456789",
            city = "Jakarta",
            gender = 0
        )

        val result = addUserUseCase(user)

        assertThat(result.isFailure).isTrue()
        assertThat(result.exceptionOrNull()?.message).isEqualTo("Email cannot be empty")
    }

    @Test
    fun `when phone is empty, should return failure`() = runTest {
        val user = User(
            id = "",
            name = "Ziad",
            address = "Jakarta",
            email = "ziad@gmail.com",
            phoneNumber = "",  // empty
            city = "Jakarta",
            gender = 0
        )

        val result = addUserUseCase(user)

        assertThat(result.isFailure).isTrue()
        assertThat(result.exceptionOrNull()?.message).isEqualTo("Phone cannot be empty")
    }

    @Test
    fun `when city is empty, should return failure`() = runTest {
        val user = User(
            id = "",
            name = "Ziad",
            address = "Jakarta",
            email = "ziad@gmail.com",
            phoneNumber = "08123456789",
            city = "",  // empty
            gender = 0
        )

        val result = addUserUseCase(user)

        assertThat(result.isFailure).isTrue()
        assertThat(result.exceptionOrNull()?.message).isEqualTo("City cannot be empty")
    }

    @Test
    fun `when all fields are valid, should return success`() = runTest {
        val user = User(
            id = "",
            name = "Ziad",
            address = "Jakarta",
            email = "ziadsyahrulm@gmail.com",
            phoneNumber = "08123456789",
            city = "Jakarta",
            gender = 0
        )

        coEvery { userRepository.addUser(user) } returns Result.success(user)

        val result = addUserUseCase(user)

        assertThat(result.isSuccess).isTrue()
    }
}