package pl.xt.jokii.websocket;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import pl.xt.jokii.events.WebSocketEventListener;

import android.webkit.WebView;
import android.widget.Button;

import com.strumsoft.websocket.phonegap.WebSocket;

public class WebSocketListener extends WebSocket{
	private WebView appView;
	private List 	_listeners = new ArrayList();
	
	/**
	 * Add event listener
	 * @param listener
	 */
	public synchronized void addWebSocketEventListener(WebSocketEventListener listener)	{
	_listeners.add(listener);
	}
	
	/**
	 * Remove event listener
	 * @param listener
	 */
	public synchronized void removeWebSocketEventListener(WebSocketEventListener listener)	{
	_listeners.remove(listener);
	}
	  
	
	// call this method when need to notify
	// the event listeners of the particular event
	private synchronized void fireOpenEvent()	{
		Iterator i = _listeners.iterator();
		
		while(i.hasNext())	
		{
			((WebSocketEventListener) i.next()).handleOpenEvent();
		}
	}
		
	
	
	// call this method when need to notify listener
	// the event listeners of the send event
	private synchronized void fireSendEvent(String msg)
	{	
		Iterator i = _listeners.iterator();
		
		while(i.hasNext())	
		{
			((WebSocketEventListener) i.next()).handleSendEvent(msg);
		}					
	}
	
	
	// call this method when need to notify listener
	// the event listeners of the close event
	private synchronized void fireCloseEvent()
	{	
		Iterator i = _listeners.iterator();
		
		while(i.hasNext())	
		{
			((WebSocketEventListener) i.next()).handleCloseEvent();
		}					
	}	

	/**
	 * Constructor
	 * @param appView
	 * @param uri
	 * @param draft
	 * @param id
	 */
	protected WebSocketListener(WebView appView, URI uri, Draft draft, String id) {
		super(appView, uri, draft, id);
		
		this.appView = appView;
	}
	
	
//	@Override
//	public Thread connect() throws IOException {
//		return super.connect();
//	}
	
	
	/**
	 * Wraper for connect function, run it on separate thread
	 * @return thread which in is sunned
	 * @throws IOException
	 */
	public Thread connectTh()
	{
		Thread th = new Thread(new Runnable() {
			
			public void run() {
				try {
					connect();
				} catch (IOException e) {
					e.printStackTrace();
				}
				
			}
		});
		
		th.start();
		
		return th;		
	}
	


	@Override
	public void onOpen() {
		super.onOpen();

		String customHtml = "<html><body>Conecte</body></html>";
		this.appView.loadData(customHtml, "text/html", "UTF-8");
	   
		fireOpenEvent();
	}
	
	@Override
	public void onClose() {
		super.onClose();
		fireCloseEvent();
	}
	
	public void onMessage(String msg) {
		   fireSendEvent(msg);
	};
	


}
