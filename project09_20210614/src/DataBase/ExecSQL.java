package DataBase;

import java.sql.SQLException;
import java.util.ArrayList;

import GUI.Date;
import GUI.Event;

public class ExecSQL {
	private GetData getter;
	private UploadData uploader;
	public ExecSQL() {
		this.getter=new GetData();
		this.uploader=new UploadData();
	}
	public ArrayList<String> getCourse(String stuID){
		return getter.getCourse(stuID);
	}
	public ArrayList<Event> getEvent(Student student,Date date) throws SQLException{
		if(getter.findTable(student.getID())) {
			return getter.getEvents(student, date);
		}else {
			return null;
		}
	}
	public boolean addEvent(Student student,Event event) throws SQLException {
		if(uploader.findTable(student.getID())) {
			uploader.addEvent(student.getID(), event);
			return true;
		}else {
			return false;
		}
	}
	public boolean deleteEvent(Student student,Event event) throws SQLException{
		if(uploader.findTable(student.getID())) {
			uploader.deleteEvent(student.getID(), event);
			return true;
		}else {
			return false;
		}
	}
}
