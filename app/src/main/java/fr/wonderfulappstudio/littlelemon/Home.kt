package fr.wonderfulappstudio.littlelemon

import android.content.res.Configuration
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import fr.wonderfulappstudio.littlelemon.ui.theme.bigImageSize

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Home(navigateToProfile: () -> Unit) {
    Scaffold(topBar = {
        TopAppBar(
            title = { Text(text = "Little Lemon") },
            actions = {
                IconButton(
                    modifier = Modifier.clip(CircleShape),
                    onClick = navigateToProfile
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.profile),
                        contentDescription = "Profile",
                        contentScale = ContentScale.Fit
                    )
                }
            })
    }) {
        Box(
            modifier = Modifier
                .padding(paddingValues = it)
                .fillMaxSize(), contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "Logo",
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .size(bigImageSize)
            )
        }
    }
}

@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
fun HomePreview() {
    Home {}
}