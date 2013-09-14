package de.pokernews.activities;

import java.util.ArrayList;

import de.pokernews.helper.PS_ArticleInfo;
import de.pokernews.helper.PS_Article;
import de.pokernews.helper.PS_GetArticlesTask;
import de.pokernews.helper.PS_GetUrlsTask;
import de.pokernews.helper.PS_ArticleListAdapter;
import de.ps.crawler.R;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class PSActivity extends ListActivity implements
		OnItemClickListener {


	// Adapter for Listview
	private PS_ArticleListAdapter adapter;
	// position in list of the clicked image
	private int actPosition;
	
	// ProgressDialog
	private ProgressDialog pd;

	
	private static final String BASE_URL = "http://de.pokerstrategy.com/home/";
	public ArrayList<PS_ArticleInfo> articleInfos = new ArrayList<PS_ArticleInfo>();
	public ArrayList<PS_Article> articles = new ArrayList<PS_Article>();
	private static Handler articleHandler;
	
	
	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		
		//Remove TitleBar
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		// Show ProgressDialog until Gallery is loaded
		pd = ProgressDialog
				.show(this,
						"Bitte warten","Lade Artikel");


		articleHandler = new Handler() {
			public void handleMessage(Message msg) {
				super.handleMessage(msg);
				// What did that async task say?
				switch (msg.what) {
				case 1:

					
					// Create ListAdapter
					adapter = new PS_ArticleListAdapter(
							PSActivity.this, articles);
					setListAdapter(adapter);

					// Fill ListView
					ListView listView = getListView();
					
					// OnItemClickListener for Click on an item in list
					listView.setOnItemClickListener(PSActivity.this);

					// Dismiss ProgressDialog, when Gallery is loaded
					pd.dismiss();
					
					break;
				}
			}
		};

		Handler asyncHandler = new Handler() {
			public void handleMessage(Message msg) {
				super.handleMessage(msg);
				// What did that async task say?
				switch (msg.what) {
				case 1:
					// System.out.println(articleURLs.get(0));
					PS_GetArticlesTask getArticlesFromPSTask = new PS_GetArticlesTask(
							PSActivity.this, PSActivity.articleHandler);
					getArticlesFromPSTask.execute(articleInfos);
					break;
				}
			}
		};

		PS_GetUrlsTask task = new PS_GetUrlsTask(this, asyncHandler);
		task.execute(BASE_URL);

	}

	
	
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {

		Intent i = new Intent(PSActivity.this, WebViewActivity.class);
		i.putExtra("content", articles.get(position).getContent());
		startActivity(i);	

	}
	
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	
	
}
