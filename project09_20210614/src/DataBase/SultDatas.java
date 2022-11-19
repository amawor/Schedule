package DataBase;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;

public class SultDatas {
	ArrayList<String> courseName=new ArrayList();
    ArrayList<String> teacher=new ArrayList();
    ArrayList<String> courseHour=new ArrayList();
    ArrayList<String> courseDay=new ArrayList();
    ResultSetMetaData metaData;
    ResultSet result;
    public SultDatas(ResultSet result) throws SQLException {
    	this.result=result;
    	metaData = result.getMetaData();
    	sultResultSet();
    }
	public void sultResultSet() throws SQLException{
		
	    while (result.next()) {
	    	courseName.add(result.getString(2));
	    	teacher.add(result.getString(3));
	    	courseHour.add(result.getString(4));
	    	courseDay.add(result.getString(5));
	    }
	}
	public ArrayList<String> getCourName(){
		return courseName;
	}
	public ArrayList<String> getTeacher(){
		return teacher;
	}
	public ArrayList<String> getCourHour(){
		return courseHour;
	}
	public ArrayList<String> getCourDay(){
		return courseDay;
	}
}
