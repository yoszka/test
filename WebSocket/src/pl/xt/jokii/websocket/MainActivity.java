package pl.xt.jokii.websocket;

import java.io.IOException;
import java.net.URI;
import pl.xt.jokii.events.WebSocketEventListener;

import com.strumsoft.websocket.phonegap.WebSocket;
import com.strumsoft.websocket.phonegap.WebSocketFactory;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends Activity {
	private Button 				button1;
	private EditText 			editText;
	private WebView 			appView;
	private WebSocketListener 	webSocketListener;	

	
	
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        appView = (WebView) findViewById(R.id.webView1);
        button1 = (Button) findViewById(R.id.button1);
        editText = (EditText) findViewById(R.id.editText1);
        
        webSocketListener = new WebSocketListener(appView, URI.create("wss://echo.websocket.org"), WebSocket.Draft.DRAFT75, WebSocketFactory.getRandonUniqueIdS());
        
        webSocketListener.addWebSocketEventListener(new WebSocketEventListener() {
        	
        	public void handleWebSocketEvent() {
//				button1.setEnabled(true);													// ViewRootImpl$CalledFromWrongThreadException        		
				String customHtml = "<html><body>Conected to<br/> \"wss://echo.websocket.org\"</body></html>";
				appView.loadData(customHtml, "text/html", "UTF-8");												// only for appView
			}

			public void handleSendEvent(String msg) {
//				button1.setEnabled(true);													// ViewRootImpl$CalledFromWrongThreadException
				String customHtml = "<html><body><h1>"+msg+"</h1></body></html>";
				appView.loadData(customHtml, "text/html", "UTF-8");				
			}
		});
             
        
        ConnectWebSocketTask task = new ConnectWebSocketTask();
        task.execute();
    }

    
    
    
    /**
     * Class for asynchronous tasks
     * @author Tomasz Jokiel
     */
    private class ConnectWebSocketTask extends AsyncTask<Void, Void, Void>
    {

		@Override
		protected Void doInBackground(Void... params) {
	        try {
				webSocketListener.connect();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return null;			
		}
    	
		protected void onPostExecute(Void v) {
			button1.setEnabled(true);
		};
    }
    
    
    
    
    /**
     * Button callback function
     * @param v - related Button view
     */
    public void onClickButton1(View v)
    {
//    	button1.setEnabled(false);
    	webSocketListener.send(editText.getText().toString());
    }
}
