package com.mako.taskmngr.core.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.mako.taskmngr.R

private val TmBaseTextStyle = TextStyle(
    fontFamily = FontFamily.Default,
    letterSpacing = 0.5.sp
)

val Typography.titleHuge: TextStyle
    get() = TmBaseTextStyle.copy(
        fontSize = 48.sp,
        fontWeight = FontWeight.Bold,
        lineHeight = 56.sp,
        letterSpacing = 0.sp
    )

val Typography.screenTitle: TextStyle
    get() = TmTypography.titleLarge.copy(
        fontFamily = FontFamily(Font(R.font.pacifico))
    )

val TmTypography = Typography(
    titleLarge = TmBaseTextStyle.copy(
        fontSize = 32.sp,
        fontWeight = FontWeight.Bold,
        lineHeight = 40.sp,
        letterSpacing = 0.sp
    ),

    titleMedium = TmBaseTextStyle.copy(
        fontSize = 20.sp,
        fontWeight = FontWeight.SemiBold,
        lineHeight = 28.sp,
        letterSpacing = 0.15.sp
    ),

    titleSmall = TmBaseTextStyle.copy(
        fontSize = 16.sp,
        fontWeight = FontWeight.Medium,
        lineHeight = 24.sp,
        letterSpacing = 0.1.sp
    ),

    bodyLarge = TmBaseTextStyle.copy(
        fontSize = 16.sp,
        fontWeight = FontWeight.Normal,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    ),

    bodyMedium = TmBaseTextStyle.copy(
        fontSize = 14.sp,
        fontWeight = FontWeight.Normal,
        lineHeight = 20.sp,
        letterSpacing = 0.25.sp
    ),

    bodySmall = TmBaseTextStyle.copy(
        fontSize = 12.sp,
        fontWeight = FontWeight.Normal,
        lineHeight = 16.sp,
        letterSpacing = 0.4.sp
    ),

    labelLarge = TmBaseTextStyle.copy(
        fontSize = 14.sp,
        fontWeight = FontWeight.Medium,
        lineHeight = 20.sp,
        letterSpacing = 0.1.sp
    ),

    labelMedium = TmBaseTextStyle.copy(
        fontSize = 12.sp,
        fontWeight = FontWeight.Medium,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    ),

    labelSmall = TmBaseTextStyle.copy(
        fontSize = 10.sp,
        fontWeight = FontWeight.Medium,
        lineHeight = 14.sp,
        letterSpacing = 0.5.sp
    )
)
