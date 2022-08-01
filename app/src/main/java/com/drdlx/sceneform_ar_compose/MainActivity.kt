package com.drdlx.sceneform_ar_compose

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.LiveData
import com.drdlx.sceneform_ar_compose.arScreen.ArScreen
import com.drdlx.sceneform_ar_compose.arScreen.ArScreenUiState
import com.drdlx.sceneform_ar_compose.arScreen.ArScreenViewModel
import com.drdlx.sceneform_ar_compose.ui.theme.MyApplicationTheme
import com.google.ar.sceneform.ux.ArFrontFacingFragment

class MainActivity : FragmentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val fragmentManager = this.supportFragmentManager

        setContent {
            MyApplicationTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    val arScreenViewModel = ArScreenViewModel()
                    val arScreenFragment = ArFrontFacingFragment()
                    arScreenViewModel.setFragment(arScreenFragment)
                    ArScreen(
                        supportManager = fragmentManager,
                        uiState = arScreenViewModel.uiState
                    )
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MyApplicationTheme {
        Greeting("Android")
    }
}