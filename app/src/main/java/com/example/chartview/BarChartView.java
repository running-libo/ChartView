package com.example.chartview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import androidx.annotation.Nullable;
import java.util.ArrayList;

/**
 * create by libo
 * create on 2020/7/26
 * description 柱状图View
 */
public class BarChartView extends View {
    /** 文字Paint */
    private Paint textPaint;
    /** Y轴基准线Paint */
    private Paint linePaint;
    /** 柱型paint */
    private Paint rectPaint;
    /** Y轴每单元数量高度 */
    private float unitHeight;
    /** Y轴数据数组 */
    private int[] unitHeightNum = new int[] {100, 200, 300, 400, 500};
    /** 各个阶段数据数组 */
    private int[] stageNum = new int[] {125, 230, 323, 253, 398, 410};
    private String[] stageStr = new String[] {"一月", "二月", "三月", "四月", "五月", "六月"};
    private int[] colors = new int[] {R.color.green, R.color.blue, R.color.yellow, R.color.red};
    /** X轴单元宽度 */
    private float unitWidth;
    /** 横线左边距大小 */
    private float lineLeftPadding;
    /** 柱状图左右间距 */
    private int rectPadding = 12;
    /** 每个柱子集合 */
    private ArrayList<Bar> bars = new ArrayList<>();
    /** 当前显示值的位置 */
    private int showValuePos = -1;

    public BarChartView(Context context) {
        super(context);
        initPaint();
    }

    public BarChartView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initPaint();
    }

    private void initPaint() {
        textPaint = new Paint();
        textPaint.setColor(getResources().getColor(R.color.textcolor));
        textPaint.setTextSize(40);
        textPaint.setAntiAlias(true);

        linePaint = new Paint();
        linePaint.setColor(getResources().getColor(R.color.linecolor));
        linePaint.setAntiAlias(true);
        linePaint.setStyle(Paint.Style.FILL);
        linePaint.setStrokeWidth(2);

        rectPaint = new Paint();
        rectPaint.setAntiAlias(true);
        rectPaint.setStyle(Paint.Style.FILL);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        drawYText(canvas);
        drawXText(canvas);
        drawBars(canvas);
    }

    /**
     * 绘制Y轴文字及基准线
     */
    private void drawYText(Canvas canvas) {
        int top = getHeight() - 80;  //给底部文字留下高度
        unitHeight = getHeight()/unitHeightNum.length - 20;

        for (int num : unitHeightNum) {
            Rect rect = new Rect();
            String text = num + "万";
            textPaint.getTextBounds(text, 0, text.length(), rect);
            canvas.drawText(text, 0, top, textPaint);  //画文字

            lineLeftPadding = rect.width() + 20;
            canvas.drawLine(lineLeftPadding, top, getWidth(), top, linePaint);  //画横线
            top -= unitHeight;
        }
    }

    /**
     * 绘制X轴文字
     */
    private void drawXText(Canvas canvas) {
        float left = lineLeftPadding;
        unitWidth = getWidth()/stageNum.length - 20;

        Bar bar;
        for (int i=0;i<stageNum.length;i++) {
            canvas.drawText(stageStr[i], left + unitWidth/4, getHeight()-20, textPaint);  //画文字

            float top = getHeight() - (float)stageNum[i]/100*unitHeight;
            int color = getResources().getColor(colors[i%colors.length]);
            bar = new Bar(stageNum[i], left+rectPadding, top, left+unitWidth-rectPadding, getHeight()- 80, color);
            bars.add(bar);
            left += unitWidth;
        }
    }

    /**
     * 绘制柱形
     */
    private void drawBars(Canvas canvas) {
        //画矩形，并左右设置间距
        //根据该项数值获取实际的柱形高度
        //Y轴每格单元高度为100数值
        for (int i=0;i<bars.size();i++) {
            Bar bar = bars.get(i);
            rectPaint.setColor(bar.color);
            canvas.drawRect(bar.left, bar.top, bars.get(i).right, bar.bootom, rectPaint);

            //绘制柱形上数值
            if (showValuePos == i) {
                String value = String.valueOf(bar.value);
                Rect rect = new Rect();
                textPaint.getTextBounds(value, 0, value.length(), rect);
                float textLeft = bar.left + (bar.right-bar.left-rect.width())/2;  //计算使文字在柱形居中位置
                canvas.drawText(value, textLeft, bar.top-20, textPaint);  //绘制柱顶部数值
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            for (int i=0;i<bars.size();i++) {
                if (event.getX() > bars.get(i).left && event.getX() < bars.get(i).right) {
                    //按下事件在当前柱形内
                    showValuePos = i;
                    invalidate();
                }
            }
        }

        return true;
    }

    /**
     * 柱形类
     */
    class Bar {
        private int value;
        private float left;
        private float top;
        private float right;
        private float bootom;
        private int color;

        public Bar(int value, float left, float top, float right, float bootom, int color) {
            this.value = value;
            this.left = left;
            this.top = top;
            this.right = right;
            this.bootom = bootom;
            this.color = color;
        }
    }

}
