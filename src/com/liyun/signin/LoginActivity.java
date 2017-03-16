package com.liyun.signin;


import java.util.List;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends BaseActivity implements OnClickListener  {

	//获取数据库helper
	MySQLiteOpenHelper mysqlitehelper;
	SQLiteDatabase mydb;
	private EditText account_et;
	private EditText password_et;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.login_layout);	
		//绑定Button
		Button log_button=(Button) findViewById(R.id.login);
		Button reg_buton=(Button)findViewById(R.id.register);
		Button quit_button=(Button) findViewById(R.id.quit_login);
		//绑定 EditTexta
		account_et=(EditText) findViewById(R.id.account);
		password_et=(EditText) findViewById(R.id.password);
		//
		log_button.setOnClickListener(this);
		reg_buton.setOnClickListener(this);
		quit_button.setOnClickListener(this);
		mysqlitehelper=new MySQLiteOpenHelper(this, "Signin", null, 1);
		mydb=mysqlitehelper.getWritableDatabase();
	}

	@Override
	public void onClick(View view) {
		final String account=account_et.getText().toString();
		final String password=password_et.getText().toString();

		//
		switch (view.getId()) {
		case R.id.register:				
			if(account.equals("")){
				Toast.makeText(LoginActivity.this, "请输入工号 ", Toast.LENGTH_LONG).show();
			}
			else if(password.equals("")){
				Toast.makeText(LoginActivity.this,"请输入密码",Toast.LENGTH_LONG).show();
			}else {
				// TODO Auto-generated method stub
				Cursor cursor=mydb.rawQuery("select * from Staff where staff_id=?", new String[]{account});
				try {
					if(cursor.moveToFirst()==false) {
						mydb.execSQL("insert into Staff (staff_id,staff_name,title,password) values(?,?,?,?)", new 
								String[]{account,null,null,password});
					}
					else{
						Toast.makeText(LoginActivity.this, "用户已存在", Toast.LENGTH_LONG).show();
					}
				} catch (Exception e) {
					// TODO: handle exception
				}finally{
					cursor.close();
				}				
			}		

			break;
		case R.id.login:
			if(account.equals("")){
				Toast.makeText(LoginActivity.this, "请输入工号 ", Toast.LENGTH_LONG).show();
			}
			else if(password.equals("")){
				Toast.makeText(LoginActivity.this,"请输入密码",Toast.LENGTH_LONG).show();
			}else {
				// TODO Auto-generated method stub
				Cursor cursor1=mydb.rawQuery("select * from Staff where staff_id=?", new String[]{account});
				try {
					if(cursor1.moveToFirst()==false) {
						Toast.makeText(LoginActivity.this, "用户不存在", Toast.LENGTH_LONG).show();
					}
					else{
						String password_db=cursor1.getString(cursor1.getColumnIndex("password"));
						if(password.equals(password_db)){ 
							Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_LONG).show();
							Intent intent1=new Intent(LoginActivity.this, MainActivity.class);
							//向下一个Activity传递数据
							intent1.putExtra("account", account);
							startActivity(intent1);
						}else{ Toast.makeText(LoginActivity.this, "用户名或者密码错误", Toast.LENGTH_LONG).show();}
					}
				} catch (Exception e) {
					// TODO: handle exception
				}finally{
					cursor1.close();
				}

			}			
			break;
		case R.id.quit_login:
			Toast.makeText(LoginActivity.this, "退出了", Toast.LENGTH_LONG).show();
			finish();			
			break;
		default:
			break;
		}
	}
}

