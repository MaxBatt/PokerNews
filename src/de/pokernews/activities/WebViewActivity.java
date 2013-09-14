package de.pokernews.activities;

import de.ps.crawler.R;
import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;

public class WebViewActivity extends Activity {

	private WebView webView;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.webview);

		Bundle extras = getIntent().getExtras();
		if (extras == null) {
			return;
		}
		String content = extras.getString("content");
		if (content != null) {
			webView = (WebView) findViewById(R.id.webView1);
			webView.getSettings().setJavaScriptEnabled(true);
			
			String html = "<html><body>" + content + "</body></html>";
			webView.loadData(html, "text/html", null);
		}

		

	}

}