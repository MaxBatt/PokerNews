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

			String html = "<!DOCTYPE html PUBLIC '-//W3C//DTD XHTML 1.0 Strict//EN''http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd'><html xmlns='http://www.w3.org/1999/xhtml' xml:lang='de' lang='de'><head><meta name='language' content='german, de, deutsch' /><meta http-equiv='Content-Type' content='text/html; charset=utf-8' /><title></title></head><body>" + content + "</body></html>";
			webView.loadData(html, "text/html", null);
		}

	}
}