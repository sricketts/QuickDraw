
package com.example.quickdraw;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.MotionEvent;
import android.widget.TextView;

import java.lang.String;

public class QuickDraw extends Activity {

	DrawView dv;
	Integer x=0;
	
   @Override
   public void onCreate(Bundle savedInstanceState) {
       super.onCreate(savedInstanceState);
       dv = new DrawView(this);
       setContentView(dv);
   }
   
   public class DrawView extends TextView {
	   public DrawView(Context c) {
		   super(c);
	   }
	   
	   @Override public boolean onTouchEvent(MotionEvent event) {
		   String s = "nice draw " + x.toString();
		   this.setText(s);
		   x++;
		   return true;
	   }
   }
}