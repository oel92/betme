package com.example.betme;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.betme.R;
import com.example.betme.R.id;
import com.example.betme.R.layout;
import com.example.betme.R.menu;
import com.google.android.gms.internal.pd;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;



import android.support.v7.app.ActionBarActivity;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


@TargetApi(Build.VERSION_CODES.GINGERBREAD)
@SuppressLint("NewApi")
public class MyFeed extends ActionBarActivity {
	private String userID = new String();
	Context context;
	private ArrayList<String> invitedBets = new ArrayList<String>();
	private ArrayList<String> activeBets = new ArrayList<String>();
	private ArrayList<String> finishedBets = new ArrayList<String>();
	
	
    @TargetApi(Build.VERSION_CODES.GINGERBREAD)
	@SuppressLint("NewApi")
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

		StrictMode.setThreadPolicy(policy); 
        
        context = this;
        setContentView(R.layout.my_feed);
        Intent myIntent = getIntent(); // gets the previously created intent
        userID = myIntent.getStringExtra("id");
        
        /**Uncomment to test using backend
        getFeed();					//initializes invited, active, and finished bets
        
        for(int i = 0; i < invitedBets.size(); i++){
        LinearLayout layout = (LinearLayout) findViewById(R.id.invitedLayout);
        LayoutInflater inf = (LayoutInflater) context
                .getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View convertView = inf.inflate(R.layout.bet_preview, null);
        
        layout.addView(convertView);
        getBet(convertView, invitedBets.get(i));
        }
        for(int i = 0; i < activeBets.size(); i++){
        	LinearLayout layout = (LinearLayout) findViewById(R.id.invitedLayout);
            LayoutInflater inf = (LayoutInflater) context
                    .getSystemService(context.LAYOUT_INFLATER_SERVICE);
            View convertView = inf.inflate(R.layout.bet_preview, null);
            
            layout.addView(convertView);
        	getBet(convertView, activeBets.get(i));
//            LinearLayout layout = (LinearLayout) findViewById(R.id.activeLayout);
//            LayoutInflater inf = (LayoutInflater) context
//                    .getSystemService(context.LAYOUT_INFLATER_SERVICE);
//            View convertView = inf.inflate(R.layout.bet_preview, null);
//            
//            layout.addView(convertView);
//            
//            ImageView profile_photo = (ImageView) convertView.findViewById(R.id.profile_pic);
//         
//            try {
//            	  
//            	  Bitmap bitmap = BitmapFactory.decodeStream((InputStream)new URL("https://lh3.googleusercontent.com/-htt-o8qHiyQ/AAAAAAAAAAI/AAAAAAAAAEo/0ZwB0uI91UM/photo.jpg?sz=200").getContent());
//            	  profile_photo.setImageBitmap(bitmap); 
//            	} catch (MalformedURLException e) {
//            	  e.printStackTrace();
//            	} catch (IOException e) {
//            	  e.printStackTrace();
//            	}
        }
        for(int i = 0; i < finishedBets.size(); i++){
            LinearLayout layout = (LinearLayout) findViewById(R.id.finishedLayout);
            LayoutInflater inf = (LayoutInflater) context
                    .getSystemService(context.LAYOUT_INFLATER_SERVICE);
            View convertView = inf.inflate(R.layout.bet_preview, null);
            
            layout.addView(convertView);
            getBet(convertView, finishedBets.get(i));
        }
        **/
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



    public void friendsFeed(View view){
    	Intent myIntent = new Intent(this,FriendsFeed.class);
    	
    	myIntent.putExtra("id", userID);
    	startActivity(myIntent);
    }
    public void bet(View view){
    	Intent myIntent = new Intent(this,MakeBet.class);
    	myIntent.putExtra("id", userID);
    	startActivity(myIntent);
    }
    public void singleBet(View view){
    	Intent myIntent = new Intent(this, ViewBet.class);
    	myIntent.putExtra("id", userID);
    	startActivity(myIntent);
    }
    
    public void createAccount(View view){
		Uri uri = Uri.parse("https://accounts.google.com/SignUp");
		Intent intent = new Intent(Intent.ACTION_VIEW, uri);
		startActivity(intent);
	}
	
	@TargetApi(Build.VERSION_CODES.GINGERBREAD)
	@SuppressLint("NewApi")
	public void getFeed(){
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

		StrictMode.setThreadPolicy(policy); 
       
//        Intent myIntent = getIntent(); // gets the previously created intent
//        String key = myIntent.getStringExtra("key");
        
        RequestParams params = new RequestParams();
        params.put("id", userID);
        
       // final ArrayList<String> images = new ArrayList<String>();
        AsyncHttpClient client = new AsyncHttpClient();
        client.get("betme-os.appspot.com/MyFeed", params, new AsyncHttpResponseHandler() {

            @Override
			public void onSuccess(int statusCode, Header[] headers, byte[] response) {
            	String str;
				try {
					str = new String(response, "UTF-8");
					JSONObject dict = new JSONObject(str);
					JSONArray invited = dict.getJSONArray("invited");
					JSONArray active = dict.getJSONArray("active");
					JSONArray finished = dict.getJSONArray("finished");
					
					for(int i = 0; i < invited.length();i++){
						invitedBets.add(invited.get(i).toString());
					}
					for(int i = 0; i < active.length();i++){
						activeBets.add(active.get(i).toString());
					}
					for(int i = 0; i < finished.length();i++){
						finishedBets.add(finished.get(i).toString());
					}
				
					
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				    				
            }

			@Override
			public void onFailure(int arg0, Header[] arg1, byte[] arg2,
					Throwable arg3) {
				// TODO Auto-generated method stub
				
			}
        });
	}
	public void getBet(final View view, String betID){
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

		StrictMode.setThreadPolicy(policy); 
       
//        Intent myIntent = getIntent(); // gets the previously created intent
//        String key = myIntent.getStringExtra("key");
        
        RequestParams params = new RequestParams();
        params.put("id", userID);
        params.put("bet_id", betID);
       // final ArrayList<String> images = new ArrayList<String>();
        AsyncHttpClient client = new AsyncHttpClient();
        client.get("betme-os.appspot.com/GetBet", params, new AsyncHttpResponseHandler() {

            @Override
			public void onSuccess(int statusCode, Header[] headers, byte[] response) {
            	String str;
				try {
					str = new String(response, "UTF-8");
					JSONObject dict = new JSONObject(str);
					String opponentName = dict.getJSONObject("opponent_name").toString();
					String opponentPicture = dict.getJSONObject("opponent_picture").toString();
					String team1 = dict.getJSONObject("team1").toString();
					String team2 = dict.getJSONObject("team2").toString();
					
					
			            TextView friendName = (TextView) view.findViewById(R.id.friend_name);
			            friendName.setText(opponentName);
			            TextView teamName1 = (TextView) view.findViewById(R.id.team1);
			            teamName1.setText(team1);
			            TextView teamName2 = (TextView) view.findViewById(R.id.team2);
			            teamName2.setText(team2);
			            
			            ImageView profile_photo = (ImageView) view.findViewById(R.id.profile_pic);
			            
			            try {
			            	  
			            	  Bitmap bitmap = BitmapFactory.decodeStream((InputStream)new URL(opponentPicture).getContent());
			            	  profile_photo.setImageBitmap(bitmap); 
			            	} catch (MalformedURLException e) {
			            	  e.printStackTrace();
			            	} catch (IOException e) {
			            	  e.printStackTrace();
			            	}
					
					
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				    				
            }

			@Override
			public void onFailure(int arg0, Header[] arg1, byte[] arg2,
					Throwable arg3) {
				// TODO Auto-generated method stub
				
			}
        });
	}
	
    
}
