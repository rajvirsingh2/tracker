package com.example.tracker.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.tracker.data.viewmodel.AuthViewModel
import com.example.tracker.core.util.Result
import com.example.tracker.ui.screen.DashboardScreen
import com.example.tracker.ui.screen.ExpenseScreen
import com.example.tracker.ui.screen.LoginScreen
import com.example.tracker.ui.screen.RegisterScreen

object Routes {
    const val LOGIN = "login"
    const val REGISTER = "register"
    const val DASHBOARD = "dashboard"
    const val ADD_EXPENSE = "add_expense"
}

@Composable
fun AppNavigator(authViewModel: AuthViewModel =hiltViewModel()){
    val navController=rememberNavController()
    val userState by authViewModel.userState.collectAsState()

    val startDestination = when (val state = userState) {
        is Result.Success -> if (state.data != null) Routes.DASHBOARD else Routes.LOGIN
        is Result.Error -> Routes.LOGIN
        is Result.Loading -> "loading_screen"
    }

    if(startDestination=="loading_screen"){
        return
    }
    NavHost(
        navController,
        startDestination=startDestination
    ){
        composable(Routes.LOGIN){
            LoginScreen(
                onLoginSuccess = {
                    navController.navigate(Routes.DASHBOARD){
                        popUpTo(Routes.LOGIN){inclusive=true}
                    }
                },
                onNavigateToRegister = {navController.navigate(Routes.REGISTER)}
            )
        }
        composable(Routes.REGISTER) {
            RegisterScreen(
                onRegistrationSuccess = {
                    navController.navigate(Routes.DASHBOARD) {
                        popUpTo(Routes.LOGIN) { inclusive = true }
                    }
                },
                onNavigateToLogin = { navController.popBackStack() }
            )
        }
        composable(Routes.DASHBOARD) {
            DashboardScreen(
                viewModel = hiltViewModel(),
                onAddExpense = { navController.navigate(Routes.ADD_EXPENSE) },
                onLogout = {
                    navController.navigate(Routes.LOGIN) {
                        popUpTo(Routes.DASHBOARD) { inclusive = true }
                    }
                }
            )
        }
        composable(Routes.ADD_EXPENSE) {
            ExpenseScreen(
                onAddExpense = { navController.popBackStack() }
            )
        }
    }
}