package br.com.certiface.certifacesdk.demo.examples

import android.net.Uri
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.filled.FileUpload
import androidx.compose.material.icons.filled.QrCodeScanner
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.certiface.doc.designsystem.theme.ApplyStatusBarStyle
import br.com.certiface.doc.designsystem.ui.builders.CustomScreensBuilder
import br.com.certiface.doc.designsystem.ui.model.components.CameraPreviewComponents
import br.com.certiface.doc.designsystem.ui.model.components.QrScannerComponents
import br.com.certiface.doc.designsystem.ui.model.state.CaptureState
import br.com.certiface.doc.designsystem.ui.model.state.DocumentUploadState
import br.com.certiface.doc.designsystem.ui.model.state.QrScanState
import br.com.certiface.doc.domain.theme.CertifaceDocTheme

object ModernCustomScreensExample {
    fun createModernTheme(): CertifaceDocTheme {
        return CertifaceDocTheme.build {
            setCustomScreens(
                CustomScreensBuilder.build {
                    setCustomInstructionComposable { callbacks ->
                        ModernInstructionScreen(
                            onDocumentClick = callbacks.onDocumentClick,
                            onDigitalClick = callbacks.onDigitalClick,
                            onDocumentFileClick = callbacks.onDocumentFileClick,
                            isCnhDigital = callbacks.isCnhDigital,
                            isUploadFile = callbacks.isUploadFile,
                            onBackClick = callbacks.onBack
                        )
                    }

                    setCustomPermissionComposable { callbacks ->
                        ModernPermissionScreen(
                            onPermissionGranted = callbacks.onPermissionGranted,
                            onBack = callbacks.onBack
                        )
                    }

                    setCustomCaptureComposable { cameraPreview, cameraPreviewComponents, captureState ->
                        ModernCaptureScreen(
                            cameraPreview = cameraPreview,
                            cameraPreviewComponents = cameraPreviewComponents,
                            captureState = captureState
                        )
                    }

                    setCustomQrComposable { qrScanner, qrScannerComponents, qrScanState ->
                        ModernQrScreen(
                            qrScanner = qrScanner,
                            qrScannerComponents = qrScannerComponents,
                            qrScanState = qrScanState
                        )
                    }

                    setCustomDocumentUploadComposable { documentUploadState ->
                        ModernUploadScreen(documentUploadState)
                    }

                    setCustomCnhUploadComposable { documentUploadState ->
                        ModernUploadScreen(documentUploadState)
                    }

                    setCustomResultComposable { resultState ->
                        ModernResultScreen(
                            success = resultState.success,
                            errorMessage = resultState.errorMessage,
                            showRetryButton = resultState.showRetryButton,
                            onRetry = resultState.onRetry,
                            onSuccess = resultState.onSuccess,
                            onError = resultState.onError
                        )
                    }

                    setCustomLoadingDialogComposable { message, progress ->
                        ModernLoadingDialog(
                            message = message,
                            progress = progress
                        )
                    }
                }
            )
        }
    }
}

private const val INSTRUCTION_CARD_PADDING_DP = 20
private const val INSTRUCTION_CARD_ICON_SIZE_DP = 64
private const val INSTRUCTION_CARD_ICON_INNER_DP = 52
private const val INSTRUCTION_CARD_MIN_ROW_HEIGHT_DP = 88

@Composable
private fun InstructionOptionCard(
    onClick: () -> Unit,
    iconGradientColors: List<Color>,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    title: String,
    subtitle: String
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        elevation = CardDefaults.cardElevation(16.dp),
        shape = RoundedCornerShape(28.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(INSTRUCTION_CARD_PADDING_DP.dp)
                .heightIn(min = INSTRUCTION_CARD_MIN_ROW_HEIGHT_DP.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(INSTRUCTION_CARD_ICON_SIZE_DP.dp)
                    .background(
                        brush = Brush.linearGradient(
                            colors = iconGradientColors
                        ),
                        shape = CircleShape
                    ),
                contentAlignment = Alignment.Center
            ) {
                Box(
                    modifier = Modifier
                        .size(INSTRUCTION_CARD_ICON_INNER_DP.dp)
                        .background(
                            brush = Brush.radialGradient(
                                colors = listOf(
                                    Color.White.copy(alpha = 0.3f),
                                    Color.Transparent
                                ),
                                radius = 50f
                            ),
                            shape = CircleShape
                        )
                )
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(32.dp)
                )
            }

            Spacer(modifier = Modifier.padding(16.dp))

            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(vertical = 4.dp),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF212121),
                    fontSize = 20.sp
                )
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = subtitle,
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color(0xFF757575),
                    fontSize = 14.sp,
                    maxLines = 2
                )
            }

            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = null,
                tint = Color(0xFF757575),
                modifier = Modifier
                    .size(24.dp)
                    .scale(scaleX = -1f, scaleY = 1f)
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ModernInstructionScreen(
    onDocumentClick: () -> Unit,
    onDigitalClick: () -> Unit,
    onDocumentFileClick: () -> Unit = {},
    isCnhDigital: Boolean = false,
    isUploadFile: Boolean = false,
    onBackClick: () -> Unit
) {
    val gradient = Brush.verticalGradient(
        colors = listOf(
            Color(0xFF4CAF50),
            Color(0xFF2196F3)
        )
    )

    ApplyStatusBarStyle(
        color = Color(0xFF4CAF50),
        darkTheme = false
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Verificação de Documento",
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Voltar",
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Transparent
                )
            )
        },
        containerColor = Color.Transparent
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(gradient)
                .padding(paddingValues)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 24.dp)
                    .padding(top = 32.dp, bottom = 48.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(bottom = 24.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(100.dp)
                            .background(
                                brush = Brush.radialGradient(
                                    colors = listOf(
                                        Color.White.copy(alpha = 0.2f),
                                        Color.Transparent
                                    ),
                                    radius = 150f
                                ),
                                shape = CircleShape
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Box(
                            modifier = Modifier
                                .size(80.dp)
                                .background(
                                    brush = Brush.linearGradient(
                                        colors = listOf(
                                            Color.White.copy(alpha = 0.3f),
                                            Color.White.copy(alpha = 0.1f)
                                        )
                                    ),
                                    shape = CircleShape
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Description,
                                contentDescription = null,
                                tint = Color.White,
                                modifier = Modifier.size(48.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    Text(
                        text = "Como deseja verificar?",
                        style = MaterialTheme.typography.headlineLarge,
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        fontSize = 24.sp
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "Escolha uma das opções abaixo",
                        style = MaterialTheme.typography.bodyLarge,
                        color = Color.White.copy(alpha = 0.9f),
                        textAlign = TextAlign.Center
                    )
                }

                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(20.dp)
                ) {
                    InstructionOptionCard(
                        onClick = onDocumentClick,
                        iconGradientColors = listOf(
                            Color(0xFF2196F3),
                            Color(0xFF1976D2)
                        ),
                        icon = Icons.Filled.CameraAlt,
                        title = "Documento Físico",
                        subtitle = "Capture a frente e o verso do documento"
                    )

                    if (isCnhDigital) {
                        InstructionOptionCard(
                            onClick = onDigitalClick,
                            iconGradientColors = listOf(
                                Color(0xFF4CAF50),
                                Color(0xFF388E3C)
                            ),
                            icon = Icons.Filled.QrCodeScanner,
                            title = "CNH Digital",
                            subtitle = "Escaneie o QR Code ou envie o arquivo"
                        )
                    }

                    if (isUploadFile) {
                        InstructionOptionCard(
                            onClick = onDocumentFileClick,
                            iconGradientColors = listOf(
                                Color(0xFFFF9800),
                                Color(0xFFF57C00)
                            ),
                            icon = Icons.Filled.FileUpload,
                            title = "Enviar Arquivo",
                            subtitle = "Selecione e envie um PDF do documento para verificação"
                        )
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ModernPermissionScreen(
    onPermissionGranted: () -> Unit,
    onBack: () -> Unit
) {
    ApplyStatusBarStyle(
        color = Color.White,
        darkTheme = true
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Permissão Necessária") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, "Voltar")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Box(
                modifier = Modifier
                    .size(120.dp)
                    .background(
                        color = Color(0xFF2196F3).copy(alpha = 0.1f),
                        shape = CircleShape
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Filled.CameraAlt,
                    contentDescription = null,
                    tint = Color(0xFF2196F3),
                    modifier = Modifier.size(64.dp)
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            Text(
                text = "Precisamos da permissão da câmera",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Para capturar o documento, precisamos acessar sua câmera",
                style = MaterialTheme.typography.bodyLarge,
                color = Color(0xFF757575),
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(48.dp))

            Button(
                onClick = onPermissionGranted,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF2196F3)
                )
            ) {
                Text(
                    "Conceder Permissão",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    }
}

@Composable
fun ModernCaptureScreen(
    cameraPreview: @Composable () -> Unit,
    cameraPreviewComponents: CameraPreviewComponents,
    captureState: CaptureState
) {
    ApplyStatusBarStyle(
        color = Color(0xFF1A1A1A),
        darkTheme = false
    )

    val scale by animateFloatAsState(
        targetValue = if (captureState.isLoading) 0.9f else 1f,
        animationSpec = tween(300), label = ""
    )

    Scaffold(
        containerColor = Color(0xFF1A1A1A)
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = if (captureState.currentScreen == 0) "Capture a frente" else "Capture o verso",
                    style = MaterialTheme.typography.headlineSmall,
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 24.dp)
                )

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .scale(scale),
                    contentAlignment = Alignment.Center
                ) {
                    cameraPreview()
                }

                Spacer(modifier = Modifier.height(32.dp))

                if (captureState.showPreview && captureState.previewBase64 != null) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        Button(
                            onClick = captureState.onRetakeCapture,
                            modifier = Modifier.weight(1f),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFF424242)
                            ),
                            shape = RoundedCornerShape(16.dp)
                        ) {
                            Icon(Icons.Filled.Refresh, "Refazer", modifier = Modifier.size(20.dp))
                            Spacer(modifier = Modifier.padding(4.dp))
                            Text("Refazer")
                        }

                        Button(
                            onClick = captureState.onAcceptCapture,
                            modifier = Modifier.weight(1f),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFF4CAF50)
                            ),
                            shape = RoundedCornerShape(16.dp)
                        ) {
                            Icon(
                                Icons.Filled.CheckCircle,
                                "Aceitar",
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(modifier = Modifier.padding(4.dp))
                            Text(if (captureState.currentScreen == 0) "Continuar" else "Concluir")
                        }
                    }
                } else {
                    Button(
                        onClick = captureState.onCaptureButtonClick,
                        enabled = !captureState.isLoading,
                        modifier = Modifier.size(80.dp),
                        shape = CircleShape,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF2196F3)
                        )
                    ) {
                        if (captureState.isLoading) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(32.dp),
                                color = Color.White,
                                strokeWidth = 3.dp
                            )
                        } else {
                            Icon(
                                Icons.Filled.CameraAlt,
                                "Capturar",
                                modifier = Modifier.size(40.dp),
                                tint = Color.White
                            )
                        }
                    }
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp, start = 16.dp, end = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                IconButton(
                    onClick = captureState.onBack,
                    modifier = Modifier
                        .size(48.dp)
                        .background(
                            color = Color.Black.copy(alpha = 0.5f),
                            shape = CircleShape
                        )
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Voltar",
                        tint = Color.White,
                        modifier = Modifier.size(24.dp)
                    )
                }

                IconButton(
                    onClick = captureState.onClose,
                    modifier = Modifier
                        .size(48.dp)
                        .background(
                            color = Color.Black.copy(alpha = 0.5f),
                            shape = CircleShape
                        )
                ) {
                    Icon(
                        imageVector = Icons.Filled.Close,
                        contentDescription = "Fechar",
                        tint = Color.White,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ModernQrScreen(
    qrScanner: @Composable () -> Unit,
    qrScannerComponents: QrScannerComponents,
    qrScanState: QrScanState
) {
    ApplyStatusBarStyle(
        color = Color.Black,
        darkTheme = false
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Escaneie o QR Code",
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                },
                navigationIcon = {
                    IconButton(onClick = qrScanState.onBack) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            "Voltar",
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Transparent
                )
            )
        },
        containerColor = Color.Transparent
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black)
                .padding(paddingValues)
        ) {
            Box(
                modifier = Modifier.fillMaxSize()
            ) {
                qrScanner()
            }

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        brush = Brush.radialGradient(
                            colors = listOf(
                                Color.Transparent,
                                Color.Black.copy(alpha = 0.4f),
                                Color.Black.copy(alpha = 0.7f)
                            ),
                            radius = 800f
                        )
                    )
            )

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(48.dp),
                contentAlignment = Alignment.Center
            ) {
                Box(
                    modifier = Modifier
                        .size(280.dp)
                        .border(
                            width = 3.dp,
                            color = Color(0xFF4CAF50),
                            shape = RoundedCornerShape(24.dp)
                        )
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(2.dp)
                            .border(
                                width = 2.dp,
                                color = Color(0xFF2196F3),
                                shape = RoundedCornerShape(22.dp)
                            )
                    )
                }
            }

            Column(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                Color.Transparent,
                                Color.Black.copy(alpha = 0.95f)
                            )
                        )
                    )
                    .padding(32.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier
                        .size(80.dp)
                        .background(
                            brush = Brush.verticalGradient(
                                colors = listOf(
                                    Color(0xFF4CAF50),
                                    Color(0xFF2196F3)
                                )
                            ),
                            shape = CircleShape
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Filled.QrCodeScanner,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(40.dp)
                    )
                }

                Spacer(modifier = Modifier.height(20.dp))

                Text(
                    text = "Posicione o QR code",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Mantenha o código dentro da área destacada",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color(0xFFB0B0B0),
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(32.dp))

                Button(
                    onClick = qrScanState.onUploadFileClick,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF4CAF50)
                    ),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Icon(
                        Icons.Filled.FileUpload,
                        "Enviar",
                        modifier = Modifier.size(22.dp),
                        tint = Color.White
                    )
                    Spacer(modifier = Modifier.padding(8.dp))
                    Text(
                        "Enviar Arquivo Manualmente",
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 16.sp,
                        color = Color.White
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ModernUploadScreen(state: DocumentUploadState) {
    ModernUploadScreen(
        selectedFile = state.selectedFile,
        onSelectFile = state.onSelectFile,
        onDeleteFile = state.onDeleteFile,
        onSendFile = state.onSendFile,
        onBack = state.onBack
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ModernUploadScreen(
    selectedFile: Uri?,
    onSelectFile: () -> Unit,
    onDeleteFile: () -> Unit,
    onSendFile: (Uri?) -> Unit,
    onBack: () -> Unit
) {
    val gradient = Brush.verticalGradient(
        colors = listOf(
            Color(0xFF4CAF50),
            Color(0xFF2196F3)
        )
    )

    ApplyStatusBarStyle(
        color = Color(0xFF4CAF50),
        darkTheme = false
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Enviar Documento",
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            "Voltar",
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Transparent
                )
            )
        },
        containerColor = Color.Transparent
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(gradient)
                .padding(paddingValues)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                if (selectedFile == null) {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth(0.9f),
                        elevation = CardDefaults.cardElevation(24.dp),
                        shape = RoundedCornerShape(32.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = Color.White
                        )
                    ) {
                        Column(
                            modifier = Modifier.padding(40.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(140.dp)
                                    .background(
                                        brush = Brush.radialGradient(
                                            colors = listOf(
                                                Color(0xFF4CAF50).copy(alpha = 0.2f),
                                                Color(0xFF2196F3).copy(alpha = 0.1f)
                                            )
                                        ),
                                        shape = CircleShape
                                    ),
                                contentAlignment = Alignment.Center
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(100.dp)
                                        .background(
                                            brush = Brush.verticalGradient(
                                                colors = listOf(
                                                    Color(0xFF4CAF50),
                                                    Color(0xFF2196F3)
                                                )
                                            ),
                                            shape = CircleShape
                                        ),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        imageVector = Icons.Filled.FileUpload,
                                        contentDescription = null,
                                        tint = Color.White,
                                        modifier = Modifier.size(56.dp)
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.height(32.dp))

                            Text(
                                text = "Selecione o arquivo",
                                style = MaterialTheme.typography.headlineMedium,
                                fontWeight = FontWeight.Bold,
                                textAlign = TextAlign.Center,
                                color = Color(0xFF212121)
                            )

                            Spacer(modifier = Modifier.height(12.dp))

                            Text(
                                text = "Escolha o arquivo da sua CNH digital para continuar",
                                style = MaterialTheme.typography.bodyLarge,
                                color = Color(0xFF757575),
                                textAlign = TextAlign.Center
                            )

                            Spacer(modifier = Modifier.height(40.dp))

                            Button(
                                onClick = onSelectFile,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(56.dp),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Color(0xFF2196F3)
                                ),
                                shape = RoundedCornerShape(16.dp)
                            ) {
                                Icon(
                                    Icons.Filled.FileUpload,
                                    "Selecionar",
                                    modifier = Modifier.size(24.dp),
                                    tint = Color.White
                                )
                                Spacer(modifier = Modifier.padding(8.dp))
                                Text(
                                    "Selecionar Arquivo",
                                    fontWeight = FontWeight.SemiBold,
                                    fontSize = 16.sp
                                )
                            }
                        }
                    }
                } else {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth(0.9f),
                        elevation = CardDefaults.cardElevation(24.dp),
                        shape = RoundedCornerShape(32.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = Color.White
                        )
                    ) {
                        Column(
                            modifier = Modifier.padding(32.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(100.dp)
                                    .background(
                                        brush = Brush.linearGradient(
                                            colors = listOf(
                                                Color(0xFF4CAF50),
                                                Color(0xFF45A049)
                                            )
                                        ),
                                        shape = CircleShape
                                    ),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Filled.Description,
                                    contentDescription = null,
                                    tint = Color.White,
                                    modifier = Modifier.size(56.dp)
                                )
                            }

                            Spacer(modifier = Modifier.height(24.dp))

                            Text(
                                text = "Arquivo Selecionado",
                                style = MaterialTheme.typography.titleLarge,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF212121)
                            )

                            Spacer(modifier = Modifier.height(8.dp))

                            Text(
                                text = selectedFile.lastPathSegment ?: "Arquivo",
                                style = MaterialTheme.typography.bodyLarge,
                                color = Color(0xFF757575),
                                textAlign = TextAlign.Center,
                                maxLines = 2,
                                overflow = TextOverflow.Ellipsis
                            )

                            Spacer(modifier = Modifier.height(32.dp))

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(12.dp)
                            ) {
                                Button(
                                    onClick = onDeleteFile,
                                    modifier = Modifier
                                        .weight(1f)
                                        .height(56.dp),
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = Color(0xFFF44336)
                                    ),
                                    shape = RoundedCornerShape(16.dp)
                                ) {
                                    Icon(
                                        Icons.Filled.Delete,
                                        "Remover",
                                        modifier = Modifier.size(20.dp),
                                        tint = Color.White
                                    )
                                    Spacer(modifier = Modifier.padding(6.dp))
                                    Text(
                                        "Remover",
                                        fontWeight = FontWeight.SemiBold,
                                        color = Color.White
                                    )
                                }

                                Button(
                                    onClick = { onSendFile(selectedFile) },
                                    modifier = Modifier
                                        .weight(1f)
                                        .height(56.dp),
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = Color(0xFF4CAF50)
                                    ),
                                    shape = RoundedCornerShape(16.dp)
                                ) {
                                    Icon(
                                        Icons.Filled.FileUpload,
                                        "Enviar",
                                        modifier = Modifier.size(20.dp),
                                        tint = Color.White
                                    )
                                    Spacer(modifier = Modifier.padding(6.dp))
                                    Text(
                                        "Enviar",
                                        fontWeight = FontWeight.SemiBold,
                                        color = Color.White
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ModernResultScreen(
    success: Boolean,
    errorMessage: String?,
    showRetryButton: Boolean,
    onRetry: () -> Unit,
    onSuccess: () -> Unit,
    onError: (String) -> Unit
) {
    LaunchedEffect(success, errorMessage) {
        if (success) {
            onSuccess()
        } else {
            errorMessage?.let { onError(it) }
        }
    }

    val iconScale by animateFloatAsState(
        targetValue = 1f,
        animationSpec = tween(600, delayMillis = 200), label = ""
    )

    val cardScale by animateFloatAsState(
        targetValue = 1f,
        animationSpec = tween(400), label = ""
    )

    val bgGradient = if (success) {
        Brush.radialGradient(
            colors = listOf(
                Color(0xFF00E676),
                Color(0xFF00C853),
                Color(0xFF009624)
            ),
            radius = 1200f
        )
    } else {
        Brush.radialGradient(
            colors = listOf(
                Color(0xFFFF5252),
                Color(0xFFE53935),
                Color(0xFFB71C1C)
            ),
            radius = 1200f
        )
    }

    ApplyStatusBarStyle(
        color = if (success) Color(0xFF00C853) else Color(0xFFE53935),
        darkTheme = false
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(bgGradient)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .scale(cardScale),
                elevation = CardDefaults.cardElevation(24.dp),
                shape = RoundedCornerShape(32.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White
                )
            ) {
                Column(
                    modifier = Modifier.padding(40.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box(
                        modifier = Modifier
                            .size(100.dp)
                            .background(
                                color = if (success) Color(0xFF00E676).copy(alpha = 0.1f) else Color(
                                    0xFFFF5252
                                ).copy(alpha = 0.1f),
                                shape = CircleShape
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = if (success) Icons.Filled.CheckCircle else Icons.Filled.Error,
                            contentDescription = null,
                            tint = if (success) Color(0xFF00C853) else Color(0xFFE53935),
                            modifier = Modifier
                                .size(60.dp)
                                .scale(iconScale)
                        )
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    Text(
                        text = if (success) {
                            "Verificação Concluída!"
                        } else {
                            "Ops, algo deu errado"
                        },
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF212121),
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = if (success) {
                            "Seu documento foi verificado com sucesso"
                        } else {
                            errorMessage
                                ?: "Não foi possível verificar seu documento. Tente novamente."
                        },
                        style = MaterialTheme.typography.bodyLarge,
                        color = Color(0xFF757575),
                        textAlign = TextAlign.Center
                    )

                    if (showRetryButton && !success) {
                        Spacer(modifier = Modifier.height(32.dp))

                        Button(
                            onClick = onRetry,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(56.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFFE53935)
                            ),
                            shape = RoundedCornerShape(16.dp)
                        ) {
                            Icon(
                                Icons.Filled.Refresh,
                                "Tentar",
                                modifier = Modifier.size(20.dp),
                                tint = Color.White
                            )
                            Spacer(modifier = Modifier.padding(4.dp))
                            Text(
                                "Tentar Novamente",
                                color = Color.White,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ModernLoadingDialog(
    message: String?,
    progress: Float?
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.radialGradient(
                    colors = listOf(
                        Color(0xFF1A1A1A).copy(alpha = 0.95f),
                        Color.Black.copy(alpha = 0.9f)
                    )
                )
            ),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Box(
                modifier = Modifier.size(120.dp),
                contentAlignment = Alignment.Center
            ) {
                Box(
                    modifier = Modifier
                        .size(100.dp)
                        .background(
                            brush = Brush.verticalGradient(
                                colors = listOf(
                                    Color(0xFF4CAF50),
                                    Color(0xFF2196F3)
                                )
                            ),
                            shape = CircleShape
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(80.dp),
                        color = Color.White,
                        strokeWidth = 6.dp,
                        trackColor = Color.White.copy(alpha = 0.2f)
                    )
                }
            }

            message?.let {
                Spacer(modifier = Modifier.height(32.dp))
                Text(
                    text = it,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    textAlign = TextAlign.Center
                )
            }

            progress?.let { progressValue ->
                Spacer(modifier = Modifier.height(32.dp))

                Card(
                    modifier = Modifier.fillMaxWidth(0.7f),
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color.White.copy(alpha = 0.1f)
                    )
                ) {
                    Column(
                        modifier = Modifier.padding(20.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        LinearProgressIndicator(
                            progress = { progressValue },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(8.dp)
                                .clip(RoundedCornerShape(4.dp)),
                            color = Color(0xFF2196F3),
                            trackColor = Color.White.copy(alpha = 0.3f)
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        Text(
                            text = "${(progressValue * 100).toInt()}%",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    }
                }
            }
        }
    }
}

