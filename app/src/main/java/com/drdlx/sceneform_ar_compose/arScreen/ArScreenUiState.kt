package com.drdlx.sceneform_ar_compose.arScreen

import androidx.lifecycle.LiveData
import com.google.ar.sceneform.ux.ArFrontFacingFragment

data class ArScreenUiState(
    val arFragment: LiveData<ArFrontFacingFragment>,
)
