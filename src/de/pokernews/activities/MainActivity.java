package de.pokernews.activities;

import de.pokernews.helper.ConnectionCheck;
import de.ps.crawler.R;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.TableRow;

public class MainActivity extends Activity {

	private ImageView btnPS, btnHGP, btnPN, btnPO, btnHDB, btnCP;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// Remove TitleBar
		//this.requestWindowFeature(Window.FEATURE_NO_TITLE);

		setContentView(R.layout.starting_activity);

		ConnectionCheck cc = new ConnectionCheck(getApplicationContext());
		Boolean isOnline = cc.checkConnection();

		if (!isOnline) {
			showDialog("Du hast leider keine Verbindung zum Internet. Bitte überprüfe die Verbindung und starte die App erneut.");
		}
		
		btnPS = (ImageView) findViewById(R.id.btnPS);
		btnHGP = (ImageView) findViewById(R.id.btnHGP);
		btnPN = (ImageView) findViewById(R.id.btnPN);
		btnPO = (ImageView) findViewById(R.id.btnPO);
		btnHDB = (ImageView) findViewById(R.id.btnHDB);
		btnCP = (ImageView) findViewById(R.id.btnCP);
		
		int actionBarHeight = getActionBarHeight();
		
		

		int[] displayMeasures = getMeasures();
		int screenWidth = displayMeasures[0];
		int screenHeight = displayMeasures[1] - actionBarHeight;
		

		int imgWidth = (screenWidth) / 2;
		int imgHeight = (screenHeight - 38) / 3;

		System.out.println("Width: " + imgWidth + "\n Height: " + imgHeight);

		TableRow.LayoutParams layoutParams = new TableRow.LayoutParams(
				imgWidth, imgHeight);

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

	public int[] getMeasures() {
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
		Intent i = new Intent(this, ArticleListActivity.class);
		i.putExtra("activity", "PO");
		startActivity(i);
	}

	public void openHGP(View v) {
		Intent i = new Intent(this, ArticleListActivity.class);
		i.putExtra("activity", "HGP");
		startActivity(i);
	}

	public void openPN(View v) {
		Intent i = new Intent(this, ArticleListActivity.class);
		i.putExtra("activity", "PN");
		startActivity(i);
	}

	public void openCP(View v) {
		Intent i = new Intent(this, ArticleListActivity.class);
		i.putExtra("activity", "CP");
		startActivity(i);
	}

	public void openHDB(View v) {
		Intent i = new Intent(this, ArticleListActivity.class);
		i.putExtra("activity", "HDB");
		startActivity(i);
	}
	
	public int getActionBarHeight(){
		// Calculate ActionBar height
		TypedValue tv = new TypedValue();
		int actionBarHeight = 0;
		if (getTheme().resolveAttribute(android.R.attr.actionBarSize, tv, true))
		{
		    actionBarHeight = TypedValue.complexToDimensionPixelSize(tv.data,getResources().getDisplayMetrics());
		}
		System.out.println("ACTION HEIGHT: "+ actionBarHeight);
		return actionBarHeight;
	}
	
	protected void showDialog(String msg) {
		new AlertDialog.Builder(this).setMessage(msg)

		.setPositiveButton("Beenden",
		// Click Listener
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						// cloase dialog
						dialog.cancel();
						MainActivity.this.finish();
					}
				}).create().show();
	}
	

}
