package com.example.ui.screens

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Diamond
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Layers
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Save
import androidx.compose.material.icons.filled.Speed
import androidx.compose.material.icons.filled.Tune
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.InterfaceBuilderManager
import com.example.data.SettingsManager
import com.example.ui.components.FullSpectrumColorPicker
import com.example.ui.components.GlassButton
import com.example.ui.components.GlassIconButton
import com.example.ui.components.GlassSurface
import com.example.ui.components.GlassVideoReflection
import com.example.ui.components.LiquidGlassSubscribeButton
import com.example.ui.theme.LiquidGlassStyleConfig
import com.example.ui.theme.YtBorder
import com.example.ui.theme.YtSurfaceDark
import com.example.ui.theme.YtSurfaceVariant
import com.example.ui.theme.YtTextPrimary
import com.example.ui.theme.YtTextSecondary

/**
 * Complete, unified Liquid Glass Studio:
 * Provides all 25 working controls, live preview, Easy and Advanced modes,
 * Full-Spectrum Color Pickers, and Subscribe Button customizations.
 */
fun LazyListScope.liquidGlassStudioItems(
    settingsManager: SettingsManager,
    interfaceBuilderManager: InterfaceBuilderManager,
    glassConfig: LiquidGlassStyleConfig,
    accentColor: Color,
    context: Context,
    lgMaster: Boolean,
    lgTransparency: Float,
    lgBlur: Float,
    lgCornerRadius: Float,
    lgRefractionHeight: Float,
    lgRefraction: Float,
    lgLayerRefraction: Boolean,
    lgDepthEnabled: Boolean,
    lgVibrancy: Float,
    lgEdgeHighlights: Float,
    lgBorderThickness: Float,
    lgReflection: Float,
    lgTintColorHex: Long,
    lgShadowElevation: Float,
    lgAnimIntensity: Float,
    lgPerfMode: String,
    lgSpecular: Boolean,
    opticalChromaticDispersion: Float,
    neonMaster: Boolean,
    neonColorHex: Long,
    neonIntensity: Float,
    neonAnimated: Boolean,
    neonSpeed: Float,
    subGlassEnabled: Boolean,
    subGlassTintHex: Long,
    subTransparency: Float,
    subCornerRadius: Float,
    subDepth: Boolean,
    subRefraction: Float,
    subEdgeHighlight: Float,
    currentTheme: String,
    isAdvancedMode: Boolean,
    onToggleAdvancedMode: (Boolean) -> Unit
) {
    // 1. MASTER LIQUID GLASS TOGGLE
    item {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 6.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(if (lgMaster) accentColor.copy(alpha = 0.15f) else YtSurfaceDark)
                .border(
                    width = if (lgMaster) 1.5.dp else 1.dp,
                    color = if (lgMaster) accentColor else YtBorder,
                    shape = RoundedCornerShape(16.dp)
                )
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    modifier = Modifier.weight(1f),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(42.dp)
                            .clip(CircleShape)
                            .background(if (lgMaster) accentColor else Color.Gray.copy(alpha = 0.3f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Diamond,
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Column {
                        Text(
                            text = "Master Liquid Glass",
                            color = YtTextPrimary,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = if (lgMaster) "Real-Time GPU Optics & Refraction Active ✨" else "Disabled (Standard Conventional UI)",
                            color = if (lgMaster) accentColor else YtTextSecondary,
                            fontSize = 11.sp
                        )
                    }
                }
                Switch(
                    checked = lgMaster,
                    onCheckedChange = { settingsManager.setLiquidGlassMaster(it) },
                    colors = SwitchDefaults.colors(
                        checkedThumbColor = Color.White,
                        checkedTrackColor = accentColor
                    ),
                    modifier = Modifier.testTag("liquid_glass_master_switch")
                )
            }
        }
    }

    // 2. EASY VS ADVANCED MODE TOGGLE
    item {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 6.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .clip(RoundedCornerShape(12.dp))
                    .background(if (!isAdvancedMode) accentColor else YtSurfaceVariant)
                    .clickable { onToggleAdvancedMode(false) }
                    .padding(vertical = 10.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Easy Mode",
                    color = if (!isAdvancedMode) Color.White else YtTextSecondary,
                    fontWeight = FontWeight.Bold,
                    fontSize = 13.sp
                )
            }

            Box(
                modifier = Modifier
                    .weight(1f)
                    .clip(RoundedCornerShape(12.dp))
                    .background(if (isAdvancedMode) accentColor else YtSurfaceVariant)
                    .clickable { onToggleAdvancedMode(true) }
                    .padding(vertical = 10.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Advanced Studio ✨",
                    color = if (isAdvancedMode) Color.White else YtTextSecondary,
                    fontWeight = FontWeight.Bold,
                    fontSize = 13.sp
                )
            }
        }
    }

    // 3. INTERACTIVE LIVE PREVIEW CARD
    item {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp)
                .clip(RoundedCornerShape(20.dp))
                .border(1.dp, Color.White.copy(alpha = 0.2f), RoundedCornerShape(20.dp))
        ) {
            // Vivid Background Wallpaper behind glass to display refraction and transparency
            Box(
                modifier = Modifier
                    .matchParentSize()
                    .background(
                        Brush.linearGradient(
                            listOf(
                                Color(0xFF1A1A36),
                                Color(0xFF0F3460),
                                Color(0xFFE94560).copy(alpha = 0.4f),
                                Color(0xFF16213E)
                            )
                        )
                    )
            )

            // Layered Glass Preview surface
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(14.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "LIVE GLASS PREVIEW",
                        color = Color.White.copy(alpha = 0.85f),
                        fontSize = 11.sp,
                        fontWeight = FontWeight.ExtraBold,
                        letterSpacing = 1.sp
                    )
                    Text(
                        text = "Blur: ${lgBlur.toInt()}dp • Opacity: ${(lgTransparency * 100).toInt()}%",
                        color = accentColor,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))

                GlassSurface(
                    shape = RoundedCornerShape(lgCornerRadius.dp),
                    elevation = if (lgDepthEnabled) lgShadowElevation.dp else 0.dp,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(14.dp)
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier
                                    .size(34.dp)
                                    .clip(CircleShape)
                                    .background(Color(lgTintColorHex)),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(Icons.Default.Diamond, contentDescription = null, tint = Color.White, modifier = Modifier.size(18.dp))
                            }
                            Spacer(modifier = Modifier.width(10.dp))
                            Column {
                                Text("Liquid Glass Material", color = Color.White, fontSize = 14.sp, fontWeight = FontWeight.Bold)
                                Text("Real optical depth, curved highlights & caustics", color = Color.White.copy(alpha = 0.7f), fontSize = 11.sp)
                            }
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        // Interactive buttons row inside glass
                        var isPreviewSubscribed by remember { mutableStateOf(false) }
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            GlassButton(
                                text = "Glass Button",
                                icon = Icons.Default.PlayArrow,
                                onClick = {},
                                testTag = "preview_glass_button"
                            )

                            GlassIconButton(
                                onClick = {},
                                icon = Icons.Default.Favorite,
                                contentDescription = "Favorite",
                                tint = accentColor,
                                size = 36.dp,
                                iconSize = 18.dp
                            )

                            LiquidGlassSubscribeButton(
                                isSubscribed = isPreviewSubscribed,
                                onToggleSubscribe = { isPreviewSubscribed = !isPreviewSubscribed },
                                testTag = "preview_subscribe_button"
                            )
                        }

                        // Light spill / reflection underneath
                        GlassVideoReflection(
                            height = 12.dp,
                            tintColor = accentColor
                        )
                    }
                }
            }
        }
    }

    // 4. ACTION BUTTONS: RESET TO DEFAULT & APPLY / SAVE CHANGES
    item {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 6.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            OutlinedButton(
                onClick = {
                    settingsManager.resetLiquidGlassDefaults()
                    Toast.makeText(context, "Liquid Glass restored to factory defaults!", Toast.LENGTH_SHORT).show()
                },
                modifier = Modifier.weight(1f)
            ) {
                Icon(Icons.Default.Refresh, contentDescription = null, modifier = Modifier.size(16.dp))
                Spacer(modifier = Modifier.width(6.dp))
                Text("Reset Defaults", fontSize = 12.sp)
            }

            Button(
                onClick = {
                    interfaceBuilderManager.saveConfiguration()
                    Toast.makeText(context, "Liquid Glass settings saved offline persistently!", Toast.LENGTH_SHORT).show()
                },
                colors = ButtonDefaults.buttonColors(containerColor = accentColor),
                modifier = Modifier.weight(1f)
            ) {
                Icon(Icons.Default.Save, contentDescription = null, modifier = Modifier.size(16.dp))
                Spacer(modifier = Modifier.width(6.dp))
                Text("Save & Apply", fontSize = 12.sp)
            }
        }
    }

    // ==========================================
    // CONTROLS: EASY MODE & CORE SETTINGS
    // ==========================================
    item {
        StudioSectionTitle("Core Glass Properties")
    }

    // Control 2: Transparency / Opacity
    item {
        StudioSlider(
            label = "Transparency / Opacity",
            value = lgTransparency * 100f,
            valueRange = 10f..95f,
            unit = "%",
            accentColor = accentColor,
            onValueChange = { settingsManager.setLiquidGlassTransparency(it / 100f) },
            testTag = "slider_transparency"
        )
    }

    // Control 3: Background Blur Radius
    item {
        StudioSlider(
            label = "Background Blur Radius",
            value = lgBlur,
            valueRange = 0f..50f,
            unit = "dp",
            accentColor = accentColor,
            onValueChange = { settingsManager.setLiquidGlassBlur(it) },
            testTag = "slider_blur_radius"
        )
    }

    // Control 4: Glass Depth & Enable Depth
    item {
        StudioSwitch(
            title = "Glass 3D Depth & Illumination",
            subtitle = "Separates foreground and background with 3D shadow and gradient lighting",
            checked = lgDepthEnabled,
            accentColor = accentColor,
            onCheckedChange = { settingsManager.setLiquidGlassDepthEnabled(it) }
        )
    }

    if (lgDepthEnabled) {
        // Control 17: Shadow / Elevation
        item {
            StudioSlider(
                label = "Shadow Elevation / Depth",
                value = lgShadowElevation,
                valueRange = 0f..16f,
                unit = "dp",
                accentColor = accentColor,
                onValueChange = { settingsManager.setLiquidGlassShadowElevation(it) },
                testTag = "slider_shadow_elevation"
            )
        }
    }

    // Control 16: Corner Radius
    item {
        StudioSlider(
            label = "Corner Radius",
            value = lgCornerRadius,
            valueRange = 0f..32f,
            unit = "dp",
            accentColor = accentColor,
            onValueChange = { settingsManager.setLiquidGlassCornerRadius(it) },
            testTag = "slider_corner_radius"
        )
    }

    // Control 9: Vibrancy / Saturation
    item {
        StudioSlider(
            label = "Glass Vibrancy / Color Saturation",
            value = lgVibrancy * 100f,
            valueRange = 10f..100f,
            unit = "%",
            accentColor = accentColor,
            onValueChange = { settingsManager.setLiquidGlassVibrancy(it / 100f) },
            testTag = "slider_vibrancy"
        )
    }

    // Control 23: App Accent Color Full-Spectrum Picker
    item {
        FullSpectrumColorPicker(
            title = "App Accent Color",
            initialColorHex = settingsManager.customAccentColorHex.value,
            onColorChanged = { settingsManager.setCustomAccentColorHex(it) },
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 6.dp)
        )
    }

    // Control 22: Custom Background Wallpaper / Theme
    item {
        StudioSectionTitle("Custom Background Wallpaper & Theme")
    }
    item {
        val themes = listOf(
            "Liquid Glass with Reflection",
            "Liquid Glass (Light)",
            "Dark Mode",
            "AMOLED Black",
            "Neon Glow"
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            themes.forEach { themeName ->
                val isSelected = currentTheme == themeName
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(12.dp))
                        .background(if (isSelected) accentColor else YtSurfaceDark)
                        .border(1.dp, if (isSelected) Color.White else YtBorder, RoundedCornerShape(12.dp))
                        .clickable { settingsManager.setUiTheme(themeName) }
                        .padding(horizontal = 14.dp, vertical = 8.dp)
                ) {
                    Text(
                        text = themeName,
                        color = if (isSelected) Color.White else YtTextSecondary,
                        fontSize = 12.sp,
                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium
                    )
                }
            }
        }
    }

    // ==========================================
    // CONTROLS: ADVANCED STUDIO
    // ==========================================
    if (isAdvancedMode) {
        item {
            StudioSectionTitle("Advanced Optical Refraction & Caustics")
        }

        // Control 5: Refraction Height
        item {
            StudioSlider(
                label = "Refraction Caustic Height",
                value = lgRefractionHeight * 100f,
                valueRange = 10f..100f,
                unit = "%",
                accentColor = accentColor,
                onValueChange = { settingsManager.setLiquidGlassRefractionHeight(it / 100f) },
                testTag = "slider_refraction_height"
            )
        }

        // Control 6: Refraction Amount
        item {
            StudioSlider(
                label = "Refraction Amount",
                value = lgRefraction * 100f,
                valueRange = 0f..100f,
                unit = "%",
                accentColor = accentColor,
                onValueChange = { settingsManager.setLiquidGlassRefraction(it / 100f) },
                testTag = "slider_refraction_amount"
            )
        }

        // Control 7: Edge Dispersion
        item {
            StudioSlider(
                label = "Edge Dispersion (Chromatic Aberration)",
                value = opticalChromaticDispersion,
                valueRange = 0f..3.0f,
                unit = "x",
                accentColor = accentColor,
                onValueChange = { settingsManager.setOpticalChromaticDispersion(it) },
                testTag = "slider_chromatic_dispersion"
            )
        }

        // Control 8: Layer Fraction / Layer Separation
        item {
            StudioSwitch(
                title = "Dual-Layer Optical Separation",
                subtitle = "Renders secondary inner glass contour with offset refraction line",
                checked = lgLayerRefraction,
                accentColor = accentColor,
                onCheckedChange = { settingsManager.setLiquidGlassLayerRefraction(it) }
            )
        }

        // Control 10: Specular Bevel Highlights
        item {
            StudioSwitch(
                title = "Specular Bevel Highlights",
                subtitle = "Polished luminous sheen along curved glass perimeter",
                checked = lgSpecular,
                accentColor = accentColor,
                onCheckedChange = { settingsManager.setLiquidGlassSpecular(it) }
            )
        }

        // Control 11: Edge Highlight Intensity
        item {
            StudioSlider(
                label = "Edge Highlight Intensity",
                value = lgEdgeHighlights * 100f,
                valueRange = 0f..100f,
                unit = "%",
                accentColor = accentColor,
                onValueChange = { settingsManager.setLiquidGlassEdgeHighlights(it / 100f) },
                testTag = "slider_edge_highlights"
            )
        }

        // Control 12: Glass Thickness / Bevel Strength
        item {
            StudioSlider(
                label = "Glass Thickness / Bevel Border",
                value = lgBorderThickness,
                valueRange = 0.5f..3.5f,
                unit = "dp",
                accentColor = accentColor,
                onValueChange = { settingsManager.setLiquidGlassBorderThickness(it) },
                testTag = "slider_border_thickness"
            )
        }

        // Control 13: Reflection Intensity
        item {
            StudioSlider(
                label = "Reflection Intensity & Light Spill",
                value = lgReflection * 100f,
                valueRange = 0f..100f,
                unit = "%",
                accentColor = accentColor,
                onValueChange = { settingsManager.setLiquidGlassReflection(it / 100f) },
                testTag = "slider_reflection_intensity"
            )
        }

        // Control 14: Luminous Border Thickness
        item {
            StudioSlider(
                label = "Luminous Border Thickness",
                value = lgBorderThickness,
                valueRange = 0.5f..3.0f,
                unit = "dp",
                accentColor = accentColor,
                onValueChange = { settingsManager.setLiquidGlassBorderThickness(it) },
                testTag = "slider_luminous_border"
            )
        }

        // Control 15: Animation Intensity
        item {
            StudioSlider(
                label = "Animation Intensity & Speed",
                value = lgAnimIntensity,
                valueRange = 0.2f..2.0f,
                unit = "x",
                accentColor = accentColor,
                onValueChange = { settingsManager.setLiquidGlassAnimationIntensity(it) },
                testTag = "slider_animation_intensity"
            )
        }

        // Control 21: Performance Mode (Automatic, Balanced, Quality)
        item {
            StudioSectionTitle("Performance & Battery Mode")
        }
        item {
            val perfModes = listOf("Quality", "Balanced", "Battery Saver")
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                perfModes.forEach { mode ->
                    val isSelected = lgPerfMode == mode
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .clip(RoundedCornerShape(10.dp))
                            .background(if (isSelected) accentColor else YtSurfaceDark)
                            .border(1.dp, if (isSelected) Color.White else YtBorder, RoundedCornerShape(10.dp))
                            .clickable { settingsManager.setLiquidGlassPerformanceMode(mode) }
                            .padding(vertical = 10.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = mode,
                            color = if (isSelected) Color.White else YtTextSecondary,
                            fontSize = 12.sp,
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium
                        )
                    }
                }
            }
        }

        // Controls 18-20: Neon Lights System
        item {
            StudioSectionTitle("Neon Lights System")
        }

        // Control 18: Neon Lights ON/OFF
        item {
            StudioSwitch(
                title = "Neon Glow Borders",
                subtitle = "Illuminated neon perimeter with ambient colored glow",
                checked = neonMaster,
                accentColor = accentColor,
                onCheckedChange = { settingsManager.setNeonLightsMaster(it) }
            )
        }

        if (neonMaster) {
            // Control 19: Neon Color Full-Spectrum Picker
            item {
                FullSpectrumColorPicker(
                    title = "Neon Glow Color",
                    initialColorHex = neonColorHex,
                    onColorChanged = { settingsManager.setNeonColorHex(it) },
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 6.dp)
                )
            }

            // Control 19: Neon Glow Intensity
            item {
                StudioSlider(
                    label = "Neon Glow Intensity",
                    value = neonIntensity * 100f,
                    valueRange = 10f..100f,
                    unit = "%",
                    accentColor = Color(neonColorHex),
                    onValueChange = { settingsManager.setNeonIntensity(it / 100f) },
                    testTag = "slider_neon_intensity"
                )
            }

            // Control 20: Animated Pulsing Neon ON/OFF
            item {
                StudioSwitch(
                    title = "Animated Pulsing Neon",
                    subtitle = "Dynamic glowing pulse sweeping across borders",
                    checked = neonAnimated,
                    accentColor = Color(neonColorHex),
                    onCheckedChange = { settingsManager.setNeonAnimated(it) }
                )
            }

            if (neonAnimated) {
                // Control 20: Animated Speed
                item {
                    StudioSlider(
                        label = "Pulse Animation Speed",
                        value = neonSpeed,
                        valueRange = 0.5f..2.5f,
                        unit = "x",
                        accentColor = Color(neonColorHex),
                        onValueChange = { settingsManager.setNeonAnimationSpeed(it) },
                        testTag = "slider_neon_speed"
                    )
                }
            }
        }

        // Control 26: Subscribe Button Customization Studio
        item {
            StudioSectionTitle("Subscribe Button Glass Customization")
        }

        item {
            StudioSwitch(
                title = "Glass Subscribe Button",
                subtitle = "Apply liquid glass transparency, depth and edge sheen to Subscribe button",
                checked = subGlassEnabled,
                accentColor = accentColor,
                onCheckedChange = { settingsManager.setSubscribeGlassEnabled(it) }
            )
        }

        if (subGlassEnabled) {
            item {
                FullSpectrumColorPicker(
                    title = "Subscribe Button Tint",
                    initialColorHex = subGlassTintHex,
                    onColorChanged = { settingsManager.setSubscribeGlassTintHex(it) },
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 6.dp)
                )
            }

            item {
                StudioSlider(
                    label = "Subscribe Transparency",
                    value = subTransparency * 100f,
                    valueRange = 20f..95f,
                    unit = "%",
                    accentColor = accentColor,
                    onValueChange = { settingsManager.setSubscribeTransparency(it / 100f) },
                    testTag = "slider_sub_transparency"
                )
            }

            item {
                StudioSlider(
                    label = "Subscribe Corner Radius",
                    value = subCornerRadius,
                    valueRange = 4f..28f,
                    unit = "dp",
                    accentColor = accentColor,
                    onValueChange = { settingsManager.setSubscribeCornerRadius(it) },
                    testTag = "slider_sub_corner_radius"
                )
            }

            item {
                StudioSwitch(
                    title = "Subscribe 3D Depth",
                    subtitle = "Gives the button elevated glass depth and shadow",
                    checked = subDepth,
                    accentColor = accentColor,
                    onCheckedChange = { settingsManager.setSubscribeDepth(it) }
                )
            }

            item {
                StudioSlider(
                    label = "Subscribe Edge Highlight",
                    value = subEdgeHighlight * 100f,
                    valueRange = 0f..100f,
                    unit = "%",
                    accentColor = accentColor,
                    onValueChange = { settingsManager.setSubscribeEdgeHighlight(it / 100f) },
                    testTag = "slider_sub_edge_highlight"
                )
            }
        }
    }

    item {
        Spacer(modifier = Modifier.height(30.dp))
    }
}

@Composable
private fun StudioSectionTitle(title: String) {
    Text(
        text = title,
        color = YtTextPrimary,
        fontSize = 14.sp,
        fontWeight = FontWeight.Bold,
        modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp)
    )
}

@Composable
private fun StudioSlider(
    label: String,
    value: Float,
    valueRange: ClosedFloatingPointRange<Float>,
    unit: String,
    accentColor: Color,
    onValueChange: (Float) -> Unit,
    testTag: String = "studio_slider"
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 5.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(label, color = YtTextPrimary, fontSize = 13.sp)
            Text(
                text = "${value.toInt()} $unit",
                color = accentColor,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold
            )
        }
        Slider(
            value = value,
            onValueChange = onValueChange,
            valueRange = valueRange,
            colors = SliderDefaults.colors(
                thumbColor = Color.White,
                activeTrackColor = accentColor,
                inactiveTrackColor = YtBorder
            ),
            modifier = Modifier
                .fillMaxWidth()
                .testTag(testTag)
        )
    }
}

@Composable
private fun StudioSwitch(
    title: String,
    subtitle: String,
    checked: Boolean,
    accentColor: Color,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(end = 12.dp)
        ) {
            Text(title, color = YtTextPrimary, fontSize = 14.sp, fontWeight = FontWeight.SemiBold)
            Text(subtitle, color = YtTextSecondary, fontSize = 11.sp)
        }
        Switch(
            checked = checked,
            onCheckedChange = onCheckedChange,
            colors = SwitchDefaults.colors(
                checkedThumbColor = Color.White,
                checkedTrackColor = accentColor
            )
        )
    }
}
