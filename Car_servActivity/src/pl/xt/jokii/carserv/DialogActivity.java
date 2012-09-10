package pl.xt.jokii.carserv;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;

public class DialogActivity extends Activity {
	private long EntryId;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.delete_dialog);
        
		Intent intent = getIntent();     
		EntryId = intent.getLongExtra(Car_servActivity.DELETE_ITEM_RES, 0);
		Log.v("DialogActivity", "EntryId = " + EntryId);
    }

	
    /**
     * Callback method defined by the View
     * @param v
     */
    public void deleteItemOK(View v) {
		Intent data = new Intent();
		data.putExtra(Car_servActivity.DELETE_ITEM_RES, EntryId);		
		DialogActivity.this.setResult(Activity.RESULT_OK, data);  
		DialogActivity.this.finish();    	
    }
    
    /**
     * Callback method defined by the View
     * @param v
     */
    public void deleteItemCancel(View v) {
		Intent data = new Intent();
		data.putExtra(Car_servActivity.DELETE_ITEM_RES, EntryId);		
		DialogActivity.this.setResult(Activity.RESULT_CANCELED, data);
		DialogActivity.this.finish(); 
    }    
  
}
