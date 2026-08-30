package com.diksed.kriptak.features.component

import android.content.Context
import android.view.Gravity
import android.widget.Toast
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Yellow
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.diksed.kriptak.R
import com.diksed.kriptak.data.model.PriceAlert
import com.diksed.kriptak.features.ui.theme.Gray69
import com.diksed.kriptak.features.ui.theme.PaleViolet
import com.diksed.kriptak.features.ui.theme.White
import com.diksed.kriptak.features.ui.theme.boxColor
import com.diksed.kriptak.features.ui.theme.scaffoldBackgroundColor
import com.diksed.kriptak.utils.vibrate

/**
 * Bell icon that lets the user set (or remove) a price alert for the currently
 * viewed coin. Tapping while an alert is active removes it directly; tapping
 * while there's none opens a small dialog to pick a target price.
 */
@Composable
fun PriceAlertButton(
    coinName: String,
    currentPrice: Double?,
    activeAlert: PriceAlert?,
    onSetAlert: (Double) -> Unit,
    onRemoveAlert: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    var showDialog by rememberSaveable { mutableStateOf(false) }

    IconButton(
        onClick = {
            vibrate(context)
            if (activeAlert != null) {
                onRemoveAlert()
                showToast(context, context.getString(R.string.priceAlertRemovedMessage, coinName))
            } else {
                showDialog = true
            }
        },
        modifier = modifier
    ) {
        Icon(
            imageVector = Icons.Default.Notifications,
            contentDescription = null,
            tint = if (activeAlert != null) Yellow else White,
            modifier = Modifier.size(26.dp)
        )
    }

    if (showDialog) {
        PriceAlertDialog(
            coinName = coinName,
            currentPrice = currentPrice,
            onDismiss = { showDialog = false },
            onConfirm = { target ->
                onSetAlert(target)
                showDialog = false
                showToast(context, context.getString(R.string.priceAlertSetMessage, coinName))
            }
        )
    }
}

@Composable
private fun PriceAlertDialog(
    coinName: String,
    currentPrice: Double?,
    onDismiss: () -> Unit,
    onConfirm: (Double) -> Unit
) {
    var input by rememberSaveable { mutableStateOf(currentPrice?.toString() ?: "") }
    var isInvalid by remember { mutableStateOf(false) }

    AlertDialog(
        onDismissRequest = onDismiss,
        containerColor = boxColor,
        titleContentColor = White,
        textContentColor = White,
        shape = RoundedCornerShape(16.dp),
        title = { KripTakText(text = stringResource(id = R.string.setPriceAlert)) },
        text = {
            OutlinedTextField(
                value = input,
                onValueChange = {
                    input = it
                    isInvalid = false
                },
                label = { KripTakText(text = stringResource(id = R.string.priceAlertTargetHint), color = Gray69) },
                singleLine = true,
                isError = isInvalid,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                textStyle = TextStyle(color = White),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = White,
                    unfocusedTextColor = White,
                    cursorColor = White,
                    focusedBorderColor = PaleViolet,
                    unfocusedBorderColor = Gray69,
                    focusedContainerColor = scaffoldBackgroundColor,
                    unfocusedContainerColor = scaffoldBackgroundColor,
                    errorContainerColor = scaffoldBackgroundColor,
                )
            )
            if (isInvalid) {
                KripTakText(
                    text = stringResource(id = R.string.priceAlertInvalidPrice),
                    color = Color(0xFFFF6B6B),
                    modifier = Modifier.padding(top = 4.dp)
                )
            }
        },
        confirmButton = {
            TextButton(
                onClick = {
                    val target = input.replace(',', '.').toDoubleOrNull()
                    if (target == null || target <= 0.0) {
                        isInvalid = true
                    } else {
                        onConfirm(target)
                    }
                },
                colors = ButtonDefaults.textButtonColors(contentColor = PaleViolet)
            ) {
                KripTakText(text = stringResource(id = R.string.setAction))
            }
        },
        dismissButton = {
            TextButton(
                onClick = onDismiss,
                colors = ButtonDefaults.textButtonColors(contentColor = Gray69)
            ) {
                KripTakText(text = stringResource(id = R.string.cancel))
            }
        }
    )
}

private fun showToast(context: Context, message: String) {
    val toast = Toast.makeText(context, message, Toast.LENGTH_SHORT)
    toast.setGravity(Gravity.BOTTOM, 0, 200)
    toast.show()
}
