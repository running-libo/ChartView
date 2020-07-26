package com.example.chartview;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    private BarChartView barChartView;
    private LineChartView lineChartView;
    private PieChartView pieChartView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        barChartView = findViewById(R.id.barview);
        lineChartView = findViewById(R.id.lineview);
        pieChartView = findViewById(R.id.pieview);

        findViewById(R.id.btn_bar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideAll();
                barChartView.setVisibility(View.VISIBLE);
            }
        });

        findViewById(R.id.btn_line).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideAll();
                lineChartView.setVisibility(View.VISIBLE);
            }
        });

        findViewById(R.id.btn_pie).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideAll();
                pieChartView.setVisibility(View.VISIBLE);
            }
        });
    }

    private void hideAll() {
        barChartView.setVisibility(View.GONE);
        lineChartView.setVisibility(View.GONE);
        pieChartView.setVisibility(View.GONE);
    }
}