package com.forms.Bezier;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;

/**
 * Created by forms on 2017/12/3.
 * 模拟QQ等级增长曲线
 */

public class BezierLayout extends RelativeLayout {

    private static int IMAGE_WIDTH = 90;  //设置圆形图片的宽度
    private static int IMAGE_HEIGHT = 90; //设置圆形图片的高度
    private ImageView ivQQ, ivVIP, ivYVIP, ivYSVIP; // 4个qq等级的图片
    private ImageView ivUser;     //用户头像
    private PointF ptStart, ptControl, ptEnd;   // 开始点坐标、控制点坐标，结束点坐标
    private PointF ptQQ, ptVIP, ptYVIP, ptYSVIP, ptUser;    // 4个曲线上的点
    private Paint mPaint;
    private Bitmap bpUser;
    private boolean isFirst=true;


    public BezierLayout(Context context) {
        this(context, null);
    }

    public BezierLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BezierLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setAntiAlias(true);
        mPaint.setColor(0xFF33ccff);
        mPaint.setStrokeWidth(10);

        ivQQ = new ImageView(context);
        ivQQ.setImageResource(R.mipmap.qq);
        RelativeLayout.LayoutParams parms1 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        parms1.width = IMAGE_WIDTH;
        parms1.height = IMAGE_HEIGHT;
        ivQQ.setLayoutParams(parms1);
        this.addView(ivQQ);

        ivVIP = new ImageView(context);
        ivVIP.setImageResource(R.mipmap.vip);
        RelativeLayout.LayoutParams parms2 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        parms2.width = IMAGE_WIDTH;
        parms2.height = IMAGE_HEIGHT;
        ivVIP.setLayoutParams(parms2);
        this.addView(ivVIP);

        ivYVIP = new ImageView(context);
        ivYVIP.setImageResource(R.mipmap.yvip);
        RelativeLayout.LayoutParams parms3 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        parms3.width = IMAGE_WIDTH;
        parms3.height = IMAGE_HEIGHT;
        ivYVIP.setLayoutParams(parms3);
        this.addView(ivYVIP);

        ivYSVIP = new ImageView(context);
        ivYSVIP.setImageResource(R.mipmap.ysvip);
        RelativeLayout.LayoutParams parms4 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        parms4.width = IMAGE_WIDTH;
        parms4.height = IMAGE_HEIGHT;
        ivQQ.setLayoutParams(parms4);
        this.addView(ivYSVIP);

        bpUser=BitmapFactory.decodeResource(getResources(), R.mipmap.user);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Path path = new Path();
        path.moveTo(ptStart.x, ptStart.y);
        path.quadTo(ptControl.x, ptControl.y, ptEnd.x, ptEnd.y);
        canvas.drawPath(path, mPaint);
        canvas.drawCircle(ptQQ.x, ptQQ.y, 10, mPaint);
        canvas.drawCircle(ptVIP.x, ptVIP.y, 10, mPaint);
        canvas.drawCircle(ptYVIP.x, ptYVIP.y, 10, mPaint);
        canvas.drawCircle(ptYSVIP.x, ptYSVIP.y, 10, mPaint);
        canvas.drawBitmap(bpUser,(ptUser.x-bpUser.getWidth()/2),(ptUser.y-bpUser.getHeight()/2),mPaint);
        if (isFirst){
            moveUserLogo();
            isFirst=false;
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        ptStart = new PointF(0, h * 4 / 5);
        ptEnd = new PointF(w, h / 5);
        ptControl = new PointF(w * 3 / 4, h * 3 / 4);

        ptQQ = BezierUtil.getPointFromQuadBezier(0.15f, ptStart, ptControl, ptEnd);
        ptVIP = BezierUtil.getPointFromQuadBezier(0.38f, ptStart, ptControl, ptEnd);
        ptYVIP = BezierUtil.getPointFromQuadBezier(0.61f, ptStart, ptControl, ptEnd);
        ptYSVIP = BezierUtil.getPointFromQuadBezier(0.85f, ptStart, ptControl, ptEnd);
        ptUser = BezierUtil.getPointFromQuadBezier(0.0f, ptStart, ptControl, ptEnd);

        ((RelativeLayout.LayoutParams) ivQQ.getLayoutParams()).leftMargin = (int) (ptQQ.x - IMAGE_WIDTH / 2);
        ((RelativeLayout.LayoutParams) ivQQ.getLayoutParams()).topMargin = (int) (ptQQ.y - IMAGE_HEIGHT / 2);

        ((RelativeLayout.LayoutParams) ivVIP.getLayoutParams()).leftMargin = (int) (ptVIP.x - IMAGE_WIDTH / 2);
        ((RelativeLayout.LayoutParams) ivVIP.getLayoutParams()).topMargin = (int) (ptVIP.y - IMAGE_HEIGHT / 2);

        ((RelativeLayout.LayoutParams) ivYVIP.getLayoutParams()).leftMargin = (int) (ptYVIP.x - IMAGE_WIDTH / 2);
        ((RelativeLayout.LayoutParams) ivYVIP.getLayoutParams()).topMargin = (int) (ptYVIP.y - IMAGE_HEIGHT / 2);

        ((RelativeLayout.LayoutParams) ivYSVIP.getLayoutParams()).leftMargin = (int) (ptYSVIP.x - IMAGE_WIDTH / 2);
        ((RelativeLayout.LayoutParams) ivYSVIP.getLayoutParams()).topMargin = (int) (ptYSVIP.y - IMAGE_HEIGHT / 2);

    }

    public void moveUserLogo(){
        ValueAnimator value=ValueAnimator.ofFloat(0,0.7f);
        value.setDuration(3000);
        value.setInterpolator(new LinearInterpolator());
        value.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float val= (float) valueAnimator.getAnimatedValue();
                ptUser = BezierUtil.getPointFromQuadBezier(val, ptStart, ptControl, ptEnd);
                invalidate();
            }
        });
        value.start();
    }
}
