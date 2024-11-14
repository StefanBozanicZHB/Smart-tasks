package com.zhbcompany.smarttasks.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun NotepadView(
    backgroundColor: Color = MaterialTheme.colorScheme.primary,
    pillarColor: Color = MaterialTheme.colorScheme.errorContainer,
    pillarWidth: Dp = 8.dp,
    pillarHeight: Dp = 30.dp,
    numberOfPillar: Int = 14,
    pillarShape: Shape = MaterialTheme.shapes.small,
    cardShape: Shape = MaterialTheme.shapes.small,
    content: @Composable () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = pillarHeight / 2)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = backgroundColor,
                    shape = cardShape
                )
                .padding(top = pillarHeight / 2)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .offset(y = (-pillarHeight))
                    .padding(horizontal = 25.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                repeat(numberOfPillar) {
                    Box(
                        modifier = Modifier
                            .size(pillarWidth, pillarHeight)
                            .background(
                                color = pillarColor,
                                shape = pillarShape
                            )
                    )
                }
            }
            content()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun NotepadViewPreview() {
    NotepadView {
        Text("NotepadViewPreview")
    }
}