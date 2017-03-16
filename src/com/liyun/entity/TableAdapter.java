package com.liyun.entity;

import java.util.List;

import com.liyun.signin.R;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class TableAdapter extends ArrayAdapter<Table_sign> {
	private int resourceId;
	public TableAdapter(Context context, int TextViewresourceId, List<Table_sign> objects) {
		super(context, TextViewresourceId, objects);
		// TODO Auto-generated constructor stub
		resourceId=TextViewresourceId;
	}
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		Log.d("MainActivity", String.valueOf(position));
		Table_sign table_sign=getItem(position);//获取当前Table_sign实例
		Log.d("MainActivity", String.valueOf(table_sign.getDate()));
		Log.d("MainActivity", String.valueOf(table_sign.getTime_signin()));
		Log.d("MainActivity", String.valueOf(table_sign.getTime_signout()));
		View view=LayoutInflater.from(getContext()).
				inflate(resourceId, parent, false);
		TextView id=(TextView) view.findViewById(R.id.id_sign1);
		TextView date=(TextView) view.findViewById(R.id.date_sign1);
		TextView firsttime=(TextView) view.findViewById(R.id.time_first1);
		TextView lasttime=(TextView) view.findViewById(R.id.time_second1);
		id.setText(table_sign.getId());
		date.setText(table_sign.getDate());
		firsttime.setText(table_sign.getTime_signin());
		lasttime.setText(table_sign.getTime_signout());
		return view;
	}

}
