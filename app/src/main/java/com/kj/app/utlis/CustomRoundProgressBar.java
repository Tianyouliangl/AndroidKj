package com.kj.app.utlis;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import com.kj.app.R;


public class CustomRoundProgressBar extends View {
    private Paint paint;//画笔对象的引用
    private int roundColor;//圆环的颜色
    private int roundProgressColor;//圆环进度的颜色
    private int innerRoundColor;//圆环内部圆颜色
    private float roundWidth;//圆环的宽度
    private int textColor;//中间进度百分比字符串的颜色
    private float textSize ;//中间进度百分比字符串的字体
    private int max;//最大进度
    private int progress;//当前进度
    private boolean isDisplayText;//是否显示中间百分比进度字符串
    private int style;//进度条的风格：空心圆环或者实心圆环
    private static final int STROKE = 0;//空心
    private static final int FILL = 1;//实心

   private ValueAnimator animator;

    public CustomRoundProgressBar(Context context){
        this(context, null);
    }
    public CustomRoundProgressBar(Context context, AttributeSet attrs){
        this(context,attrs,0);
    }
    public CustomRoundProgressBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        // TODO Auto-generated constructor stub

        paint  =  new Paint();
        //从attrs.xml中获取自定义属性和默认值
        TypedArray typedArray  = context.obtainStyledAttributes(attrs, R.styleable.CustomRoundProgressBar);

        roundColor = typedArray.getColor(R.styleable.CustomRoundProgressBar_roundColor, Color.argb(00,00,00,00));
        roundProgressColor = typedArray.getColor(R.styleable.CustomRoundProgressBar_roundProgressColor, Color.WHITE);
        innerRoundColor = typedArray.getColor(R.styleable.CustomRoundProgressBar_innerRoundColor, Color.argb(00,00,00,00));

        roundWidth = typedArray.getDimension(R.styleable.CustomRoundProgressBar_roundWidth, 20);
        textColor  =typedArray.getColor(R.styleable.CustomRoundProgressBar_textColor, Color.WHITE);
        textSize = typedArray.getDimension(R.styleable.CustomRoundProgressBar_textSize, 15);
        max = typedArray.getInteger(R.styleable.CustomRoundProgressBar_max, 100);
        style = typedArray.getInt(R.styleable.CustomRoundProgressBar_style, STROKE);
        isDisplayText  =typedArray.getBoolean(R.styleable.CustomRoundProgressBar_textIsDisplayable, true);
        typedArray.recycle();

    }
    @Override
    protected void onDraw(Canvas canvas) {
        // TODO Auto-generated method stub
        super.onDraw(canvas);

        //画最外层大圆环
        int centerX = getWidth()/2;//获取中心点X坐标
        int certerY = getHeight()/2;//获取中心点Y坐标
        int radius =(int)(centerX - roundWidth/2);//圆环的半径
        paint.setColor(roundColor);
        paint.setStyle(Paint.Style.STROKE);//设置空心
        paint.setStrokeWidth(roundWidth);//设置圆环宽度
        paint.setAntiAlias(true);//消除锯齿
        canvas.drawCircle(centerX,certerY, radius, paint);//绘制圆环

        //绘制圆环内部圆
        paint.setColor(innerRoundColor);
        paint.setStyle(Paint.Style.FILL);
        paint.setAntiAlias(true);
        canvas.drawCircle(centerX, certerY, radius-roundWidth/2, paint);



        //画进度
        paint.setStrokeWidth(roundWidth);//设置圆环宽度
        paint.setColor(roundProgressColor);//设置进度颜色
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStrokeCap(Paint.Cap.ROUND);
        RectF oval = new RectF(centerX - radius, centerX - radius, centerX + radius, certerY + radius);  //用于定义的圆弧的形状和大小的界限
        switch (style) {
            case STROKE:
                {
                paint.setStyle(Paint.Style.STROKE);
                canvas.drawArc(oval, -90, (-(float)progress / max)*360, false, paint); // 根据进度画圆弧
               }
            break;
            case FILL:
                {
                paint.setStyle(Paint.Style.FILL);
                if (progress != 0)
                    canvas.drawArc(oval, 270, (-(float)progress / max)*360, false, paint); // 根据进度画圆弧
                }
            break;
            default:
                break;
        }
        //画中间进度百分比字符串
        paint.setStrokeWidth(0);
        paint.setColor(textColor);
        paint.setTextSize(textSize);
        paint.setTypeface(Typeface.DEFAULT_BOLD);
        int percent = (int)(((float)progress / (float)max) * 100);//计算百分比
        float textWidth = paint.measureText(percent +"%");//测量字体宽度，需要居中显示

        if(isDisplayText && style == STROKE && percent != 0){
            canvas.drawText(percent+"%"+"", centerX-textWidth/2, (centerX - (textSize/4)), paint);
            paint.setStrokeWidth(0);
            paint.setColor(textColor);
            paint.setTextSize(textSize/2);
            paint.setTypeface(Typeface.DEFAULT_BOLD);
            float textWidth2 = paint.measureText("整体车况");
            canvas.drawText("整体车况", centerX-textWidth2/2, certerY+textWidth2/2-10, paint);
        }


    }
    public Paint getPaint() {
        return paint;
    }
    public void setPaint(Paint paint) {
        this.paint = paint;
    }
    public int getRoundColor() {
        return roundColor;
    }
    public void setRoundColor(int roundColor) {
        this.roundColor = roundColor;
    }
    public int getRoundProgressColor() {
        return roundProgressColor;
    }
    public void setRoundProgressColor(int roundProgressColor) {
        this.roundProgressColor = roundProgressColor;
    }
    public float getRoundWidth() {
        return roundWidth;
    }
    public void setRoundWidth(float roundWidth) {
        this.roundWidth = roundWidth;
    }
    public int getTextColor() {
        return textColor;
    }
    public void setTextColor(int textColor) {
        this.textColor = textColor;
    }
    public float getTextSize() {
        return textSize;
    }
    public void setTextSize(float textSize) {
        this.textSize = textSize;
    }
    public synchronized int getMax() {
        return max;
    }
    public synchronized void setMax(int max) {
        if(max < 0){
            throw new IllegalArgumentException("max must more than 0");
        }
        this.max = max;
    }
    public synchronized int getProgress() {
        return progress;
    }
    public boolean isDisplayText() {
        return isDisplayText;
    }
    public void setDisplayText(boolean isDisplayText) {
        this.isDisplayText = isDisplayText;
    }
    public int getStyle() {
        return style;
    }
    public void setStyle(int style) {
        this.style = style;
    }
    public void startDotAnimator(int mProgress,int time) {
        animator = ValueAnimator.ofFloat(max, mProgress);
        animator.setDuration(time);
        animator.setInterpolator(new AccelerateDecelerateInterpolator());
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                progress=Math.round((Float) animation.getAnimatedValue());
                invalidate();
            }
        });
        animator.start();
    }
}
