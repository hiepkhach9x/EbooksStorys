package com.CTCStudio.ebookstorys.Adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.CTCStudio.ebookstorys.R;
import com.CTCStudio.ebookstorys.Util.Ebook;

public class NewBooksAdapter extends ArrayAdapter<Ebook> {
	Context Ctx;
	ArrayList<Ebook> lstEbook;
	private LayoutInflater layoutInflater;

	public NewBooksAdapter(Context context, ArrayList<Ebook> objects) {
		super(context, R.layout.item_new_book, objects);
		// TODO Auto-generated constructor stub
		Ctx = context;
		lstEbook = objects;
		layoutInflater = LayoutInflater.from(context);
	}

	@Override
	public Ebook getItem(int position) {
		// TODO Auto-generated method stub
		return lstEbook.get(position);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		Ebook temp = lstEbook.get(position);
		if (convertView == null) {
			convertView = layoutInflater.inflate(R.layout.item_new_book, null);
			holder = new ViewHolder();
			holder.imgBook = (ImageView) convertView
					.findViewById(R.id.imgNewStory);
			holder.tvNameBook = (TextView) convertView
					.findViewById(R.id.tvnameBook);
			holder.tvAuthor = (TextView) convertView
					.findViewById(R.id.tvAuthorBook);
			holder.tvPublished = (TextView) convertView
					.findViewById(R.id.tvpublishedBook);
			holder.tvDescription = (TextView) convertView
					.findViewById(R.id.tvDescriptionBook);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.tvNameBook.setText(temp.getTitle());
		holder.tvAuthor.setText(temp.getAuthor());
		holder.tvPublished.setText(temp.getPublished());
		holder.tvDescription.setText(temp.getDescription());
		return convertView;
	}

	static class ViewHolder {
		ImageView imgBook;
		TextView tvNameBook, tvAuthor, tvPublished, tvDescription;
	}
}
