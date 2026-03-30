package br.com.certiface.certifacesdk.demo.ui.screens

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.certiface.certifacesdk.demo.ui.theme.CertifaceBlue
import br.com.certiface.certifacesdk.demo.ui.theme.CertifaceWhite

@Composable
fun CredentialScreen(
    modifier: Modifier = Modifier,
    context: Context,
    onAppKeySaved: (String) -> Unit
) {
    val scrollState = rememberScrollState()
    var inputAppKey by remember { mutableStateOf("") }

    Box(
        modifier = modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .padding(horizontal = 16.dp, vertical = 12.dp)
                .padding(bottom = 88.dp) 
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
                Column {
                    Text(
                        "Configurar Credencial",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface,
                        letterSpacing = 0.5.sp
                    )
                    Spacer(Modifier.height(6.dp))
                    Text(
                        "Insira sua AppKey para utilizar as facilidades do Liveness",
                        fontSize = 14.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        lineHeight = 20.sp
                    )
                }
            }
            
            Spacer(Modifier.height(20.dp))
            
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
                        value = inputAppKey,
                        onValueChange = { inputAppKey = it },
                        label = { Text("AppKey") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth(),
                        placeholder = { Text("Insira a sua AppKey aqui") },
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = CertifaceBlue,
                            unfocusedBorderColor = MaterialTheme.colorScheme.outline
                        )
                    )
                }
            }
        }
        
        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.surface)
                .padding(horizontal = 16.dp, vertical = 16.dp)
                .shadow(
                    elevation = 8.dp,
                    shape = RoundedCornerShape(0.dp),
                    spotColor = Color.Black.copy(alpha = 0.15f)
                )
        ) {
            Button(
                onClick = {
                    onAppKeySaved(inputAppKey)
                },
                enabled = inputAppKey.isNotBlank(),
                modifier = Modifier
                    .fillMaxWidth()
                    .shadow(
                        elevation = if (inputAppKey.isNotBlank()) 4.dp else 0.dp,
                        shape = androidx.compose.foundation.shape.RoundedCornerShape(12.dp),
                        spotColor = CertifaceBlue.copy(alpha = 0.3f)
                    ),
                shape = androidx.compose.foundation.shape.RoundedCornerShape(12.dp),
                colors = androidx.compose.material3.ButtonDefaults.buttonColors(
                    containerColor = CertifaceBlue
                )
            ) {
                Text(
                    "Salvar AppKey",
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    }
}
