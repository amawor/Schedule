package GUI;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JButton;

import DataBase.ExecSQL;
import DataBase.Student;

public class Date extends JButton{
	private ArrayList<Event> events;
	private int year, month,date;
	private String tags;
	private DateFrame frame;
	private Student student;
	private ExecSQL exec;
	public Date(int year,int month,int date,Student student) {
		this.year=year;
		this.month=month;
		this.date=date;
		this.setText(Integer.toString(date));
		this.student=student;
		this.events=new ArrayList<Event>();
		createButtonListener();
	}
	public ArrayList<Event> getEvents() {
		return events;
	}
	public void createFrame() {
		frame=new DateFrame(this,student);
		frame.setVisible(true);
	}
	public void createButtonListener() {//按下日期的按鈕後，從資料庫讀取當天的所有事件並生成dateFrame
		Date date=this;
		class ClickListener implements ActionListener{
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				exec=new ExecSQL();
				createFrame();
				try {
					setEventList(exec.getEvent(student,date));
					
					for(Event event:events) {//建立資料庫儲存的事件時，順便建立通知
						int eventInt = event.callThread(event.getTime(), "", "");
					}
				} catch (SQLException e1) {
					System.out.println(e1.getMessage());
				}
				frame.updateEventPanel();
			}
		}
		ActionListener buttonListener=new ClickListener();
		addActionListener(buttonListener);
	}
	public void addEvent(Event e) {
		events.add(e);
	}
	public boolean setEvent(Event event) {
		for(Event e:events) {
			if(e==event) {
				events.set(events.indexOf(e), event);
				return true;
			}
		}
		addEvent(event);
		return false;
	}
	public void setEventList(ArrayList<Event> events) {
		if(events!=null) {
			this.events=events;
		}
	}
	public DateFrame getFrame() {
		return this.frame;
	}
	public int getYear() {
		return this.year;
	}
	public int getMonth() {
		return this.month;
	}
	public int getDate() {
		return this.date;
	}
}
