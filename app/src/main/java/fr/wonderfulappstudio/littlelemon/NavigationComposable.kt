package fr.wonderfulappstudio.littlelemon

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "little_lemon")

val FIRST_NAME = stringPreferencesKey("first_name")
val LAST_NAME = stringPreferencesKey("last_name")
val EMAIL = stringPreferencesKey("email")
val IS_LOGGING = booleanPreferencesKey("is_logging")

@Composable
fun Navigation(navHostController: NavHostController) {
    val context = LocalContext.current
    var isLogging: Boolean by remember {
        mutableStateOf(false)
    }

    LaunchedEffect(key1 = Unit) {
        val isLoggingFlow: Flow<Boolean> = context.dataStore.data.map { preferences ->
            preferences[IS_LOGGING] ?: false
        }
        isLoggingFlow.collect {
            isLogging = it
        }
    }

    NavHost(navController = navHostController, startDestination = if (isLogging) Home.route else Onboarding.route ) {
        composable(Onboarding.route) {
            Onboarding {
                navHostController.navigate(Home.route)
            }
        }
        composable(Home.route) {
            Home {
                navHostController.navigate(Profile.route)
            }
        }
        composable(Profile.route) {
            Profile(navigateToOnboarding = {
                navHostController.navigate(Onboarding.route)
            }) {
                navHostController.popBackStack()
            }
        }
    }
}