package com.example.facegameapplication

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Rect

/**
 * 水果类
 */
class Fruit(private var leftFruitBitmap: Bitmap,private var rightFruitBitmap: Bitmap):GameObject {
    companion object {//伴生对象是可以指定名字的，不过一般都省略掉。
//    var leftFruitBitmap: Bitmap? = null
//        var rightFruitBitmap: Bitmap? = null
//    var leftFruitName:String?=null
//        var rightFruitName:String ?=null
    }
//    var leftFruitBitmap: Bitmap? = null
//    var rightFruitBitmap: Bitmap? = null

    private var leftFruitRect: Rect? = Rect(200, 200, 200, 200)
    private var rightFruitRect: Rect? = Rect(200, 200, 200, 200)

//    /**
//     * 绘制水果图片rect
//     */
//    fun Fruit(leftFruitBitmap:Bitmap,rightFruitBitmap:Bitmap) { //l,t,r,b
//        leftFruitRect = Rect(200, 200, 200, 200)
//        rightFruitRect = Rect(200, 200, 200, 200)
//        this.leftFruitBitmap = leftFruitBitmap
//        this.rightFruitBitmap = rightFruitBitmap
//    }

    /**
     * 判断玩家与水果进行碰撞的方法
     */
    fun playerCollide(player: RectPlayer): Boolean {
        return Rect.intersects(
            leftFruitRect,
            player.rectangle
        ) || Rect.intersects(rightFruitRect, player.rectangle)
    }



    override fun update() {}

    override fun draw(canvas: Canvas?) {
        canvas?.drawBitmap(leftFruitBitmap!!, 100f, Constants.SCREEN_HEIGHT/4f, null)
        canvas?.drawBitmap(rightFruitBitmap!!, Constants.SCREEN_WIDTH-300f, Constants.SCREEN_HEIGHT/4f, null)

    }
}