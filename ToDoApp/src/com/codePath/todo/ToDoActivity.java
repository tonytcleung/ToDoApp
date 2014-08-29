package com.codePath.todo;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.commons.io.FileUtils;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;


public class ToDoActivity extends ActionBarActivity {
	private ArrayList<String> 		todoItems;
	private ArrayAdapter<String>	todoAdapter;
	private ListView 				lvItems;
	private EditText				etNewItem;
	
	private final int 				EDIT_REQUEST_CODE	= 20;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo);

    	etNewItem	= (EditText) findViewById(R.id.etNewItem);
    	readItems();
        todoAdapter	= new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, todoItems);
        lvItems		= (ListView) findViewById(R.id.lvItems);
        lvItems.setAdapter(todoAdapter);
        
        setupListViewListener();
    }


    private void setupListViewListener() {
		lvItems.setOnItemLongClickListener(new OnItemLongClickListener() {

			/**
			 * remove item of the array list and notify adaptor
			 */
			public boolean onItemLongClick(AdapterView<?> adaptor, View item, int position, long id) {
				todoItems.remove(position);
				todoAdapter.notifyDataSetChanged();
				// write to file
				writeItems();
				return true;
			}
		
		});
		
		lvItems.setOnItemClickListener(new OnItemClickListener() {

			/**
			 * create intent, add extra and start activity
			 */
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Intent intent	= new Intent(ToDoActivity.this, EditItemActivity.class);
				intent.putExtra("item", todoItems.get(position));
				intent.putExtra("position", position);
				startActivityForResult(intent, EDIT_REQUEST_CODE);
			}
		});
	}
    
    /**
     * update the 
     */
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
    	// make srue that it is the same request code is the edit request code
    	if (resultCode == RESULT_OK && requestCode == EDIT_REQUEST_CODE) {
    		// grab the position and newItem
    		int position	= intent.getExtras().getInt("position");
    		String newItem	= intent.getExtras().getString("newItem");
    		
    		// set the new item and write to file and notify set changed
    		todoItems.set(position, newItem);
    		todoAdapter.notifyDataSetChanged();
    		writeItems();
    	}
    }

    /**
     * read file from android for the list
     */
    private void readItems() {
    	File filesDir	= getFilesDir();
    	File todoFile 	= new File(filesDir, "todo.txt");
    	
    	// attempt to load the files
    	try {
    		todoItems	= new ArrayList<String>(FileUtils.readLines(todoFile));
    	}
    	// if they don't exist, then create them
    	catch (IOException e) {
    		todoItems	= new ArrayList<String>();
    	}
    }
    
    private void writeItems() {
    	File filesDir	= getFilesDir();
    	File todoFile 	= new File(filesDir, "todo.txt");
    	
    	// attempt to load the files
    	try {
    		FileUtils.writeLines(todoFile, todoItems);
    	}
    	// if they don't exist, then create them
    	catch (IOException e) {
    		e.printStackTrace();
    	}
    }

    /**
     * Add et text to adaptor and clear out et
     * @param view
     */
    public void onAddedItem(View view) {
    	String itemText	= etNewItem.getText().toString();
    	todoAdapter.add(itemText);
    	
    	etNewItem.setText("");
    	// write to file
    	writeItems();
    }
    

	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.to_do, menu);
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
