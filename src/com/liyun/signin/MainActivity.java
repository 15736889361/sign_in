package com.liyun.signin;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.text.format.Time;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends BaseActivity {

	private Button button_more;
	private Button sign;
	private TextView mfirsttime;
	private TextView mlasttime;
	private String data;
	//获取数据库helper
	MySQLiteOpenHelper mysqlitehelper=new MySQLiteOpenHelper(this, "Signin", null, 1);
	


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);	
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.main_layout);
		//取出上一个Activity 传来的数据
		Intent intent1=getIntent();
		data=intent1.getStringExtra("account");
		//Log.d("MainActivity", data);
		//绑定Button 控件
		button_more=(Button) findViewById(R.id.sign_more);
		sign=(Button) findViewById(R.id.sign);	
		//绑定TextView 控件
		mfirsttime=(TextView) findViewById(R.id.first_time);		
		mlasttime=(TextView) findViewById(R.id.last_time);
		//获取数据库
		final SQLiteDatabase mydb=mysqlitehelper.getWritableDatabase();
		//查询签到记录并显示 
		long currentdate = MainActivity.getCurrentDate();	
		this.updateShowTime(mydb,currentdate);			

		/*
		 * Button “签到”  设置响应事件
		 */
		sign.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Date now = new Date();
				//当前签到时间
				GregorianCalendar cal_sign = new GregorianCalendar();
				cal_sign.setTime(now);
				Long currenttime=cal_sign.getTimeInMillis();
				//点击事件的当前日期
				Long date=getCurrentDate();
				//查询数据库签到记录
				Cursor cursor=mydb.rawQuery("select * from Attendance where signin_date=? and staff_id=?", 
						new String[]{String.valueOf(date),data});
				try {
					if(cursor.moveToFirst() == false){					
						mydb.execSQL("insert into Attendance (staff_id,signin_date,sign_first,sign_last) values (?,?,?,?)",
								new String[]{data,String.valueOf(date),String.valueOf(currenttime),String.valueOf(currenttime)});
						updateShowTime(mydb,date);
					}
					else{
						mydb.execSQL("update Attendance set sign_last=? where signin_date=? and staff_id=? ",
								new String[]{String.valueOf(currenttime),String.valueOf(date),data});
						updateShowTime(mydb,date);
					}
				} catch (Exception e) {
					// TODO: handle exception
				}finally{
					cursor.close();
				}
			}
		});
		/*
		 * Button “访问更多记录”   设置响应事件
		 */
		button_more.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent_more=new Intent(MainActivity.this, Login_info.class);
				intent_more.putExtra("id_sign", data);
				startActivity(intent_more);
			}
		});
	}

	/*
	 * 更新显示签到时间的两个TextView
	 */
	private void updateShowTime(SQLiteDatabase mydb,Long date){
		Cursor cursor1=mydb.rawQuery("select * from Attendance where signin_date=? and staff_id=?",
				new String[]{String.valueOf(date),data});
		try {
			if(cursor1.moveToFirst()!= false){
				Long time_first_database=cursor1.getLong(cursor1.getColumnIndex("sign_first"));
				if(time_first_database!=0){
					Log.d("MainActivity", String.valueOf(time_first_database));
					mfirsttime.setText(ConvertTime(time_first_database));
				}
				Long time_last_database=cursor1.getLong(cursor1.getColumnIndex("sign_last"));
				if(time_last_database!=0){
					//Log.d("MainActivity", String.valueOf(time_last_database));
					mlasttime.setText(ConvertTime(time_last_database));
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
		}finally{
			cursor1.close();
		}		
	}
	/*
	 * 获取Long类型的当天日期
	 */
	public static Long getCurrentDate(){
		GregorianCalendar cal = new GregorianCalendar();
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		//毫秒可根据系统需要清除或不清除
		cal.set(Calendar.MILLISECOND, 0);
		//返回当天日期
		return cal.getTimeInMillis();		
	}
	/*
	 * 将Long类型的时间数据转换成00:00:00格式
	 */
	public static String ConvertTime(Long Time){
		GregorianCalendar tmpCal=new GregorianCalendar();
		tmpCal.setTimeInMillis(Time);
		/*int year = tmpCal.get(Calendar.YEAR);  
		int month = tmpCal.get(Calendar.MONTH);  
		int day = tmpCal.get(Calendar.DAY_OF_MONTH); */
		int hour = tmpCal.get(Calendar.HOUR_OF_DAY);  
		int minute = tmpCal.get(Calendar.MINUTE);
		int second=tmpCal.get(Calendar.SECOND);
		String mhour=(String) (hour>=10?Integer.toString(hour):("0"+Integer.toString(hour)));
		String mminute=(String) (minute>=10?Integer.toString(minute):("0"+Integer.toString(minute)));
		String msecond=(String) (second>=10?Integer.toString(second):("0"+Integer.toString(second)));
		return new String(mhour+":"+mminute+":"+msecond);
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
			dialog("二室");
			break;
		default:

		}
		return super.onOptionsItemSelected(item);
	}
	//显示信息
	private void dialog(String string) {
		// TODO Auto-generated method stub
		AlertDialog.Builder dialog=new AlertDialog.Builder(MainActivity.this);
		dialog.setTitle(string);
		dialog.show();
	}
}
