package com.example.asus.myapplication.utils.pan;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;

import com.example.asus.myapplication.R;

import java.util.Random;


public class MySurfaceView extends SurfaceView implements Callback, Runnable {
    private SurfaceHolder mHolder;
    private Canvas mCanvas;
    private Thread t;
    private boolean isRunning;
    private String[] mStrings = new String[]{"1", "2", "3", "4"};
    private int num = 0;
    private Bitmap mCenterBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.arrow);
    /*
     * 奖项的图片
     */
    private int[] mImgs = new int[]{R.mipmap.ic_launcher, R.mipmap.ic_launcher, R.mipmap.ic_launcher, R.mipmap.ic_launcher,};
    /*
     * 盘快的颜色
     */
    private int[] mColors = new int[]{Color.BLACK, Color.BLUE, Color.CYAN,
            Color.GREEN};
    private int mItemCount = 4;
    /*
     * 与图片对应的bitmap数组
     */
    private Bitmap[] mImgsBitmaps;
    /*
     * 盘快的范围
     */
    private RectF mRectF = new RectF();
    /*
     * 盘快的直径
     */
    private int mRadius;
    /*
     * 绘制文字的画笔
     */
    private Paint mTextPaint;
    /*
     * 绘制盘快的画笔
     */
    private Paint mArcPaint;
    /*
     * 盘快滚动的速度
     */
    private double mSpeed;
    private volatile float mStartAngle = 0;
    /*
     * 判断是否点击了停止按钮
     */
    public boolean isShouldEnd;
    /*
     * 转盘的中心位置
     */
    private int mCenter;
    private int mPadding;
    private Bitmap mBgitmap = BitmapFactory.decodeResource(getResources(),
            R.mipmap.m);
    private float mTextSize = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_SP, 20, getResources().getDisplayMetrics());

    public MySurfaceView(Context context) {
        this(context, null);
    }

    public MySurfaceView(Context context, AttributeSet attrs) {

        super(context, attrs);
        mHolder = getHolder();
        mHolder.addCallback(this);
        setFocusable(true);
        setFocusableInTouchMode(true);
        setKeepScreenOn(true);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int with = Math.min(getMeasuredWidth(), getMeasuredHeight());
        mPadding = getPaddingLeft();
        mRadius = with - mPadding * 2;
        mCenter = with / 2;
        setMeasuredDimension(with, with);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        // 初始化绘制盘快的画笔
        mArcPaint = new Paint();
        mArcPaint.setAntiAlias(true);
        mArcPaint.setDither(true);
        // 初始化绘制文字的画笔
        mTextPaint = new Paint();
        mTextPaint.setColor(0xffffffff);
        mTextPaint.setTextSize(mTextSize);

        // 初始化盘快绘制的范围
        mRectF = new RectF(mPadding, mPadding, mPadding + mRadius, mPadding
                + mRadius);
        // 初始化图片
        mImgsBitmaps = new Bitmap[mItemCount];
        for (int i = 0; i < mItemCount; i++) {
            mImgsBitmaps[i] = BitmapFactory.decodeResource(getResources(),
                    mImgs[i]);

        }
        isRunning = true;
        t = new Thread(this);
        t.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width,
                               int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

        isRunning = false;
    }

    @Override
    public void run() {
        while (isRunning) {
            long start = System.currentTimeMillis();

            draw();
            long end = System.currentTimeMillis();
            if (end - start < 50) {
                try {
                    Thread.sleep(50 - (end - start));
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
    }

    private void draw() {
        try {
            mCanvas = mHolder.lockCanvas();
            if (mCanvas != null) {
                // 绘制背景
                drawBg();


                // 绘制盘快
                float tmpAngle = mStartAngle;
                float sweepAngle = 360 / mItemCount;
                for (int i = 0; i < mItemCount; i++) {
                    mArcPaint.setColor(mColors[i]);
                    // 绘制盘快
                    mCanvas.drawArc(mRectF, tmpAngle, sweepAngle, true,
                            mArcPaint);
                    // 绘制文本
                    drawText(tmpAngle, sweepAngle, mStrings[i]);

                    // 绘制Icon
                    drawIcon(tmpAngle, mImgsBitmaps[i]);
                    tmpAngle += sweepAngle;
                }
                mStartAngle += mSpeed;
                if (isShouldEnd) {
                    mSpeed -= 1;

                }
                if (mSpeed <= 0) {
                    mSpeed = 0;
                    isShouldEnd = false;
                }

                //绘制中心箭头
                drawArrow();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (mCanvas != null) {
                mHolder.unlockCanvasAndPost(mCanvas);
            }
        }
    }

    private void drawArrow() {
        mCanvas.drawBitmap(mCenterBitmap, null, new Rect(mCenter - 50, mCenter - 50, mCenter + 50, mCenter + 50), null);
    }

    public void panStart(int index) {
        //计算每一项的角度
        float angle = 360 / mItemCount;

        //计算每一项的中奖范围（当前index）
        //1->150-210
        //0->210~270
//        float from = 270 - (index + 1) * angle;
//        float end = from + angle;
        float from = 270 - (index + 1) * angle+10;
        float end = from + angle-10;
        //设置停下来需要旋转的距离
        float targeFrom = 4 * 360 + from;
        float targeEnd = 4 * 360 + end;
         /*
          * v1->0
		  * 且每次-1
		  * （v1+0）*（v1+1）/2=targeFrom
		  * v1*v1+v1-2*targeFrom=0
		  * v1=(-1+Math.sqrt(1+8*targeFrom))/2)
		  */
        float v1 = (float) ((-1 + Math.sqrt(1 + 8 * targeFrom)) / 2);
        float v2 = (float) ((-1 + Math.sqrt(1 + 8 * targeEnd)) / 2);
        mSpeed = v1 + Math.random() * (v2 - v1);


//		mSpeed = 50;
        isShouldEnd = false;
    }

    public void panEnd() {
        mStartAngle = 0;
        isShouldEnd = true;
    }

    public boolean isStart() {
        return mSpeed != 0;
    }

    public boolean isEnd() {
        return isShouldEnd;
    }

    private void drawIcon(float tmpAngle, Bitmap mBgitmap2) {
        int imgWith = mRadius / 8;
        float angle = (float) ((tmpAngle + 360 / mItemCount / 2) * Math.PI / 180);
        int x = (int) (mCenter + mRadius / 2 / 2 * Math.cos(angle));
        int y = (int) (mCenter + mRadius / 2 / 2 * Math.sin(angle));
        // 确定那个图片位置
        RectF rectF = new RectF(x - imgWith / 2, y - imgWith / 2, x + imgWith
                / 2, y + imgWith / 2);
        mCanvas.drawBitmap(mBgitmap2, null, rectF, null);

    }

    private void drawText(float tmpAngle, float sweepAngle, String string) {
        Path path = new Path();
        path.addArc(mRectF, tmpAngle, sweepAngle);
        // 利用水平偏移量使文字剧中
        float textWith = mTextPaint.measureText(string);
        int hOffset = (int) (mRadius * Math.PI / mItemCount / 2 - textWith / 2);
        int vOffset = mRadius / 2 / 6;
        mCanvas.drawTextOnPath(string, path, hOffset, vOffset, mTextPaint);
    }

    private void drawBg() {
        mCanvas.drawColor(Color.WHITE);
//        mCanvas.drawBitmap(mBgitmap, null, new RectF(mPadding / 2,
//                mPadding / 2, getMeasuredWidth() - mPadding / 2,
//                getMeasuredHeight() - mPadding / 2), null);
        Paint paint = new Paint();
        paint.setARGB(22, 22, 22, 22);
        mCanvas.drawCircle(mCenter, mCenter, mRadius / 2 + 50, paint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_UP) {
            if (event.getX() > mCenter - 50 && event.getX() < mCenter + 50)
                if (event.getY() > mCenter - 50 && event.getY() < mCenter + 50) {
                    if (num % 2 == 0) {
                       panStart(new Random().nextInt(mItemCount));
        Log.d("blw", "start");
                    } else
                       panEnd();
        Log.d("blw","end");
                }
        num++;
        }
        return true;
    }
}
