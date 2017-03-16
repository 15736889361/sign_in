package com.liyun.signin;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import com.liyun.entity.TableAdapter;
import com.liyun.entity.Table_sign;

import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.ListView;

public class Login_info extends BaseActivity {

	//��ȡ���ݿ�helper
	MySQLiteOpenHelper mysqlitehelper=new MySQLiteOpenHelper(this, "Signin", null, 1);
	//
	private List<Table_sign> tableList=new ArrayList<Table_sign>();
	//
	String id_sign;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.login_info);
		//��ȡ��һ��Activity����������
		Intent intent2=getIntent();
		id_sign=intent2.getStringExtra("id_sign");
		//��ListView����Adapter
		InitTableList();
		TableAdapter myAdapter=new TableAdapter(Login_info.this, R.layout.list_item, tableList);
		ListView myList=(ListView) findViewById(R.id.listView1);
		myList.setAdapter(myAdapter);

	}
	/*
	 * ��ʼ������Դ
	 */
	private void InitTableList() {
		// TODO Auto-generated method stub
		//��ȡ���ݿ�
		SQLiteDatabase mydb=mysqlitehelper.getWritableDatabase();
		//��ǰ����
		Long cur_date=MainActivity.getCurrentDate();
		Long lastweek_date=cur_date-7*24 * 3600 * 1000;
		Cursor cursor=mydb.rawQuery("select * from Attendance where staff_id=? and signin_date>=? and signin_date<=?",
				new String[]{id_sign,String.valueOf(lastweek_date),String.valueOf(cur_date)});
		try {
			int counter=0;
			if(cursor.moveToFirst()){
				do{
					String id=id_sign;
					Long date=cursor.getLong(cursor.getColumnIndex("signin_date"));
					Log.d("MainActivity", String.valueOf(date));
					Long time_signin=cursor.getLong(cursor.getColumnIndex("sign_first"));
					Log.d("MainActivity", String.valueOf(time_signin));
					Long time_signout=cursor.getLong(cursor.getColumnIndex("sign_last"));
					Log.d("MainActivity", String.valueOf(time_signout));
					Table_sign table=new Table_sign(id,date,time_signin,time_signout);
					counter++;
					tableList.add(table);
				}while(cursor.moveToNext());
			}
			Log.d("MainActivity", String.valueOf(counter));
		} catch (Exception e) {
			// TODO: handle exception
		}finally{
			cursor.close();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub


		switch (item.getItemId()) {
		case R.id.quit_all:
			ActivityCollector.finishAll();
			break;
		case R.id.info :
			dialog("����");
			break;
		default:

		}
		return super.onOptionsItemSelected(item);
	}
	//��ʾ��Ϣ
	private void dialog(String string) {
		// TODO Auto-generated method stub
		AlertDialog.Builder dialog=new AlertDialog.Builder(this);
		dialog.setTitle(string);
		dialog.show();
	}
}
