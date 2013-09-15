package de.pokernews.helper;

public class ArticleInfo {
	private String url, img;

	public ArticleInfo(String url){
		this.url = url;
	}
	
	public String getUrl() {
		return url;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}
	
	
}
