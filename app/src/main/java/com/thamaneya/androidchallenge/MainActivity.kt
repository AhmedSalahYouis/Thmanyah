package com.thamaneya.androidchallenge

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.exclude
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration.Indefinite
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTagsAsResourceId
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.thamaneya.androidchallenge.core.design.IbmPlexSansArabic
import com.thamaneya.androidchallenge.core.design.theme.ThmanyahTheme
import com.thamaneya.androidchallenge.core.network.NetworkMonitor
import com.thamaneya.androidchallenge.core.ui.app.ThmanyahAppState
import com.thamaneya.androidchallenge.navigation.AppNavHost
import com.thamaneya.logger.logging.ITimberLogger
import org.koin.android.ext.android.inject

class MainActivity : ComponentActivity() {
    private val networkMonitor: NetworkMonitor by inject()
    private val logger: ITimberLogger by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        logger.logInfo("MainActivity: onCreate started")

        setContent {
            ThmanyahTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    CompositionLocalProvider(
                        LocalTextStyle provides LocalTextStyle.current.copy(fontFamily = IbmPlexSansArabic)
                    ) {
                        Box {
                            val snackbarHostState = remember { SnackbarHostState() }
                            val appState = rememberThmanyahAppState(networkMonitor = networkMonitor)

                            val isOffline by appState.isOffline.collectAsStateWithLifecycle()

                            // If user is not connected to the internet show a snack bar to inform them.
                            val notConnectedMessage = stringResource(R.string.not_connected)
                            LaunchedEffect(isOffline) {
                                if (isOffline) {
                                    snackbarHostState.showSnackbar(
                                        message = notConnectedMessage,
                                        withDismissAction = true,
                                        duration = Indefinite,
                                    )
                                }
                            }

                            ThmanyahApp(appState, snackbarHostState)
                        }
                    }
                }


            }

            logger.logInfo("MainActivity: onCreate completed")
        }
    }

    @Composable
    fun ThmanyahApp(appState: ThmanyahAppState, snackbarHostState: SnackbarHostState) {

        Scaffold(
            modifier = Modifier.semantics {
                testTagsAsResourceId = true
            },
            containerColor = Color.Transparent,
            contentColor = MaterialTheme.colorScheme.onBackground,
            contentWindowInsets = WindowInsets(0, 0, 0, 0),
            snackbarHost = {
                SnackbarHost(
                    snackbarHostState,
                    modifier = Modifier.windowInsetsPadding(
                        WindowInsets.safeDrawing.exclude(
                            WindowInsets.ime,
                        ),
                    ),
                )
            },
        ) { padding ->
            Column(
                Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .consumeWindowInsets(padding)
                    .windowInsetsPadding(
                        WindowInsets.safeDrawing.only(
                            WindowInsetsSides.Horizontal,
                        ),
                    ),
            ) {
                Box {

                    AppNavHost(appState)
                }
            }
        }
    }

    @Preview(showBackground = true)
    @Composable
    fun ThmanyahAppPreview() {
        ThmanyahTheme {
            // Preview content
        }
    }
}