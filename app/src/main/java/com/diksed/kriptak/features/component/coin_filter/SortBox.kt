package com.diksed.kriptak.features.component.coin_filter

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.diksed.kriptak.R
import com.diksed.kriptak.features.component.KripTakText
import com.diksed.kriptak.features.component.SortDirection
import com.diksed.kriptak.features.component.SortType
import com.diksed.kriptak.features.ui.theme.Gray69
import com.diksed.kriptak.features.ui.theme.PaleViolet
import com.diksed.kriptak.features.ui.theme.boxColor

@Composable
fun SortBox(
    modifier: Modifier = Modifier,
    onSortChange: () -> Unit,
    sortType: SortType,
    currentSortType: SortType,
    sortName: String,
    showIcon: Boolean = true,
    sortDirection: SortDirection
) {
    val isSelected = showIcon && currentSortType == sortType
    val shape = RoundedCornerShape(10.dp)
    val contentColor = if (isSelected) PaleViolet else Gray69

    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .clip(shape)
            .background(color = boxColor, shape = shape)
            .then(
                if (isSelected) Modifier.border(1.5.dp, PaleViolet, shape) else Modifier
            )
            .clickable { onSortChange() }
            .padding(horizontal = 12.dp, vertical = 8.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            KripTakText(
                text = sortName,
                color = contentColor,
                modifier = Modifier.padding(end = 4.dp)
            )
            if (showIcon) {
                if (currentSortType == sortType) {
                    SortIcon(sortDirection = sortDirection, tint = contentColor)
                } else {
                    Image(
                        painter = painterResource(id = R.drawable.ic_swap_vert),
                        colorFilter = ColorFilter.tint(contentColor),
                        contentDescription = null,
                        modifier = Modifier.size(16.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun SortIcon(sortDirection: SortDirection, modifier: Modifier = Modifier, tint: Color = Color.White) {
    val icon = when (sortDirection) {
        SortDirection.ASCENDING -> R.drawable.ic_down_arrow
        SortDirection.DESCENDING -> R.drawable.ic_up_arrow
        else -> R.drawable.ic_swap_vert
    }
    Image(
        colorFilter = ColorFilter.tint(tint),
        painter = painterResource(id = icon),
        contentDescription = null,
        modifier = modifier.size(16.dp)
    )
}
