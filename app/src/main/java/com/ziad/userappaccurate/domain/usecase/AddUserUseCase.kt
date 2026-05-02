package com.ziad.userappaccurate.domain.usecase

import com.ziad.userappaccurate.domain.model.User
import com.ziad.userappaccurate.domain.repository.IUserRepository
import javax.inject.Inject

class AddUserUseCase @Inject constructor(
    private val repository: IUserRepository
) {
    suspend operator fun invoke(user: User): Result<User> {
        if (user.name.isBlank()) return Result.failure(Exception("Name cannot be empty"))
        if (user.email.isBlank()) return Result.failure(Exception("Email cannot be empty"))
        if (user.phoneNumber.isBlank()) return Result.failure(Exception("Phone cannot be empty"))
        if (user.city.isBlank()) return Result.failure(Exception("City cannot be empty"))
        return repository.addUser(user)
    }
}