package com.diksed.kriptak.ui.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun MainAppScaffold(
    modifier: Modifier = Modifier,
    snackbarHost: @Composable () -> Unit = {},
    topBar: @Composable (() -> Unit) = {},
    bottomBar: @Composable () -> Unit = {},
    floatingActionButton: @Composable (() -> Unit) = {},
    backgroundColor: Color = MaterialTheme.colorScheme.background,
    contentColor: Color = contentColorFor(backgroundColor),
    content: @Composable (PaddingValues) -> Unit,
) {
    Scaffold(
        modifier = modifier
            .statusBarsPadding()
            .navigationBarsPadding().imePadding(),
        topBar = topBar,
        content = content,
        bottomBar = bottomBar,
        contentColor = contentColor,
        floatingActionButton = floatingActionButton,
        snackbarHost = snackbarHost,
    )
}