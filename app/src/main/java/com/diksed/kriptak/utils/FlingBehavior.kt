package com.diksed.kriptak.utils

import androidx.compose.animation.core.spring
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.pager.PagerDefaults
import androidx.compose.foundation.pager.PagerState
import androidx.compose.runtime.Composable

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun springPagerFlingBehavior(pagerState: PagerState) =
    PagerDefaults.flingBehavior(
        state = pagerState,
        snapAnimationSpec = spring(dampingRatio = 1.9f, stiffness = 600f),
    )
