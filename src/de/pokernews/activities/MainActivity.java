package de.pokernews.activities;

import de.ps.crawler.R;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class MainActivity extends Activity {

	final static String PREF_FILE = "de.pokernews";
	
	private ImageView btnPS, btnHGP, btnPN, btnPO, btnHDB, btnCP;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		//Remove TitleBar
		this.requestWindowFeature(Window.FEATURE_NO_TITLE); 
		
		setContentView(R.layout.starting_activity);
		
		

		// SharedPreferences prefs = getSharedPreferences(PREF_FILE, 0);
		// prefs.edit().clear().commit();

		ImageView btnPS = (ImageView) findViewById(R.id.btnPS);
		ImageView btnHGP = (ImageView) findViewById(R.id.btnHGP);
		ImageView btnPN = (ImageView) findViewById(R.id.btnPN);
		ImageView btnPO = (ImageView) findViewById(R.id.btnPO);
		ImageView btnHDB = (ImageView) findViewById(R.id.btnHDB);
		ImageView btnCP = (ImageView) findViewById(R.id.btnCP);
		

		int[] displayMeasures = getMeasures();
		int screenWidth = displayMeasures[0];
		int screenHeight = displayMeasures[1];
		
		int imgWidth  = (screenWidth) /2;
		int imgHeight = (screenHeight - 40) /3;
		
		System.out.println("Width: " + imgWidth + "\n Height: " + imgHeight);
		
		TableRow.LayoutParams layoutParams = new TableRow.LayoutParams(imgWidth, imgHeight);
		
		btnPS.setLayoutParams(layoutParams);
		btnHGP.setLayoutParams(layoutParams);
		btnPN.setLayoutParams(layoutParams);
		btnPO.setLayoutParams(layoutParams);
		btnHDB.setLayoutParams(layoutParams);
		btnCP.setLayoutParams(layoutParams);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	private int[] getMeasures() {
		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		String width = "" + dm.widthPixels;
		String height = "" + dm.heightPixels;
		int[] measures = new int[2];
		measures[0] = Integer.parseInt(width);
		measures[1] = Integer.parseInt(height);

		return measures;
	}

	public void openPS(View v) {
		Intent i = new Intent(this, ArticleListActivity.class);
		i.putExtra("activity", "PS");
		startActivity(i);
	}

	public void openPO(View v) {
		Intent i = new Intent(this, POActivity.class);
		i.putExtra("activity", "PO");
		startActivity(i);
	}

	public void openHGP(View v) {
		Intent i = new Intent(this, HGPActivity.class);
		i.putExtra("activity", "HGP");
		startActivity(i);
	}

	public void openPN(View v) {
		Intent i = new Intent(this, PNActivity.class);
		i.putExtra("activity", "PN");
		startActivity(i);
	}

	public void openCP(View v) {
		Intent i = new Intent(this, CPActivity.class);
		i.putExtra("activity", "CP");
		startActivity(i);
	}

	public void openHDB(View v) {
		Intent i = new Intent(this, HDBActivity.class);
		i.putExtra("activity", "HDB");
		startActivity(i);
	}

}
