package fr.wonderfulappstudio.littlelemon.ui.composable

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import fr.wonderfulappstudio.littlelemon.R

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun LittleLemonTopBar(actions: @Composable RowScope.() -> Unit = {}, navigationIcon: @Composable () -> Unit = {}) {
    CenterAlignedTopAppBar(
        title = {
            Image(
                modifier = Modifier.height(50.dp),
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "Logo",
            )
        },
        modifier = Modifier.fillMaxWidth(),
        actions = actions,
        navigationIcon = navigationIcon
    )
}