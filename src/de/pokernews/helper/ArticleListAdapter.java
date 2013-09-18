package de.pokernews.helper;


import java.util.ArrayList;

import com.loopj.android.image.SmartImageView;


import de.pokernews.activities.ArticleListActivity;
import de.pokernews.activities.MainActivity;
import de.ps.crawler.R;

import android.app.ActionBar;
import android.app.ActionBar.LayoutParams;
import android.content.Context;
import android.graphics.Color;

import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TableRow;
import android.widget.TextView;




public class ArticleListAdapter extends ArrayAdapter<ArticleInfo> {
	private final Context context;
	private ArticleListActivity listActivity;
	private final ArrayList<ArticleInfo> articleInfos;
	private SmartImageView imgView;
	private TextView tvTitle;
	

	// Build ListAdapter from ArrayList
	public ArticleListAdapter(Context context,
			ArrayList<ArticleInfo> articleInfos) {
		super(context, R.layout.ps_article_row, articleInfos);
		this.context = context;
		this.articleInfos = articleInfos;
		listActivity = (ArticleListActivity) context;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		// Fill ListRow
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View rowView = inflater.inflate(R.layout.article_row, parent, false);
		
		// Put image in row via WebCachedImageView
		imgView = (SmartImageView) rowView.findViewById(R.id.imgView);
		imgView.setBackgroundColor(Color.parseColor("#000000"));
		
		
		
		int[] measures = listActivity.getMeasures();
		int screenWidth = measures[0];
		int screenHeight = measures[1];
		
		int imgWidth  = (screenWidth + 140 )/ 3;
		int imgHeight = (screenHeight + 100) /6;
		
		
		System.out.println("Width: " + imgWidth + "\n Height: " + imgHeight);
		
		TableRow.LayoutParams layoutParams = new TableRow.LayoutParams(imgWidth, imgHeight);
		
		imgView.setLayoutParams(layoutParams);
		
		imgView.setImageUrl(articleInfos.get(position).getImg());
		
		tvTitle = (TextView) rowView.findViewById(R.id.tvTitle);
		
		//System.out.println("img id: " + imageList.get(position).getId());
		tvTitle.setText(articleInfos.get(position).getTitle());
		
		//tvDate.setText(articles.get(position).getDate());
		
		
		// Return ListRow.
		return rowView;

	}
	
	


}
