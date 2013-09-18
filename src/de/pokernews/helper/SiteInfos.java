package de.pokernews.helper;

public class SiteInfos {
	private int resID;
	private String sitename;
	
	public SiteInfos(int resID, String sitename) {
		this.resID = resID;
		this.sitename = sitename;
	}

	public int getResID() {
		return resID;
	}

	public String getSitename() {
		return sitename;
	}
}
