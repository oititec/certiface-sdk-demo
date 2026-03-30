package br.com.certiface.certifacesdk.demo.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.foundation.shape.RoundedCornerShape
import kotlinx.coroutines.delay
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.certiface.certifacesdk.demo.ui.theme.CertifaceBlue
import br.com.certiface.certifacesdk.demo.ui.theme.CertifaceGreen
import br.com.certiface.certifacesdk.demo.ui.theme.CertifaceWhite
import br.com.certiface.certifacesdk.demo.utils.copyToClipboard

@Composable
fun ResultScreen(
    modifier: Modifier = Modifier,
    context: android.content.Context,
    resultados: List<String>,
    onClearResults: () -> Unit
) {
    val listState = rememberLazyListState()
    
    LaunchedEffect(resultados.size) {
        if (resultados.isNotEmpty()) {
            delay(200)
            listState.scrollToItem(resultados.size - 1)
        }
    }
    
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp, vertical = 12.dp)
    ) {
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
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    "Resultados",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.weight(1f),
                    letterSpacing = 0.5.sp
                )
                
                if (resultados.isNotEmpty()) {
                    TextButton(onClick = onClearResults) {
                        Text("Limpar", color = MaterialTheme.colorScheme.primary)
                    }
                }
            }
        }
        
        Spacer(Modifier.height(16.dp))

        if (resultados.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(32.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Info,
                        contentDescription = null,
                        modifier = Modifier.size(64.dp),
                        tint = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f)
                    )
                    Spacer(Modifier.height(16.dp))
                    Text(
                        "Sem resultados ainda",
                        fontSize = 16.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Text(
                        "Execute testes na aba Home para ver os resultados aqui",
                        fontSize = 14.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f),
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }
            }
        } else {
            LazyColumn(
                state = listState,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                itemsIndexed(resultados) { index, resultado ->
                    ResultItem(
                        resultado = resultado,
                        index = index,
                        context = context
                    )
                }
            }
        }
    }
}

@Composable
private fun ResultItem(
    resultado: String,
    index: Int,
    context: android.content.Context,
    modifier: Modifier = Modifier
) {
    val isSuccess = resultado.startsWith("OK:")
    val isError = resultado.startsWith("ERRO:")
    val isInfo = resultado.startsWith("AppKey salva:")

    val backgroundColor = MaterialTheme.colorScheme.surface
    
    val borderColor = when {
        isSuccess -> CertifaceGreen.copy(alpha = if (MaterialTheme.colorScheme.background == CertifaceWhite) 0.4f else 0.3f)
        isError -> Color(0xFFC62828).copy(alpha = if (MaterialTheme.colorScheme.background == CertifaceWhite) 0.4f else 0.3f)
        isInfo -> CertifaceBlue.copy(alpha = if (MaterialTheme.colorScheme.background == CertifaceWhite) 0.4f else 0.3f)
        else -> MaterialTheme.colorScheme.outline.copy(alpha = if (MaterialTheme.colorScheme.background == CertifaceWhite) 0.3f else 0.2f)
    }
    
    val iconColor = when {
        isSuccess -> CertifaceGreen
        isError -> Color(0xFFC62828)
        isInfo -> CertifaceBlue
        else -> MaterialTheme.colorScheme.onSurfaceVariant
    }

    val icon = when {
        isSuccess -> Icons.Default.CheckCircle
        isError -> Icons.Default.Error
        else -> Icons.Default.Info
    }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .shadow(
                elevation = 1.dp,
                shape = RoundedCornerShape(10.dp),
                spotColor = borderColor.copy(alpha = 0.3f)
            )
            .clip(RoundedCornerShape(10.dp))
            .background(backgroundColor)
            .border(
                width = 1.dp,
                color = borderColor,
                shape = RoundedCornerShape(10.dp)
            )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = iconColor,
                modifier = Modifier.size(20.dp)
            )
            
            Spacer(Modifier.size(12.dp))
            
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = resultado.substringBefore(":").trim(),
                    fontSize = 11.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f)
                )
                Spacer(Modifier.height(4.dp))
                Text(
                    text = resultado.substringAfter(":").trim(),
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onSurface,
                    lineHeight = 20.sp
                )
            }
            
            if (isInfo) {
                Spacer(Modifier.size(8.dp))
                IconButton(
                    onClick = {
                        val appKey = resultado.substringAfter(":").trim()
                        copyToClipboard(context, appKey)
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.ContentCopy,
                        contentDescription = "Copiar AppKey",
                        tint = CertifaceBlue,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }
        }
    }
}

