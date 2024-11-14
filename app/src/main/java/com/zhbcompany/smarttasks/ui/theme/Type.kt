package com.zhbcompany.smarttasks.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.zhbcompany.smarttasks.R

val AmsiPro = FontFamily(
    Font(R.font.amsi_pro_bold, weight = FontWeight.Bold),
    Font(R.font.amsi_pro_regular, weight = FontWeight.Normal)
)

val AppTypography = Typography(
    titleLarge = TextStyle(
        fontFamily = AmsiPro,
        fontWeight = FontWeight.Bold,
        fontSize = 20.sp
    ),
    labelMedium = TextStyle(
        fontFamily = AmsiPro,
        fontWeight = FontWeight.Normal,
        fontSize = 13.sp
    )
)