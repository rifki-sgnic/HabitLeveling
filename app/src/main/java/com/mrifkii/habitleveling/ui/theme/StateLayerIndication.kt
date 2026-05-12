package com.mrifkii.habitleveling.ui.theme

import androidx.compose.foundation.IndicationNodeFactory
import androidx.compose.foundation.interaction.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.ContentDrawScope
import androidx.compose.ui.node.DelegatableNode
import androidx.compose.ui.node.DrawModifierNode
import androidx.compose.ui.node.invalidateDraw
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

object ShadowStateIndication : IndicationNodeFactory {
    override fun create(interactionSource: InteractionSource): DelegatableNode {
        return ShadowStateNode(interactionSource)
    }

    override fun hashCode(): Int = -1
    override fun equals(other: Any?): Boolean = other === this
}

private class ShadowStateNode(
    private val interactionSource: InteractionSource
) : DelegatableNode, DrawModifierNode, Modifier.Node() {
    
    private var isPressed: Boolean = false
    private var isHovered: Boolean = false
    private var isFocused: Boolean = false

    override fun onAttach() {
        coroutineScope.launch {
            interactionSource.interactions.collect { interaction ->
                when (interaction) {
                    is PressInteraction.Press -> isPressed = true
                    is PressInteraction.Release, is PressInteraction.Cancel -> isPressed = false
                    is HoverInteraction.Enter -> isHovered = true
                    is HoverInteraction.Exit -> isHovered = false
                    is FocusInteraction.Focus -> isFocused = true
                    is FocusInteraction.Unfocus -> isFocused = false
                }
                invalidateDraw()
            }
        }
    }

    override fun ContentDrawScope.draw() {
        drawContent()

        val alpha = when {
            isPressed -> 0.12f
            isFocused -> 0.10f
            isHovered -> 0.08f
            else -> 0f
        }

        if (alpha > 0f) {
            drawRect(
                color = Color.White.copy(alpha = alpha),
                size = size
            )
        }
    }
}
