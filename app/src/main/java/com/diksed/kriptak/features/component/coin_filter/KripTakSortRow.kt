package com.diksed.kriptak.features.component.coin_filter

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.diksed.kriptak.features.component.SortDirection
import com.diksed.kriptak.features.component.SortType

@Composable
fun KripTakSortRow(
    onSortChange: (SortType) -> Unit,
    sortTypeByName: SortType,
    sortTypeByPrice: SortType,
    sortTypeByPercentage: SortType,
    sortDirection: SortDirection
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        SortBox(
            sortName = "İsim",
            onSortChange = { onSortChange(SortType.NAME) },
            sortType = sortTypeByName,
            modifier = Modifier.weight(1f),
            firstSort = true,
            sortDirection = sortDirection
        )
        SortBox(
            sortName = "1g Grafik",
            sortType = SortType.NONE,
            onSortChange = {},
            modifier = Modifier.weight(1f),
            sortDirection = SortDirection.DEFAULT
        )
        SortBox(
            sortName = "Fiyat",
            onSortChange = { onSortChange(SortType.PRICE) },
            sortType = sortTypeByPrice,
            modifier = Modifier.weight(1f),
            sortDirection = sortDirection
        )
        SortBox(
            sortName = "1g %",
            onSortChange = { onSortChange(SortType.PERCENTAGE) },
            sortType = sortTypeByPercentage,
            modifier = Modifier.weight(1f),
            lastSort = true,
            sortDirection = sortDirection,
        )
    }
}
