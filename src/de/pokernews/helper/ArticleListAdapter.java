package de.pokernews.helper;


import java.util.ArrayList;

import com.loopj.android.image.SmartImageView;


import de.ps.crawler.R;

import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;




public class ArticleListAdapter extends ArrayAdapter<Article> {
	private final Context context;
	private final ArrayList<Article> articles;
	private SmartImageView imgView;

	private TextView tvTitle;
	

	// Build ListAdapter from ArrayList
	public ArticleListAdapter(Context context,
			ArrayList<Article> articles) {
		super(context, R.layout.ps_article_row, articles);
		this.context = context;
		this.articles = articles;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		// Fill ListRow
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View rowView = inflater.inflate(R.layout.ps_article_row, parent, false);
				
		
		// Put image in row via WebCachedImageView
		imgView = (SmartImageView) rowView.findViewById(R.id.imgView);
		imgView.setImageUrl(articles.get(position).getImgURL());
		
		tvTitle = (TextView) rowView.findViewById(R.id.tvTitle);
		
		//System.out.println("img id: " + imageList.get(position).getId());
		tvTitle.setText(articles.get(position).getTitle());
		
		
		// Return ListRow.
		return rowView;

	}

}
