package pl.xt.jokii.jdbc_test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity {
	private static final String DB_HOST 			= "10.0.2.2";
	private static final String DB_PORT 			= "3306";
	private static final String DB_NAME 			= "test";
	private static final String DB_USER_NAME 		= "root";
	private static final String DB_USER_PASSWORD	= "vertrigo";
	private StringBuilder strb;
	private TextView tv;
	private Button btn;
	private Button btnGet;
	private boolean operationsPending 	 = false;
	private boolean getDbDataOnly 		 = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        tv = (TextView) findViewById(R.id.textView1);
        btn = (Button) findViewById(R.id.button1);
        btnGet = (Button) findViewById(R.id.button2);
       
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
    
    /**
     * Execute remote DB operations
     * @param v
     */
    public void startJDBC(View v)
    {    	
    	if(!operationsPending)
    	{
    		operationsPending = true;
    		    		
    		btn.setEnabled(false);
    		btnGet.setEnabled(false);
    		new refreshDbData().execute("Treœæ zapisana: ");
    	}
    }
    
    /**
     * Retrieve data from remote data base
     * @param v - related Button view
     */
    public void getDbData(View v)
    {
    	if(!operationsPending)
    	{
    		getDbDataOnly = true;
    		operationsPending = true;
    		
    		btn.setEnabled(false);
    		btnGet.setEnabled(false);
    		new refreshDbData().execute();
    	}    	
    }
  
    
    /**
     * Connecting to remote MySQL data base, perform INSERT and SELECT operation
     * source http://developer.android.com/guide/components/processes-and-threads.html
     * @author Tomasz Jokiel
     *
     */
    private class refreshDbData extends AsyncTask<String, Void, String>
    {
    	/** The system calls this to perform work in a worker thread and
         * delivers it the parameters given to AsyncTask.execute() */
		@Override
		protected String doInBackground(String... arg0) {
			Connection connection;
			Statement statement;
			ResultSet result;
			
			try {
				Class.forName("com.mysql.jdbc.Driver").newInstance();
			} catch (Exception e) {
				throw new RuntimeException("JDBC driver fail");
			}
			
			
			try {
				//Connection connection = DriverManager.getConnection("jdbc:mysql://db_address:port/bd_name","user","passowrd");
	        	connection = DriverManager.getConnection("jdbc:mysql://"+DB_HOST+":"+DB_PORT+"/"+DB_NAME ,DB_USER_NAME , DB_USER_PASSWORD);
	        	DriverManager.setLoginTimeout(10);
	        	statement   = connection.createStatement();
	        	
	        	if(!getDbDataOnly)
	        	{
	        		statement.execute("INSERT INTO `proba` (`ID`, `text`) VALUES (NULL, '"+arg0[0]+""+connection+"');");		// use statement.execute("....."); for INSERT, UPDATE ?
	        	}
	        	
	        	result = statement.executeQuery("SELECT * FROM `proba`"); 																			// use statement.executeQuery("....."); for SELECT
	        	
	        	
	        	strb = new StringBuilder();
	        	 while (result.next()) {
	        		 strb.append(result.getString(result.findColumn("text")) + "\n");
	        	 }
	        	 
	        	
	        	result.close();
	        	statement.close();
	        	connection.close();	
			} catch (Exception e) {
				throw new RuntimeException("JDBC connection fail");
			}
			
			return strb.toString();
		}
		
	    /** The system calls this to perform work in the UI thread and delivers
	      * the result from doInBackground() */		
		@Override
		protected void onPostExecute(String result) {
			tv.setText(result);
			operationsPending = false;
			getDbDataOnly = false;
			btn.setEnabled(true);
			btnGet.setEnabled(true);
		}
    }
}
