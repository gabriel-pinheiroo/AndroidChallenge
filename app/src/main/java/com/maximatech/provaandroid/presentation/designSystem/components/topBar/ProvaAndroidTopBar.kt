package com.maximatech.provaandroid.presentation.designSystem.components.topBar

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.maximatech.provaandroid.presentation.designSystem.tokens.AppColors

@Composable
fun ProvaAndroidTopBar(
    modifier: Modifier = Modifier,
    config: TopBarConfig = TopBarConfig(),
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(AppColors.Primary)
            .windowInsetsPadding(WindowInsets.statusBars),
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .padding(horizontal = 16.dp)
        ) {
            Row(
                modifier = Modifier
                    .align(Alignment.CenterStart)
            ) {
                if (config.showBackButton) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        modifier = Modifier
                            .clickable(
                                interactionSource = remember { MutableInteractionSource() },
                                indication = null
                            ) { config.onBackClicked() }
                            .size(24.dp),
                        contentDescription = "Voltar",
                        tint = AppColors.OnPrimary
                    )

                    Spacer(modifier = Modifier.width(16.dp))
                }

                if (config.showTitle) {
                    Text(
                        text = config.title,
                        color = AppColors.OnPrimary,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Medium,
                    )
                }
            }

            if (config.showMenuButton) {
                Icon(
                    imageVector = Icons.Default.MoreVert,
                    modifier = Modifier
                        .align(Alignment.CenterEnd)
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null
                        ) { config.onMenuClicked() }
                        .size(24.dp),
                    contentDescription = "Menu",
                    tint = AppColors.OnPrimary
                )
            }
        }
    }
}