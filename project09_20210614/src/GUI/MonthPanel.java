package GUI;
import javax.swing.JLabel;
import javax.swing.JPanel;

import DataBase.Student;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormatSymbols;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.util.Calendar;
import java.util.Locale;
public class MonthPanel extends JPanel{
	private Calendar calendar;
	private int year,month,weekDay;
	private int days;
	private int x,y;
	private DateFormatSymbols format=new DateFormatSymbols(Locale.US);
	private static final int BUTTON_WIDTH=65;
	private static final int BUTTON_HEIGHT=50;
	private Student student;
	public MonthPanel(Calendar calendar,Student student) {
		this.calendar=calendar;
		this.student=student;
		this.year=calendar.get(Calendar.YEAR);
		this.month=calendar.get(Calendar.MONTH)+1;
		this.getFirstDay();
		calendar.set(calendar.DAY_OF_MONTH,1);
		x=weekDay*70+5;
		y=50;
		this.days=calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
		this.creatPanel();
	}
	public void getFirstDay(){
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		weekDay=calendar.get(Calendar.DAY_OF_WEEK)-1;
	}
	public void createDateButton() {
		for(int i=0;i<days;i++) {
			Date b=new Date(year,month,i+1,student);
			this.add(b);
			b.setBounds(x, y, BUTTON_WIDTH, BUTTON_HEIGHT);
			weekDay+=1;
			if(weekDay%7==0) {
				y+=50;
				x=5;
			}else {
				x+=70;
			}
		}
	}
	public void addWeekDay(){
		for(int i=1;i<8;i++) {
			JLabel weekDay=new JLabel(format.getShortWeekdays()[i],JLabel.CENTER);
			this.add(weekDay);
			weekDay.setBounds((i-1)*70+5,30,70,20);
		}
	}
	public void createMonthPanel() {
		JLabel monthLabel;
		monthLabel=new JLabel(format.getMonths()[month-1]+" "+year);
		monthLabel.setBounds(10, 10, 150, 20);
		monthLabel.setFont(new java.awt.Font("Dialog", 1, 15));
		monthLabel.setForeground(Color.blue);
		this.add(monthLabel);
	}
	public void creatPanel() {
		this.setLayout(null);
		createDateButton();
		addWeekDay();
		this.createMonthPanel();
	}
}
