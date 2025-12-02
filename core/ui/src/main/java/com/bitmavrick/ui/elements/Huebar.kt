package com.bitmavrick.ui.elements

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.drag
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.graphics.toRect
import android.graphics.Color as AndroidColor
import androidx.core.graphics.createBitmap

@Composable
fun HueBar(
    hue: Float,
    onHueChange: (Float) -> Unit,
    onHueChangeFinished: (Float) -> Unit
) {
    val pressOffset = remember { mutableStateOf(Offset.Zero) }

    Canvas(
        modifier = Modifier
            .fillMaxWidth()
            .height(40.dp)
            .clip(RoundedCornerShape(50))
            .pointerInput(Unit) {
                awaitEachGesture {
                    // Wait for first touch
                    val down = awaitFirstDown(requireUnconsumed = false)
                    val width: Float = size.width.toFloat()
                    val initialX = down.position.x.coerceIn(0f, width)

                    pressOffset.value = Offset(initialX, 0f)
                    val newHue = (initialX / width) * 360f
                    onHueChange(newHue) // 👈 Tap instantly updates

                    // Now handle drag if user moves finger
                    drag(down.id) { change ->
                        val posX = change.position.x.coerceIn(0f, width)
                        pressOffset.value = Offset(posX, 0f)
                        val dragHue = (posX / width) * 360f
                        onHueChange(dragHue)
                    }

                    // When drag ends or tap released
                    val finalHue = (pressOffset.value.x / width) * 360f
                    onHueChangeFinished(finalHue)
                }
            }
    ) {
        val bitmap =
            createBitmap(size.width.toInt(), size.height.toInt())
        val hueCanvas = Canvas(bitmap)
        val huePanel = RectF(0f, 0f, bitmap.width.toFloat(), bitmap.height.toFloat())

        val hueColors = IntArray(huePanel.width().toInt())
        var h = 0f
        for (i in hueColors.indices) {
            hueColors[i] = AndroidColor.HSVToColor(floatArrayOf(h, 1f, 1f))
            h += 360f / hueColors.size
        }

        val linePaint = Paint()
        for (i in hueColors.indices) {
            linePaint.color = hueColors[i]
            hueCanvas.drawLine(i.toFloat(), 0F, i.toFloat(), huePanel.bottom, linePaint)
        }

        drawIntoCanvas {
            it.nativeCanvas.drawBitmap(bitmap, null, huePanel.toRect(), null)
        }

        // Draw indicator circle
        val hueX = (hue / 360f) * size.width
        if (pressOffset.value == Offset.Zero) {
            pressOffset.value = Offset(hueX, 0f)
        }

        drawCircle(
            Color.White,
            radius = size.height / 2,
            center = Offset(pressOffset.value.x, size.height / 2),
            style = Stroke(width = 2.dp.toPx())
        )
    }
}

@Preview()
@Composable
private fun HueBarPreview(){
    HueBar(
        hue = 10f,
        onHueChange = {},
        onHueChangeFinished = {}
    )
}