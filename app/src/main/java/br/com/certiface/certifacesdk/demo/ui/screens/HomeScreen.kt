package br.com.certiface.certifacesdk.demo.ui.screens

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.SkipNext
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.oiti.manager.exports.LivenessResult
import br.com.certiface.certifacesdk.demo.components.ProdutoToggleButtons
import br.com.certiface.certifacesdk.demo.domain.model.Features
import br.com.certiface.certifacesdk.demo.extensions.format
import br.com.certiface.certifacesdk.demo.ui.components.SquareActionCard
import br.com.certiface.certifacesdk.demo.ui.theme.CertifaceBlue
import br.com.certiface.certifacesdk.demo.ui.theme.CertifaceGreen
import br.com.certiface.certifacesdk.demo.ui.theme.CertifaceWhite

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    context: Context,
    appKey: String,
    onAppKeyChange: (String) -> Unit,
    selectedFeature: Features,
    onFeatureChange: (Features) -> Unit,
    isLoading: Boolean,
    onStartClick: (
        appKey: String,
        feature: Features,
        onSuccess: (LivenessResult?) -> Unit,
        onError: (String) -> Unit,
        isCustomEnabled: Boolean,
        useCustomView: Boolean,
        onLoadingChange: (Boolean) -> Unit
    ) -> Unit,
    onAddResult: (String) -> Unit,
    onLoadingChange: (Boolean) -> Unit
) {
    val scrollState = rememberScrollState()
    val features = Features.entries

    Column(
        modifier = modifier
            .fillMaxWidth()
            .verticalScroll(scrollState)
            .padding(horizontal = 16.dp, vertical = 12.dp)
    ) {
        // Header com fundo
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(14.dp))
                .background(
                    if (MaterialTheme.colorScheme.background == CertifaceWhite) {
                        MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.6f)
                    } else {
                        MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.35f)
                    }
                )
                .padding(horizontal = 18.dp, vertical = 16.dp)
        ) {
            Text(
                "Certiface SDK - Demo",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface,
                letterSpacing = 0.5.sp
            )
        }
        
        Spacer(Modifier.height(20.dp))

        // Card para configurações
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .shadow(
                    elevation = 2.dp,
                    shape = RoundedCornerShape(16.dp),
                    spotColor = if (MaterialTheme.colorScheme.background == CertifaceWhite) {
                        Color.Black.copy(alpha = 0.1f)
                    } else {
                        Color.Black.copy(alpha = 0.3f)
                    }
                )
                .clip(RoundedCornerShape(16.dp))
                .background(
                    if (MaterialTheme.colorScheme.background == CertifaceWhite) {
                        MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f)
                    } else {
                        MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
                    }
                )
                .padding(18.dp)
        ) {
            Column {
                OutlinedTextField(
                    value = appKey,
                    onValueChange = onAppKeyChange,
                    label = { Text("AppKey") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(),
                    enabled = !isLoading,
                    colors = androidx.compose.material3.OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = CertifaceBlue,
                        unfocusedBorderColor = androidx.compose.material3.MaterialTheme.colorScheme.outline
                    )
                )

                Spacer(Modifier.height(16.dp))
                
                ProdutoToggleButtons(
                    features = features,
                    selected = selectedFeature,
                    onSelect = onFeatureChange
                )
            }
        }
        
        Spacer(Modifier.height(24.dp))
        
        Text(
            "Testes",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // Card para os cards de teste
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .shadow(
                    elevation = 2.dp,
                    shape = RoundedCornerShape(16.dp),
                    spotColor = if (MaterialTheme.colorScheme.background == CertifaceWhite) {
                        Color.Black.copy(alpha = 0.1f)
                    } else {
                        Color.Black.copy(alpha = 0.3f)
                    }
                )
                .clip(RoundedCornerShape(16.dp))
                .background(
                    if (MaterialTheme.colorScheme.background == CertifaceWhite) {
                        MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f)
                    } else {
                        MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
                    }
                )
                .padding(14.dp)
        ) {
            // Grid de 2 colunas
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                SquareActionCard(
                    title = if (isLoading) "Iniciando..." else "Default",
                    description = "Teste padrão",
                    icon = Icons.Default.PlayArrow,
                    onClick = {
                        onLoadingChange(true)
                        onStartClick(
                            appKey,
                            selectedFeature,
                            { msg ->
                                onAddResult("OK: ${msg?.format()}")
                            },
                            { err ->
                                onAddResult("ERRO: $err")
                                onLoadingChange(false)
                            },
                            false,
                            false,
                            onLoadingChange
                        )
                    },
                    enabled = appKey.isNotBlank() && !isLoading,
                    modifier = Modifier.fillMaxWidth(),
                    primaryColor = CertifaceBlue
                )
                
                SquareActionCard(
                    title = if (isLoading) "Iniciando..." else "Custom View",
                    description = "Views customizadas",
                    icon = Icons.Default.Visibility,
                    onClick = {
                        onLoadingChange(true)
                        onStartClick(
                            appKey,
                            selectedFeature,
                            { msg ->
                                onAddResult("OK: ${msg?.format()}")
                            },
                            { err ->
                                onAddResult("ERRO: $err")
                                onLoadingChange(false)
                            },
                            true,
                            true,
                            onLoadingChange
                        )
                    },
                    enabled = appKey.isNotBlank() && !isLoading,
                    modifier = Modifier.fillMaxWidth(),
                    primaryColor = CertifaceBlue
                )
            }
            
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                SquareActionCard(
                    title = if (isLoading) "Iniciando..." else "Custom",
                    description = "Tema customizado",
                    icon = Icons.Default.Settings,
                    onClick = {
                        onLoadingChange(true)
                        onStartClick(
                            appKey,
                            selectedFeature,
                            { msg ->
                                onAddResult("OK: ${msg?.format()}")
                            },
                            { err ->
                                onAddResult("ERRO: $err")
                                onLoadingChange(false)
                            },
                            true,
                            false,
                            onLoadingChange
                        )
                    },
                    enabled = appKey.isNotBlank() && !isLoading,
                    modifier = Modifier.fillMaxWidth(),
                    primaryColor = CertifaceBlue
                )
                
                SquareActionCard(
                    title = if (isLoading) "Iniciando..." else "Sem Instruções",
                    description = "Pula instruções",
                    icon = Icons.Default.SkipNext,
                    onClick = {
                        onLoadingChange(true)
                        if (selectedFeature == Features.Document) {
                            onAddResult("ERRO: Document não suporta pular a tela de instruções.")
                            onLoadingChange(false)
                        } else {
                            br.com.certiface.certifacesdk.demo.LivenessExecutor(appKey, selectedFeature)
                                .executeLiveness(
                                    context = context,
                                    execOnSuccess = { result ->
                                        onLoadingChange(false)
                                        onAddResult("OK: ${result?.format()}")
                                    },
                                    execOnError = { throwable ->
                                        onLoadingChange(false)
                                        onAddResult("ERRO: ${throwable?.errorMessage ?: "erro desconhecido"}")
                                    },
                                    isCustomEnabled = true,
                                    useCustomView = false,
                                    showInstructionScreen = false
                                )
                        }
                    },
                    enabled = appKey.isNotBlank() && !isLoading,
                    modifier = Modifier.fillMaxWidth(),
                    primaryColor = CertifaceBlue
                )
            }
            }
        }
    }
}

