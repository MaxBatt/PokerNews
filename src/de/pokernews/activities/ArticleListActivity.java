package de.pokernews.activities;

import java.util.ArrayList;

import de.pokernews.helper.ArticleInfo;
import de.pokernews.helper.GetUrlsTask;
import de.pokernews.helper.ArticleListAdapter;
import de.pokernews.helper.SiteInfos;
import de.ps.crawler.R;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.annotation.SuppressLint;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class ArticleListActivity extends ListActivity implements
		OnItemClickListener {

	// Adapter for Listview
	private ArticleListAdapter adapter;

	// ProgressDialog
	private ProgressDialog pd;
	public ArrayList<ArticleInfo> articleInfos = new ArrayList<ArticleInfo>();
	private String callingActivity;

	private String baseURL, linkSelector, imgSelector;

	@SuppressLint("HandlerLeak")
	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);

		// Get Extras
		// Aufrufende Activity
		Bundle extras = getIntent().getExtras();
		callingActivity = extras.getString("activity");

		setVariables(callingActivity);
		SiteInfos siteInfos = getSiteInfos();

		getActionBar().setIcon(siteInfos.getResID());
		getActionBar().setTitle(siteInfos.getSitename());

		// Show ProgressDialog until Gallery is loaded
		pd = ProgressDialog.show(this, "Bitte warten", "Lade Artikel");

		Handler asyncHandler = new Handler() {
			@SuppressLint("HandlerLeak")
			public void handleMessage(Message msg) {
				super.handleMessage(msg);
				// What did that async task say?
				switch (msg.what) {
				case 1:

					// Create ListAdapter
					adapter = new ArticleListAdapter(ArticleListActivity.this,
							articleInfos);
					setListAdapter(adapter);

					// Fill ListView
					ListView listView = getListView();

					// OnItemClickListener for Click on an item in list
					listView.setOnItemClickListener(ArticleListActivity.this);

					// Dismiss ProgressDialog, when Gallery is loaded
					pd.dismiss();

					break;
				}
			}
		};

		// Get Article URLS aufrufen
		// als Params Context, Handler, linkSelector, imgSelector,
		// callingActivity
		GetUrlsTask task = new GetUrlsTask(this, asyncHandler, linkSelector,
				imgSelector, callingActivity);
		task.execute(baseURL);

	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {

		Intent i = new Intent(ArticleListActivity.this, ArticleActivity.class);
		i.putExtra("url", articleInfos.get(position).getUrl());
		i.putExtra("activity", callingActivity);
		startActivity(i);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	private void setVariables(String callingActivity) {
		if (callingActivity.equals("PS")) {
			baseURL = "http://de.pokerstrategy.com/home/";
			linkSelector = ".top-news div h5 a";
			imgSelector = ".top-news a img";
		}
		if (callingActivity.equals("HGP")) {
			baseURL = "http://www.hochgepokert.com/";
			linkSelector = "#hgp_link_div";
			imgSelector = "#image-over a img";
		}
		if (callingActivity.equals("PO")) {
			baseURL = "http://www.pokerolymp.com/";
			linkSelector = ".article h2 a";
			imgSelector = ".entry-info img";
		}
		if (callingActivity.equals("PN")) {
			baseURL = "http://de.pokernews.com/neuigkeiten/popular-this-week/";
			linkSelector = "section ul li div a";
			imgSelector = "section ul li a img";
		}
		if (callingActivity.equals("HDB")) {
			baseURL = "http://www.highstakesdb.com/";
			linkSelector = ".NewsTitle a";
			imgSelector = ".NewsImage a img";
		}
		if (callingActivity.equals("CP")) {
			baseURL = "http://www.cardplayer.com/poker-news";
			linkSelector = ".newsinfo a";
			imgSelector = ".newsicon img";
		}
	}

	public String getBaseURL() {
		return baseURL;
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

	public SiteInfos getSiteInfos() {
		int resID = 0;
		String filename = "logo";
		String sitename = "PokerReader";
		Resources res = this.getResources();
		if (callingActivity.equals("PS")) {
			filename = "ps_button";
			sitename = "PokerStrategy";
		} else if (callingActivity.equals("HGP")) {
			filename = "hgp_button";
			sitename = "Hochgepokert";
		} else if (callingActivity.equals("PO")) {
			filename = "po_button";
			sitename = "PokerOlymp";
		} else if (callingActivity.equals("PN")) {
			filename = "pn_button";
			sitename = "PokerNews";
		} else if (callingActivity.equals("HDB")) {
			filename = "hdb_button";
			sitename = "HighstakesDB";
		} else if (callingActivity.equals("CP")) {
			filename = "cp_button";
			sitename = "CardPlayer";
		}

		resID = res.getIdentifier(filename, "drawable", this.getPackageName());
		SiteInfos siteInfos = new SiteInfos(resID, sitename);
		return siteInfos;
	}
}
