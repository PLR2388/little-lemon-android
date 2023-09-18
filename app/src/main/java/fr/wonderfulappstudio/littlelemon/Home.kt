package fr.wonderfulappstudio.littlelemon

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import fr.wonderfulappstudio.littlelemon.ui.composable.LittleLemonTopBar
import fr.wonderfulappstudio.littlelemon.ui.theme.darkGreen
import fr.wonderfulappstudio.littlelemon.ui.theme.smallPadding
import fr.wonderfulappstudio.littlelemon.ui.theme.yellow
import androidx.compose.runtime.livedata.observeAsState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Home(navigateToProfile: () -> Unit) {
    val context = LocalContext.current
    val menuItems by LittleLemonDatabase.getDatabase(context).menuItemDao().getMenuItems()
        .observeAsState(
            emptyList()
        )

    Scaffold(topBar = {
        LittleLemonTopBar {
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
        }
    }) { padding ->
        LazyColumn(contentPadding = padding) {
            item {
                Column(
                    modifier = Modifier
                        .background(darkGreen)
                        .padding(smallPadding)
                        .fillMaxWidth()
                ) {
                    Text(
                        "Little Lemon",
                        color = yellow,
                        fontSize = 32.sp,
                        fontWeight = FontWeight.Medium
                    )
                    Row(modifier = Modifier.fillMaxWidth()) {
                        Column(modifier = Modifier.weight(0.8f)) {
                            Text(
                                "Chicago",
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Medium,
                                color = Color.White
                            )
                            Text(
                                "We are a family-owned Mediterranean restaurant, focused on traditional recipes served with a modern twist",
                                textAlign = TextAlign.Start,
                                color = Color.White
                            )
                        }
                        Box(modifier = Modifier.weight(1f), contentAlignment = Alignment.Center) {
                            Image(
                                painter = painterResource(id = R.drawable.hero),
                                contentDescription = "hero",
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .size(100.dp)
                                    .clip(RoundedCornerShape(8.dp))
                            )
                        }
                    }
                }
            }
            items(menuItems, key = { it.id }) {
                MenuItem(menuItem = it)
            }
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun MenuItem(menuItem: MenuItem) {
    Divider()
    Column(modifier = Modifier.padding(smallPadding)) {
        Text(
            menuItem.title,
            fontWeight = FontWeight.Bold
        )
        Row {
            Column(modifier = Modifier.weight(0.8f)) {
                Text(
                    menuItem.description,
                    maxLines = 2
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text("$${menuItem.price}")
            }
            GlideImage(model = menuItem.image, contentDescription = null, modifier = Modifier
                .weight(0.2f)
                .size(50.dp))
        }

    }
}

@Preview(showBackground = true)
@Composable
fun MenuItemPreview() {
    MenuItem(menuItem = MenuItem(
        1,
        "Greek Salad",
        "The famous greek salad of crispy lettuce, peppers, olives, our Chicago.",
        "10",
        "https://github.com/Meta-Mobile-Developer-PC/Working-With-Data-API/blob/main/images/greekSalad.jpg?raw=true",
        "starters"
    ))
}

@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
fun HomePreview() {
    Home {}
}