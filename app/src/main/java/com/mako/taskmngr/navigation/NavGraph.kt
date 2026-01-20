package com.mako.taskmngr.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.mako.taskmngr.presentation.SplashScreen
import com.mako.taskmngr.presentation.task_details.TaskDetailsScreen
import com.mako.taskmngr.presentation.task_list.TasksListScreen
import kotlinx.serialization.Serializable

@Serializable
data object Splash

@Serializable
data object TasksGraph

@Serializable
data object List

@Serializable
data class Details(val taskId: Long)

@Composable
fun NavGraph() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Splash
    ) {
        composable<Splash> {
            SplashScreen(
                onNavigateToList = {
                    navController.navigate(TasksGraph) {
                        popUpTo(Splash) { inclusive = true }
                    }
                }
            )
        }

        navigation<TasksGraph>(startDestination = List) {
            composable<List> {
                TasksListScreen(
                    onTaskClick = { taskId ->
                        navController.navigate(Details(taskId)) {
                            launchSingleTop = true
                        }
                    }
                )
            }
            composable<Details> {
                TaskDetailsScreen(
                    onNavigateBack = { navController.safePopBackStack() }
                )
            }
        }
    }
}

fun NavController.safePopBackStack() {
    if (currentBackStackEntry?.lifecycle?.currentState == Lifecycle.State.RESUMED) {
        popBackStack()
    }
}