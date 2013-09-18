package de.pokernews.activities;

import de.pokernews.helper.GetArticleTask;
import de.ps.crawler.R;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.webkit.WebView;

public class ArticleActivity extends Activity {

	private WebView webView;
	private static Handler articleHandler;
	private SharedPreferences prefs;
	private String content;
	private ProgressDialog pd;
	private String callingActivity;

	@SuppressLint("HandlerLeak")
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.webview);
		
		// Show ProgressDialog until Gallery is loaded
		pd = ProgressDialog.show(this, "Bitte warten", "Lade Artikel");

		Bundle extras = getIntent().getExtras();
		if (extras == null) {
			return;
		}
		
		callingActivity = extras.getString("activity");
		
		String url = extras.getString("url");
		
		articleHandler = new Handler() {
			public void handleMessage(Message msg) {
				super.handleMessage(msg);
				// What did that async task say?
				switch (msg.what) {
				case 1:
					if (content != null) {
						webView = (WebView) findViewById(R.id.webView1);
						//webView.getSettings().setJavaScriptEnabled(true);
						webView.getSettings().setBuiltInZoomControls(true);
						webView.getSettings().setDisplayZoomControls(false);
						
						String html = "<!DOCTYPE html PUBLIC '-//W3C//DTD XHTML 1.0 Strict//EN''http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd'><html xmlns='http://www.w3.org/1999/xhtml' xml:lang='de' lang='de'><head><meta name='language' content='german, de, deutsch' /><meta http-equiv='Content-Type' content='text/html; charset=utf-8' /><title></title></head><body>" + content + "</body></html>";
						
						webView.loadData(html, "text/html", null);
						
						pd.hide();
					}
					break;
				}
			}
		};
		
		GetArticleTask getArticleTask = new GetArticleTask(
				ArticleActivity.this, articleHandler);
		getArticleTask.execute(url);
	
		

	}
	
	
	
	public SharedPreferences getPrefs() {
		return prefs;
	}

	public void setPrefs(SharedPreferences prefs) {
		this.prefs = prefs;
	}



	public void setContent(String content) {
		this.content = content;
	}





	public String getActivity() {
		return callingActivity;
	}
}