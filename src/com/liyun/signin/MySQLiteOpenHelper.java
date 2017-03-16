package com.liyun.signin;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

public class MySQLiteOpenHelper extends SQLiteOpenHelper {
	private Context mcontext;
	private final String CREATE_STAFF="create table Staff ("+
			"staff_id integer,"+
			"staff_name text,"+
			"title text,"+
			"password text,"+
			"primary key(staff_id))";
	private final String CREATE_ATTENDANCE="create table Attendance ("+
			"staff_id integer ,"+
			"signin_date integer,"+
			"sign_first integer,"+
			"sign_last integer,"+
			"primary key(staff_id,signin_date))";

	public MySQLiteOpenHelper(Context context, String name,
			CursorFactory factory, int version) {
		super(context, name, factory, version);
		// TODO Auto-generated constructor stub
		mcontext=context;
	}

	@Override
	public void onCreate(SQLiteDatabase arg0) {
		// TODO Auto-generated method stub
		arg0.execSQL(CREATE_STAFF); 
		arg0.execSQL(CREATE_ATTENDANCE);
		Toast.makeText(mcontext, "table book has been created ", Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onUpgrade(SQLiteDatabase arg0, int oldversion, int newversion) {
		// TODO Auto-generated method stub
		arg0.execSQL("drop table if exists Staff");
		arg0.execSQL("drop table if exists Attendance");
		onCreate(arg0);
	}

}
