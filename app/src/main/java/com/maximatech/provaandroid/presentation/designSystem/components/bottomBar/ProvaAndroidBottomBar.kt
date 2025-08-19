package com.maximatech.provaandroid.presentation.designSystem.components.bottomBar

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.maximatech.provaandroid.presentation.designSystem.tokens.AppColors

@Composable
fun ProvaAndroidBottomBar(
    modifier: Modifier = Modifier,
    menu: Set<BottomBarMenuItem>,
    onItemClicked: (BottomBarMenuItem) -> Unit,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(AppColors.BottomNavigation)
            .padding(vertical = 12.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        menu.forEach { item ->
            BottomBarButton(
                item = item,
                onItemClicked = onItemClicked
            )
        }
    }
}

@Composable
private fun BottomBarButton(
    item: BottomBarMenuItem,
    onItemClicked: (BottomBarMenuItem) -> Unit
) {
    val iconColor = if (item.isSelected) {
        AppColors.BottomNavigationActive
    } else {
        AppColors.BottomNavigationInactive
    }

    Column(
        modifier = Modifier
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) { onItemClicked(item) }
            .padding(vertical = 8.dp, horizontal = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        when {
            item.isSelected && item.activeIcon != null -> {
                Image(
                    painter = painterResource(id = item.activeIcon),
                    contentDescription = item.name,
                    modifier = Modifier.size(36.dp),
                    colorFilter = ColorFilter.tint(iconColor)
                )
            }
            !item.isSelected && item.inactiveIcon != null -> {
                Image(
                    painter = painterResource(id = item.inactiveIcon),
                    contentDescription = item.name,
                    modifier = Modifier.size(36.dp),
                    colorFilter = ColorFilter.tint(iconColor)
                )
            }
        }

        Text(
            text = item.name,
            color = iconColor,
            fontSize = 16.sp,
            modifier = Modifier.padding(top = 4.dp)
        )
    }
}