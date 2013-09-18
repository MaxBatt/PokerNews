package de.pokernews.helper;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import android.content.Context;

import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;

import de.pokernews.activities.ArticleActivity;

public class GetArticleTask extends AsyncTask<String, Integer, String> {

	// Used to send messages back to the mainUI
	private Handler mainUIHandler;
	private ArticleActivity articleActivity;

	public GetArticleTask(Context context, Handler mainUIHandler) {
		this.mainUIHandler = mainUIHandler;
		articleActivity = (ArticleActivity) context;
	}

	protected String doInBackground(String... params) {

		String url = params[0];
		String content = "";

		Document doc;
		try {
			// Artikel abrufen
			doc = Jsoup.connect(url).get();
			
			if (articleActivity.getActivity().equals("PS")) {
				content = getPSArticle(doc);
			} else if (articleActivity.getActivity().equals("HGP")) {
				content = getHGPArticle(doc);
			} else if (articleActivity.getActivity().equals("PO")) {
				content = getPOArticle(doc);
			} else if (articleActivity.getActivity().equals("PN")) {
				content = getPNArticle(doc);
			} else if (articleActivity.getActivity().equals("HDB")) {
				content = getHDBArticle(doc);
			} else if (articleActivity.getActivity().equals("CP")) {
				content = getCPArticle(doc);
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		content.replace("Û", "&euro;");
		System.out.println(content);
		return content;
	}

	@Override
	protected void onPostExecute(String content) {
		articleActivity.setContent(content);
		super.onPostExecute(content);

		// We should probably let the Main UI know we're done...
		// Let's send a message!
		Message msg = Message.obtain();
		msg.what = 1; // A public enumeration signifying success would be
						// better.
		mainUIHandler.sendMessage(msg);
	}

	private String getPSArticle(Document doc) {

		doc.select(".articleBody div").first().remove();
		doc.select(".articleBody div").last().remove();
		doc.select(".articleBody div").last().remove();
		doc.select(".articleBody div").last().remove();
		doc.select(".articleBody div").last().remove();
		doc.select(".articleBody div").last().remove();
		doc.select(".articleBody div").last().remove();
		doc.select(".articleBody div").last().remove();

		// Links aus Text entfernen
		doc.select("a").removeAttr("href");

		// HTML Content
		String content = doc.select(".articleBody").html()
				+ doc.select("#comments").html();

		return content;
	}

	private String getHGPArticle(Document doc) {
		String content = "";
		return content;
	}

	private String getPOArticle(Document doc) {

		String baseURL = "http://www.pokerolymp.com/";

		doc.select(".entry-info dl dd a").remove();
		String date = doc
				.select(".entry-info dl dd")
				.first()
				.text()
				.substring(2,
						doc.select(".entry-info dl dd").first().text().length());

		doc.select("a").removeAttr("href");

		doc.select(".entry-info dl").remove();
		doc.select(".entry-info").html(date);
		doc.select("#content a").last().remove();
		doc.select("#article-actions").remove();
		doc.select("#new-comment-link").remove();

		// Base-URL vor IMG-URLS setzen,. weil relative URLs
		Elements images = doc.select("img");
		for (Element image : images) {
			image.attr("src", baseURL + image.attr("src"));
		}

		// HTML Content
		String content = doc.select("#content").html();

		return content;
	}

	private String getPNArticle(Document doc) {

		String baseURL = "http://www.pokernews.com/";

		// Links aus Text entfernen
		doc.select("a").removeAttr("href");

		doc.select(".editButton").remove();
		doc.select(".fblike").remove();
		doc.select(".social").remove();
		doc.select(".meta li span").remove();
		doc.select(".relatedBottom").remove();
		doc.select(".weekPopularArticles").remove();
		doc.select(".commentForm").remove();
		doc.select("#footer").remove();
		doc.select(".loginToRead").remove();
		doc.select(".relatedContent").remove();
		doc.select("iframe").remove();
		doc.select(".tags").remove();

		doc.select(".meta").attr("style", "list-style: none; padding: 0;");

		// Base-URL vor IMG-URLS setzen,. weil relative URLs
		Elements images = doc.select(".limited img");
		for (Element image : images) {
			String src = image.attr("src");
			// System.out.println("SRC: " + src.substring(0, 1));
			if (src.substring(0, 1).equals("/")) {
				image.attr("src", baseURL + src);
			}

		}

		// HTML Content
		String content = doc.select("section").html();

		return content;
	}

	private String getHDBArticle(Document doc) {

		doc.select(".MoreArticle").remove();
		doc.select(".HeadContText div").remove();
		doc.select(".TextBoxCont").remove();

		// Links aus Text entfernen
		doc.select("a").removeAttr("href");

		// HTML Content
		String content = doc.select("#LeftSideNew").html();

		return content;
	}

	private String getCPArticle(Document doc) {

		doc.select(".byline a, .byline script").remove();
		String date = doc.select(".byline").html()
				.substring(34, doc.select(".byline").html().length());
		date = date.substring(0, 12);
		doc.select(".byline").html(date);
		// Headline

		doc.select("tbody tr:last-child").remove();

		// Links aus Text entfernen
		doc.select("a").removeAttr("href");

		// doc.select("a").attr("href",
		// "http://twitter.com/#!/cardplayermedia").remove();

		// Tags entfernen
		doc.select(".tags").remove();

		// HTML Content
		String content = doc.select(".col1").html();

		return content;
	}

}