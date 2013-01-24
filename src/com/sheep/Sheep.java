package com.sheep;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;

enum SheepState {
	Idle,
	Catching,
	Catched,
}

public class Sheep {
	private Point mSheepPos;
	private int mSheepR;
	private int mSheepSpeed;
	SheepState mState;
	private GameView mView;
	private long mTime;
	private BitmapDrawable mPicIdle;
	private int[] mIdleAdjust = {0,0,0,0,0,0,0,0,-1,-2,-3,-5,-8,-5,-3,-2,-1};
	private int mIdleIndex;
	private int mIdleMax = 8*mIdleAdjust.length;
	
	public Sheep(GameView view) {
		mView = view;
		
		mSheepPos = new Point(50, 50);
		mSheepR = 40;
		mSheepSpeed = 3;
		
		startIdle();
		
		Resources res = mView.getResources();
		mPicIdle = (BitmapDrawable)res.getDrawable(R.drawable.idle);
		mIdleIndex = 0;
	}
	
	public void startIdle() {
		mState = SheepState.Idle;
		mTime = 0;
	}
	
	public void startCatching() {
		mState = SheepState.Catching;
		mTime = 0;
	}
	
	//public void animStart() {
	//	
	//}
	
	public void animStep() {
		//if (mTime == 0)
		//	mTime = System.currentTimeMillis();
		
		switch (mState) {
		case Idle:
			mIdleIndex += 1;
			mIdleIndex %= mIdleAdjust.length;
			
			mView.invalidate();
			
			mTime++;
			if (mTime > mIdleMax)
				startCatching();
			
			break;
		case Catching:
			int bugX = mView.getBugX();
			int bugY = mView.getBugY();
			int bugR = mView.getBugR();
			
			int deltaX = bugX - mSheepPos.x;
			int deltaY = bugY - mSheepPos.y;
			
			int s = (int) Math.sqrt(deltaX*deltaX+deltaY*deltaY);
			
			if (s <= (mSheepR+bugR)) {
				mState = SheepState.Catched;
			} else {
				
				int addX, addY;
				if (s == 0) {
					addX = addY = 0;
				} else {
					addX = mSheepSpeed*deltaX/s;
					addY = mSheepSpeed*deltaY/s;				
				}
				
				mSheepPos.x += addX;
				mSheepPos.y += addY;
				
				mView.invalidate();
			}
			break;
		case Catched:
			break;
		}
	}
	
	public void onDraw(Canvas canvas) {
		canvas.save();
		
		Paint paint = new Paint();
		int x,y,bottom,w,h;
		Bitmap bitmap;
		Rect src = new Rect();
		Rect dst = new Rect();
		switch (mState) {
		case Idle:
			// head
			// body
			// save
			// ro
			// leg first
			// restore
			// save
			// ro
			// leg last
			// restore
			canvas.rotate(180, mSheepPos.x, mSheepPos.y);
			//canvas.scale(1, -1, 0, canvas.getHeight()/2);
			
			bitmap = mPicIdle.getBitmap();
			w = mPicIdle.getIntrinsicWidth();
			h = mPicIdle.getIntrinsicHeight();
			src.left = 0;
			src.top = 0;
			src.right = w;
			src.bottom = h;
			
			bottom = mSheepPos.y + h/2; 
			h += mIdleAdjust[mIdleIndex];
			x = mSheepPos.x - w/2;
			y = bottom-h;
			
			dst.left = x;
			dst.right = x+w;
			dst.top = y;
			dst.bottom = bottom;

			canvas.drawBitmap(bitmap, src, dst, paint);
			//canvas.drawBitmap(bitmap, x, y, paint);
			break;
		case Catching:
			paint.setARGB(0xFF, 0xFF, 0, 0);
			canvas.drawCircle(mSheepPos.x, mSheepPos.y, mSheepR, paint);
			break;
		}
		
		canvas.restore();
	}
	
	public boolean isCatched() {
		return (mState == SheepState.Catched);
	}
}
