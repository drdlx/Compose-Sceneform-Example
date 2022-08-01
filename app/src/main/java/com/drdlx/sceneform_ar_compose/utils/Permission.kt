package com.drdlx.sceneform_ar_compose.utils

import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionsRequired
import com.google.accompanist.permissions.rememberMultiplePermissionsState

@ExperimentalPermissionsApi
@Composable
fun Permission(
    vararg permissions: String,
    rationale: String = "I need this permission",
    permissionsNotAvailableContent: @Composable () -> Unit = { },
    content: @Composable () -> Unit = { }
) {
    val multiplePermissionsState = rememberMultiplePermissionsState(permissions = permissions.asList())
    PermissionsRequired(
        multiplePermissionsState = multiplePermissionsState,
        permissionsNotGrantedContent = { Rationale(
            text = rationale,
            onRequestPermission = {
                multiplePermissionsState.launchMultiplePermissionRequest()
            }
        )},
        permissionsNotAvailableContent = permissionsNotAvailableContent,
        content = content,
    )

}

@Composable
private fun Rationale(
    text: String,
    onRequestPermission: () -> Unit
) {
    AlertDialog(
        onDismissRequest = { /* Don't */ },
        title = {
            Text(text = "Permission required")
        },
        text = {
            Text(text)
        },
        confirmButton = {
            Button(onClick = onRequestPermission) {
                Text("Ok")
            }
        }
    )
}