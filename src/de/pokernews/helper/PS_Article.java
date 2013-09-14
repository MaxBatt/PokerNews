package de.pokernews.helper;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import android.os.AsyncTask;

public class PS_Article {
	
	private String title, url, date, headline, content, imgURL;
	
	public PS_Article(String url, String imgURL){
		this.url = url;		
		this.imgURL = imgURL;
	}
	

	
	

	public String getTitle() {
		return title;
	}



	public void setTitle(String title) {
		this.title = title;
	}



	public String getUrl() {
		return url;
	}



	public void setUrl(String url) {
		this.url = url;
	}



	public String getDate() {
		return date;
	}



	public void setDate(String date) {
		this.date = date;
	}



	public String getHeadline() {
		return headline;
	}



	public void setHeadline(String headline) {
		this.headline = headline;
	}



	public String getContent() {
		return content;
	}



	public void setContent(String content) {
		this.content = content;
	}





	public String getImgURL() {
		return imgURL;
	}





	public void setImgURL(String imgURL) {
		this.imgURL = imgURL;
	}

}
