package com.diksed.kriptak.features.screen.settings

import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.os.LocaleListCompat
import com.diksed.kriptak.R
import com.diksed.kriptak.features.component.KripTakScaffold
import com.diksed.kriptak.features.component.KripTakText
import com.diksed.kriptak.features.component.KripTakTopBar
import com.diksed.kriptak.features.ui.theme.Gray69
import com.diksed.kriptak.features.ui.theme.PaleViolet
import com.diksed.kriptak.features.ui.theme.VeryDarkViolet
import com.diksed.kriptak.features.ui.theme.White
import com.diksed.kriptak.features.ui.theme.boxColor
import com.diksed.kriptak.utils.Currency
import com.diksed.kriptak.utils.LocalKripTakApp
import com.diksed.kriptak.utils.vibrate
import java.util.Locale

@Composable
fun SettingsScreen() {
    val app = LocalKripTakApp.current
    val context = LocalContext.current
    val currency = app?.currency?.value ?: Currency.USD
    val isCurrencyReady = app?.usdToTryRate?.value != null
    val isTurkish = currentAppLanguageIsTurkish()

    KripTakScaffold(
        modifier = Modifier.fillMaxSize(),
        content = {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    // Same horizontal inset every other screen uses (10dp) - KripTakTopBar
                    // sizes its logo relative to however much width it's given, so a
                    // different padding here made the logo render smaller than elsewhere.
                    .padding(start = 10.dp, end = 10.dp, bottom = 80.dp)
            ) {
                KripTakTopBar()

                Spacer(modifier = Modifier.height(16.dp))

                // Extra breathing room for the setting rows specifically, without
                // affecting the top bar/logo above.
                Column(modifier = Modifier.padding(horizontal = 10.dp)) {
                    SettingRow {
                        KripTakText(text = stringResource(id = R.string.language), fontSize = 15.sp)
                        Row {
                            SettingsChip(
                                text = "TR",
                                isSelected = isTurkish,
                                onClick = {
                                    if (!isTurkish) {
                                        vibrate(context)
                                        setAppLanguage("tr")
                                    }
                                }
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            SettingsChip(
                                text = "EN",
                                isSelected = !isTurkish,
                                onClick = {
                                    if (isTurkish) {
                                        vibrate(context)
                                        setAppLanguage("en")
                                    }
                                }
                            )
                        }
                    }

                    HorizontalDivider(color = boxColor, modifier = Modifier.padding(vertical = 4.dp))

                    SettingRow {
                        Column {
                            KripTakText(text = stringResource(id = R.string.settingsCurrencyLabel), fontSize = 15.sp)
                            if (!isCurrencyReady) {
                                KripTakText(
                                    text = stringResource(id = R.string.settingsCurrencyWaiting),
                                    fontSize = 12.sp,
                                    color = Gray69,
                                    modifier = Modifier.padding(top = 2.dp)
                                )
                            }
                        }
                        Row {
                            SettingsChip(
                                text = Currency.USD.name,
                                isSelected = currency == Currency.USD,
                                onClick = {
                                    if (currency != Currency.USD) {
                                        vibrate(context)
                                        app?.toggleCurrency()
                                    }
                                }
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            SettingsChip(
                                text = "${Currency.TRY.symbol} ${Currency.TRY.name}",
                                isSelected = currency == Currency.TRY,
                                onClick = {
                                    if (currency != Currency.TRY) {
                                        vibrate(context)
                                        app?.toggleCurrency()
                                    }
                                }
                            )
                        }
                    }
                }
            }
        },
    )
}

private fun currentAppLanguageIsTurkish(): Boolean {
    val appLocales = AppCompatDelegate.getApplicationLocales()
    val languageTag = if (!appLocales.isEmpty) appLocales[0]?.language else Locale.getDefault().language
    return languageTag == "tr"
}

private fun setAppLanguage(languageTag: String) {
    AppCompatDelegate.setApplicationLocales(LocaleListCompat.forLanguageTags(languageTag))
}

@Composable
private fun SettingRow(content: @Composable () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 14.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        content()
    }
}

/** Small compact pill, sized to its own text - not a full-height Material Button. */
@Composable
private fun SettingsChip(text: String, isSelected: Boolean, onClick: () -> Unit) {
    KripTakText(
        text = text,
        fontSize = 13.sp,
        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
        color = if (isSelected) VeryDarkViolet else White,
        modifier = Modifier
            .background(
                color = if (isSelected) PaleViolet else boxColor,
                shape = RoundedCornerShape(20.dp)
            )
            .clickable(onClick = onClick)
            .padding(horizontal = 14.dp, vertical = 8.dp)
    )
}
