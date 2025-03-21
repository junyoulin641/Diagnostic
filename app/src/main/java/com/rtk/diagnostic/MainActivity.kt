package com.rtk.diagnostic

import android.os.Bundle
import android.view.View
import androidx.compose.material3.*
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.fillMaxSize
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.rtk.diagnostic.ui.screen.MainMenu
import com.rtk.diagnostic.ui.screen.ToneScreen
import androidx.compose.ui.Modifier
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.navigation.compose.rememberNavController

class MainActivity : ComponentActivity() {

    private lateinit var windowInsetsController: WindowInsetsControllerCompat
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        windowInsetsController = WindowCompat.getInsetsController(window, window.decorView)
        setContent {
            MaterialTheme {
                // 创建导航控制器
                val navController = rememberNavController()

                // 使用官方NavHost并添加动画
                NavHost(
                    navController = navController,
                    startDestination = "main",
                    modifier = Modifier.fillMaxSize()
                ) {
                    // 主菜单屏幕
                    composable(
                        route = "main",
                        enterTransition = {
                            slideIntoContainer(
                                towards = AnimatedContentTransitionScope.SlideDirection.Up,
                                animationSpec = tween(1000)
                            )
                        },
                        // 定义退出动画
                        exitTransition = {
                            slideOutOfContainer(
                                towards = AnimatedContentTransitionScope.SlideDirection.Down,
                                animationSpec = tween(1000)
                            )
                        }
                    ) {
                        MainMenu(
                            onToneDisplaySelected = {
                                prepareForToneScreen()
                                navController.navigate("tone")
                            }
                        )
                    }

                    // 颜色测试屏幕
                    composable(
                        route = "tone",
                        enterTransition = {
                            slideIntoContainer(
                                towards = AnimatedContentTransitionScope.SlideDirection.Up,
                                animationSpec = tween(1000)
                            )
                        },
                        // 定义退出动画
                        exitTransition = {
                            slideOutOfContainer(
                                towards = AnimatedContentTransitionScope.SlideDirection.Down,
                                animationSpec = tween(1000)
                            )
                        }
                    ) {
                        ToneScreen(
                            onBackPressed = {
                                restoreSystemUI()
                                navController.popBackStack()
                            }
                        )
                    }
                }
            }
        }
    }
    private fun prepareForToneScreen() {
        windowInsetsController.apply {
            hide(WindowInsetsCompat.Type.systemBars())
            systemBarsBehavior = WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        }
    }

    // 恢复系统UI
    private fun restoreSystemUI() {
        windowInsetsController.show(WindowInsetsCompat.Type.systemBars())
    }

    override fun onResume() {
        super.onResume()
    }
}