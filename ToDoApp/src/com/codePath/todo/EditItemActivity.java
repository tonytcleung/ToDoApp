package com.codePath.todo;

import java.util.Calendar;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

public class EditItemActivity extends Activity {
	private String 		title;
	private String		dueDate;
	private int			position;
	private EditText	etNewItem;
	private EditText	etNewTimeItem;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_item);
		
		// grab values from intent and setup edititem
		title			= getIntent().getStringExtra("title");
		dueDate			= getIntent().getStringExtra("dueDate");
		position		= getIntent().getIntExtra("position", 0);
		etNewItem		= (EditText) findViewById(R.id.etNewItem);
		etNewTimeItem	= (EditText) findViewById(R.id.etNewTimeItem);
		
		// setup the edit text
		setupNewItem();
	}

	/**
	 * save item and finish intent
	 */
	public void saveItem(View view) {
		String newItemValue	= etNewItem.getText().toString();
		String newTimeValue	= etNewTimeItem.getText().toString();
		
		// create data intent and finish
		Intent dataIntent	= new Intent();
		dataIntent.putExtra("title", newItemValue);
		dataIntent.putExtra("dueDate", newTimeValue);
		dataIntent.putExtra("position", position);
		setResult(RESULT_OK, dataIntent);
		finish();
	}
	
	/**
	 * assign the edit item and set position on the cursor
	 */
	private void setupNewItem() {
		etNewTimeItem.setText(dueDate);
		etNewItem.setText(title);
		etNewItem.setSelection(title.length());
	}
	
	
    /**
     * present date picker label
     * @param view
     */
    //TODO should not use deprecated method
	@Deprecated
    public void onDateItem(View view) {
    	showDialog(0);
    }
    
	@Override
	protected Dialog onCreateDialog(int id) {

    	Calendar cal	= Calendar.getInstance();
		return new DatePickerDialog(this, datePickerListener, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));
	}
	private DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() {
		public void onDateSet(DatePicker view, int selectedYear, int selectedMonth, int selectedDay) {
			etNewTimeItem.setText((selectedMonth + 1) + " / " + selectedDay + " / "
					+ selectedYear);
		}
	};
}
