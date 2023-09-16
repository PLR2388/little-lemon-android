package fr.wonderfulappstudio.littlelemon

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.datastore.preferences.core.edit
import fr.wonderfulappstudio.littlelemon.ui.composable.LittleLemonMainButton
import fr.wonderfulappstudio.littlelemon.ui.composable.LittleLemonTopBar
import fr.wonderfulappstudio.littlelemon.ui.theme.mediumPadding
import fr.wonderfulappstudio.littlelemon.ui.theme.smallPadding
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Profile(navigateToOnboarding: () -> Unit) {

    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    var firstName: String by remember {
        mutableStateOf("")
    }
    var lastName: String by remember {
        mutableStateOf("")
    }
    var email: String by remember {
        mutableStateOf("")
    }

    LaunchedEffect(key1 = Unit) {
        val tripleFlow: Flow<Triple<String, String, String>> =
            context.dataStore.data.map { preferences ->
                Triple(
                    preferences[FIRST_NAME] ?: "",
                    preferences[LAST_NAME] ?: "",
                    preferences[EMAIL] ?: ""
                )
            }

        tripleFlow.collect {
            firstName = it.first
            lastName = it.second
            email = it.third
        }
    }

    Scaffold(topBar = { LittleLemonTopBar() }, bottomBar = {
        LittleLemonMainButton(text = stringResource(R.string.log_out)) {
            scope.launch {
                context.dataStore.edit { settings ->
                    settings[FIRST_NAME] = firstName
                    settings[LAST_NAME] = lastName
                    settings[EMAIL] = email
                    settings[IS_LOGGING] = false
                }
                navigateToOnboarding()
            }
        }
    }) { paddingValues ->
        LazyColumn(contentPadding = paddingValues) {
            item {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(smallPadding),
                    verticalArrangement = Arrangement.SpaceEvenly
                ) {
                    Text(
                        stringResource(R.string.personal_information),
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(mediumPadding))
                    OutlinedTextField(
                        value = firstName,
                        onValueChange = {},
                        readOnly = true,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(smallPadding))
                    OutlinedTextField(
                        value = lastName,
                        onValueChange = {},
                        readOnly = true,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(smallPadding))
                    OutlinedTextField(
                        value = email,
                        onValueChange = {},
                        readOnly = true,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun ProfilePreview() {
    Profile {}
}