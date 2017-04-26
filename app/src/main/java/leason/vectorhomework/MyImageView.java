package leason.vectorhomework;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by leason on 2017/4/26.
 */

public class MyImageView extends View {
    Paint paintScaleDisplay;
    Bitmap bitmapScale;
    Canvas canvasScale;
    int width, height;
    double centerY, centerX;
    Dot dotStart, dotEnd;
    List<Path> PathList;
    Paint dotpaint, strokePaint, paint;

    public MyImageView(Context context, Dot dotStart, Dot dotEnd, int witdh, int height) {
        super(context);
        this.width = witdh;
        this.height = height;
        centerX = witdh / 2;
        centerY = height / 2;
        this.dotStart = new Dot(centerX + dotStart.x, centerY + dotStart.y, dotStart.Q);
        this.dotEnd = new Dot(centerX + dotEnd.x, centerY + dotEnd.y, dotEnd.Q);
        PathList = new ArrayList<Path>();


        dotpaint = new Paint();
        dotpaint.setColor(Color.RED);


        strokePaint = new Paint();
        strokePaint.setARGB(255, 255, 0, 0);
        strokePaint.setStyle(Paint.Style.STROKE);
        strokePaint.setStrokeWidth(2);

        paint = new Paint();

        paint.setColor(Color.BLACK);
        paint.setStrokeWidth((float) 8.0);              //线宽
        paint.setStyle(Paint.Style.STROKE);

        for (int deg = 0; deg < 359; deg = deg + 10) {
            PathList.add(vectorCal(this.dotStart, this.dotEnd, deg));
            Log.i("info", String.valueOf(deg));
        }
        if ((this.dotStart.Q > 0) && (this.dotEnd.Q > 0) || (this.dotStart.Q < 0) && (this.dotEnd.Q < 0)) {
            for (int deg = 0; deg < 359; deg = deg + 10) {
                PathList.add(vectorCal(this.dotEnd, this.dotStart, deg));
            }
        }


        // invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        // TODO Auto-generated method stub
        super.onDraw(canvas);


        Rect r = canvas.getClipBounds();
        Rect outline = new Rect(1, 1, r.right - 1, r.bottom - 1);
        canvas.drawRect(outline, strokePaint);


        for (Path path : PathList) {
            canvas.drawPath(path, paint);
        }

        canvas.drawCircle(dotStart.x.floatValue(), dotStart.y.floatValue(), 20, dotpaint);
        canvas.drawCircle(dotEnd.x.floatValue(), dotEnd.y.floatValue(), 20, dotpaint);

    }

    public Path vectorCal(Dot dotStart, Dot dotEnd, int deg) {
        Path path = new Path();
        path.moveTo(dotStart.x.floatValue(), dotStart.y.floatValue());
        Dot testdot = new Dot(dotStart.x + Math.cos(deg), dotStart.y + Math.sin(deg));
       double E= Math.pow(8.85, -12);
        for (int i = 0; i < 500; i++) {

            Dot vector1 = new Dot((testdot.x - dotStart.x), (testdot.y - dotStart.y));
            Dot vector2 = new Dot((testdot.x - dotEnd.x), (testdot.y - dotEnd.y));
            Double con1 = dotStart.Q * ((Math.pow(testdot.x - dotStart.x, 2) + Math.pow(testdot.y - dotStart.y, 2)) * E);
            Double con2 = dotEnd.Q * ((Math.pow(testdot.x - dotEnd.x, 2) + Math.pow(testdot.y - dotEnd.y, 2)) * E);
            Dot vectorAll = new Dot(((vector1.x / con1) + (vector2.x / con2)), ((vector1.y / con1) + (vector2.y / con2)));


            Double conAll = Math.sqrt(Math.pow(vectorAll.x, 2) + Math.pow(vectorAll.y, 2));
            vectorAll.x = 5*vectorAll.x / conAll;
            vectorAll.y = 5*vectorAll.y / conAll;
            if(vectorAll.x==0&&vectorAll.y==0){break;}
            path.lineTo(
                    ((Double)(testdot.x + vectorAll.x)).floatValue(), ((Double)(testdot.y + vectorAll.y)).floatValue());
            testdot = new Dot(testdot.x + vectorAll.x, testdot.y + vectorAll.y);

        }
        Log.i("info", String.valueOf(deg));
        return path;

    }
/*
    public Bitmap drawvector(Dot dotStart, Dot dotEnd) {

        Bitmap bitnap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitnap);
        Paint dotpaint = new Paint();
        dotpaint.setColor(Color.RED);
        canvas.drawCircle(Float.valueOf(String.valueOf(dotStart.x)), Float.valueOf(String.valueOf(dotStart.y)), 20, dotpaint);
        canvas.drawCircle(Float.valueOf(String.valueOf(dotEnd.x)), Float.valueOf(String.valueOf(dotEnd.y)), 20, dotpaint);


        Paint paint = new Paint();

        paint.setColor(Color.BLACK);
        paint.setStrokeWidth((float) 8.0);              //线宽
        paint.setStyle(Paint.Style.STROKE);
        for (int deg = 0; deg < 359; deg = deg + 10) {

            Path path = new Path();
            vectorCal(dotStart, dotEnd, deg, path);
            Log.i("info", String.valueOf(deg));

            canvas.drawPath(path, paint);
        }
        if ((dotStart.Q > 0) && (dotEnd.Q > 0) || (dotStart.Q < 0) && (dotEnd.Q < 0)) {

            for (int deg = 0; deg < 359; deg = deg + 10) {

                Path path = new Path();
                vectorCal(dotEnd, dotStart, deg, path);

                canvas.drawPath(path, paint);
            }

        }
        return bitnap;

    }
*/

}

