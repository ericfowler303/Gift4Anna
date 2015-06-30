package com.hroo772.gift4anna;

import java.util.Random;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.util.Log;

public class TooEarlyDialogFragment extends DialogFragment{
	private static final String TAG = "Gift4AnnaActivity";
	private String[] messageString = new String[] {
			"No Peeking",
			"Not Yet",
			"I thought you liked surprises",
			"Don't spoil it",
			"I Can Haz Burfday?",
			"And I thought you could wait...",
			"It's worth the wait",
			"Poke...Poke...",
			"Shaking me won't help",
			"Cheaters never win",
			"It isn't July 20 is it...nawww",
	};
	
	@Override
	public Dialog onCreateDialog(Bundle onSaveInstance) {
		
		Log.d(TAG, "onCreateDialog called");
		
		return new AlertDialog.Builder(getActivity())
		.setTitle(randomResponse())
		.setPositiveButton(android.R.string.ok, null)
		.create();
		
	}

	private String randomResponse() {
		Log.d(TAG, "randomResponse called");
		// Draw a random response from the list of strings
		Random rand = new Random();
		return messageString[rand.nextInt(messageString.length)];
	}
}
