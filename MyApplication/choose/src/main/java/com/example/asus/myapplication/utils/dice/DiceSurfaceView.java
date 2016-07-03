package com.example.asus.myapplication.utils;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.view.MotionEvent;

/**
 * 色子的渲染视图
 * @author Yue Jinbiao
 *
 */
public class DiceSurfaceView extends GLSurfaceView {

	private DiceRenderer mRenderer = null;
	private float mPreviousX = 0;
	private float mPreviousY = 0;

	public DiceSurfaceView(Context context) {
		super(context);
		// 设置渲染器，
		mRenderer = new DiceRenderer(this);
		setRenderer(mRenderer);
		// 设置描绘方式，
		setAutoRender(false);
		this.requestRender();
	}

	@Override
	public boolean onTouchEvent(MotionEvent e) {
		float x = e.getX();
		float y = e.getY();
		//转换坐标方向；
		y = -y;

		switch (e.getAction()) {
		case MotionEvent.ACTION_MOVE:
			float dx = x - mPreviousX;
			float dy = y - mPreviousY;
			mRenderer.onTouchMove(dx, dy);
		case MotionEvent.ACTION_DOWN:
//			Log.i("tg","touch down/" + x + "/" + y);
			this.mPreviousX = x;
			this.mPreviousY = y;
			break;
		case MotionEvent.ACTION_UP:
//			Log.i("tg","touch up/" + x + "/" + y);
			this.mPreviousX = 0;
			this.mPreviousY = 0;
			setAutoRender(true);
			this.mRenderer.startRotate();
			break;
		}
		this.requestRender();
		return true;
	}
	/**
	 * 设置是否自动连续渲染
	 * @param auto
	 */
	public void setAutoRender(boolean auto){
		// RENDERMODE_WHEN_DIRTY-有改变时重绘-需调用requestRender()
		// RENDERMODE_CONTINUOUSLY -自动连续重绘（默认）
		if(auto){
			setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);
		}else{
			setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
		}
	}

	//重置背景画
	public void resetBackground(int optionalBg){
		TextureManager.bgIndex = optionalBg;
		this.requestRender();
	}
}
