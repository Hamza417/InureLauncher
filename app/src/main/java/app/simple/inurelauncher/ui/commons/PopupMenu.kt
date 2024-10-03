package app.simple.inurelauncher.ui.commons

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties
import androidx.compose.ui.zIndex

@Composable
fun PopupMenu(
        menuItems: List<String>,
        onClickCallbacks: List<() -> Unit>,
        showMenu: Boolean,
        onDismiss: () -> Unit,
) {
    DropdownMenu(
            expanded = showMenu,
            onDismissRequest = { onDismiss() },
    ) {
        menuItems.forEachIndexed { index, item ->
            DropdownMenuItem(onClick = {
                onDismiss()
                onClickCallbacks[index]
            }, text = {
                Text(text = item)
            })
        }
    }
}

@Composable
fun PopupBox(popupWidth: Float, popupHeight: Float, showPopup: Boolean, onClickOutside: () -> Unit, content: @Composable() () -> Unit) {
    if (showPopup) {
        // full screen background
        Card(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Green)
                    .zIndex(10F),
        ) {
            // popup
            Popup(
                    alignment = Alignment.Center,
                    properties = PopupProperties(
                            excludeFromSystemGesture = true,
                    ),
                    // to dismiss on click outside
                    onDismissRequest = { onClickOutside() }
            ) {
                Box(
                        Modifier
                            .width(popupWidth.dp)
                            .height(popupHeight.dp)
                            .background(Color.White)
                            .clip(RoundedCornerShape(4.dp)),
                        contentAlignment = Alignment.Center
                ) {
                    content()
                }
            }
        }
    }
}
