package com.hroo772.gift4anna;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Random;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.app.FragmentManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.view.animation.AnticipateInterpolator;
import android.widget.ImageButton;
import android.widget.ImageView;

public class MainActivity extends Activity {
	private static final String TAG = "Gift4AnnaActivity";
	private static final String MESSAGE_BOX = "message";
	private ImageButton giftBoxButton;
	private ImageView giftBoxTop;
	public Random rand = new Random();
	public ArrayList<Heart> heartList;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate(Bundle) called");
        
        setContentView(R.layout.activity_main);
        
        // Get Both the GiftBox and it's top and
        // assign them to the same listener
        giftBoxButton = (ImageButton) findViewById(R.id.giftBox);
        giftBoxButton.setOnClickListener(new GiftBoxListener());
        giftBoxTop = (ImageView) findViewById(R.id.giftBoxTop);
        giftBoxTop.setOnClickListener(new GiftBoxListener());
         
    }
    
    // Listener for when the GiftBox or it's Top are touched
    public class GiftBoxListener implements View.OnClickListener {
    	
    	@Override
		public void onClick(View v) {
    		Log.d(TAG, "GiftBoxListener onClick called");
    		
    		// isBirthday will handle showing the message box
    		// if it's not her birthday yet.
    		if(isBirthday(System.currentTimeMillis())) {
    		 //if(true) {
    			 giftAnimationSequence();
    		}
 
		}
    }
    
    private void giftAnimationSequence() {
    	// On or After the Birthday
		Log.d(TAG, "Beginning of GiftBoxTop Animation setup");
		// Animate the Box top flying off of the side of the screen.
		ObjectAnimator animX = ObjectAnimator.ofFloat(giftBoxTop, "x", findViewById(R.id.container).getWidth() + 40);
		// By setting this Interpolator, the box will slide back before leaving the screen
		animX.setInterpolator(new AnticipateInterpolator(0.4f));
		animX.setDuration(4600);
		
		// Get some height too
		ObjectAnimator animY = ObjectAnimator.ofFloat(giftBoxTop, "y", findViewById(R.id.giftBoxTop).getY() - 60);
		animY.setDuration(4000);
		
		// Add some rotation so it looks like it's opening the present
		ObjectAnimator animRotate = ObjectAnimator.ofFloat(giftBoxTop, "rotation", 88);
		animRotate.setStartDelay(2000);
		animRotate.setDuration(2000);
		
		// Group animations together so they'll play at the same time
		AnimatorSet animGroup = new AnimatorSet();
		animGroup.playTogether(animX, animY, animRotate);
		
		animGroup.addListener(new AnimatorListenerAdapter() {
			
			// When the animation starts
			@Override
			public void onAnimationStart(Animator animator) {
				// When the box opening animation starts
				// kickoff the heart animation and the
				// slow appearance of the Birthday Text
				textAnimationSequence();
				
				// Use a timer to delay the release of the hearts
				new CountDownTimer(2000,1000) {
					@Override
					public void onFinish() {
						// start the sequence
						heartReleaseSequence();
					}
					@Override
					public void onTick(long millisUntilFinished) {}
				}.start();
				
			}
			
			// After the animation completes
			@Override
			public void onAnimationEnd(Animator animator) {
				// The box top should be off of the screen, so make it GONE
    			giftBoxTop.setVisibility(View.GONE);
    			Log.d(TAG, "Animation is complete and box is GONE");
    			
			}
			
		});
		
		// Start the animation
		animGroup.start();
		Log.d(TAG, "GiftBoxTop Animation start");

    }
    
    private void textAnimationSequence() {
    	// The text will start as alpha=0
    	// increase the alpha so it's visible again
    	ObjectAnimator animText = ObjectAnimator.ofFloat(findViewById(R.id.birthdayText), "alpha", 1);
    	animText.setDuration(6000);
    	animText.setStartDelay(3000);
    	// start fading in
    	animText.start();
    	Log.d(TAG, "start the fade in of the birthdayText");
    }
    
    private void heartReleaseSequence() {
    	Log.d(TAG, "heartReleaseSequence() called");
    	// While the app is open, continue to generate hearts
    	new CountDownTimer(32394, 243) {

			@Override
			public void onFinish() {}

			@Override
			public void onTick(long arg0) {
				// Generate a heart each time
				makeAHeart();
			}
    		
    	}.start();
    	// Stagger a second timer to vary the heart release pattern
    	new CountDownTimer(30000, 888) {

			@Override
			public void onFinish() {}

			@Override
			public void onTick(long arg0) {
				// Generate a heart each time
				makeAHeart();
			}
    		
    	}.start();
    	new CountDownTimer(30000, 420) {

			@Override
			public void onFinish() {}

			@Override
			public void onTick(long arg0) {
				// Generate a heart each time
				makeAHeart();
			}
    		
    	}.start();

    }
    
    private void makeAHeart() {
    	new Heart(this, rand.nextInt(3));
    }
    
    private boolean isBirthday(long currentTime) {
    	// Set for Anna's Birthday
    	Calendar cal = new GregorianCalendar(2014, 07, 20);
    	long bDay = cal.getTimeInMillis();
    	Log.d(TAG, "calendar.set(y,m,d), bDay is " + bDay + "");
    	
    	Log.d(TAG, "isBirthday called, currentTime is " + currentTime + "");
    	
    	if(currentTime >= bDay){
    		// Correct date, so start the animation in onClick()
    		return true;
    	} else {
    		// Not her Birthday yet, so pop-up a message box
    		FragmentManager fm = this.getFragmentManager();
    		TooEarlyDialogFragment message = new TooEarlyDialogFragment();
    		message.show(fm, MESSAGE_BOX);
    		Log.d(TAG, "Show TooEarlyDialogFragment()");
    		return false;
    	}
    }
    
    @Override
    public void onResume() {
    	super.onResume();
    	Log.d(TAG, "onResume called");

    }
    @Override
    public void onPause() {
    	super.onPause();
    	Log.d(TAG, "onPause called");

    }

}
