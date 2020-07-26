package com.example.chartview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;
import androidx.annotation.Nullable;

/**
 * create by libo
 * create on 2020/7/26
 * description 折线图View
 */
public class LineChartView extends View {
    /** 文字Paint */
    private Paint textPaint;
    /** 基准线Paint */
    private Paint linePaint;
    /** 折线paint */
    private Paint charLinePaint;
    /** Y轴每单元数量高度 */
    private float unitHeight;
    /** Y轴数据数组 */
    private int[] unitHeightNum = new int[] {0, 20, 40, 60, 80, 100};
    private String[] stageStr = new String[] {"Jan", "Feb", "Mar", "Apr", "May", "Jun"};
    /** 横线左边距大小 */
    private float lineLeftPadding;
    /** X轴单元宽度 */
    private float unitWidth;
    /** 各个阶段数据数组 */
    private int[] stageNum = new int[] {56, 40, 82, 74, 60, 92};
    private Path linePath;

    public LineChartView(Context context) {
        super(context);
        initPaint();
    }

    public LineChartView(Context context, @Nullable AttributeSet attrs) {
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
        linePaint.setStyle(Paint.Style.STROKE);
        linePaint.setStrokeWidth(2);

        charLinePaint = new Paint();
        charLinePaint.setColor(getResources().getColor(R.color.orange));
        charLinePaint.setAntiAlias(true);
        charLinePaint.setStyle(Paint.Style.FILL);

        linePath = new Path();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        drawYText(canvas);
        drawXText(canvas);
        drawLinePath(canvas);
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

        for (int i=0;i<stageNum.length;i++) {
            canvas.drawText(stageStr[i], left + unitWidth/4, getHeight()-20, textPaint);  //画文字

            canvas.drawLine(left, getHeight()-80, left, 80, linePaint);

            left += unitWidth;
        }
    }

    /**
     * 绘制折线
     */
    private void drawLinePath(Canvas canvas) {
        float left = lineLeftPadding;

        for (int i=0;i<stageNum.length;i++) {
            float topX = left + unitWidth/2;
            float topY = getHeight() - (float)stageNum[i]/20*unitHeight;

            if (i == 0) {
                linePath.moveTo(topX, topY);
            } else {
                linePath.lineTo(topX, topY);
            }

            canvas.drawCircle(topX, topY, 10, charLinePaint);  //绘制拐点小圆

            left += unitWidth;
        }

        charLinePaint.setStyle(Paint.Style.STROKE);
        charLinePaint.setStrokeWidth(4);
        canvas.drawPath(linePath, charLinePaint);
    }
}
