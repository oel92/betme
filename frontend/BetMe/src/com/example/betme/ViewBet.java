package com.example.betme;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

@TargetApi(Build.VERSION_CODES.GINGERBREAD)
@SuppressLint("NewApi")
public class ViewBet extends ActionBarActivity{
	
	private String userID = new String();
	private boolean accepted = false;
   
	
	@TargetApi(Build.VERSION_CODES.GINGERBREAD)
	@SuppressLint("NewApi")
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
		StrictMode.setThreadPolicy(policy); 
        
        Intent myIntent = getIntent(); // gets the previously created intent
        userID = myIntent.getStringExtra("id");
        setContentView(R.layout.view_bet);
        
        if(accepted){
        	Button acceptButton = (Button) findViewById(R.id.submit);
        	Button declineButton = (Button) findViewById(R.id.cancel);
        	acceptButton.setVisibility(View.GONE);
        	declineButton.setVisibility(View.GONE);
        }
        
        ImageView challenger_photo = (ImageView) this.findViewById(R.id.challenger_pic);
        
        try {
        	  
        	  Bitmap bitmap = BitmapFactory.decodeStream((InputStream)new URL("https://lh3.googleusercontent.com/-htt-o8qHiyQ/AAAAAAAAAAI/AAAAAAAAAEo/0ZwB0uI91UM/photo.jpg?sz=200").getContent());
        	  challenger_photo.setImageBitmap(bitmap); 
        	} catch (MalformedURLException e) {
        	  e.printStackTrace();
        	} catch (IOException e) {
        	  e.printStackTrace();
        	}
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    public void accept(View view){
    	Intent myIntent = new Intent(this,MyFeed.class);
    	
    	myIntent.putExtra("id", userID);
    	startActivity(myIntent);
    }
    public void decline(View view){
    	Intent myIntent = new Intent(this,MyFeed.class);
    	
    	myIntent.putExtra("id", userID);
    	startActivity(myIntent);
    }
    public void myFeed(View view){
    	Intent myIntent = new Intent(this,MyFeed.class);
    
    	myIntent.putExtra("id", userID);
    	startActivity(myIntent);
    }
    public void friendsFeed(View view){
    	Intent myIntent = new Intent(this,FriendsFeed.class);
    	
    	myIntent.putExtra("id", userID);
    	startActivity(myIntent);
    }
    
}
