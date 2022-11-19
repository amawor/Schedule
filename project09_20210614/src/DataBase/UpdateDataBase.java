package DataBase;
import java.io.IOException;
import java.sql.Connection;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import Crawer.Crawer;
import Crawer.SultElements;
import GUI.CalendarFrame;
import GUI.CurriculumPanel;

public class UpdateDataBase {
	public static void main(String[] args,String stuID,String stuPW, CalendarFrame frame) throws SQLException, IOException{
		// TODO Auto-generated method stub
		String server="jdbc:mysql://140.119.19.73:9306/";
		String database="MG09";
		String url=server+database+"?useUnicode=true&characterEncoding=UTF-8";
		String username="MG09";
		String password="HcFT2Z";
		String studentID="S"+stuID;
		String hours="";
		
		try{
			Connection conn=DriverManager.getConnection(url,username,password);
			if(findTable(stuID,conn)) {
				String query = "SELECT * FROM "+studentID;
				Statement stat = conn.createStatement();
				CurriculumPanel curriculumPanel=new CurriculumPanel();
				boolean hasResultSet = stat.execute(query);
				if (hasResultSet) {
					ResultSet result = stat.getResultSet();
					System.out.print(result);
					SultDatas sd=new SultDatas(result);
					result.close();
					curriculumPanel.setTable(sd.getCourDay(),sd.getCourName(),sd.getTeacher(),sd.getCourHour());
			        frame.login(curriculumPanel);
				}
			}else {
				try {
					Statement stat = conn.createStatement();
					
					String html=Crawer.main(null, stuID, stuPW);
	
			        SultElements se=new SultElements(html);
					if(html.contains("¬ì¥Ø¦WºÙ")) {
						String createTable = "CREATE TABLE "+studentID+"(ID INT(5) NOT NULL AUTO_INCREMENT PRIMARY KEY ,";
						createTable+="courName VARCHAR(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,";
						createTable+="teacher VARCHAR(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,";
						createTable+="courHour VARCHAR(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL ,";
						createTable+="courDay VARCHAR(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,";
						createTable+="INDEX ID (ID)) ENGINE = InnoDB";
						stat.execute(createTable);
						CurriculumPanel curriculumPanel=new CurriculumPanel();
						se=curriculumPanel.setTable(html);
				        frame.login(curriculumPanel);
					}else {
						JOptionPane.showMessageDialog(frame,"The user name or password is wrong");
					}
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
							String setTable="INSERT INTO `"+studentID 
									+ "`(`ID` , `courName`, `teacher`, `courHour`, `courDay`) "
									+ "VALUES ('"+(i+1)+"','"+courName.get(i)+"','"+teacher.get(i)+"','"+courHour.get(i)+"','"+courDay.get(i)+"')";
							stat.execute(setTable);
					}
				}finally {
					conn.close();
				}
			}
		}catch(SQLException e){
			System.out.println(e.getMessage());			
		}
	}
	public static void showResultSet(ResultSet result) throws SQLException{
		ResultSetMetaData metaData = result.getMetaData();
	    int columnCount = metaData.getColumnCount();
	    System.out.print(metaData.getColumnLabel(1));
	    for (int i = 1; i <= columnCount; i++) {
	    	if (i > 1){
	    		System.out.print(", ");
	    		System.out.print(metaData.getColumnLabel(i));
	    	}
	    }
	    System.out.println();
	    while (result.next()) {
	    	
	    	System.out.print(result.getString(1));
	    	for (int i = 1; i <= columnCount; i++) {
	    		if (i > 1){
	    			System.out.print(", ");
	    			System.out.print(result.getString(i));
	    		}
	    	}
	    	System.out.println();
	    }
	}
	public static boolean findTable(String stuID,Connection conn) throws SQLException {
		String query="SHOW TABLES LIKE ?";
		PreparedStatement stat=conn.prepareStatement(query);
		stat.setString(1,"S"+stuID);
		stat.executeUpdate();
		ResultSet result=stat.getResultSet();
		if(result.next()) {
			return true;
		}else {
			return false;
		}
	}
	
}
