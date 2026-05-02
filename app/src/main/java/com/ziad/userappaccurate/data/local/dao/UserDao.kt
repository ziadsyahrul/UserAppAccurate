package com.ziad.userappaccurate.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Upsert
import com.ziad.userappaccurate.data.local.entity.UserEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    @Query("SELECT * FROM users ORDER BY name ASC")
    fun getAllUsers(): Flow<List<UserEntity>>

    @Query("SELECT * FROM users WHERE name LIKE '%' || :query || '%' ORDER BY name ASC")
    fun searchUsers(query: String): Flow<List<UserEntity>>

    @Query("SELECT * FROM users WHERE city = :city ORDER BY name ASC")
    fun getUsersByCity(city: String): Flow<List<UserEntity>>

    @Query("""
        SELECT * FROM users 
        WHERE name LIKE '%' || :query || '%'
        AND (:city = '' OR city = :city)
        ORDER BY 
            CASE WHEN :sortAsc = 1 THEN name END ASC,
            CASE WHEN :sortAsc = 0 THEN name END DESC
    """)

    fun getFilteredUsers(
        query: String,
        city: String,
        sortAsc: Boolean
    ): Flow<List<UserEntity>>

    @Upsert
    suspend fun upsertUsers(users: List<UserEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: UserEntity)

    @Query("DELETE FROM users")
    suspend fun clearAll()
}