package br.com.certiface.certifacesdk.demo.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = CertifaceBlue,
    onPrimary = CertifaceWhite,
    secondary = CertifaceGreen,
    onSecondary = CertifaceDark,
    background = CertifaceDark,
    onBackground = CertifaceWhite,
    surface = Color(0xFF1C1C1E),
    onSurface = CertifaceWhite,
    surfaceVariant = Color(0xFF2C2C2E),
    onSurfaceVariant = Color(0xFFE0E0E0)
)

private val LightColorScheme = lightColorScheme(
    primary = CertifaceBlue,
    onPrimary = CertifaceWhite,
    secondary = CertifaceGreen,
    onSecondary = CertifaceWhite,
    background = CertifaceWhite,
    onBackground = Color(0xFF000000),
    surface = CertifaceWhite,
    onSurface = Color(0xFF1A1A1A),
    surfaceVariant = Color(0xFFE8E8E8),
    onSurfaceVariant = Color(0xFF424242),
    outline = Color(0xFFD0D0D0)
)

@Composable
fun CertifaceSDKTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
