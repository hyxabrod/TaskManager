package com.mako.taskmngr.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.BiasAlignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import com.mako.taskmngr.R
import com.mako.taskmngr.core.theme.Dimens
import com.mako.taskmngr.core.theme.TaskManagerTheme
import com.mako.taskmngr.core.theme.TmTypography
import com.mako.taskmngr.core.theme.brand
import com.mako.taskmngr.core.theme.titleHuge
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(onNavigateToList: () -> Unit) {
    LaunchedEffect(Unit) {
        delay(2000)
        onNavigateToList()
    }

    TaskManagerTheme(darkTheme = true) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background),
            contentAlignment = BiasAlignment(
                horizontalBias = 0f,
                verticalBias = -0.15f
            )
        ) {
            Column(
                horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Box {
                    Text(
                        text = "Task\nManager",
                        style = TmTypography.titleHuge.copy(
                            fontFamily = FontFamily(Font(R.font.pacifico))
                        ),
                        color = MaterialTheme.colorScheme.brand,
                    )
                }

                Spacer(modifier = Modifier.height(Dimens.Padding32))

                CircularProgressIndicator(
                    modifier = Modifier.size(Dimens.LoadingIndicatorSize),
                    color = MaterialTheme.colorScheme.brand,
                    strokeWidth = Dimens.Padding4
                )
            }
        }
    }
}
