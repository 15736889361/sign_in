package com.liyun.signin;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import android.app.Activity;

public class ActivityCollector {
	private static List<Activity>  myActivityes=new ArrayList<Activity>();

	public ActivityCollector() {
		// TODO Auto-generated constructor stub
	}
	public static void addActivity(Activity activity){

		myActivityes.add(activity);
	}
	public static void removeActivity(Activity activity){
		myActivityes.remove(activity);
	}

	public static void finishAll(){
		for( Activity activity1:myActivityes){
			if(!activity1.isFinishing()){
				activity1.finish();
			}			
		}
		myActivityes.clear();
	}

}
