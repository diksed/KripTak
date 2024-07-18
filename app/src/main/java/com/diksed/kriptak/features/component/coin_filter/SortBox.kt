package com.diksed.kriptak.features.component.coin_filter

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.diksed.kriptak.R
import com.diksed.kriptak.features.component.SortDirection
import com.diksed.kriptak.features.component.SortType
import com.diksed.kriptak.features.ui.theme.boxColor

@Composable
fun SortBox(
    modifier: Modifier = Modifier,
    onSortChange: () -> Unit,
    sortType: SortType,
    currentSortType: SortType,
    sortName: String,
    showIcon: Boolean = true,
    firstSort: Boolean = false,
    lastSort: Boolean = false,
    sortDirection: SortDirection
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .padding(horizontal = 4.dp)
            .background(
                color = boxColor,
                shape = RoundedCornerShape(
                    if (firstSort) 8.dp else 0.dp,
                    if (lastSort) 8.dp else 0.dp,
                    if (lastSort) 8.dp else 0.dp,
                    if (firstSort) 8.dp else 0.dp
                )
            )
            .clickable { onSortChange() }
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = sortName,
                color = Color.White,
                modifier = Modifier.padding(vertical = 8.dp, horizontal = 2.dp)
            )
            if (showIcon && currentSortType == sortType) {
                SortIcon(sortDirection = sortDirection)
            } else {
                if (showIcon)
                    Image(
                        painter = painterResource(id = R.drawable.ic_minus),
                        colorFilter = ColorFilter.tint(Color.White),
                        contentDescription = null,
                        modifier = Modifier.size(16.dp)
                    )
            }
        }
    }
}

@Composable
fun SortIcon(sortDirection: SortDirection, modifier: Modifier = Modifier) {
    val icon = when (sortDirection) {
        SortDirection.ASCENDING -> R.drawable.ic_down_arrow
        SortDirection.DESCENDING -> R.drawable.ic_up_arrow
        else -> R.drawable.ic_minus
    }
    Image(
        colorFilter = ColorFilter.tint(Color.White),
        painter = painterResource(id = icon),
        contentDescription = null,
        modifier = modifier
    )
}
