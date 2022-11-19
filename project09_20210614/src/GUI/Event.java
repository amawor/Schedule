package GUI;

import java.util.ArrayList;
import java.util.Calendar;

import javax.swing.JPanel;

import DataBase.Student;
import notification.Thread_runnable;
public class Event {
	private String title;//事件名稱
	private String note;//事件備註
	private EventPanel panel;
	private ArrayList<String> timeArray;//事件的時間
	private String tag;//事件選取的tag
	private Date date;
	private Student student;
	private Thread thread;
	public Event(Date date,Student student) {
		this.student=student;
		this.date=date;
		this.title="new Event";
		this.note="";
		this.tag="";
		this.timeArray=new ArrayList<String>();
		this.timeArray.add("00");
		this.timeArray.add("00");
		this.timeArray.add("00");
		this.timeArray.add("00");
		createPanel();
	}
	public Event(Date date,Student student,String title,String tag,String note,ArrayList<String>timeArray) {
		this.student=student;
		this.date=date;
		this.title=title;
		this.note=note;
		this.tag=tag;
		this.timeArray=timeArray;
		createPanel();
	}
	public void createPanel() {
		panel=new EventPanel(this,date.getFrame(),student);
	}
	public String getTitle() {
		return this.title;
	}
	public String getNote() {
		return this.note;
	}
	public String getTag() {
		return this.tag;
	}
	public ArrayList<String> getTime(){
		return this.timeArray;
	}
	public void save(String title,String tag,String note,ArrayList<String> time) {
		setTitle(title);
		setTag(tag);
		setNote(note);
		setTime(time);
	}
	public void setTitle(String title) {
		this.title=title;
	}
	public void setTag(String tag) {
		this.tag=tag;
	}
	public void setNote(String note) {
		this.note=note;
	}
	public void setTime(ArrayList<String> time) {
		for(int i=0;i<4;i++) {
			this.timeArray.set(i,time.get(i));
		}
	}
	public JPanel getPanel() {
		return this.panel;
	}
	public Date getDate() {
		return this.date;
	}
	public int callThread(ArrayList<String> timeArray,String title,String text) {//接受event panel的呼叫，進行時間的計算並呼叫相關函式，以完成通知
		int time1Hour = Integer.parseInt(timeArray.get(0));//活動的開始時間
		int time1Min = Integer.parseInt(timeArray.get(1));
		int time2Hour = Integer.parseInt(timeArray.get(2));//活動的結束時間
		int time2Min = Integer.parseInt(timeArray.get(3));		
		
		int year = this.getDate().getYear();//取得date的日期
		int month = this.getDate().getMonth();
		int date = this.getDate().getDate();
		//System.out.println(month+" "+date+" ");
		Calendar cal = Calendar.getInstance();//比較活動開始時和現在時間，若比現在時間早，則return值發出警訊
		cal.set(Calendar.YEAR, year);
		cal.set(Calendar.MONTH, month-1);
		cal.set(Calendar.DATE, date);
		cal.set(Calendar.HOUR_OF_DAY,time1Hour);
		cal.set(Calendar.MINUTE,time1Min);
		cal.set(Calendar.SECOND, 0);
		long waitTime = (long)((cal.getTimeInMillis()-Calendar.getInstance().getTimeInMillis()));
		
		boolean timePriority = false;//活動開始時間是否比結束時間晚，若較晚，則return值發出警訊
		if(time1Hour < time2Hour)timePriority = true;
		else if(time1Hour == time2Hour) {
			if(time1Min <= time2Min)timePriority = true;
		}
		
		if(timePriority == false){
			return -2;//發出開始時間比結束時間晚的警訊
		}else {
			Thread_runnable th = new Thread_runnable(waitTime,title,text);
			thread = new Thread(th);
			thread.start();//此處會產生一個執行緒，等待waitTime(毫秒)後就會發出通知
			return 1;
		}
			
	}
}
