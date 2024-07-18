package com.diksed.kriptak.features.component.coin_filter

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.diksed.kriptak.R
import com.diksed.kriptak.features.component.SortDirection
import com.diksed.kriptak.features.component.SortType

@Composable
fun KripTakSortRow(
    onSortChange: (SortType) -> Unit,
    sortType: SortType,
    sortDirection: SortDirection
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        SortBox(
            sortName = stringResource(id = R.string.name),
            onSortChange = { onSortChange(SortType.NAME) },
            sortType = SortType.NAME,
            currentSortType = sortType,
            modifier = Modifier.weight(1.2f),
            firstSort = true,
            sortDirection = sortDirection
        )
        SortBox(
            sortName = stringResource(id = R.string.oneDayGraphics),
            onSortChange = {},
            sortType = SortType.NONE,
            currentSortType = sortType,
            showIcon = false,
            modifier = Modifier.weight(1f),
            sortDirection = SortDirection.DEFAULT
        )
        SortBox(
            sortName = stringResource(id = R.string.price),
            onSortChange = { onSortChange(SortType.PRICE) },
            sortType = SortType.PRICE,
            currentSortType = sortType,
            modifier = Modifier.weight(0.75f),
            sortDirection = sortDirection
        )
        SortBox(
            sortName = stringResource(id = R.string.oneDayChange),
            onSortChange = { onSortChange(SortType.PERCENTAGE) },
            sortType = SortType.PERCENTAGE,
            currentSortType = sortType,
            modifier = Modifier.weight(0.8f),
            lastSort = true,
            sortDirection = sortDirection,
        )
    }
}