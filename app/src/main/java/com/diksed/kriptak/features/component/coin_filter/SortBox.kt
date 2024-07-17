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
    sortName: String,
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
                modifier = Modifier.padding(8.dp)
            )
            if (sortType == SortType.NAME && sortDirection != SortDirection.DEFAULT) {
                SortIcon(sortType = sortType, sortDirection = sortDirection, modifier = Modifier.padding(start = 4.dp))
            } else if (sortType == SortType.PRICE && sortDirection != SortDirection.DEFAULT) {
                SortIcon(sortType = sortType, sortDirection = sortDirection, modifier = Modifier.padding(start = 4.dp))
            } else if (sortType == SortType.PERCENTAGE && sortDirection != SortDirection.DEFAULT) {
                SortIcon(sortType = sortType, sortDirection = sortDirection, modifier = Modifier.padding(start = 4.dp))
            } else {
                Image(
                    painter = painterResource(id = R.drawable.ic_minus),
                    contentDescription = null,
                    modifier = Modifier.size(16.dp)
                )
            }
        }
    }
}


@Composable
fun SortIcon(sortType: SortType, sortDirection: SortDirection, modifier: Modifier = Modifier) {
    val icon = when {
        sortType == SortType.NAME && sortDirection == SortDirection.ASCENDING -> R.drawable.ic_down_arrow
        sortType == SortType.NAME && sortDirection == SortDirection.DESCENDING -> R.drawable.ic_up_arrow
        sortType == SortType.PRICE && sortDirection == SortDirection.ASCENDING -> R.drawable.ic_down_arrow
        sortType == SortType.PRICE && sortDirection == SortDirection.DESCENDING -> R.drawable.ic_up_arrow
        sortType == SortType.PERCENTAGE && sortDirection == SortDirection.ASCENDING -> R.drawable.ic_down_arrow
        sortType == SortType.PERCENTAGE && sortDirection == SortDirection.DESCENDING -> R.drawable.ic_up_arrow
        else -> R.drawable.ic_minus // Default icon
    }
    Image(
        painter = painterResource(id = icon),
        contentDescription = null, // Provide a content description if needed
        modifier = modifier
    )
}
