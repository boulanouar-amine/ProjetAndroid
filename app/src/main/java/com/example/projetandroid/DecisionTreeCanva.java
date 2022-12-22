package com.example.projetandroid;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.View;

public class DecisionTreeCanva extends View {

    Rect rect;
    Paint paint = new Paint();

    DecisionTreeCanva(Context context){

        super(context);
    }

    @SuppressLint("DrawAllocation")
    @Override
    protected void onDraw(Canvas canvas){
        super.onDraw(canvas);

        rect = new Rect(0,100,200,300);
        paint.setColor(Color.GREEN);
        paint.setStrokeWidth(400);

        paint.setStyle(Paint.Style.STROKE);

        canvas.drawRect(rect,paint);
    }
}
