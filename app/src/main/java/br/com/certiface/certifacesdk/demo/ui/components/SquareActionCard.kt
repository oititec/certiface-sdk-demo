package br.com.certiface.certifacesdk.demo.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.certiface.certifacesdk.demo.ui.theme.CertifaceBlue
import br.com.certiface.certifacesdk.demo.ui.theme.CertifaceWhite

@Composable
fun SquareActionCard(
    modifier: Modifier = Modifier,
    title: String,
    description: String? = null,
    icon: ImageVector? = null,
    onClick: () -> Unit,
    enabled: Boolean = true,
    primaryColor: Color = CertifaceBlue
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(150.dp)
            .shadow(
                elevation = if (enabled) 3.dp else 0.dp,
                shape = RoundedCornerShape(14.dp),
                spotColor = if (MaterialTheme.colorScheme.background == CertifaceWhite) {
                    primaryColor.copy(alpha = 0.15f)
                } else {
                    Color.Black.copy(alpha = 0.4f)
                }
            )
            .clip(RoundedCornerShape(14.dp))
            .background(
                if (enabled) {
                    MaterialTheme.colorScheme.surface
                } else {
                    MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f)
                }
            )
            .border(
                width = 1.5.dp,
                color = if (enabled) {
                    if (MaterialTheme.colorScheme.background == CertifaceWhite) {
                        primaryColor.copy(alpha = 0.25f)
                    } else {
                        primaryColor.copy(alpha = 0.2f)
                    }
                } else {
                    MaterialTheme.colorScheme.outline.copy(alpha = 0.3f)
                },
                shape = RoundedCornerShape(14.dp)
            )
            .clickable(enabled = enabled, onClick = onClick)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (icon != null) {
                Box(
                    modifier = Modifier
                        .size(52.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(
                            if (enabled) {
                                primaryColor.copy(alpha = 0.1f)
                            } else {
                                MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f)
                            }
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = icon,
                        contentDescription = null,
                        tint = if (enabled) primaryColor else MaterialTheme.colorScheme.onSurface.copy(alpha = 0.4f),
                        modifier = Modifier.size(30.dp)
                    )
                }
                
                Spacer(Modifier.height(10.dp))
            }
            
            Text(
                text = title,
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold,
                color = if (enabled) {
                    MaterialTheme.colorScheme.onSurface
                } else {
                    MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
                },
                textAlign = TextAlign.Center,
                maxLines = 2
            )
            
            if (description != null) {
                Spacer(Modifier.height(4.dp))
                Text(
                    text = description,
                    fontSize = 11.sp,
                    color = if (enabled) {
                        MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f)
                    } else {
                        MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.3f)
                    },
                    textAlign = TextAlign.Center,
                    maxLines = 2
                )
            }
        }
    }
}

