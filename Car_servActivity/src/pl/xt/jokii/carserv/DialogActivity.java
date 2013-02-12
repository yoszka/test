package pl.xt.jokii.carserv;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

public class DialogActivity extends Activity {
	private long EntryId;
	private int dialogAction = 0;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.layout_dialog);
        
		Intent intent = getIntent();     
		// Check for custom header text
		String headerText = intent.getStringExtra(Car_servActivity.HEADER_ITEM_RES);
		if(headerText != null){
			TextView headerView = (TextView) findViewById(R.id.tv_header);
			headerView.setText(headerText);
		}
		
		// Check for dialog action
		dialogAction = intent.getIntExtra(Car_servActivity.ACTION_ITEM_RES, 0);
		
		switch(dialogAction){
			case Car_servActivity.DIALOG_ACTION_DELETE:
				EntryId = intent.getLongExtra(Car_servActivity.DELETE_ITEM_RES, 0);
				break;
				
			case Car_servActivity.DIALOG_ACTION_IMPORT:
				break;
				
			case Car_servActivity.DIALOG_ACTION_EXPORT:
				break;
		}
		
		Log.v("DialogActivity", "EntryId = " + EntryId);
    }

	
    /**
     * Callback method defined by the View
     * @param v
     */
    public void onClickOK(View v) {
    	Intent data = new Intent();
    	
		switch(dialogAction){
			case Car_servActivity.DIALOG_ACTION_DELETE:
				data.putExtra(Car_servActivity.DELETE_ITEM_RES, EntryId);		
				break;
				
			case Car_servActivity.DIALOG_ACTION_IMPORT:
				break;
				
			case Car_servActivity.DIALOG_ACTION_EXPORT:
				break;
		}    	
		DialogActivity.this.setResult(Activity.RESULT_OK, data);  
		DialogActivity.this.finish();    	
    }
    
    /**
     * Callback method defined by the View
     * @param v
     */
    public void onClickCancel(View v) {
    	Intent data = new Intent();
    	
		switch(dialogAction){
			case Car_servActivity.DIALOG_ACTION_DELETE:
				data.putExtra(Car_servActivity.DELETE_ITEM_RES, EntryId);		
				break;
				
			case Car_servActivity.DIALOG_ACTION_IMPORT:
				break;
				
			case Car_servActivity.DIALOG_ACTION_EXPORT:
				break;
		}    	
		DialogActivity.this.setResult(Activity.RESULT_CANCELED, data);
		DialogActivity.this.finish(); 
    }    
  
}
