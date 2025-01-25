package com.stc.openweather.composable.elements

import androidx.compose.foundation.Canvas
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.clipPath
import androidx.compose.ui.unit.dp

@Composable
fun DropletIcon(
    humidity: Int,
    modifier: Modifier = Modifier,
    outlineColor: Color = MaterialTheme.colorScheme.primary,
    fillColor: Color = MaterialTheme.colorScheme.primary
) {
    Canvas(modifier = modifier) {
        val width = size.width
        val height = size.height

        val fillHeight = height * (humidity / 100f)

        drawPath(
            path = createDropletPath(width, height),
            color = outlineColor,
            style = Stroke(width = 1.dp.toPx())
        )

        clipPath(createDropletPath(width, height)) {
            drawRect(
                color = fillColor,
                topLeft = Offset(0f, height - fillHeight),
                size = Size(width, fillHeight)
            )
        }
    }
}


private fun createDropletPath(width: Float, height: Float): Path {
    val path = Path()
    path.moveTo(width / 2f, 0f) // Top of the droplet
    path.quadraticTo(0f, 19 * height / 20f, width / 2f, height) // Left curve
    path.quadraticTo(width, 19 * height / 20f, width / 2f, 0f) // Right curve
    path.close()
    return path
}
