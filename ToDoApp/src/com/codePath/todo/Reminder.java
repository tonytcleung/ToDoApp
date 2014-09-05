package com.codePath.todo;

import java.util.ArrayList;
import java.util.List;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

@Table(name = "Reminders")
public class Reminder extends Model{	

    @Column(name = "Title")
    public String title;
    
    @Column(name = "DueDate")
    public String dueDate;

    // Make sure to have a default constructor for every ActiveAndroid model
    public Reminder(){
       super();
    }

	public Reminder(String title, String dueDate) {
		super();
		this.title 		= title;
		this.dueDate 	= dueDate;
	}
	
	/**
	 * return all the reminders
	 * @return
	 */
    public static ArrayList<Reminder> getAllReminders() {
        // This is how you execute a query
        List<Reminder> list	= new Select() .from(Reminder.class) .orderBy("DueDate ASC") .execute();
        return new ArrayList<Reminder>(list);
    }
}
