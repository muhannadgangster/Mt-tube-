package com.example.data.model

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.MusicNote
import androidx.compose.material.icons.filled.Repeat
import androidx.compose.material.icons.filled.ThumbDown
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material.icons.outlined.ChatBubbleOutline
import androidx.compose.material.icons.outlined.Share
import androidx.compose.material.icons.outlined.ThumbDown
import androidx.compose.material.icons.outlined.ThumbUp
import androidx.compose.ui.graphics.vector.ImageVector

/**
 * Configuration item for customizable Shorts action icons.
 * Coordinates (xRatio, yRatio) are normalized fractions (0.0 .. 1.0) relative to viewport width/height,
 * enabling responsive, portrait/landscape, and screen-agnostic layout.
 */
data class ShortsActionConfig(
    val id: String,
    val name: String,
    val isVisible: Boolean = true,
    val xRatio: Float,
    val yRatio: Float,
    val isProtected: Boolean = false // Protected controls cannot be deleted into the trash area
) {
    fun toSerialized(): String {
        return "$id|$name|$isVisible|$xRatio|$yRatio|$isProtected"
    }

    companion object {
        fun fromSerialized(str: String): ShortsActionConfig? {
            return try {
                val parts = str.split("|")
                if (parts.size >= 6) {
                    ShortsActionConfig(
                        id = parts[0],
                        name = parts[1],
                        isVisible = parts[2].toBoolean(),
                        xRatio = parts[3].toFloat(),
                        yRatio = parts[4].toFloat(),
                        isProtected = parts[5].toBoolean()
                    )
                } else null
            } catch (_: Exception) {
                null
            }
        }
    }
}

object ShortsActionDefaults {
    fun getDefaultActions(): List<ShortsActionConfig> = listOf(
        ShortsActionConfig(id = "like", name = "Like", isVisible = true, xRatio = 0.86f, yRatio = 0.44f),
        ShortsActionConfig(id = "dislike", name = "Dislike", isVisible = true, xRatio = 0.86f, yRatio = 0.53f),
        ShortsActionConfig(id = "comment", name = "Comment", isVisible = true, xRatio = 0.86f, yRatio = 0.62f),
        ShortsActionConfig(id = "share", name = "Share", isVisible = true, xRatio = 0.86f, yRatio = 0.71f),
        ShortsActionConfig(id = "remix", name = "Remix", isVisible = true, xRatio = 0.86f, yRatio = 0.79f),
        ShortsActionConfig(id = "more", name = "More Menu", isVisible = true, xRatio = 0.86f, yRatio = 0.87f, isProtected = true),
        ShortsActionConfig(id = "subscribe", name = "Subscribe", isVisible = true, xRatio = 0.52f, yRatio = 0.88f),
        ShortsActionConfig(id = "audio", name = "Sound Track", isVisible = true, xRatio = 0.86f, yRatio = 0.94f)
    )
}
