package com.diksed.kriptak.features.component

//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.*
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun KripTakScaffold(
    modifier: Modifier = Modifier,
    scaffoldState: ScaffoldState = rememberScaffoldState(),
    snackBarHost: @Composable (SnackbarHostState) -> Unit = {
        KripTakSnackBar(
            snackbarHostState = scaffoldState.snackbarHostState,
            snackBarEnum = SnackBarEnum.ERROR
        )
    },
    topBar: @Composable (() -> Unit) = {},
    bottomBar: @Composable () -> Unit = {},
    floatingActionButton: @Composable (() -> Unit) = {},
    backgroundColor: Color = MaterialTheme.colors.background,
    contentColor: Color = contentColorFor(backgroundColor),
    content: @Composable (PaddingValues) -> Unit
) {
    Scaffold(
        modifier = modifier,
        scaffoldState = scaffoldState,
        snackbarHost = snackBarHost,
        topBar = topBar,
        content = content,
        bottomBar = bottomBar,
        contentColor = contentColor,
        floatingActionButton = floatingActionButton,
        floatingActionButtonPosition = FabPosition.Center,
        isFloatingActionButtonDocked = true,
        backgroundColor = backgroundColor
    )
}