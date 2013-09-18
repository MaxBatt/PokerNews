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




public class ArticleListAdapter extends ArrayAdapter<ArticleInfo> {
	private final Context context;
	private final ArrayList<ArticleInfo> articleInfos;
	private SmartImageView imgView;
	private TextView tvTitle;
	

	// Build ListAdapter from ArrayList
	public ArticleListAdapter(Context context,
			ArrayList<ArticleInfo> articleInfos) {
		super(context, R.layout.ps_article_row, articleInfos);
		this.context = context;
		this.articleInfos = articleInfos;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		// Fill ListRow
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View rowView = inflater.inflate(R.layout.ps_article_row, parent, false);
				
		
		// Put image in row via WebCachedImageView
		imgView = (SmartImageView) rowView.findViewById(R.id.imgView);
		imgView.setImageUrl(articleInfos.get(position).getImg());
		
		tvTitle = (TextView) rowView.findViewById(R.id.tvTitle);
		
		//System.out.println("img id: " + imageList.get(position).getId());
		tvTitle.setText(articleInfos.get(position).getTitle());
		
		//tvDate.setText(articles.get(position).getDate());
		
		
		// Return ListRow.
		return rowView;

	}

}
