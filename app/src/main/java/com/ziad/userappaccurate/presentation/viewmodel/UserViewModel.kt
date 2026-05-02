package com.ziad.userappaccurate.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.logEvent
import com.ziad.userappaccurate.domain.model.City
import com.ziad.userappaccurate.domain.model.User
import com.ziad.userappaccurate.domain.usecase.AddUserUseCase
import com.ziad.userappaccurate.domain.usecase.GetCityUseCase
import com.ziad.userappaccurate.domain.usecase.GetFilteredUsersUseCase
import com.ziad.userappaccurate.domain.usecase.SyncUsersUseCase
import com.ziad.userappaccurate.presentation.viewmodel.state.AddUserState
import com.ziad.userappaccurate.presentation.viewmodel.state.SyncState
import com.ziad.userappaccurate.presentation.viewmodel.state.UserListState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val getFilteredUsersUseCase: GetFilteredUsersUseCase,
    private val syncUsersUseCase: SyncUsersUseCase,
    private val addUserUseCase: AddUserUseCase,
    private val getCitiesUseCase: GetCityUseCase,
    private val analytics: FirebaseAnalytics
) : ViewModel() {

    private val _searchQuery = MutableStateFlow("")
    private val _selectedCity = MutableStateFlow("")
    private val _sortAscending = MutableStateFlow(true)

    val searchQuery = _searchQuery.asStateFlow()
    val selectedCity = _selectedCity.asStateFlow()
    val sortAscending = _sortAscending.asStateFlow()

    val users: StateFlow<UserListState> = combine(
        _searchQuery, _selectedCity, _sortAscending
    ) { query, city, sort -> Triple(query, city, sort) }
        .debounce(300)
        .flatMapLatest { (query, city, sort) ->
            getFilteredUsersUseCase(query, city, sort)
                .map<List<User>, UserListState> { UserListState.Success(it) }
                .catch { emit(UserListState.Error(it.message ?: "Error")) }
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), UserListState.Loading)

    val cities: StateFlow<List<City>> = getCitiesUseCase()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    private val _addUserState = MutableStateFlow<AddUserState>(AddUserState.Idle)
    val addUserState = _addUserState.asStateFlow()

    private val _syncState = MutableStateFlow<SyncState>(SyncState.Idle)
    val syncState = _syncState.asStateFlow()

    private val _snackbarEvent = MutableSharedFlow<String>()
    val snackbarEvent = _snackbarEvent.asSharedFlow()

    init {
        syncData()
    }

    fun syncData() {
        viewModelScope.launch {
            _syncState.value = SyncState.Loading
            syncUsersUseCase().fold(
                onSuccess = { _syncState.value = SyncState.Success },
                onFailure = {
                    _syncState.value = SyncState.Error(it.message ?: "Sync failed")
                    _snackbarEvent.emit("Offline mode - Showing cached data")
                }
            )
        }
    }

    fun onSearchQueryChange(query: String) {
        _searchQuery.value = query
        analytics.logEvent("search_user") {
            param("query", query)
        }
    }

    fun onCityFilterChange(city: String) {
        _selectedCity.value = city
        analytics.logEvent("filter_by_city") {
            param("city", city)
        }
    }

    fun toggleSort() {
        _sortAscending.value = !_sortAscending.value
    }

    fun addUser(user: User) {
        viewModelScope.launch {
            _addUserState.value = AddUserState.Loading
            addUserUseCase(user).fold(
                onSuccess = { _addUserState.value = AddUserState.Success },
                onFailure = {
                    _addUserState.value = AddUserState.Error(it.message ?: "Failed")
                    _snackbarEvent.emit(it.message ?: "Failed to add user")
                }
            )
        }
    }

    fun resetAddUserState() {
        _addUserState.value = AddUserState.Idle
    }
}