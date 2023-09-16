package fr.wonderfulappstudio.littlelemon

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.datastore.preferences.core.edit
import fr.wonderfulappstudio.littlelemon.ui.composable.LittleLemonMainButton
import fr.wonderfulappstudio.littlelemon.ui.composable.LittleLemonTopBar
import fr.wonderfulappstudio.littlelemon.ui.theme.mediumPadding
import fr.wonderfulappstudio.littlelemon.ui.theme.smallPadding
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Onboarding(navigateToHome: () -> Unit) {
    var firstName by remember {
        mutableStateOf("")
    }
    var lastName by remember {
        mutableStateOf("")
    }
    var email by remember {
        mutableStateOf("")
    }

    val context = LocalContext.current
    val scope = rememberCoroutineScope()


    Scaffold(topBar = {
        LittleLemonTopBar()
    }, bottomBar = {
        LittleLemonMainButton(text = "Register") {
            if (email.isBlank() || lastName.isBlank() || firstName.isBlank()) {
                Toast.makeText(
                    context,
                    "Registration unsuccessful. Please enter all data.",
                    Toast.LENGTH_LONG
                ).show()
            } else {
                scope.launch {
                    context.dataStore.edit { settings ->
                        settings[FIRST_NAME] = firstName
                        settings[LAST_NAME] = lastName
                        settings[EMAIL] = email
                        settings[IS_LOGGING] = true
                    }
                }
                navigateToHome()
            }
        }
    }) { padding ->
        LazyColumn(contentPadding = padding) {
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(100.dp)
                        .background(Color(0xFF496E57)),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Let's get to know you",
                        color = Color.White,
                        fontSize = 28.sp
                    )
                }
            }
            item {
                Column(
                    verticalArrangement = Arrangement.SpaceAround,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(smallPadding)
                ) {
                    Text(stringResource(id = R.string.personal_information), fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(mediumPadding))
                    OutlinedTextField(
                        value = firstName,
                        onValueChange = { firstName = it },
                        modifier = Modifier.fillMaxWidth(),
                        placeholder = { Text(text = stringResource(R.string.first_name)) }
                    )
                    Spacer(modifier = Modifier.height(smallPadding))
                    OutlinedTextField(
                        value = lastName,
                        onValueChange = { lastName = it },
                        modifier = Modifier.fillMaxWidth(),
                        placeholder = { Text(text = stringResource(R.string.last_name)) }
                    )
                    Spacer(modifier = Modifier.height(smallPadding))
                    OutlinedTextField(
                        value = email,
                        onValueChange = { email = it },
                        modifier = Modifier.fillMaxWidth(),
                        placeholder = { Text(text = stringResource(R.string.email)) }
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun OnboardingPreview() {
    Onboarding {}
}