package com.ziad.userappaccurate.data.mapper

import com.ziad.userappaccurate.data.local.entity.CityEntity
import com.ziad.userappaccurate.data.local.entity.UserEntity
import com.ziad.userappaccurate.data.remote.dto.AddUserRequest
import com.ziad.userappaccurate.data.remote.dto.CityDto
import com.ziad.userappaccurate.data.remote.dto.UserDto
import com.ziad.userappaccurate.domain.model.City
import com.ziad.userappaccurate.domain.model.User

fun UserDto.toEntity() = UserEntity(
    id = id, name = name, address = address,
    email = email, phoneNumber = phoneNumber,
    city = city, gender = gender
)

fun UserEntity.toDomain() = User(
    id = id, name = name, address = address,
    email = email, phoneNumber = phoneNumber,
    city = city, gender = gender
)

fun User.toRequest() = AddUserRequest(
    name = name, address = address, email = email,
    phoneNumber = phoneNumber, city = city, gender = gender
)

fun UserDto.toDomain() = User(
    id = id, name = name, address = address,
    email = email, phoneNumber = phoneNumber,
    city = city, gender = gender
)

fun CityDto.toEntity() = CityEntity(id = id, name = name)
fun CityEntity.toDomain() = City(id = id, name = name)