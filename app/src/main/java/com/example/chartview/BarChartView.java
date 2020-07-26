package com.example.chartview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;
import androidx.annotation.Nullable;

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
    /** Y轴每单元数量高度 */
    private float unitHeight;
    /** Y轴数据数组 */
    private int[] unitHeightNum = new int[] {100, 200, 300, 400, 500};

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
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        unitHeight = getHeight()/unitHeightNum.length - 20;
        drawYText(canvas);
    }

    /**
     * 绘制Y轴文字
     */
    private void drawYText(Canvas canvas) {
        int height = getHeight() - 30;  //从底部距30开始
        for (int num : unitHeightNum) {
            Rect rect = new Rect();
            String text = num + "万";
            textPaint.getTextBounds(text, 0, text.length(), rect);
            canvas.drawText(text, 0, height, textPaint);

            canvas.drawLine(rect.width() + 20, height, getWidth(), height, linePaint);
            height -= unitHeight;
        }
    }
}
