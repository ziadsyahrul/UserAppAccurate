package com.ziad.userappaccurate.presentation.viewmodel

import com.google.common.truth.Truth.assertThat
import com.ziad.userappaccurate.domain.model.City
import com.ziad.userappaccurate.domain.model.User
import com.ziad.userappaccurate.domain.usecase.AddUserUseCase
import com.ziad.userappaccurate.domain.usecase.GetCityUseCase
import com.ziad.userappaccurate.domain.usecase.GetFilteredUsersUseCase
import com.ziad.userappaccurate.domain.usecase.SyncUsersUseCase
import com.ziad.userappaccurate.presentation.viewmodel.state.AddUserState
import com.ziad.userappaccurate.presentation.viewmodel.state.SyncState
import com.google.firebase.analytics.FirebaseAnalytics
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class UserViewModelTest {

    private lateinit var viewModel: UserViewModel
    private lateinit var getFilteredUsersUseCase: GetFilteredUsersUseCase
    private lateinit var syncUsersUseCase: SyncUsersUseCase
    private lateinit var addUserUseCase: AddUserUseCase
    private lateinit var getCityUseCase: GetCityUseCase
    private lateinit var analytics: FirebaseAnalytics

    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        getFilteredUsersUseCase = mockk()
        syncUsersUseCase = mockk()
        addUserUseCase = mockk()
        getCityUseCase = mockk()
        analytics = mockk(relaxed = true)

        coEvery { getFilteredUsersUseCase(any(), any(), any()) } returns flowOf(emptyList())
        coEvery { syncUsersUseCase() } returns Result.success(Unit)
        coEvery { getCityUseCase() } returns flowOf(emptyList<City>())

        viewModel = UserViewModel(
            getFilteredUsersUseCase,
            syncUsersUseCase,
            addUserUseCase,
            getCityUseCase,
            analytics
        )
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `syncData success, should update syncState to Success`() = runTest {
        coEvery { syncUsersUseCase() } returns Result.success(Unit)

        viewModel.syncData()
        testDispatcher.scheduler.advanceUntilIdle()

        assertThat(viewModel.syncState.value).isEqualTo(SyncState.Success)
    }

    @Test
    fun `syncData failed, should update syncState to Error`() = runTest {
        coEvery { syncUsersUseCase() } returns Result.failure(Exception("No internet"))

        viewModel.syncData()
        testDispatcher.scheduler.advanceUntilIdle()

        assertThat(viewModel.syncState.value).isInstanceOf(SyncState.Error::class.java)
        assertThat((viewModel.syncState.value as SyncState.Error).message).isEqualTo("No internet")
    }

    @Test
    fun `addUser with empty name, should update addUserState to Error`() = runTest {
        val user = User(
            id = "", name = "", address = "Jakarta",
            email = "ziad@gmail.com", phoneNumber = "08123456789",
            city = "Jakarta", gender = 0
        )

        coEvery { addUserUseCase(user) } returns Result.failure(Exception("Name cannot be empty"))

        viewModel.addUser(user)
        testDispatcher.scheduler.advanceUntilIdle()

        assertThat(viewModel.addUserState.value).isInstanceOf(AddUserState.Error::class.java)
        assertThat((viewModel.addUserState.value as AddUserState.Error).message).isEqualTo("Name cannot be empty")
    }

    @Test
    fun `addUser success, should update addUserState to Success`() = runTest {
        val user = User(
            id = "", name = "Ziad", address = "Jakarta",
            email = "ziadsyahrulm@gmail.com", phoneNumber = "08123456789",
            city = "Jakarta", gender = 0
        )

        coEvery { addUserUseCase(user) } returns Result.success(user)

        viewModel.addUser(user)
        testDispatcher.scheduler.advanceUntilIdle()

        assertThat(viewModel.addUserState.value).isEqualTo(AddUserState.Success)
    }

    @Test
    fun `toggleSort, should toggle sortAscending from true to false`() = runTest {
        assertThat(viewModel.sortAscending.value).isTrue()
        viewModel.toggleSort()
        assertThat(viewModel.sortAscending.value).isFalse()
    }

    @Test
    fun `toggleSort twice, should return back to true`() = runTest {
        viewModel.toggleSort()
        viewModel.toggleSort()
        assertThat(viewModel.sortAscending.value).isTrue()
    }

    @Test
    fun `resetAddUserState, should reset to Idle`() = runTest {
        viewModel.resetAddUserState()
        assertThat(viewModel.addUserState.value).isEqualTo(AddUserState.Idle)
    }
}