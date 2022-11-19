package GUI;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Calendar;
import javax.swing.*;

import DataBase.Student;
import DataBase.UploadData;

import java.util.ArrayList;
public class CalendarFrame extends JFrame{
	private static final int FRAME_WIDTH=520,FRAME_HEIGHT=450;
	private Calendar calendar=Calendar.getInstance();
	private CardLayout card=new CardLayout();
	private JPanel overallPanel,changePanel;
	private LoginPanel loginPanel;
	private JButton nextButton,backButton;
	private int monthID;
	private ArrayList<String > monthes;
	private int month,year;
	private JMenuBar menuBar;
	private JMenu menu;
	private JMenuItem exit,schoolSchedule,backToCalendar,update;
	private Student student;
	public CalendarFrame(String title) {
		this.monthes=new ArrayList<String>();//to record which month has calendar already
		this.setTitle(title);
		this.setSize(FRAME_WIDTH, FRAME_HEIGHT);
		this.setLayout(new BorderLayout());
		calendar=calendar.getInstance();
		createOverallPanel();
		createChangePanel();
		createMenuBar();
		card.show(overallPanel,"login");//show loginPanel
		loginPanel.addLoginListener(this);
	}
	public void createOverallPanel() {
		overallPanel=new JPanel();//calendarPanel
		overallPanel.setLayout(card);
		createLoginPanel();
		this.add(overallPanel,BorderLayout.CENTER);
	}
	public void createMonthPanel() {
		if(calendar.get(Calendar.MONTH)>6) {
			year=calendar.get(Calendar.YEAR);
			month=calendar.get(Calendar.MONTH)-6;
			calendar.set(year,month,1);
		}else {
			year=calendar.get(Calendar.YEAR)-1;
			month=calendar.get(Calendar.MONTH)+6;
			calendar.set(year,month,1);
		}//get the month which is 6 month ago
		for(int i=-6;i<6;i++) {
			MonthPanel mp=new MonthPanel(calendar,student);//create 12 month calendar
			String yearAndMonth=Integer.toString(year)+Integer.toString(month+1);
			monthes.add(yearAndMonth);
			overallPanel.add(mp,yearAndMonth);
			if(month==11) {
				year+=1;
				month=0;
				calendar.set(year,month,1);	
			}else {
				month+=1;
				calendar.set(year,month,1);
			}
		}
		calendar=calendar.getInstance();
		year=calendar.get(Calendar.YEAR);
		month=calendar.get(Calendar.MONTH)+1;
		String yearAndMonth=Integer.toString(year)+Integer.toString(month);//record which year and which month the calendar display.
		monthID=monthes.indexOf(yearAndMonth);
	}
	public void createNextButton() {
		this.nextButton=new JButton(">");
		class ClickListener implements ActionListener{
			public void actionPerformed(ActionEvent e) {
				monthID+=1;
				if(month==12) {
					year+=1;
					month=1;
				}else {
					month+=1;
				}
				if(monthID==monthes.size()) {//create new overallPanel
					calendar.set(year, month-1, 1);
					MonthPanel mp=new MonthPanel(calendar,student);
					String yearAndMonth=Integer.toString(year)+Integer.toString(month);
					monthes.add(yearAndMonth);
					overallPanel.add(mp,yearAndMonth);
				}
				card.show(overallPanel,monthes.get(monthID));
			}
		}
		ActionListener next=new ClickListener();
		this.nextButton.addActionListener(next);
	}
	public void createBackButton() {
		this.backButton=new JButton("<");
		class ClickListener implements ActionListener{
			public void actionPerformed(ActionEvent e) {
				monthID-=1;
				if(month==1) {
					year-=1;
					month=12;
				}else {
					month-=1;
				}
				if(monthID<0) {//create new overallPanel
					calendar.set(year, month-1, 1);
					MonthPanel mp=new MonthPanel(calendar,student);
					String yearAndMonth=Integer.toString(year)+Integer.toString(month);
					monthes.add(0,yearAndMonth);
					overallPanel.add(mp,yearAndMonth);
					monthID=0;
				}
				card.show(overallPanel,monthes.get(monthID));
			}
		}
		ActionListener back=new ClickListener();
		this.backButton.addActionListener(back);
	}
	public void createChangePanel() {
		createNextButton();
		createBackButton();
		this.changePanel=new JPanel();
		this.changePanel.add(this.backButton);
		this.changePanel.add(this.nextButton);
		this.add(changePanel,BorderLayout.SOUTH);
		this.changePanel.setVisible(false);
	}
	public void createMenuBar() {
		menuBar=new JMenuBar();
		menu=new JMenu("Menu");
		schoolSchedule=new JMenuItem("Curriculum");
		backToCalendar=new JMenuItem("Calendar");
		update=new JMenuItem("Update");
		exit=new JMenuItem("Exit");
		menu.add(schoolSchedule);
		menu.add(backToCalendar);
		menu.add(update);
		menu.add(exit);
		menuBar.add(menu);
		menuBar.setVisible(true);
		this.setJMenuBar(menuBar);
		backToCalendar.setVisible(false);
		schoolSchedule.setVisible(false);
		update.setVisible(false);
		createMenuItem();
	}
	public void createMenuItem() {
		class ScheduleListener implements ActionListener{
			public void actionPerformed(ActionEvent e) {
				card.show(overallPanel, "schedule");
				changePanel.setVisible(false);
				schoolSchedule.setVisible(false);
				backToCalendar.setVisible(true);
				update.setVisible(true);
			}
		}
		ActionListener scheduleListener=new ScheduleListener();
		schoolSchedule.addActionListener(scheduleListener);
		class BackListener implements ActionListener{
			public void actionPerformed(ActionEvent e) {
				card.show(overallPanel, monthes.get(monthID));
				changePanel.setVisible(true);
				schoolSchedule.setVisible(true);
				backToCalendar.setVisible(false);
				update.setVisible(false);
			}
		}
		ActionListener backListener=new BackListener();
		backToCalendar.addActionListener(backListener);
		class UpdateListener implements ActionListener{
			public void actionPerformed(ActionEvent e) {
				try {
					loginPanel.updateCurriculum();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}
		ActionListener updateListener=new UpdateListener();
		update.addActionListener(updateListener);
		class ExitListener implements ActionListener{
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		}
		ActionListener exitListener=new ExitListener();
		exit.addActionListener(exitListener);
	}
	public void createLoginPanel() {
		this.loginPanel=new LoginPanel();
		overallPanel.add(loginPanel,"login");
	}
	public void login(JPanel panel) {
		overallPanel.add(panel,"schedule");
        changePanel.setVisible(true);
		schoolSchedule.setVisible(true);
		student=loginPanel.getStudent();
		createMonthPanel();
		card.show(overallPanel,monthes.get(monthID));
	}
}
