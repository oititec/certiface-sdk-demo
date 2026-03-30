package br.com.certiface.certifacesdk.demo.ui.navigation

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Key
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.material3.MaterialTheme
import br.com.certiface.certifacesdk.demo.ui.theme.CertifaceBlue
import br.com.certiface.certifacesdk.demo.ui.theme.CertifaceWhite

enum class NavigationTab(
    val title: String,
    val icon: androidx.compose.ui.graphics.vector.ImageVector
) {
    HOME("Home", Icons.Default.Home),
    CREDENTIAL("Credencial", Icons.Default.Key),
    RESULTS("Resultados", Icons.Default.List)
}

@Composable
fun MainNavigation(
    selectedTab: Int,
    onTabSelected: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surface)
            .navigationBarsPadding()
            .padding(bottom = 8.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(70.dp)
                .padding(horizontal = 20.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            NavigationItem(
                tab = NavigationTab.CREDENTIAL,
                isSelected = selectedTab == NavigationTab.CREDENTIAL.ordinal,
                onClick = { onTabSelected(NavigationTab.CREDENTIAL.ordinal) }
            )
            
            NavigationItem(
                tab = NavigationTab.HOME,
                isSelected = selectedTab == NavigationTab.HOME.ordinal,
                onClick = { onTabSelected(NavigationTab.HOME.ordinal) },
                isCenter = true
            )
            
            NavigationItem(
                tab = NavigationTab.RESULTS,
                isSelected = selectedTab == NavigationTab.RESULTS.ordinal,
                onClick = { onTabSelected(NavigationTab.RESULTS.ordinal) }
            )
        }
    }
}

@Composable
private fun NavigationItem(
    tab: NavigationTab,
    isSelected: Boolean,
    onClick: () -> Unit,
    isCenter: Boolean = false
) {
    val targetOffset = remember(isSelected, isCenter) {
        if (isSelected && isCenter) (-20).dp else 0.dp
    }
    val offsetY by animateDpAsState(
        targetValue = targetOffset,
        animationSpec = spring(
            dampingRatio = 0.8f,
            stiffness = 500f
        ),
        label = "offsetY"
    )
    
    val targetSize = remember(isSelected, isCenter) {
        if (isSelected) (if (isCenter) 70.dp else 60.dp) else 0.dp
    }
    val circleSize by animateDpAsState(
        targetValue = targetSize,
        animationSpec = spring(
            dampingRatio = 0.85f,
            stiffness = 600f
        ),
        label = "circleSize"
    )
    
    Box(
        modifier = Modifier
            .offset(y = offsetY),
        contentAlignment = Alignment.Center
    ) {
        if (isSelected) {
            Box(
                modifier = Modifier
                    .size(circleSize)
                    .shadow(
                        elevation = 8.dp,
                        shape = CircleShape,
                        spotColor = CertifaceBlue.copy(alpha = 0.5f)
                    )
                    .clip(CircleShape)
                    .background(CertifaceBlue)
                    .clickable(onClick = onClick),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = tab.icon,
                    contentDescription = tab.title,
                    tint = CertifaceWhite,
                    modifier = Modifier.size(if (isCenter) 32.dp else 28.dp)
                )
            }
        } else {
            Box(
                modifier = Modifier
                    .size(56.dp)
                    .clip(CircleShape)
                    .clickable(onClick = onClick),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = tab.icon,
                    contentDescription = tab.title,
                    tint = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.size(28.dp)
                )
            }
        }
    }
}


