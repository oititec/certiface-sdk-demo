package br.com.certiface.certifacesdk.demo.examples

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.filled.Face
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
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.certiface.doc.designsystem.theme.ApplyStatusBarStyle
import br.com.oiti.iproov.presentation.ui.builders.IProovCustomScreensBuilder
import br.com.oiti.manager.exports.IProovTheme

object IProovModernCustomScreensExample {
    fun createModernTheme(): IProovTheme {
        return IProovTheme.build {
            setCustomScreens(
                IProovCustomScreensBuilder.build {
                    setCustomInstructionComposable { callbacks ->
                        IProovModernInstructionScreen(
                            onStartClick = callbacks.onStartClick,
                            onBack = callbacks.onBack
                        )
                    }

                    setCustomPermissionComposable { callbacks ->
                        IProovModernPermissionScreen(
                            onPermissionGranted = callbacks.onPermissionGranted,
                            onBack = callbacks.onBack
                        )
                    }

                    setCustomLoadingDialogComposable { message, progress ->
                        IProovModernLoadingDialog(
                            message = message,
                            progress = progress
                        )
                    }

                    setCustomResultComposable { resultState ->
                        IProovModernResultScreen(
                            success = resultState.success,
                            errorMessage = resultState.errorMessage,
                            showRetryButton = resultState.showRetryButton,
                            reason = resultState.reason,
                            onRetry = resultState.onRetry
                        )
                    }
                }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun IProovModernInstructionScreen(
    onStartClick: () -> Unit,
    onBack: () -> Unit
) {
    // Cores específicas do IProov - Verde e Roxo
    val gradient = Brush.verticalGradient(
        colors = listOf(
            Color(0xFF4CAF50), // Verde
            Color(0xFF66BB6A), // Verde claro
            Color(0xFF81C784)  // Verde mais claro
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
                        "Verificação Facial - IProov",
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
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
                    .fillMaxSize()
                    .padding(horizontal = 24.dp)
                    .padding(top = 32.dp, bottom = 48.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(32.dp)
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(bottom = 16.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(120.dp)
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
                                .size(96.dp)
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
                                imageVector = Icons.Filled.Face,
                                contentDescription = null,
                                tint = Color.White,
                                modifier = Modifier.size(56.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(32.dp))

                    Text(
                        text = "Verificação Facial IProov",
                        style = MaterialTheme.typography.headlineLarge,
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        fontSize = 28.sp
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "Siga as instruções na tela para completar sua verificação facial usando tecnologia IProov",
                        style = MaterialTheme.typography.bodyLarge,
                        color = Color.White.copy(alpha = 0.9f),
                        textAlign = TextAlign.Center
                    )
                }

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp),
                    elevation = CardDefaults.cardElevation(16.dp),
                    shape = RoundedCornerShape(28.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color.White
                    )
                ) {
                    Column(
                        modifier = Modifier.padding(32.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "Instruções",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF212121),
                            fontSize = 20.sp
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        Text(
                            text = "• Posicione seu rosto na área indicada\n• Siga os movimentos solicitados na tela\n• Certifique-se de ter boa iluminação\n• Remova óculos escuros se necessário\n• Mantenha o dispositivo estável",
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color(0xFF757575),
                            fontSize = 14.sp
                        )
                    }
                }

                Spacer(modifier = Modifier.weight(1f))

                Button(
                    onClick = onStartClick,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.White
                    )
                ) {
                    Text(
                        "Iniciar Verificação IProov",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color(0xFF4CAF50)
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun IProovModernPermissionScreen(
    onPermissionGranted: () -> Unit,
    onBack: () -> Unit
) {
    val primaryColor = Color(0xFF4CAF50)
    val gradient = Brush.verticalGradient(
        colors = listOf(
            Color.White,
            Color(0xFFF5F5F5)
        )
    )

    ApplyStatusBarStyle(
        color = Color.White,
        darkTheme = true
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Permissão Necessária - IProov",
                        color = Color(0xFF212121),
                        fontWeight = FontWeight.SemiBold
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            "Voltar",
                            tint = Color(0xFF212121)
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.White
                )
            )
        },
        containerColor = Color.White
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
                    .padding(horizontal = 32.dp)
                    .padding(top = 24.dp, bottom = 48.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                // Ícone da câmera com gradiente
                Box(
                    modifier = Modifier
                        .size(140.dp)
                        .background(
                            brush = Brush.radialGradient(
                                colors = listOf(
                                    primaryColor.copy(alpha = 0.15f),
                                    primaryColor.copy(alpha = 0.05f),
                                    Color.Transparent
                                ),
                                radius = 200f
                            ),
                            shape = CircleShape
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Box(
                        modifier = Modifier
                            .size(100.dp)
                            .background(
                                brush = Brush.linearGradient(
                                    colors = listOf(
                                        primaryColor.copy(alpha = 0.2f),
                                        primaryColor.copy(alpha = 0.1f)
                                    )
                                ),
                                shape = CircleShape
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Filled.CameraAlt,
                            contentDescription = null,
                            tint = primaryColor,
                            modifier = Modifier.size(56.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(40.dp))

                Text(
                    text = "Permissão de Câmera Necessária",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF212121),
                    textAlign = TextAlign.Center,
                    fontSize = 24.sp
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Para realizar a verificação facial com IProov, precisamos acessar sua câmera. Esta permissão é necessária para capturar sua imagem durante o processo de verificação.",
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color(0xFF757575),
                    textAlign = TextAlign.Center,
                    lineHeight = 24.sp
                )

                Spacer(modifier = Modifier.height(56.dp))

                Button(
                    onClick = onPermissionGranted,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = primaryColor
                    ),
                    elevation = ButtonDefaults.buttonElevation(
                        defaultElevation = 4.dp,
                        pressedElevation = 8.dp
                    )
                ) {
                    Text(
                        "Conceder Permissão",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.White
                    )
                }
            }
        }
    }
}

@Composable
fun IProovModernResultScreen(
    success: Boolean,
    errorMessage: String?,
    showRetryButton: Boolean,
    reason: String?,
    onRetry: () -> Unit
) {
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
                            "Verificação IProov Concluída!"
                        } else {
                            "Verificação IProov Falhou"
                        },
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF212121),
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = if (success) {
                            "Sua verificação facial usando IProov foi concluída com sucesso"
                        } else {
                            errorMessage
                                ?: "Não foi possível concluir sua verificação facial com IProov. Tente novamente."
                        },
                        style = MaterialTheme.typography.bodyLarge,
                        color = Color(0xFF757575),
                        textAlign = TextAlign.Center
                    )

                    reason?.let {
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Motivo: $it",
                            style = MaterialTheme.typography.bodySmall,
                            color = Color(0xFF999999),
                            textAlign = TextAlign.Center
                        )
                    }

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
fun IProovModernLoadingDialog(
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
                                    Color(0xFF66BB6A),
                                    Color(0xFF81C784)
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
                            color = Color(0xFF4CAF50),
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

