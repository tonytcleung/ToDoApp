package com.codePath.todo;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class ReminderListAdapter extends ArrayAdapter<Reminder> {
	
	// view cache
	private static class ViewHolder {
		TextView title;
		TextView dueDate;
	}
	
    public ReminderListAdapter(Context context, ArrayList<Reminder> reminder) {
        super(context, 0, reminder);
     }

     @Override
     public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
    	Reminder reminder 		= getItem(position);  
    	
    	// use view holder pattern
    	ViewHolder viewHolder;
        // Check if an existing view is being reused, 
    	// if it is new, inflate the view and set viewHolder as tag
        if (convertView == null) {
        	viewHolder			= new ViewHolder();
        	convertView 		= LayoutInflater.from(getContext()).inflate(R.layout.item_reminder, parent, false);
            viewHolder.title 	= (TextView) convertView.findViewById(R.id.tvTitle);
            viewHolder.dueDate	= (TextView) convertView.findViewById(R.id.tvDueDateValue); 
            convertView.setTag(viewHolder);
        }
        // retrieve view holder from tag
        else {
        	viewHolder			= (ViewHolder) convertView.getTag();
        }
        // Populate the data into the template view using the data object
        viewHolder.title.setText(reminder.title);
        viewHolder.dueDate.setText(reminder.dueDate);
        // Return the completed view to render on screen
        return convertView;
    }
}
