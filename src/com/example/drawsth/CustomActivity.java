package com.example.drawsth;

import android.app.Activity;
import android.os.Bundle;
import android.widget.LinearLayout;

public class CustomActivity extends Activity {  
    @Override  
    public void onCreate(Bundle savedInstanceState) {  
        super.onCreate(savedInstanceState);  
        setContentView(R.layout.activity_main);  
        init();  
    }  
  
    private void init() {  
        LinearLayout layout=(LinearLayout) findViewById(R.id.menu_settings);  
        final DrawView view=new DrawView(this);  
        view.setMinimumHeight(500);  
        view.setMinimumWidth(300);  
        //通知view组件重绘    
        view.invalidate();  
        layout.addView(view);  
          
    }  
}  