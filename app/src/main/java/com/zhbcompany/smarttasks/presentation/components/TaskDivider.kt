package com.zhbcompany.smarttasks.presentation.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun TaskDivider(
    color: Color = MaterialTheme.colorScheme.surfaceVariant,
    spaceBefore: Dp = 8.dp,
    spaceAfter: Dp = 8.dp
) {
    Spacer(modifier = Modifier.height(spaceBefore))
    HorizontalDivider(color = color)
    Spacer(modifier = Modifier.height(spaceAfter))
}