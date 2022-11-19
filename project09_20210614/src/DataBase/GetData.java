package DataBase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import GUI.Date;
import GUI.Event;
public class GetData {
	static String url;
	static String username;
	static String password;
	static Connection conn;
	public GetData() {
		String server="jdbc:mysql://140.119.19.73:9306/";
		String database="MG09";
		String config="?useUnicode=true&characterEncoding=utf8";
		url=server+database+config;
		username="MG09";
		password="HcFT2Z";
		initializing();
	}
	private void initializing() {
		try {
			this.conn=DriverManager.getConnection(url,username,password);
		}catch(SQLException e) {
			System.out.println(e.getMessage());
		}
	}
	public ArrayList<String> getCourse(String stuID) {
		try {
			stuID="S"+stuID;
			String query = "SELECT * FROM "+stuID;
			Statement stat = conn.createStatement();
			boolean hasResultSet = stat.execute(query);
			if (hasResultSet) {
				ResultSet result = stat.getResultSet();
				SultDatas sd=new SultDatas(result);
				result.close();
				return sd.getCourName();
			}
		}catch(SQLException e){
			System.out.println(e.getMessage());
		}
		return null;
	}
	public boolean findTable(String stuID) throws SQLException {
		String query="SHOW TABLES LIKE ?";
		PreparedStatement stat=conn.prepareStatement(query);
		stat.setString(1,"E"+stuID);
		stat.executeUpdate();
		ResultSet result=stat.getResultSet();
		if(result.next()) {
			return true;
		}else {
			return false;
		}
	}
	public ArrayList<Event> getEvents(Student student,Date date) throws SQLException{
		String tableName="E"+student.getID();
		String query="SELECT * FROM "+tableName
					+" WHERE Year=? "
					+"AND Month=? "
					+"AND Date=? ";
		PreparedStatement stat=conn.prepareStatement(query);
		stat.setInt(1,date.getYear());
		stat.setInt(2,date.getMonth());
		stat.setInt(3,date.getDate());
		stat.executeQuery();
		ResultSet result=stat.getResultSet();
		ArrayList<Event> events=new ArrayList<Event>();
		while(result.next()) {
			String title=result.getString("Title");
			String tag=result.getString("Tag");
			String note=result.getString("Note");
			ArrayList<String> timeArray=new ArrayList<String>();
			timeArray.add(result.getString("StartHour"));
			timeArray.add(result.getString("StartMinute"));
			timeArray.add(result.getString("EndHour"));
			timeArray.add(result.getString("EndMinute"));
			Event e=new Event(date,student,title, tag, note, timeArray);
			events.add(e);
		}
		return events;
	}
}
