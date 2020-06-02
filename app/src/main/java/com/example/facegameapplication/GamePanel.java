package com.example.facegameapplication;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import java.util.Random;

/**
 * Created by jason on 22/10/17.
 */

public class GamePanel extends SurfaceView implements SurfaceHolder.Callback{
    private MainThread thread;


    private RectPlayer player;
    private Fruit fruit;

    private Bitmap playAgain = BitmapFactory.decodeResource(getResources(), R.drawable.button);
    private Bitmap scoreBoard = BitmapFactory.decodeResource(getResources(), R.drawable.menuscore);
    private Bitmap gameOverPic = BitmapFactory.decodeResource(getResources(), R.drawable.gameover);
    private Bitmap tutorialPic = BitmapFactory.decodeResource(getResources(), R.drawable.tutorial);

    private boolean gameStarted = false;
    private boolean gameOver = false;
    private boolean win = false;

    public GamePanel(Context context){
        super(context);
        String[] names = new String[]{"apple","banana"};
//        TextView textView = findActivity(context).findViewById(R.id.fruitName);
//        textView.setText(names[(int)(Math.random()*names.length)]);
        getHolder().addCallback(this);
        thread = new MainThread(getHolder(), this);
        player = new RectPlayer(new Rect(100, 100, 100, 100), BitmapFactory.decodeResource(getResources(), R.drawable.bird));
//        Fruit.Companion.setLeftFruitBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.apple));
//        Fruit.Companion.setLeftFruitBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.banana));
//        Fruit.Companion.setLeftFruitName("apple");
//        Fruit.Companion.setRightFruitName("banana");
        fruit = new Fruit(changeBitmapSize(R.drawable.apple),changeBitmapSize(R.drawable.banana));
        reset();
        setFocusable(true);
    }


    private Bitmap changeBitmapSize(int resourceId) {

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), resourceId);

        int width = bitmap.getWidth();

        int height = bitmap.getHeight();

//设置想要的大小

        int newWidth=200;

        int newHeight=200;

//计算压缩的比率

        float scaleWidth=((float)newWidth)/width;

        float scaleHeight=((float)newHeight)/height;

//获取想要缩放的matrix

        Matrix matrix = new Matrix();

        matrix.postScale(scaleWidth,scaleHeight);

//获取新的bitmap

        bitmap=Bitmap.createBitmap(bitmap,0,0,width,height,matrix,true);

        bitmap.getWidth();

        bitmap.getHeight();

        return bitmap;

    }


    @Nullable
    public static Activity findActivity(Context context) {
        if (context instanceof Activity) {
            return (Activity) context;
        }
        if (context instanceof ContextWrapper) {
            ContextWrapper wrapper = (ContextWrapper) context;
            return findActivity(wrapper.getBaseContext());
        } else {
            return null;
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height){

    }

private boolean withinBitmap(int x, int y, Bitmap bitmap, int leftX, int topY){
    return x < leftX + bitmap.getWidth() && x > leftX && y < topY + bitmap.getHeight() && y > topY;
}

    public void reset(){
        player.init();

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder){
        thread = new MainThread(getHolder(), this);

        thread.setRunning(true);
        thread.start();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder){
        boolean retry = true;

        while(retry)
        {
            try{
                thread.setRunning(false);
                thread.join();
            } catch (Exception e) {e.printStackTrace();}
            retry = false;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event){
        int x = (int) event.getX();
        int y = (int) event.getY();
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:

             if(gameOver)
                {
                    if (withinBitmap(x, y, playAgain,Constants.SCREEN_WIDTH/2 - playAgain.getWidth()/2, Constants.SCREEN_HEIGHT/2 + 2*playAgain.getHeight()/2)) {
                        reset();
                        gameOver = false;
                        //游戏结束再点击重新开始游戏
                    }
                }

                else if (!gameStarted){
                    reset();
                    gameStarted = true;
                 //游戏还未开始点击开始游戏
                }
        }

        return true;
        //return super.onTouchEvent(event);
    }

    public void update(){
        if (!gameOver && gameStarted){
            player.update();
            fruit.update();
            if (fruit.playerCollide(player)){
                gameOver = true;
                //todo判断碰撞是否正确
            }
        }
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);



        fruit.draw(canvas);
        player.draw(canvas);

        if (gameOver){
            canvas.drawBitmap(gameOverPic, Constants.SCREEN_WIDTH/2 - gameOverPic.getWidth()/2, Constants.SCREEN_HEIGHT/2 - 50 -scoreBoard.getHeight(), null);
            canvas.drawBitmap(playAgain, Constants.SCREEN_WIDTH/2 - playAgain.getWidth()/2, Constants.SCREEN_HEIGHT/2 + 2*playAgain.getHeight()/2, null);

        } else if(!gameStarted){
            Paint paint = new Paint();
            canvas.drawBitmap(tutorialPic, Constants.SCREEN_WIDTH/2 - tutorialPic.getWidth()/2, Constants.SCREEN_HEIGHT/2 - tutorialPic.getHeight()/2, paint);
        }


    }



}
