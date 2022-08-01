package com.drdlx.sceneform_ar_compose.arScreen

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.ar.sceneform.ux.ArFrontFacingFragment

class ArScreenViewModel: ViewModel() {
    companion object {
        private const val TAG = "ArScreenViewModel"
    }

    private val arFragment = MutableLiveData<ArFrontFacingFragment>()

    val uiState = ArScreenUiState(arFragment = arFragment)

    fun setFragment(fragment: ArFrontFacingFragment) {
        arFragment.postValue(fragment)
    }
}