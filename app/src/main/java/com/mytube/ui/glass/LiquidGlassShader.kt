package com.mytube.ui.glass

import android.graphics.RuntimeShader
import android.os.Build
import androidx.annotation.RequiresApi

/**
 * Android Graphics Shading Language (AGSL) Real-Time Optical Liquid Glass Shader.
 * 
 * Physically grounded optical liquid glass:
 * - Truly fluid and transparent (lets live background, videos, and UI shine through crisp and clear)
 * - True optical lens displacement and curvature ("ہلکی سی ٹلٹی" - smooth tilted refraction normal)
 * - Prismatic chromatic dispersion (RGB channel separation on curved edges)
 * - Elegant Fresnel specular rim highlight and meniscus curvature
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

            // SDF calculation for smooth rounded rectangle
            float2 q = abs(p - center);
            float2 d = q - halfSize + float2(r, r);
            float sdf =
                length(max(d, float2(0.0, 0.0))) +
                min(max(d.x, d.y), 0.0) - r;

            // Anti-aliased boundary mask
            float inside = 1.0 - smoothstep(-0.8, 0.8, sdf);
            half4 bgOrig = background.eval(p);
            if (inside <= 0.001) {
                return bgOrig;
            }

            // Normalized coordinates across the liquid dome (-1.0 to 1.0)
            float2 normCoord = (p - center) / max(halfSize, float2(1.0, 1.0));
            float distSq = clamp(dot(normCoord, normCoord), 0.0, 1.0);
            
            // Continuous lens dome curvature: z = sqrt(1.0 - distSq)
            float z = sqrt(1.0 - distSq * 0.72);
            
            // Fluid surface normal vector
            float2 normal = normCoord * (1.15 - z * 0.35);
            
            // Meniscus rim factor along curved perimeter
            float rimFactor = exp(-abs(sdf) / 16.0);
            
            // Optical tilt & refraction displacement:
            // Combines continuous fluid dome tilt with pronounced perimeter meniscus bending
            float refractStrength = clamp(strength, 0.0, 2.5);
            float tiltAmount = (distSq * 0.42 + rimFactor * 0.88) * refractStrength * 14.0;
            float2 offset = normal * tiltAmount;

            // Chromatic dispersion (prismatic color separation through optical glass)
            float disp = 0.016 * refractStrength * (distSq * 0.4 + rimFactor * 0.8);
            float2 uvR = clamp(p - offset * (1.0 + disp), float2(0.0, 0.0), size - float2(1.0, 1.0));
            float2 uvG = clamp(p - offset, float2(0.0, 0.0), size - float2(1.0, 1.0));
            float2 uvB = clamp(p - offset * (1.0 - disp), float2(0.0, 0.0), size - float2(1.0, 1.0));

            half red = background.eval(uvR).r;
            half green = background.eval(uvG).g;
            half blue = background.eval(uvB).b;
            half3 glassCol = half3(red, green, blue);

            // Crystal-clear fluid transparency: subtle crystalline enhancement
            glassCol *= half3(1.01, 1.02, 1.04);

            // Specular Fresnel rim light: Top-left bright meniscus reflection
            float2 lightDir = normalize(float2(-0.7, -0.7));
            float specular = max(0.0, dot(normal, -lightDir));
            float rimLight = pow(specular, 4.0) * rimFactor * 0.38 * (refractStrength * 0.6 + 0.4);

            // Inner ambient liquid sheen
            float innerSheen = exp(-abs(sdf + 2.5) / 4.0) * 0.10 * refractStrength;

            glassCol += half3(rimLight + innerSheen, rimLight + innerSheen * 1.08, rimLight + innerSheen * 1.18);

            // Smoothly composite with background preserving full transparency
            half4 result = half4(glassCol, bgOrig.a);
            return mix(bgOrig, result, inside);
        }
    """.trimIndent()

    @Volatile
    private var cachedShader: RuntimeShader? = null

    fun obtain(): RuntimeShader {
        check(Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU)
        var s = cachedShader
        if (s == null) {
            synchronized(this) {
                s = cachedShader
                if (s == null) {
                    s = RuntimeShader(source)
                    cachedShader = s
                }
            }
        }
        return s!!
    }

    fun create(): RuntimeShader {
        return obtain()
    }
}
