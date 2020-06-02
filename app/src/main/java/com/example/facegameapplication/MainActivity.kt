package com.example.facegameapplication

import android.os.Bundle
import android.util.DisplayMetrics
import android.view.Window
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        val dm = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(dm)
        Constants.SCREEN_WIDTH = dm.widthPixels
        Constants.SCREEN_HEIGHT = dm.heightPixels
        var gamePanel:GamePanel = GamePanel(this)
//        gameView.addView(gamePanel)

        setContentView(gamePanel)
    }
}
