package com.example.myapplication.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.myapplication.presentation.ui.theme.MyApplicationTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlin.time.Duration.Companion.microseconds

@ExperimentalComposeUiApi
@ExperimentalFoundationApi
@AndroidEntryPoint
class MainActivity() : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        val mainViewModel: MainViewModel by viewModels()
//        mainViewModel.deleteProfileInfo()
//        val profileInfo = mainViewModel.getLocalProfileInfo()
//        if (profileInfo == null) {
//            val intent = Intent(this, RegistrationActivity::class.java)
//            startActivity(intent)
//            this.finish()
//        } else {
//            setContent {
//                MyApplicationTheme {
//                    Surface(
//                        modifier = Modifier.fillMaxSize(),
//                        color = MaterialTheme.colors.background
//                    ) {
//                        HomeScreen(profileInfo)
//                    }
//                }
//            }
//        }

        setContent {
            MyApplicationTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    var isDelayPassed by remember { mutableStateOf(false) }
                    LaunchedEffect(Unit) {
                        delay(800.microseconds)
                        isDelayPassed = false
                    }
                    val mainViewModel: MainViewModel = hiltViewModel()
                    val localPicture = mainViewModel.getLocalPictureInfo()
                    if ( localPicture != null) {

                    }
                }
            }
        }
    }
}
