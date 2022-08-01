@file:OptIn(ExperimentalPermissionsApi::class)

package com.drdlx.sceneform_ar_compose.arScreen

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.fragment.app.FragmentManager
import com.drdlx.sceneform_ar_compose.utils.Permission
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.ar.sceneform.ux.ArFrontFacingFragment

@Composable
fun ArScreen(
    supportManager: FragmentManager,
    uiState: ArScreenUiState,
) {
    val context = LocalContext.current

    val arFragment = uiState.arFragment.observeAsState()

    Permission(
        Manifest.permission.CAMERA,
        rationale = "camera needed",
        permissionsNotAvailableContent = {
            Column(Modifier) {
                Text("no camera detected")
                Spacer(modifier = Modifier.height(8.dp))
                Button(onClick = {
                    context.startActivity(Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                        data = Uri.fromParts("package", context.packageName, null)
                    })
                }) {
                    Text("Open settings")
                }
            }
        }
    ) {
        arFragment.value?.let {
        CameraPreview(
            supportFragmentManager = supportManager,
            arFragment = it,
        )
        }
    }
}
