package com.chronoclock

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.view.WindowCompat
import com.chronoclock.theme.ScoutTheme
import com.chronoclock.ui.CountDownView


class MainActivity : AppCompatActivity() {

    @ExperimentalAnimationApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Turn off the decor fitting system windows, which allows us to handle insets,
        // including IME animations
        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            ActivityUi {
                CountDownView()
            }
        }
    }
}


@Composable
fun ActivityUi(composable: @Composable () -> Unit) {
    Scaffold {
        ScoutTheme {
            // A surface container using the 'background' color from the theme
            Surface(color = MaterialTheme.colors.surface, shape = MaterialTheme.shapes.medium) {
                composable()
            }
        }
    }
}

@ExperimentalAnimationApi
@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ActivityUi {
        CountDownView()
    }
}
