package com.altitude.careerintelligence.classes;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.altitude.careerintelligence.LoginActivity;

import java.util.HashMap;

public class SessionManager {
	public static final String KEY_IMAGE_URI = "image_uri";
	// Shared Preferences
	SharedPreferences pref;
	
	// Editor for Shared preferences
	Editor editor;
	
	// Context
	Context _context;
	
	// Shared pref mode
	int PRIVATE_MODE = 0;
	
	// Sharedpref file name
	private static final String PREF_NAME = "CareerIntelligence";
	
	// All Shared Preferences Keys
	private static final String IS_LOGIN = "IsLoggedIn";
	
	// User name (make variable public to access from outside)
	public static final String KEY_NAME = "last_name";

	// Email address (make variable public to access from outside)
	public static final String KEY_EMAIL = "email";

	// Access token to access the server (make variable public to access from outside)
	public static final String KEY_JWT = "token";

	// Constructor
	public SessionManager(Context context){
		this._context = context;
		pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
		editor = pref.edit();
	}

	/**
	 * Create login session
	 * */
	public void createLoginSession(String name, String jwt, String email, String image_uri){
		// Storing login value as TRUE
		editor.putBoolean(IS_LOGIN, true);
		
		// Storing name in pref
		editor.putString(KEY_NAME, name);
		
		// Storing the token in pref
		editor.putString(KEY_JWT, jwt);

		// Storing email in pref
		editor.putString(KEY_EMAIL, email);


		// Storing image_uri in pref
		editor.putString(KEY_IMAGE_URI, image_uri);
		
		// commit changes
		editor.commit();
	}	
	
	/**
	 * Check login method wil check user login status
	 * If false it will redirect user to login page
	 * Else won't do anything
	 * */
	public void checkLogin(){
		// Check login status
		if(!this.isLoggedIn()){
			// user is not logged in redirect him to Login Activity
			Intent i = new Intent(_context, LoginActivity.class);
			// Closing all the Activities
			i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

			// Add new Flag to start new Activity
			i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);


			// Staring Login Activity
			_context.startActivity(i);
			((Activity) _context).finish();
		}
		
	}
	
	
	
	/**
	 * Get stored session data
	 * */
	public HashMap<String, String> getUserDetails(){
		HashMap<String, String> user = new HashMap<String, String>();
		// user name
		user.put(KEY_NAME, pref.getString(KEY_NAME, null));
		
		// user token
		user.put(KEY_JWT, pref.getString(KEY_JWT, null));

		user.put(KEY_EMAIL, pref.getString(KEY_EMAIL, null));
		
		//user image url
		user.put(KEY_IMAGE_URI, pref.getString(KEY_IMAGE_URI, null));
		
		// return user
		return user;
	}
	
	/**
	 * Clear session details
	 * */
	public void logoutUser(){
		// Clearing all data from Shared Preferences
		editor.clear();
		editor.commit();
		
		// After logout redirect user to Loing Activity
		Intent i = new Intent(_context, LoginActivity.class);
		// Closing all the Activities
		i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		
		// Add new Flag to start new Activity
		i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
		
		// Staring Login Activity
		_context.startActivity(i);
//		((Activity) _context).finish();

	}
	
	/**
	 * Quick check for login
	 * **/
	// Get Login State
	public boolean isLoggedIn(){
		return pref.getBoolean(IS_LOGIN, false);
	}
}
