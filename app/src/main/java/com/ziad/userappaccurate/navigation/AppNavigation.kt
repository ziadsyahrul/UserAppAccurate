package com.ziad.userappaccurate.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ziad.userappaccurate.presentation.ui.adduser.AddUserScreen
import com.ziad.userappaccurate.presentation.ui.userlist.UserListScreen
import com.ziad.userappaccurate.presentation.viewmodel.UserViewModel

@Composable
fun AppNavigation(viewModel: UserViewModel = hiltViewModel()) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Screen.UserList.route) {
        composable(Screen.UserList.route) {
            UserListScreen(
                viewModel = viewModel,
                onAddUserClick = { navController.navigate(Screen.AddUser.route) }
            )
        }
        composable(Screen.AddUser.route) {
            AddUserScreen(
                viewModel = viewModel,
                onBack = { navController.popBackStack() }
            )
        }
    }
}