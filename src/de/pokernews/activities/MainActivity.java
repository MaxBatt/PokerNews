package de.pokernews.activities;

import de.ps.crawler.R;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class MainActivity extends Activity {

	final static String PREF_FILE = "de.pokernews";

	private Button btnPS, btnPO, btnHGP, btnPN, btnCP, btnHDB;
	private TableRow tr1, tr2, tr3;
	private TextView tv1, tv2, tv3, tv4, tv5, tv6;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_activity);

		// SharedPreferences prefs = getSharedPreferences(PREF_FILE, 0);
		// prefs.edit().clear().commit();

		btnPS = (Button) findViewById(R.id.btnPS);
		btnPO = (Button) findViewById(R.id.btnPO);
		btnHGP = (Button) findViewById(R.id.btnHGP);
		btnPN = (Button) findViewById(R.id.btnPN);
		btnCP = (Button) findViewById(R.id.btnCP);
		btnHDB = (Button) findViewById(R.id.btnHDB);

		tr1 = (TableRow) findViewById(R.id.tr1);
		tr2 = (TableRow) findViewById(R.id.tr2);
		tr3 = (TableRow) findViewById(R.id.tr3);
		
		tv1 = (TextView) findViewById(R.id.tv1);
		tv2 = (TextView) findViewById(R.id.tv2);
		tv3 = (TextView) findViewById(R.id.tv3);
		tv4 = (TextView) findViewById(R.id.tv4);
		tv5 = (TextView) findViewById(R.id.tv5);
		tv6 = (TextView) findViewById(R.id.tv6);
		
		

		int[] displayMeasures = getMeasures();
		int screenWidth = displayMeasures[0];
		int screenHeight = displayMeasures[1];

		int btnWidth = screenWidth / 7;
		int marginTop = screenHeight / 7;
		int marginRight = screenWidth / 6;

		TableRow.LayoutParams buttonMeasure = new TableRow.LayoutParams(
				btnWidth, btnWidth);
		
		TableRow.LayoutParams tvMesaure = new TableRow.LayoutParams(
				btnWidth, 30);
		

	
		btnPS.setLayoutParams(buttonMeasure);
		btnPO.setLayoutParams(buttonMeasure);
		btnHGP.setLayoutParams(buttonMeasure);
		btnPN.setLayoutParams(buttonMeasure);
		btnCP.setLayoutParams(buttonMeasure);
		btnHDB.setLayoutParams(buttonMeasure);
		
		tv1.setLayoutParams(tvMesaure);
		tv2.setLayoutParams(tvMesaure);
		tv3.setLayoutParams(tvMesaure);
		tv4.setLayoutParams(tvMesaure);
		tv5.setLayoutParams(tvMesaure);
		tv6.setLayoutParams(tvMesaure);
		
		
		LinearLayout.LayoutParams buttonParams1 = (LinearLayout.LayoutParams) btnPS.getLayoutParams();
		buttonParams1.rightMargin = marginRight;
		btnPS.setLayoutParams(buttonParams1);
		//btnHGP.setLayoutParams(buttonParams);
		//btnCP.setLayoutParams(buttonParams);
		
		LinearLayout.LayoutParams buttonParams2 = (LinearLayout.LayoutParams) btnPO.getLayoutParams();
		buttonParams2.leftMargin = marginRight;
		btnPO.setLayoutParams(buttonParams2);

		
		
		

		TableLayout.LayoutParams tableRowParams = new TableLayout.LayoutParams(
				TableLayout.LayoutParams.MATCH_PARENT,
				TableLayout.LayoutParams.WRAP_CONTENT);

		int leftMargin = 0;
		int topMargin = marginTop;
		int rightMargin = 0;
		int bottomMargin = 0;

		tableRowParams.setMargins(leftMargin, topMargin, rightMargin,
				bottomMargin);

		tr1.setLayoutParams(tableRowParams);
		tr2.setLayoutParams(tableRowParams);
		tr3.setLayoutParams(tableRowParams);
		

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
		Intent i = new Intent(this, PSActivity.class);
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
