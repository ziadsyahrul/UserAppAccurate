package com.ziad.userappaccurate.domain.usecase

import com.ziad.userappaccurate.domain.model.User
import com.ziad.userappaccurate.domain.repository.IUserRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetFilteredUsersUseCase @Inject constructor(
    private val repository: IUserRepository
) {

    operator fun invoke(
        query: String = "",
        city: String = "",
        sortAscending: Boolean = true
    ): Flow<List<User>> = repository.getFilteredUsers(query, city, sortAscending)


}