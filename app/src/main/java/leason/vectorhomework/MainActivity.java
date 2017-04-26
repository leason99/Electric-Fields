package leason.vectorhomework;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.drawable.BitmapDrawable;
import android.support.annotation.IntDef;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.RelativeLayout;


public class MainActivity extends AppCompatActivity {
    Dot dot1, dot2;
    NumberPicker dot1x, dot1y, dot2x, dot2y;
    double start_locateX,start_locateY;
    double dot1x_loacte, dot1y_loacte, dot2x_loacte, dot2y_loacte;

    LinearLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        linearLayout = (LinearLayout) findViewById(R.id.view);
        dot1x = (NumberPicker) findViewById(R.id.dot1x);
        dot1y = (NumberPicker) findViewById(R.id.dot1y);
        dot2x = (NumberPicker) findViewById(R.id.dot2x);
        dot2y = (NumberPicker) findViewById(R.id.dot2y);



    //    start_locateX=200;
    //    start_locateY=0;

      //  dot1x_loacte=start_locateX;
       // dot1y_loacte=start_locateY;
      //  dot2x_loacte=-start_locateX;
     //   dot2y_loacte=start_locateY;


        NumberPicker.OnScrollListener scrollListener = new NumberPicker.OnScrollListener() {
            @Override
            public void onScrollStateChange(NumberPicker view, int scrollState) {

                switch (view.getId()) {
                    case R.id.dot1x:
                      //  dot1x_loacte = start_locateX+view.getValue()*20;
                        dot1x_loacte = (view.getValue()-10)*40;

                        break;
                    case R.id.dot1y:
                        dot1y_loacte = (view.getValue()-10)*40;
                        break;

                    case R.id.dot2x:
                        dot2x_loacte = (view.getValue()-10)*40;
                        break;
                    case R.id.dot2y:
                        dot2y_loacte = (view.getValue()-10)*40;;
                        break;
                }

            }
        };
        dot1x.setOnScrollListener(scrollListener);
        dot1y.setOnScrollListener(scrollListener);
        dot2x.setOnScrollListener(scrollListener);
        dot2y.setOnScrollListener(scrollListener);

    Button show= (Button) findViewById(R.id.show);

        show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dot1 = new Dot(dot1x_loacte, dot1y_loacte, dot1.Q);
                dot2 = new Dot(dot2x_loacte, dot2y_loacte, dot2.Q);

                linearLayout.removeViewAt(0);
                linearLayout.addView(new MyImageView(MainActivity.this, dot1, dot2, linearLayout.getWidth(), linearLayout.getHeight()));

            }
        });
        Button btn = (Button) findViewById(R.id.changeQ);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dot1 = new Dot(dot1x_loacte, dot1y_loacte, dot1.Q);
                dot2 = new Dot(dot2x_loacte, dot2y_loacte, -dot2.Q);
                linearLayout.removeViewAt(0);
                linearLayout.addView(new MyImageView(MainActivity.this, dot1, dot2, linearLayout.getWidth(), linearLayout.getHeight()));

            }
        });




    }

    @Override
    protected void onStart() {
        super.onStart();

        dot1x.setMaxValue(20);
        dot1x.setValue(5);
        dot1y.setMaxValue(20);
        dot1y.setValue(10);
        dot2x.setMaxValue(20);
        dot2x.setValue(15);
        dot2y.setMaxValue(20);
        dot2y.setValue(10);


        dot1x_loacte = (dot1x.getValue()-10)*40;


        dot1y_loacte = (dot1y.getValue()-10)*40;


        dot2x_loacte = (dot2x.getValue()-10)*40;

        dot2y_loacte = (dot2y.getValue()-10)*40;

        dot1 = new Dot(dot1x_loacte, dot1y_loacte, 1);
        dot2 = new Dot(dot2x_loacte, dot2y_loacte, 1);

        LinearLayout linearLayout = (LinearLayout) this.findViewById(R.id.view);
        View view=new View(this);
        linearLayout.addView(view);

        linearLayout.measure(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        int width = linearLayout.getMeasuredWidth();
        int height = linearLayout.getMeasuredHeight();


        MyImageView myImageView = new MyImageView(this, dot1, dot2,width, height);
        linearLayout.addView(myImageView, 0);
    }
}
