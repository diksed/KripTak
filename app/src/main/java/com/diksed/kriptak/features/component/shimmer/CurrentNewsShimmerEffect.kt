package com.diksed.kriptak.features.component.shimmer

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.diksed.kriptak.R
import com.diksed.kriptak.features.component.BoxShape
import com.diksed.kriptak.features.component.KripTakCurrentBoxTitle
import com.valentinilk.shimmer.shimmer

@Composable
fun CurrentNewsShimmerEffect() {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        KripTakCurrentBoxTitle(
            title = stringResource(id = R.string.currentNews),
            modifier = Modifier.shimmer()
        )
        LazyColumn {
            items(3) { index ->
                val boxShape = when (index) {
                    0 -> BoxShape.TOP
                    2 -> BoxShape.BOTTOM
                    else -> BoxShape.MIDDLE
                }
                CurrentNewsShimmerItem(boxShape = boxShape)
                Spacer(modifier = Modifier.height(5.dp))
            }
        }
    }
}
