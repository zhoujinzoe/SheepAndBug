package com.sheep;

import android.graphics.Point;

enum BugAct{
	Idle,
	Moving,
}

public class BugPos {
	private Point mBugPoint;
	
	private int mBugR;
	private float mBugDegree = 0;
	private GameView mView;
	
	public BugPos(GameView view) {
		mView = view;
	
	
}