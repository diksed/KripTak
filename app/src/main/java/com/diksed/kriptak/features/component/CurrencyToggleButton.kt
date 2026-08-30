package com.diksed.kriptak.features.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.diksed.kriptak.features.ui.theme.boxColor
import com.diksed.kriptak.utils.Currency
import com.diksed.kriptak.utils.LocalKripTakApp

/**
 * Small pill button that shows the app's currently selected display currency
 * (see LocalKripTakApp) and toggles it on tap. Disabled until a USD->TRY rate
 * has actually been fetched, so it never offers a conversion we can't do yet.
 */
@Composable
fun CurrencyToggleButton(modifier: Modifier = Modifier) {
    val app = LocalKripTakApp.current ?: return
    val currency = app.currency.value
    val enabled = app.usdToTryRate.value != null

    KripTakText(
        text = "${currency.symbol} ${currency.name}",
        fontSize = 12.sp,
        fontWeight = FontWeight.Bold,
        modifier = modifier
            .background(boxColor, shape = RoundedCornerShape(20.dp))
            .clickable(enabled = enabled) { app.toggleCurrency() }
            .padding(horizontal = 12.dp, vertical = 6.dp)
    )
}
