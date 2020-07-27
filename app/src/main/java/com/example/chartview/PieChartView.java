package com.example.chartview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import androidx.annotation.Nullable;

/**
 * create by libo
 * create on 2020/7/26
 * description 饼状图
 */
public class PieChartView extends View {
    private Paint piePaint;
    /** 圆环宽度 */
    private int ringWidth;
    private int[] colors = new int[] {R.color.green, R.color.blue, R.color.yellow, R.color.red, R.color.orange};
    private int[] values = new int[] {21, 12, 30, 23, 14};
    private String[] titles = new String[] {"医院", "学校", "酒店", "商场", "商业建筑"};
    private Paint textPaint;
    private Paint centerTextPaint;
    private String title = "设施占比";

    public PieChartView(Context context) {
        super(context);
        init();
    }

    public PieChartView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        piePaint = new Paint();
        piePaint.setAntiAlias(true);
        piePaint.setStyle(Paint.Style.STROKE);

        textPaint = new Paint();
        textPaint.setAntiAlias(true);
        textPaint.setColor(getResources().getColor(R.color.white));
        textPaint.setTextSize(45);

        centerTextPaint = new Paint();
        centerTextPaint.setAntiAlias(true);
        centerTextPaint.setColor(getResources().getColor(R.color.colorPrimary));
        centerTextPaint.setTextSize(55);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        drawRing(canvas);
        drawText(canvas);
        drawCenterText(canvas);
    }

    /**
     * 画分块圆环
     */
    private void drawRing(Canvas canvas) {
        ringWidth = getMeasuredWidth()/5;
        piePaint.setStrokeWidth(ringWidth);
        RectF rectF = new RectF(ringWidth, ringWidth, getWidth()-ringWidth, getHeight()-ringWidth);

        int startAngle = -90;  //12点钟方向起始
        for (int i=0;i<values.length;i++) {
            float sweepAngle = (float) values[i]/100*360;
            piePaint.setColor(getResources().getColor(colors[i]));
            piePaint.setAlpha(190);
            canvas.drawArc(rectF, startAngle, sweepAngle, false, piePaint);
            startAngle += sweepAngle;
        }
    }

    /**
     * 画每分块文字
     */
    private void drawText(Canvas canvas) {
        int startAngle = 0;  //12点钟方向起始
        int radius = getWidth()/2-ringWidth;
        for (int i=0;i<titles.length;i++) {
            float sweepAngle = (float) values[i]/100*360;

            double angle = Math.toRadians(startAngle + sweepAngle/2);
            float x = (float) (getWidth()/2 + radius*Math.sin(angle));
            float y = (float) (getHeight()/2 - radius*Math.cos(angle));

            //横坐标需要全部左移文字宽度的一半
            Rect rect = new Rect();
            textPaint.getTextBounds(titles[i], 0, titles[i].length(), rect);
            canvas.drawText(titles[i], x-rect.width()/2, y, textPaint);

            startAngle += sweepAngle;
        }
    }

    /**
     * 中心文字
     */
    private void drawCenterText(Canvas canvas) {
        Rect rect = new Rect();
        centerTextPaint.getTextBounds(title, 0, title.length(), rect);
        canvas.drawText(title, getWidth()/2-rect.width()/2, getHeight()/2+rect.height()/2, centerTextPaint);
    }

}
