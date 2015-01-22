/*
 * Road Quiz
 * 
 * Developed by Victor Cheong
 * http://www.victorcheong.org
 * vche036@gmail.com
 * 
 */
package com.android.roadquiz;

import com.android.roadquiz.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MobileArrayAdapter extends ArrayAdapter<String> {
	private final Context context;
	private final String[] values;
	private final int[] images;
 
	public MobileArrayAdapter(Context context, String[] values, int[] images) {
		super(context, R.layout.main, values);
		this.context = context;
		this.values = values;
		this.images = images;
	}
 
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context
			.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
 
		View rowView = inflater.inflate(R.layout.main, parent, false);
		TextView textView = (TextView) rowView.findViewById(R.id.label);
		TextView textView2 = (TextView) rowView.findViewById(R.id.label2);
		ImageView imageView = (ImageView) rowView.findViewById(R.id.logo);
		textView.setText("Question " + (position + 1));
		textView2.setText(values[position]);
		imageView.setImageResource(images[position]);
 
		return rowView;
	}
}
