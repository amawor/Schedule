package GUI;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import Crawer.*;
import DataBase.Student;
import DataBase.UpdateDataBase;
import DataBase.UploadData;
public class LoginPanel extends JPanel{
	private static final int FIELD_WIDTH=25;
	private JButton loginButton;
	private JTextField stuIDField;
	private JPasswordField pwField;
	private JLabel stuIDLabel,pwLabel;
	private JPanel stuIDPanel,pwPanel;
	private String studentID=null,password=null;
	private Student student;
	private CalendarFrame frame;
	public LoginPanel() {
		GridBagConstraints grid=new GridBagConstraints();
		this.setLayout(new GridBagLayout()); 
		stuIDLabel=new JLabel("student ID:");
		stuIDField=new JTextField(FIELD_WIDTH);
		stuIDPanel=new JPanel();
		stuIDPanel.add(stuIDLabel);
		stuIDPanel.add(stuIDField);
		grid.gridx=1;
		grid.gridy=1;
		grid.gridwidth=3;
		grid.gridheight=1;
		add(stuIDPanel,grid);
		pwLabel=new JLabel("Password:");
		pwField=new JPasswordField(FIELD_WIDTH);
		pwField.setEchoChar('*');
		pwPanel=new JPanel();
		pwPanel.add(pwLabel);
		pwPanel.add(pwField);
		grid.gridy=2;
		add(pwPanel,grid);
		loginButton=new JButton("Log in");
		grid.gridx=3;
		grid.gridy=3;
		grid.gridwidth=1;
		add(loginButton,grid);
	}
	public void addLoginListener(CalendarFrame frame) {
		this.frame=frame;
		class ClickListener implements ActionListener{
			public void actionPerformed(ActionEvent e) {
				studentID=stuIDField.getText();
				password=new String(pwField.getPassword());
				if(studentID!=null&&password!=null) {
					try {
						student=new Student(studentID,password);
						UpdateDataBase.main(null,studentID,password,frame);
						
					} catch (IOException | SQLException e1) {
						e1.printStackTrace();
					}
					
				}
			}
		}
		ActionListener loginListener=new ClickListener();
		this.loginButton.addActionListener(loginListener);
		loginButton.setSize(70, 30);
	}
	public Student getStudent() {
		return this.student;
	}
	public void updateCurriculum() throws SQLException, IOException {
		UploadData uploader=new UploadData();
		uploader.updateDataBase(student,frame);
	}
}
