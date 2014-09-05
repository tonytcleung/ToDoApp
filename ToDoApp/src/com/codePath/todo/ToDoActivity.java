package com.codePath.todo;

import java.util.ArrayList;
import java.util.Calendar;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;


public class ToDoActivity extends Activity {
	private ArrayList<Reminder> 	reminderList;
	private ReminderListAdapter 	reminderAdapter;
	private ListView 				lvItems;
	private EditText				etNewItem;
	private EditText				etTimeItem;
	
	private final int 				EDIT_REQUEST_CODE	= 20;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo);

        lvItems		= (ListView) findViewById(R.id.lvItems);
    	etNewItem	= (EditText) findViewById(R.id.etNewItem);
    	etTimeItem	= (EditText) findViewById(R.id.etTimeItem);
    	readItems();
    	resetDefaults();
    	
        setupListViewListener();
    }


    private void setupListViewListener() {
		lvItems.setOnItemLongClickListener(new OnItemLongClickListener() {

			/**
			 * remove item of the array list and notify adaptor
			 */
			public boolean onItemLongClick(AdapterView<?> adaptor, View item, int position, long id) {
				Reminder reminder	= reminderList.remove(position);
				reminderAdapter.notifyDataSetChanged();
				
				// remove from db
				reminder.delete();
				return true;
			}
		});
		
		lvItems.setOnItemClickListener(new OnItemClickListener() {

			/**
			 * create intent, add extra and start activity
			 */
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Intent intent		= new Intent(ToDoActivity.this, EditItemActivity.class);
				
				//TODO should use parcels to pass an object
				// set the title, dueDate and reminder as extra
				Reminder reminder	= reminderList.get(position);
				intent.putExtra("title", reminder.title);
				intent.putExtra("dueDate", reminder.dueDate);
				intent.putExtra("position", position);
				startActivityForResult(intent, EDIT_REQUEST_CODE);
			}
		});
	}
    
    /**
     * update the 
     */
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
    	// make sure that it is the same request code is the edit request code
    	if (resultCode == RESULT_OK && requestCode == EDIT_REQUEST_CODE) {
    		// grab the position and newItem
    		int position		= intent.getExtras().getInt("position");
    		String title		= intent.getExtras().getString("title");
    		String dueDate		= intent.getExtras().getString("dueDate");
    		
    		Reminder reminder	= new Reminder(title, dueDate);
    		
    		// set the new item and write to file and notify set changed
    		reminderList.set(position, reminder);
    		reminderAdapter.notifyDataSetChanged();
    		
    		// save edits
    		reminder.save();
    	}
    }

    /**
     * read file from android for the list
     */
    private void readItems() {
		// Construct the data source
    	reminderList				= Reminder.getAllReminders();
		// Create the adapter to convert the array to views
    	reminderAdapter				= new ReminderListAdapter(this, reminderList);
		// Attach the adapter to a ListView
		lvItems.setAdapter(reminderAdapter);
    }
    
    /**
     * resets the fields to defaults
     */
	private void resetDefaults() {
    	// reset text label
    	etNewItem.setText("");
    	
    	// reset the date label to current date
    	Calendar cal	= Calendar.getInstance();
    	etTimeItem.setText((cal.get(Calendar.MONTH) + 1) + " / " + cal.get(Calendar.DAY_OF_MONTH) + " / " + cal.get(Calendar.YEAR));
	}

    /**
     * Add new reminder to adapter
     * @param view
     */
	//TODO need to resort array
    public void onAddedItem(View view) {
    	String itemText		= etNewItem.getText().toString();
    	String dateText		= etTimeItem.getText().toString();
    	
    	// create reminder, add to adapter and save
    	Reminder reminder	= new Reminder(itemText, dateText); 
    	reminderAdapter.add(reminder);
    	reminder.save();
    	
    	// reset defaults;
    	resetDefaults();
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
			etTimeItem.setText((selectedMonth + 1) + " / " + selectedDay + " / "
					+ selectedYear);
		}
	};
}
