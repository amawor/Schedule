package DataBase;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import Crawer.Crawer;
import Crawer.SultElements;
import GUI.CalendarFrame;
import GUI.CurriculumPanel;
import GUI.Event;

public class UploadData {
	static String url;
	static String username;
	static String password;
	static Connection conn;
	public	UploadData() {
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
	public boolean findTable(String stuID) throws SQLException {
		String query="SHOW TABLES LIKE ?";
		PreparedStatement stat=conn.prepareStatement(query);
		stat.setString(1,"E"+stuID);
		stat.executeQuery();
		ResultSet result=stat.getResultSet();
		if(result.next()) {
			return true;
		}else {
			return createEventTable(stuID);
		}
	}
	public boolean createEventTable(String stuID) throws SQLException {
		String tableName="E"+stuID;
		String query="CREATE TABLE "+tableName
					+"(Title VARCHAR(50) NOT NULL,"
					+"Year INT(4) NOT NULL,"
					+"Month INT(2) NOT NULL,"
					+"Date INT(2) NOT NULL,"
					+"StartHour VARCHAR(2) NOT NULL,"
					+"StartMinute VARCHAR(2) NOT NULL,"
					+"EndHour VARCHAR(2) NOT NULL,"
					+"EndMinute VARCHAR(2) NOT NULL,"
					+"Tag VARCHAR(20),"
					+"Note VARCHAR(200))";
		PreparedStatement stat=conn.prepareStatement(query);
		if(stat.executeUpdate()==0) {
			return true;
		}else {
			return false;
		}
	}
	public boolean addEvent(String stuID,Event event) throws SQLException {
		String tableName="E"+stuID;
		String query="INSERT INTO "+tableName+" VALUES(?,?,?,?,?,?,?,?,?,?)";
		PreparedStatement stat=conn.prepareStatement(query);
		stat.setString(1,event.getTitle());
		stat.setInt(2,event.getDate().getYear());
		stat.setInt(3,event.getDate().getMonth());
		stat.setInt(4,event.getDate().getDate());
		stat.setString(5,event.getTime().get(0));
		stat.setString(6,event.getTime().get(1));
		stat.setString(7,event.getTime().get(2));
		stat.setString(8,event.getTime().get(3));
		stat.setString(9,event.getTag());
		stat.setString(10,event.getNote());
		if(stat.executeUpdate()==1) {
			return true;
		}else {
			return false;
		}
	}
	
	public boolean deleteEvent(String stuID,Event event) throws SQLException {
		String tableName= "E"+stuID;
		String query="DELETE FROM "+tableName
					+" Where Title=? "
					+"AND Year=? "
					+"AND Month=? "
					+"AND Date=? "
					+"AND StartHour=? "
					+"AND StartMinute=?"
					+"AND EndHour=? "
					+"AND EndMinute=? ";	
		PreparedStatement stat=conn.prepareStatement(query);
		stat.setString(1,event.getTitle());
		stat.setInt(2,event.getDate().getYear());
		stat.setInt(3,event.getDate().getMonth());
		stat.setInt(4,event.getDate().getDate());
		stat.setString(5, event.getTime().get(0));
		stat.setString(6, event.getTime().get(1));
		stat.setString(7, event.getTime().get(2));
		stat.setString(8, event.getTime().get(3));
		if(stat.executeUpdate()==1) {
			return true;
		}else {
			return false;
		}
	}
	public static void updateDataBase(Student student,CalendarFrame frame) throws SQLException, IOException {
		String hours="";
		String tableName= "S"+student.getID();
		String query="DROP TABLE "+tableName;
		PreparedStatement stat=conn.prepareStatement(query);
		stat.executeUpdate();
		try {
			Statement crawerStat = conn.createStatement();
			
			String html=Crawer.main(null, student.getID(), student.getPW());

	        SultElements se=new SultElements(html);
			if(html.contains("¬ì¥Ø¦WºÙ")) {
				ArrayList<String> courName=se.sultCourName(),
				  		  teacher=se.sultTeacher(),
				  		  courDay=se.sultCourDay(),
				  		  courHour=new ArrayList();
				for(int i=0;i<courName.size();i++) {
					hours="";
					for(String s:se.sultCourHour(i)) {
						hours+=s;
					}
					courHour.add(hours);
				}
				for(int i=0;i<courName.size();i++) {
						String setTable="INSERT INTO `S"+student.getID() 
								+ "`(`ID` , `courName`, `teacher`, `courHour`, `courDay`) "
								+ "VALUES ('"+(i+1)+"','"+courName.get(i)+"','"+teacher.get(i)+"','"+courHour.get(i)+"','"+courDay.get(i)+"')";
						stat.execute(setTable);
				}
					CurriculumPanel curriculumPanel=new CurriculumPanel();
					se=curriculumPanel.setTable(html);
			        frame.login(curriculumPanel);
			}else {
				JOptionPane.showMessageDialog(frame,"Operation error");
			}
			
		}finally {
			
		}
	}
}
