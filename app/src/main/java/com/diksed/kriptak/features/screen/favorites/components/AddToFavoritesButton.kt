package com.diksed.kriptak.features.screen.favorites.components

import android.content.ComponentName
import android.content.Intent
import android.content.pm.PackageManager
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.diksed.kriptak.R
import com.diksed.kriptak.features.ui.theme.bottomAppBarItemColor
import com.diksed.kriptak.features.ui.theme.boxColor

@Composable
fun KripTakButton(onClick: () -> Unit = {}, buttonText: Int = R.string.tryAgain) {
    val context = LocalContext.current
    val packageManager: PackageManager = context.packageManager
    val intent: Intent = packageManager.getLaunchIntentForPackage(context.packageName)!!
    val componentName: ComponentName = intent.component!!
    val restartIntent: Intent = Intent.makeRestartActivityTask(componentName)

    Button(
        onClick = {
            if (buttonText == R.string.tryAgain) {
                context.startActivity(restartIntent)
                Runtime.getRuntime().exit(0)
            } else {
                onClick()
            }
        },
        colors = ButtonDefaults.buttonColors(
            containerColor = boxColor,
            contentColor = bottomAppBarItemColor
        ),
        shape = RoundedCornerShape(10.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .height(48.dp)
            .shadow(4.dp, shape = RoundedCornerShape(10.dp))
    ) {
        Text(
            text = stringResource(id = buttonText),
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp
        )
    }
}
