package com.liyun.entity;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class Table_sign {
	private String id;
	private Long date;
	private Long time_signin;
	private Long time_signout;

	public Table_sign(String id,Long date,Long time_signin,Long time_signout){
		this.id=id;
		this.date=date;
		this.time_signin=time_signin;
		this.time_signout=time_signout;
	}

	public String getId() {
		return id;
	}

	public String getDate() {
		GregorianCalendar mycalender=new GregorianCalendar();		
		mycalender.setTimeInMillis(date);
		int year = mycalender.get(Calendar.YEAR);  
		int month = mycalender.get(Calendar.MONTH);  
		int day = mycalender.get(Calendar.DAY_OF_MONTH); 		
		String myear=(String) (year>=10?Integer.toString(year):("0"+Integer.toString(year)));
		String mmonth=(String) (month>=10?Integer.toString(month):("0"+Integer.toString(month)));
		String mday=(String) (day>=10?Integer.toString(day):("0"+Integer.toString(day)));
		String tmp=new String(myear+"-"+mmonth+"-"+mday);
		return tmp;
	}

	public String getTime_signin() {
		GregorianCalendar mycalender=new GregorianCalendar();
		mycalender.setTimeInMillis(time_signin);
		int hour = mycalender.get(Calendar.HOUR_OF_DAY);  
		int minute = mycalender.get(Calendar.MINUTE);
		int second=mycalender.get(Calendar.SECOND);		
		String mhour=(String) (hour>=10?Integer.toString(hour):("0"+Integer.toString(hour)));
		String mminute=(String) (minute>=10?Integer.toString(minute):("0"+Integer.toString(minute)));
		String msecond=(String) (second>=10?Integer.toString(second):("0"+Integer.toString(second)));
		String tmp=new String(mhour+":"+mminute+":"+msecond);
		return tmp;
	}

	public String getTime_signout() {
		GregorianCalendar mycalender=new GregorianCalendar();
		mycalender.setTimeInMillis(time_signout);
		int hour = mycalender.get(Calendar.HOUR_OF_DAY);  
		int minute = mycalender.get(Calendar.MINUTE);
		int second=mycalender.get(Calendar.SECOND);		
		String mhour=(String) (hour>=10?Integer.toString(hour):("0"+Integer.toString(hour)));
		String mminute=(String) (minute>=10?Integer.toString(minute):("0"+Integer.toString(minute)));
		String msecond=(String) (second>=10?Integer.toString(second):("0"+Integer.toString(second)));
		String tmp=new String(mhour+":"+mminute+":"+msecond);
		return tmp;
	}



}
