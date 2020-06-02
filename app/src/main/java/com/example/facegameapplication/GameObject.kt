package com.example.facegameapplication

import android.graphics.Canvas

/**
 * Created by jason on 22/10/17.
 */
interface GameObject {
    fun draw(canvas: Canvas?)
    fun update()
}