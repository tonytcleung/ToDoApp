package com.codePath.todo;

import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

public class EditItemActivity extends ActionBarActivity {
	private String 		itemValue;
	private int			position;
	private EditText	etEditItem;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_item);
		
		// grab values from intent and setup edititem
		itemValue	= getIntent().getStringExtra("item");
		position	= getIntent().getIntExtra("position", 0);
		etEditItem	= (EditText) findViewById(R.id.etEditItem);
		
		// setup the edit text
		setupEditItem();
	}

	/**
	 * save item and finish intent
	 */
	public void saveItem(View view) {
		String newItemValue	= etEditItem.getText().toString();
		
		// create data intent and finish
		Intent dataIntent	= new Intent();
		dataIntent.putExtra("newItem", newItemValue);
		dataIntent.putExtra("position", position);
		setResult(RESULT_OK, dataIntent);
		finish();
	}
	
	/**
	 * assign the edit item and set position on the cursor
	 */
	private void setupEditItem() {
		etEditItem.setText(itemValue);
		etEditItem.setSelection(itemValue.length());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.edit_item, menu);
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
}
