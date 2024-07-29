package com.diksed.kriptak.features.component

import android.content.Context
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.Yellow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.diksed.kriptak.R
import com.diksed.kriptak.features.ui.theme.White
import android.os.Build
import android.view.Gravity
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.diksed.kriptak.utils.vibrate

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun KripTakFavoriteButton(
    isFavorite: Boolean,
    coinName: String,
    onClick: () -> Unit
) {
    val context = LocalContext.current
    val iconId = if (isFavorite) R.drawable.ic_favorited else R.drawable.ic_unfavorited

    IconButton(
        onClick = {
            onClick()
            vibrate(context)
            showToast(context, isFavorite, coinName)
        }
    ) {
        Icon(
            painter = painterResource(id = iconId),
            contentDescription = null,
            tint = if (isFavorite) Yellow else White,
            modifier = Modifier.size(30.dp)
        )
    }
}

private fun showToast(context: Context, isFavorite: Boolean, coinName: String) {
    val messageResId = if (isFavorite) {
        R.string.removedFromFavorites
    } else {
        R.string.addedToFavorites
    }

    val message = context.getString(messageResId, coinName)

    val toast = Toast.makeText(context, message, Toast.LENGTH_SHORT)
    toast.setGravity(Gravity.BOTTOM, 0, 200)
    toast.show()
}
