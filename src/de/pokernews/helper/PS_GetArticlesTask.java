package de.pokernews.helper;

import java.io.IOException;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import de.pokernews.activities.PSActivity;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;

public class PS_GetArticlesTask extends
		AsyncTask<ArrayList<ArticleInfo>, Integer, ArrayList<Article>> {

	private final Context context;
	// Used to send messages back to the mainUI
	private Handler mainUIHandler;


	public PS_GetArticlesTask(Context context, Handler mainUIHandler) {
		this.context = context;
		this.mainUIHandler = mainUIHandler;
	}

	protected ArrayList<Article> doInBackground(ArrayList<ArticleInfo>... params) {

		ArrayList<ArticleInfo> articleInfos = params[0];
		ArrayList<Article> articles = new ArrayList<Article>();
			
		for (ArticleInfo info : articleInfos) {
			Document doc;
			try {
				doc = Jsoup.connect(info.getUrl()).get();
				
				String title = doc.select(".articleBody h1").first().text();
				String date = doc.select(".articleBody p").first().text();
				String headline = doc.select(".articleBody h1").first().nextElementSibling().text();
				String content = doc.select(".articleBody").html();
				
				
				Article article = new Article(info.getUrl(), info.getImg());
				article.setTitle(title);
				article.setDate(date);
				article.setHeadline(headline);
				article.setContent(content);
				article.setImgURL(info.getImg());
				
				articles.add(article);
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return articles;
	}

	@Override
	protected void onPostExecute(ArrayList<Article> articles) {
		PSActivity psActivity = (PSActivity) context;
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
