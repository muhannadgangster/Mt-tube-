package com.mytube.ui.glass

import android.graphics.RuntimeShader
import android.os.Build
import androidx.annotation.RequiresApi

/**
 * Android Graphics Shading Language (AGSL) Real-Time Optical Liquid Glass Shader.
 * 
 * Provides physically grounded optical refraction, restrained center curvature,
 * edge-specific caustic highlights, subtle chromatic dispersion, and clean background blending.
 */
@RequiresApi(Build.VERSION_CODES.TIRAMISU)
object LiquidGlassShader {

    val source = """
        uniform shader background;
        uniform float2 resolution;
        uniform float cornerRadius;
        uniform float strength;
        uniform float time;

        half4 main(float2 p) {
            float2 size = max(resolution, float2(1.0, 1.0));
            float2 center = size * 0.5;
            float2 halfSize = size * 0.5;
            float r = clamp(
                cornerRadius,
                0.0,
                min(halfSize.x, halfSize.y)
            );

            float2 q = abs(p - center);
            float2 d = q - halfSize + float2(r, r);

            float sdf =
                length(max(d, float2(0.0, 0.0))) +
                min(max(d.x, d.y), 0.0) - r;

            float inside = 1.0 - smoothstep(-1.0, 1.0, sdf);

            // If outside the glass shape, return the untouched original background
            half4 bgOrig = background.eval(p);
            if (inside <= 0.001) {
                return bgOrig;
            }

            // Restrict refraction mainly to the curved glass rim.
            float rim = exp(-abs(sdf) / 13.0) * inside;
            float edgeFade = smoothstep(0.0, 18.0, abs(sdf));

            float2 radial = p - center;
            float len = max(length(radial), 0.001);
            float2 direction = radial / len;

            // Subtle, controlled background bending.
            float bend = clamp(strength, 0.0, 2.0) *
                         rim * edgeFade * 2.4;

            // A gentle asymmetric optical wave.
            float wave = sin(p.y * 0.025 + time) *
                         sin(p.x * 0.018) * 0.25 * rim;

            float2 offset = direction * (bend + wave);

            float2 uv = clamp(
                p - offset,
                float2(0.0, 0.0),
                size - float2(1.0, 1.0)
            );

            // Very subtle edge-only color separation.
            float chroma = rim * edgeFade * 0.35;
            float2 shift = direction * chroma;

            half red = background.eval(
                clamp(uv - shift, float2(0.0, 0.0), size - float2(1.0, 1.0))
            ).r;

            half green = background.eval(uv).g;

            half blue = background.eval(
                clamp(uv + shift, float2(0.0, 0.0), size - float2(1.0, 1.0))
            ).b;

            half3 glass = half3(red, green, blue);

            // Neutral tint: preserve the original background colors.
            glass *= half3(0.985, 0.99, 1.0);

            // Restrained highlights close to the curved rim.
            float rimLight =
                exp(-abs(sdf + 1.5) / 3.5) * inside * 0.12;

            float softReflection =
                exp(-abs(sdf - 2.0) / 9.0) * inside * 0.035;

            glass += half3(rimLight + softReflection);

            // Preserve alpha and smoothly composite with background
            half4 finalGlass = half4(glass, bgOrig.a);
            return mix(bgOrig, finalGlass, inside);
        }
    """.trimIndent()

    fun create(): RuntimeShader {
        check(Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU)
        return RuntimeShader(source)
    }
}
