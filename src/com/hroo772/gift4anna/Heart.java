package com.hroo772.gift4anna;

import java.util.Random;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.CycleInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;

public class Heart {
	private static final String TAG = "Gift4AnnaActivity";
	protected ImageView imgHeart;
	private Activity thisAct;
	
	public Heart(Activity act, int color) {
		// Get the activity so the heart can be deleted from the array
		// when it leaves the screen.
		thisAct = act;
		
		// Gotta have the randomness...
		Random rand = new Random();
		// Creates a heart in the center of the gift box
		
		// Find the FrameLayout from the MainActivity so we can add the heart to it
		FrameLayout flCont = (FrameLayout)act.findViewById(R.id.container);
		
		// Get the dummy heart to copy from
		ImageView dummyHeart = (ImageView)act.findViewById(R.id.dummyHeart);
		
		// Get the giftbox so you can get it's location
		ImageButton gb = (ImageButton)flCont.findViewById(R.id.giftBox);
		
		// Create the Image with the heart on it
		imgHeart = new ImageView(act);
		// Get layout parameters from the dummy heart to clone it's position
		imgHeart.setLayoutParams(dummyHeart.getLayoutParams());
		// Set all of the attributes
		
		imgHeart.setPadding(0, 0, 0, 0);
		imgHeart.setClickable(false);
		imgHeart.setVisibility(View.VISIBLE);
		// Randomly select the color of the heart
		switch (color) {
			case 0: imgHeart.setImageResource(R.drawable.heart_pink);break;
			case 1: imgHeart.setImageResource(R.drawable.heart_creme);break;
			default: imgHeart.setImageResource(R.drawable.heart_red); break;
		}
		
		Log.d(TAG, "heart created");
		// Add it to FrameLayout
		flCont.addView(imgHeart,0);
		
		// Once it's been added, start it's animator so it will
		// shoot off of the top of the screen
		ObjectAnimator animY = ObjectAnimator.ofFloat(imgHeart, "y", gb.getTop()+((gb.getHeight()/2)-(dummyHeart.getWidth()/2)), -80);
		animY.setDuration(4000);
		
		// Make it drift to some fraction of the screen width left or right
		ObjectAnimator animX = ObjectAnimator.ofFloat(imgHeart, "x", gb.getLeft()+((gb.getWidth()/2)-(dummyHeart.getWidth()/2)), (((flCont.getWidth()+200)*(rand.nextFloat()))-100));
		animX.setInterpolator(new AccelerateInterpolator(0.93f));
		animX.setDuration(4000);
		
		// Give the hearts a rotation which cycles so it looks like they are floating
		ObjectAnimator animFloat;
		if(rand.nextBoolean()) {
			animFloat = ObjectAnimator.ofFloat(imgHeart, "rotation", -30,30);
		} else {
			animFloat = ObjectAnimator.ofFloat(imgHeart, "rotation", 30,-30);
		}
		animFloat.setInterpolator(new CycleInterpolator(14));
		animFloat.setStartDelay(700);
		animFloat.setDuration(30000);
		
		// Play both together
		AnimatorSet animGroup = new AnimatorSet();
		animGroup.playTogether(animY,animX,animFloat);
		animGroup.start();
		Log.d(TAG, "heart animation started");
		
		animGroup.addListener(new AnimatorListener() {
			@Override
			public void onAnimationEnd(Animator animation) {
				// Call once it's off of the screen
				leaveScreen();
			}

			@Override
			public void onAnimationCancel(Animator animation) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onAnimationRepeat(Animator animation) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onAnimationStart(Animator animation) {
				// TODO Auto-generated method stub
				
			}
		});
	}

	protected void leaveScreen() {
		// Once it's off of the screen, hopefully reclaim the memory
		imgHeart.setVisibility(View.GONE);
	}
}
