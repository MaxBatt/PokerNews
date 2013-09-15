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

public class GetUrlsTask extends AsyncTask<String, Integer, ArrayList<ArticleInfo>> {


	private ArrayList<ArticleInfo> articleInfos = new ArrayList<ArticleInfo>();	   
	private final Context context; 
	//Used to send messages back to the mainUI
    private Handler mainUIHandler;
    private String linkSelector, imgSelector, callingActivity;
	   

	public GetUrlsTask(Context context, Handler mainUIHandler, String linkSelector, String imgSelector, String callingActivity) {
		this.context = context;
		this.mainUIHandler = mainUIHandler;
		this.linkSelector = linkSelector;
		this.imgSelector = imgSelector;
		this.callingActivity = callingActivity;
		
	}

	@Override
	protected ArrayList<ArticleInfo> doInBackground(String... urls) {
		
		String url = urls[0];
		
		Document doc;
		try {
			doc = Jsoup.connect(url).get();
			Elements links = doc.select(linkSelector); 
			
			for (Element link : links){
				String articleURL = link.attr("abs:href");
				ArticleInfo articleInfo = new ArticleInfo(articleURL);
				articleInfos.add(articleInfo);
			}
			
			Elements images = doc.select(imgSelector); 
			int count = 0;
			for (Element img : images){
				String imgURL = img.attr("src");
				articleInfos.get(count).setImg(imgURL);
				count++;
			}

		
		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return articleInfos; 
	}

	@Override
	protected void onPostExecute(ArrayList<ArticleInfo> articleInfos) {
		
		if(callingActivity.equals("PS")){
				PSActivity psActivity = (PSActivity) context;
				psActivity.articleInfos = articleInfos;
				super.onPostExecute(articleInfos); 
		}
			
		       
	    	
	    
	  //We should probably let the Main UI know we're done...
        //Let's send a message!
        Message msg = Message.obtain();
        msg.what = 1; //A public enumeration signifying success would be better.
        mainUIHandler.sendMessage(msg);
	}
	
	
	

	
}
