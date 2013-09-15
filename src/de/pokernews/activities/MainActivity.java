package de.pokernews.activities;


import de.ps.crawler.R;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {
	
	final static String PREF_FILE = "de.pokernews";


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

//		SharedPreferences prefs = getSharedPreferences(PREF_FILE, 0);
//		prefs.edit().clear().commit();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public void openPS(View v){
		Intent i = new Intent(this, PSActivity.class);
		i.putExtra("activity", "PS");
		startActivity(i);
	}
	
	
	/*
	public void openHP(View v){
		Intent i = new Intent(this, HPActivity.class);
		startActivity(i);
	}
	*/
}
