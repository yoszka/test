package pl.xt.jokii.websocket;

import java.net.URI;
import pl.xt.jokii.events.WebSocketEventListener;

import com.strumsoft.websocket.phonegap.WebSocket;
import com.strumsoft.websocket.phonegap.WebSocketFactory;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

public class MainActivity extends Activity implements AnimationListener{
	private LinearLayout		mSendTools;
	private ProgressBar 		mProgresBar;
	private Button 				mButton1;
	private EditText 			mEditText;
	private WebView 			mAppView;
	private WebSocketListener 	mWebSocketListener;
	protected String			mOldMessage = "<br/>";
	private TimePicker			mTimePicker;
	private Spinner				mSocketSpinner;
	private int					mSelectedSocketPos;
	private String 				mWebSocketAddress;
	private static final int 	DEFAULT_POS = 0;
	private static final boolean RECONNECT_DEFAULT = false;
//	private static final String WEB_SOCKET_ADDRESS = "wss://echo.websocket.org";
//	private static final String WEB_SOCKET_ADDRESS = "ws://10.3.20.24/reef-test/connect";	
	private boolean				mReconnect = RECONNECT_DEFAULT;		



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Request progress bar
        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        
        setContentView(R.layout.activity_main);

        mAppView 		= (WebView) 	findViewById(R.id.webView1);
        mButton1 		= (Button) 		findViewById(R.id.button1);
        mEditText 		= (EditText) 	findViewById(R.id.editText1);
        mTimePicker 	= (TimePicker) 	findViewById(R.id.timePicker1);
        mSocketSpinner	= (Spinner) 	findViewById(R.id.spinner1);
        mSendTools		= (LinearLayout)findViewById(R.id.sendTools);
        
        mProgresBar  	= (ProgressBar) findViewById(R.id.progressBar);
        
        setProgressBarIndeterminateVisibility(true);
        
        // Add listener for spinner
        addListenerOnSpinnerItemSelection();
        
        // Choose selected web socket address
        mSelectedSocketPos = mSocketSpinner.getSelectedItemPosition();
        mWebSocketAddress  = getResources().getStringArray(R.array.sockets)[mSelectedSocketPos];
//        webSocketAddress = WEB_SOCKET_ADDRESS;

        // Set web socket address and add listener
        setWebSocket(mWebSocketAddress);
       
        // connect
        Toast.makeText(getApplicationContext(), "Connecting", Toast.LENGTH_SHORT).show();
        mWebSocketListener.connectTh();
    }
    
    
    
    /**
     * Set web socket address and add listener
     * @param webSocketAddress
     */
    private void setWebSocket(String webSocketAddress)
    {
    	if(webSocketAddress != null)
    	{
    		this.mWebSocketAddress = webSocketAddress; 
    	}
    	else
    	{
    		throw new RuntimeException("setWebSocket: webSocketAddress is NULL");
    	}
    	
    	//showTimer();
    	
        // configure connection
        //webSocketListener = new WebSocketListener(appView, URI.create(WEB_SOCKET_ADDRESS), WebSocket.Draft.DRAFT75, WebSocketFactory.getRandonUniqueIdS());
        mWebSocketListener = new WebSocketListener(mAppView, URI.create(webSocketAddress), WebSocket.Draft.DRAFT75, WebSocketFactory.getRandonUniqueIdS());
        
        // add event listener
        addWebSocketEventListener();
    }
    
    
    
    
    /**
     * Close Web Socket connection, and reconnect if needed
     * @param reconnect - if true reconnect will be performed
     */
    private void closeWebSocket(boolean reconnect)
    {
    	this.mReconnect = reconnect;
    	
    	// Disable send button
    	setButtonSendEnabledT(false);
    	
    	// Close current connection 
    	mWebSocketListener.close();    	   
    }



    private void addWebSocketEventListener() {
    	
    	mWebSocketListener.addWebSocketEventListener(new WebSocketEventListener() {
    	
	    	public void handleOpenEvent() {
	    		Log.v("handleOpenEvent", "OPENED");
				String customHtml = "<html><body>Conected</body></html>";
				mAppView.loadData(customHtml, "text/html", "UTF-8");												// only for appView
				
				if(mSelectedSocketPos == DEFAULT_POS){
					//editText.setEnabled(false);
					setTonePickerVisibilityT(true);
				}else{
					setSendToolsVisibilityT(true);
					setButtonSendEnabledT(true);
					mEditText.setEnabled(true);
				}
				setProgressBarVisibilityT(false);
			}
	
			public void handleSendEvent(String newMsg) {
	    		Log.v("handleOpenEvent", "RECEIVED");
	    		String customHtml;

				if(mSelectedSocketPos == DEFAULT_POS){	// default socket handle TimePicker
					customHtml = "<html><body>"+newMsg+"<br/>"+mOldMessage+"</body></html>";
					updateTimePickerT(getHourFromString(newMsg), getMinuteFromString(newMsg));
				}else{
					customHtml = "<html><body><font color=\"red\">RESPONSE: </font>"+newMsg+"<br/>"+mOldMessage+"</body></html>";
					setEditTextT("");
					setButtonSendEnabledT(true);
				}
				
				mAppView.loadData(customHtml, "text/html", "UTF-8");
				
				if(mSelectedSocketPos != DEFAULT_POS){
					mOldMessage =  newMsg+"<br/>" + mOldMessage;						// to store history					
				}
			}

			public void handleCloseEvent() {
				Log.v("handleOpenEvent", "CLOSED");
				//Toast.makeText(getApplicationContext(), "Closed", Toast.LENGTH_SHORT).show();
				
				if(mReconnect)
				{
					Toast.makeText(getApplicationContext(), "Reconnecting", Toast.LENGTH_SHORT).show();
					mReconnect = RECONNECT_DEFAULT;					
					
			        // Set web socket address and add listener
			        setWebSocket(mWebSocketAddress);
			       
			        // connect
			        mWebSocketListener.connectTh();
				}
				else
				{
					//setButtonSendEnabledT(true);			// enable button
				}
				
			}
		});		
	}



	/**
     * Choose web socket address, must be invoked before connecting
     */
    private void addListenerOnSpinnerItemSelection() {
    	mSocketSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			public void onItemSelected(AdapterView<?> arg0, View arg1, int pos, long id) {
				if(mSelectedSocketPos != pos)
				{
					setProgressBarIndeterminateVisibility(true);
					
					if(pos == DEFAULT_POS)
					{
						if(mSendTools.getVisibility() == LinearLayout.VISIBLE)
						{
							hideSendTools();
						}
//						timePicker.setVisibility(TimePicker.VISIBLE);
						mEditText.setEnabled(false);
					}
					else
					{
						if(mTimePicker.getVisibility() == TimePicker.VISIBLE)
						{
							hideTimer();
						}
//						timePicker.setVisibility(TimePicker.INVISIBLE);
						mEditText.setEnabled(true);
					}
					
					// Clear WebView
					mAppView.loadData("", "text/html", "UTF-8");	
					
					//Toast.makeText(getApplicationContext(), "Changed", Toast.LENGTH_SHORT).show();
					mSelectedSocketPos = pos;
					mWebSocketAddress  = getResources().getStringArray(R.array.sockets)[pos];
					
					//setButtonSendEnabledT(false);			// disable button
					closeWebSocket(true);
				}
			}

			public void onNothingSelected(AdapterView<?> arg0) {}
		});		
	}




	/**
     * Set UI button state.
     * Function is thread safe. 
     * @param isEnabled - new state for button 
     */
    private void setButtonSendEnabledT(final boolean isEnabled)
    {
		mButton1.post(new Runnable() {					
			public void run() {
				mButton1.setEnabled(isEnabled);
			}
		});
    }
    
    
    
    
    /**
     * Set time picker hour and minute.
     * Function is thread safe.
     * @param hour
     * @param minute
     */
    private void updateTimePickerT(final Integer hour, final Integer minute)
    {
    	mTimePicker.post(new Runnable() {					
			public void run() {
				mTimePicker.setCurrentHour(hour);
				mTimePicker.setCurrentMinute(minute);
			}
		});
    }    

    
    /**
     * Setedit text text state.
     * Function is thread safe.
     * @param isEnabled - new state for button 
     */
    private void setEditTextT(final String str)
    {
    	mEditText.post(new Runnable() {					
			public void run() {
				mEditText.setText(str);
			}
		});
    }     
    
    
    /**
     * Set time picker state.
     * Function is thread safe.
     * @param isEnabled - new state for button 
     */
    private void setTonePickerVisibilityT(final boolean visibility)
    {
    	mTimePicker.post(new Runnable() {					
			public void run() {
				if(visibility)
				{
					showTimer();
				}
				else
				{
					hideTimer();
				}
			}
		});
    }   
    
    
    /**
     * Set send tools state.
     * Function is thread safe.
     * @param isEnabled - new state for button 
     */
    private void setSendToolsVisibilityT(final boolean visibility)
    {
    	mSendTools.post(new Runnable() {					
			public void run() {
				if(visibility){
					showSendTools();
				}else{
					hideSendTools();
				}
			}
		});
    }    
    
    
    /**
     * Setting visibility of progress bar in window tab.
     * Function is thread safe.
     * @param visibility
     */
    private void setProgressBarVisibilityT(final boolean visibility)
    {  	
    	mProgresBar.post(new Runnable() {					
			public void run() {
				setProgressBarIndeterminateVisibility(visibility);
			}
		});    	
    }     
    
    
    /**
     * Convert string for server to integer hour value
     * @param str
     * @return hour integer
     */
    private Integer getHourFromString(String str)
    {
    	int startHour = str.indexOf(" ", str.indexOf(" ", str.indexOf(" ")+1)+1)+1; 
    	String hour = str.substring(startHour, startHour+2);
//    	Log.v("HOUR", "H: "+Integer.parseInt(hour));
    	return Integer.parseInt(hour);
    }
    
    
    
    
    /**
     * Convert string for server to integer minutes value
     * @param str
     * @return minutes integer
     */
    private Integer getMinuteFromString(String str)
    {
    	int startMinute = str.indexOf(":")+1;
    	String minute = str.substring(startMinute, startMinute+2);
//    	Log.v("MINUTE", "M: "+Integer.parseInt(minute));
    	return Integer.parseInt(minute);
    }  
    

    
    
    /**
     * Convert string for server to integer seconds value
     * @param str
     * @return seconds integer
     */
//    private Integer getSecondFromString(String str)
//    {
//    	int startSecond = str.indexOf(":", str.indexOf(":")+1)+1;
//    	String second = str.substring(startSecond, startSecond+2);
////    	Log.v("SECOND", "S: "+Integer.parseInt(second));
//    	return Integer.parseInt(second);
//    }    
    

    
    
    /**
     * Button callback function
     * @param v - related Button view
     */
    public void onClickButton1(View v)
    {
    	if(mEditText.getText().length() > 0)
    	{
	    	mButton1.setEnabled(false);
	    	mWebSocketListener.send(mEditText.getText().toString());
    	}
    }
    
    
    
    /**
     * Animation function
     */
	public void onAnimationEnd(Animation arg0) {
		if(mSelectedSocketPos != DEFAULT_POS){
			mTimePicker.setVisibility(TimePicker.INVISIBLE);
//			showSendTools();
		}else{
			mSendTools.setVisibility(LinearLayout.GONE);
		}
			
	}
	public void onAnimationRepeat(Animation animation) { }
	public void onAnimationStart(Animation animation) {	}
	
	
	/**
	 * Show time picker
	 */
	public void showTimer()
	{
        MainActivity	me		= MainActivity.this;
        Context 		context = me;
        Animation 		anim;
        
        anim = AnimationUtils.loadAnimation(context, R.anim.push_down_in);
        
        anim.setAnimationListener(me);        
        mTimePicker.startAnimation(anim);
        mTimePicker.setVisibility(TimePicker.VISIBLE);
	}
	
	
	/**
	 * Hide time picker
	 */
	public void hideTimer()
	{
        MainActivity	me		= MainActivity.this;
        Context 		context = me;
        Animation 		anim;
        
        anim = AnimationUtils.loadAnimation(context, R.anim.push_down_out);
        
        anim.setAnimationListener(me);
//        timePicker.setVisibility(TimePicker.VISIBLE);
        mTimePicker.startAnimation(anim);
	}	
	
	
	/**
	 * Show send tools
	 */
	public void showSendTools()
	{
        MainActivity	me		= MainActivity.this;
        Context 		context = me;
        Animation 		anim;
        
        anim = AnimationUtils.loadAnimation(context, R.anim.push_up_in);
        
        anim.setAnimationListener(me);        
        mSendTools.startAnimation(anim);
        mSendTools.setVisibility(LinearLayout.VISIBLE);
	}	
    
 
	
	/**
	 * Hide send tools
	 */
	public void hideSendTools()
	{
        MainActivity	me		= MainActivity.this;
        Context 		context = me;
        Animation 		anim;
        
        anim = AnimationUtils.loadAnimation(context, R.anim.push_up_out);
        
        anim.setAnimationListener(me);        
        mSendTools.startAnimation(anim);
        //mSendTools.setVisibility(LinearLayout.VISIBLE);
	}	
}
