package de.pokernews.activities;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.google.gson.Gson;

import de.pokernews.helper.ArticleInfo;
import de.pokernews.helper.Article;
import de.pokernews.helper.GetUrlsTask;
import de.pokernews.helper.ArticleListAdapter;
import de.ps.crawler.R;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class CPActivity extends ListActivity implements OnItemClickListener {

	// Adapter for Listview
	private ArticleListAdapter adapter;

	// ProgressDialog
	private ProgressDialog pd;
	public ArrayList<ArticleInfo> articleInfos = new ArrayList<ArticleInfo>();
	public ArrayList<Article> articles = new ArrayList<Article>();
	private static Handler articleHandler;
	private SharedPreferences prefs;
	private String callingActivity;
	
	final static String BASE_URL = "http://www.cardplayer.com/poker-news";
	final static String PREF_FILE = "de.pokernews";
	final static String linkSelector = ".newsinfo a";
	final static String imgSelector = ".newsicon img";

	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);

		// Get Extras
		// Aufrufende Activity
		Bundle extras = getIntent().getExtras();
		callingActivity = extras.getString("activity");

		// Shared Prefs
		prefs = getSharedPreferences(PREF_FILE, 0);

		// Show ProgressDialog until Gallery is loaded
		pd = ProgressDialog.show(this, "Bitte warten", "Lade Artikel");

		// Erster Handler
		// Kriegt die Liste aller Artiekl-URLs zurück und ruft damit
		// GetArticlesTask auf
		Handler asyncHandler = new Handler() {
			public void handleMessage(Message msg) {
				super.handleMessage(msg);
				// What did that async task say?
				switch (msg.what) {
				case 1:
					// System.out.println(articleURLs.get(0));
					GetArticlesTask getArticlesFromPSTask = new GetArticlesTask(
							CPActivity.this, CPActivity.articleHandler);
					getArticlesFromPSTask.execute(articleInfos);
					break;
				}
			}
		};

		// Zweiter Handler
		// Kriegt Liste mit Artiekl-Objekten zurück und erstellt damit ListView
		articleHandler = new Handler() {
			public void handleMessage(Message msg) {
				super.handleMessage(msg);
				// What did that async task say?
				switch (msg.what) {
				case 1:

					// Create ListAdapter
					adapter = new ArticleListAdapter(CPActivity.this, articles);
					setListAdapter(adapter);

					// Fill ListView
					ListView listView = getListView();

					// OnItemClickListener for Click on an item in list
					listView.setOnItemClickListener(CPActivity.this);

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
		task.execute(BASE_URL);

	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {

		Intent i = new Intent(CPActivity.this, WebViewActivity.class);
		i.putExtra("content", articles.get(position).getContent());
		startActivity(i);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	// AsyncTask zum Abrufen der Artikel
	// Muss iinnerhalb dieser Klasse aufgerufen werden, damit man Zugriff auf
	// die Shared Prefs hat
	public class GetArticlesTask extends
			AsyncTask<ArrayList<ArticleInfo>, Integer, ArrayList<Article>> {

		private final Context context;
		// Used to send messages back to the mainUI
		private Handler mainUIHandler;

		public GetArticlesTask(Context context, Handler mainUIHandler) {
			this.context = context;
			this.mainUIHandler = mainUIHandler;
		}

		protected ArrayList<Article> doInBackground(
				ArrayList<ArticleInfo>... params) {
			
			ArrayList<ArticleInfo> articleInfos = params[0];
			ArrayList<Article> articles = new ArrayList<Article>();

			Editor edit = prefs.edit();
			Gson gson = new Gson();
			
			// Artikel URLs durchlaufen
			for (ArticleInfo info : articleInfos) {
				// Wenn Artikel noch nicht gecached ist
				if (!prefs.contains(info.getUrl())) {
					Document doc;
					try {
						// Artikel abrufen
						doc = Jsoup.parse(new URL(info.getUrl()).openStream(), "UTF-8", info.getUrl());
						// Titel
						String title = doc.select("#contributor tbody tr td h1").text();
						// DAtum
						doc.select(".byline a, .byline script").remove();
						String date = doc.select(".byline").html().substring(34, doc.select(".byline").html().length());
						date = date.substring(0, 12);
						doc.select(".byline").html(date);
						// Headline
						String headline = doc.select("#contributor tbody tr td h2").text();
						
						doc.select("tbody tr:last-child").remove();
						
						
						// Links aus Text entfernen
						doc.select("a").removeAttr("href");
						
						//doc.select("a").attr("href", "http://twitter.com/#!/cardplayermedia").remove();
						
						//Tags entfernen
						doc.select(".tags").remove();
						
						
						// HTML Content
						String content = doc.select(".col1").html();
						

						// Artikel Objekt bauen
						Article article = new Article(info.getUrl(),
								info.getImg());
						article.setTitle(title);
						article.setDate(date);
						article.setHeadline(headline);
						article.setContent(content);
						article.setImgURL(info.getImg());

						// Artikel Objekt zur Liste hinzufügen
						articles.add(article);

						// Artikel Objekt als Json in den Shared Prefs cachen
						String json = gson.toJson(article, Article.class);

						edit.putString(article.getUrl(), json);
						edit.apply();

					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} else {
					// Artikel Objekt aus JSON String in den Shared Prefs bauen
					Article article = gson.fromJson(
							prefs.getString(info.getUrl(), "n/a"),
							Article.class);

					// Artikel Objekt zur Liste hinzufügen
					articles.add(article);
				}
			}

			return articles;
		}

		@Override
		protected void onPostExecute(ArrayList<Article> articles) {
			CPActivity psActivity = (CPActivity) context;
			psActivity.articles = articles;
			super.onPostExecute(articles);

			// We should probably let the Main UI know we're done...
			// Let's send a message!
			Message msg = Message.obtain();
			msg.what = 1; // A public enumeration signifying success would be
							// better.
			mainUIHandler.sendMessage(msg);
		}

	}

}
