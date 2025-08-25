package com.thamaneya.androidchallenge

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarDuration.Indefinite
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.thamaneya.androidchallenge.core.network.NetworkMonitor
import com.thamaneya.androidchallenge.core.ui.app.ThmanyahAppState
import com.thamaneya.androidchallenge.navigation.AppNavHost
import com.thamaneya.androidchallenge.ui.theme.ThmanyahTheme
import com.thamaneya.logger.logging.ITimberLogger
import org.koin.android.ext.android.inject

class MainActivity : ComponentActivity() {
    private val networkMonitor: NetworkMonitor by inject()
    private val logger: ITimberLogger by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        logger.logInfo("MainActivity: onCreate started")

        setContent {
            val appState = rememberThmanyahAppState(networkMonitor = networkMonitor)
            val navController = rememberNavController()
            ThmanyahTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ThmanyahApp(
                        appState = appState,
                        content = {
                            AppNavHost(appState)
                        })
                }
            }


        }

        logger.logInfo("MainActivity: onCreate completed")
    }
}

@Composable
fun ThmanyahApp(
    appState: ThmanyahAppState,
    content: @Composable () -> Unit
) {
    val snackbarHostState = remember { SnackbarHostState() }

    // If user is not connected to the internet show a snack bar to inform them.
    val notConnectedMessage = "offline"
    LaunchedEffect(appState.isOffline) {
        if (appState.isOffline.value) {
            snackbarHostState.showSnackbar(
                message = notConnectedMessage,
                duration = Indefinite
            )
        } else {
            // Auto-dismiss when back online
            snackbarHostState.currentSnackbarData?.dismiss()
        }
    }
    content()
}

@Preview(showBackground = true)
@Composable
fun ThmanyahAppPreview() {
    ThmanyahTheme {
        // Preview content
    }
}