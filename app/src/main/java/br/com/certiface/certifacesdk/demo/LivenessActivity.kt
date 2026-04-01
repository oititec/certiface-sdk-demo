package br.com.certiface.certifacesdk.demo

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import br.com.certiface.certifacesdk.demo.components.LoadingDialog
import br.com.certiface.certifacesdk.demo.domain.model.Features
import br.com.certiface.certifacesdk.demo.ui.navigation.MainNavigation
import br.com.certiface.certifacesdk.demo.ui.navigation.NavigationTab
import br.com.certiface.certifacesdk.demo.ui.screens.CredentialScreen
import br.com.certiface.certifacesdk.demo.ui.screens.HomeScreen
import br.com.certiface.certifacesdk.demo.ui.screens.ResultScreen
import br.com.certiface.certifacesdk.demo.ui.theme.CertifaceSDKTheme
import br.com.certiface.certifacesdk.demo.utils.PreferencesHelper

class LivenessActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            CertifaceSDKTheme {
                MainLivenessScreen(context = this@LivenessActivity)
            }
        }
    }
}

@Composable
fun MainLivenessScreen(context: Context) {
    var selectedTab by remember { mutableIntStateOf(0) }
    var isLoading by remember { mutableStateOf(false) }
    var isLoadingAppKey by remember { mutableStateOf(false) }
    var appKey by remember { mutableStateOf("") }
    var selectedFeature by remember { mutableStateOf(Features.entries.first()) }
    val resultados = remember {
        mutableStateListOf<String>().apply {
            addAll(PreferencesHelper.getResultsHistory(context))
        }
    }

    Scaffold(
        bottomBar = {
            MainNavigation(
                selectedTab = selectedTab,
                onTabSelected = { selectedTab = it }
            )
        }
    ) { innerPadding ->
        LoadingDialog(
            isLoading = isLoading || isLoadingAppKey,
            onTimeout = { }
        ) {
            when (NavigationTab.entries[selectedTab]) {
                NavigationTab.HOME -> {
                    HomeScreen(
                        modifier = Modifier.padding(innerPadding),
                        context = context,
                        appKey = appKey,
                        onAppKeyChange = { appKey = it },
                        selectedFeature = selectedFeature,
                        onFeatureChange = { selectedFeature = it },
                        isLoading = isLoading,
                        onStartClick = { key, feature, onSuccess, onError, isCustom, useCustom, onLoading ->
                            onLoading(true)
                            if (feature == Features.Document) {
                                DocumentExecutor(key).executeDocument(
                                    context = context,
                                    execOnSuccess = { result ->
                                        onLoading(false)
                                        val livenessResult = result?.let { docResult ->
                                            when (docResult) {
                                                is br.com.certiface.doc.domain.models.CertifaceDocResult.Success -> {
                                                    br.com.certiface.manager.exports.LivenessResult(
                                                        valid = true,
                                                        codID = docResult.documentCode.toString(),
                                                        cause = null,
                                                        protocol = null,
                                                        scanResultBlob = null
                                                    )
                                                }

                                                else -> null
                                            }
                                        }
                                        onSuccess(livenessResult)
                                    },
                                    execOnError = { throwable ->
                                        onLoading(false)
                                        onError(throwable?.errorMessage ?: "erro desconhecido")
                                    },
                                    isCustomEnabled = isCustom,
                                    useCustomView = useCustom
                                )
                            } else {
                                LivenessExecutor(key, feature).executeLiveness(
                                    context = context,
                                    execOnSuccess = { result ->
                                        onLoading(false)
                                        onSuccess(result)
                                    },
                                    execOnError = { throwable ->
                                        onLoading(false)
                                        onError(throwable?.errorMessage ?: "erro desconhecido")
                                    },
                                    isCustomEnabled = isCustom,
                                    useCustomView = useCustom,
                                    showInstructionScreen = true
                                )
                            }
                        },
                        onAddResult = { resultado ->
                            resultados.add(resultado)
                            PreferencesHelper.saveResultsHistory(context, resultados.toList())
                            if (!resultado.startsWith("AppKey salva:")) {
                                selectedTab = NavigationTab.RESULTS.ordinal
                            }
                        },
                        onLoadingChange = { isLoading = it }
                    )
                }

                NavigationTab.CREDENTIAL -> {
                    CredentialScreen(
                        modifier = Modifier.padding(innerPadding),
                        context = context,
                        onAppKeySaved = { savedAppKey ->
                            appKey = savedAppKey
                            resultados.add("AppKey salva: $savedAppKey")
                            PreferencesHelper.saveResultsHistory(context, resultados.toList())
                            selectedTab = NavigationTab.HOME.ordinal
                        }
                    )
                }

                NavigationTab.RESULTS -> {
                    ResultScreen(
                        modifier = Modifier.padding(innerPadding),
                        context = context,
                        resultados = resultados.toList(),
                        onClearResults = {
                            resultados.clear()
                            PreferencesHelper.clearResultsHistory(context)
                        }
                    )
                }
            }
        }
    }
}
