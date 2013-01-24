package com.sheep;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.os.Handler;
import android.os.Message;
import android.view.MotionEvent;
import android.view.View;

class Obs implements Handler.Callback {
	private GameView mView;
	
	public Obs(GameView view) {
		mView = view;
	}

	@Override
	public boolean handleMessage(Message msg) {
		switch (msg.what) {
		case 101:
			mView.animStep();
			break;
		}
		// TODO Auto-generated method stub
		return false;
	}
	
}

public class GameView extends View {
	private Sheep mSheep;
	
	private Point mBugPos;
	private int mBugR;
	private float mBugDegree = 0;
	
	private Obs mObs;
	private Handler mHandler;
	private AlertDialog mDialog;

	public GameView(Context context) {
		super(context);
		mSheep = new Sheep(this);
		
		mBugPos = new Point(100, 300);
		mBugR = 20;
		
		mObs = new Obs(this);
		mHandler = new Handler(mObs);
		
		Message msg = mHandler.obtainMessage(101, 0, 0, null);
		mHandler.sendMessageDelayed(msg, 1000/60);
		
		AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
		builder.setMessage("ok");
		builder.setNegativeButton("ok", null);
		mDialog = builder.create();
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);
		
		mSheep.onDraw(canvas);
		
		Paint paint = new Paint();
		paint.setARGB(0xFF, 0, 0, 0xFF);
		canvas.rotate(mBugDegree, mBugPos.x, mBugPos.y);
		canvas.drawRect(mBugPos.x - mBugR, mBugPos.y - mBugR, mBugPos.x + mBugR, mBugPos.y + mBugR, paint);
		//canvas.drawCircle(mBugPos.x, mBugPos.y, mBugR, paint);
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		int action = event.getAction();
		int x, y;
		switch (action) {
		case MotionEvent.ACTION_DOWN:
			x = (int) event.getX();
			y = (int) event.getY();
			mBugPos.x = x;
			mBugPos.y = y;
			
			invalidate();
			return true;
		case MotionEvent.ACTION_MOVE:
			x = (int) event.getX();
			y = (int) event.getY();
			mBugPos.x = x;
			mBugPos.y = y;
			
			invalidate();
			return true;
		}
		
		return super.onTouchEvent(event);
	}

	public void animStep() {
		mSheep.animStep();
		
		mBugDegree += 10;
		if (mBugDegree >= 360.0)
			mBugDegree -= 360.0;
		
		invalidate();
		
		if (mSheep.isCatched())
			mDialog.show();
		else
			mHandler.sendEmptyMessageDelayed(101, 1000/60);
	}
	
	public int getBugX() {
		return mBugPos.x;
	}
	
	public int getBugY() {
		return mBugPos.y;
	}
	
	public int getBugR() {
		return mBugR;
	}
}
