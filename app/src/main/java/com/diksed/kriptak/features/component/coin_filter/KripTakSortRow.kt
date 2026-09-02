package com.diksed.kriptak.features.component.coin_filter

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.diksed.kriptak.R
import com.diksed.kriptak.features.component.SortDirection
import com.diksed.kriptak.features.component.SortType
import com.diksed.kriptak.utils.vibrate

/**
 * A row of compact filter chips, each sized to its own label - not an attempt
 * to line up under CryptoListItem's columns (weight-matching two separate
 * composables that way proved fragile: any layout tweak to one silently broke
 * alignment with the other). Reads as its own filter bar instead of a table
 * header.
 */
@Composable
fun KripTakSortRow(
    onSortChange: (SortType) -> Unit,
    sortType: SortType,
    sortDirection: SortDirection
) {
    val context = LocalContext.current
    val onSortChangeWithFeedback: (SortType) -> Unit = {
        vibrate(context)
        onSortChange(it)
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        SortBox(
            sortName = stringResource(id = R.string.name),
            onSortChange = { onSortChangeWithFeedback(SortType.NAME) },
            sortType = SortType.NAME,
            currentSortType = sortType,
            sortDirection = sortDirection
        )
        SortBox(
            sortName = stringResource(id = R.string.oneDayGraphics),
            onSortChange = {},
            sortType = SortType.NONE,
            currentSortType = sortType,
            showIcon = false,
            sortDirection = SortDirection.DEFAULT
        )
        SortBox(
            sortName = stringResource(id = R.string.price),
            onSortChange = { onSortChangeWithFeedback(SortType.PRICE) },
            sortType = SortType.PRICE,
            currentSortType = sortType,
            sortDirection = sortDirection
        )
        SortBox(
            sortName = stringResource(id = R.string.oneDayChange),
            onSortChange = { onSortChangeWithFeedback(SortType.PERCENTAGE) },
            sortType = SortType.PERCENTAGE,
            currentSortType = sortType,
            sortDirection = sortDirection,
        )
    }
}
