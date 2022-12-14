package com.drdlx.sceneform_ar_compose.arScreen

import android.content.Context
import android.view.View
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.viewinterop.AndroidView
import androidx.fragment.app.FragmentContainerView
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.commit
import com.google.ar.sceneform.ux.ArFrontFacingFragment

@Composable
fun CameraPreview(
    supportFragmentManager: FragmentManager,
    arFragment: ArFrontFacingFragment,
) {

    FragmentContainer(
        modifier = Modifier.fillMaxSize(),
        fragmentManager = supportFragmentManager,
        commit = { add(it, arFragment) }
    )

}

/** Access to package-private method in FragmentManager through reflection */
private fun FragmentManager.onContainerAvailable(view: FragmentContainerView) {
    val method = FragmentManager::class.java.getDeclaredMethod(
        "onContainerAvailable",
        FragmentContainerView::class.java
    )
    method.isAccessible = true
    method.invoke(this, view)
}

@Composable
fun FragmentContainer(
    modifier: Modifier = Modifier,
    fragmentManager: FragmentManager,
    commit: FragmentTransaction.(containerId: Int) -> Unit
) {
    val localView = LocalView.current
    val containerId = rememberSaveable {
        mutableStateOf(View.generateViewId()) }
    val container = remember {
        mutableStateOf<FragmentContainerView?>(null) }
    val viewBlock: (Context) -> View = remember(localView) {
        { context ->
            FragmentContainerView(context)
                .apply { id = containerId.value }
                .also {
                    fragmentManager.commit { commit(it.id) }
                    container.value = it
                }
        }
    }
    AndroidView(
        modifier = modifier,
        factory = viewBlock,
        update = {},
    )

    val localContext = LocalContext.current
    DisposableEffect(localView, localContext, container) {
        onDispose {
            val existingFragment = fragmentManager.findFragmentById(container.value?.id ?: 0)
            if (existingFragment != null &&
                !fragmentManager.isStateSaved) {
                fragmentManager.commit {
                    detach(existingFragment)
                    remove(existingFragment)
                }
            }
        }
    }
}