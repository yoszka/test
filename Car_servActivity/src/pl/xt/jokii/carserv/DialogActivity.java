/*
 * Copyright (C) 2012 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
		//Log.e("DELETE_ITEM_RES", "RESULT_OK " + EntryId);
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
		//Log.e("RESULT_CANCELED", "RESULT_OK " + EntryId);
		DialogActivity.this.finish(); 
    }    
  
}
