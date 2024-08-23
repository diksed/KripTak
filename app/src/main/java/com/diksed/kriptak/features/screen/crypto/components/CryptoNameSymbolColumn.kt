package com.diksed.kriptak.features.screen.crypto.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import com.diksed.kriptak.features.component.KripTakText

@Composable
fun CryptoNameSymbolColumn(
    modifier: Modifier = Modifier,
    cryptoName: String,
    cryptoSymbol: String,
    cryptoNameFontSize: TextUnit = 15.sp,
    cryptoSymbolFontSize: TextUnit = 12.sp,
    cryptoNameColor: Color = Color.White,
    cryptoSymbolColor: Color = Color.Gray
) {
    Column(
        verticalArrangement = Arrangement.Center,
        modifier = modifier,
    ) {
        KripTakText(
            text = cryptoName,
            color = cryptoNameColor,
            fontSize = cryptoNameFontSize,
            maxLines = 1,
            fontWeight = FontWeight.SemiBold
        )
        KripTakText(
            text = cryptoSymbol,
            color = cryptoSymbolColor,
            fontSize = cryptoSymbolFontSize,
        )
    }
}