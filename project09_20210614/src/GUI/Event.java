package GUI;

import java.util.ArrayList;
import java.util.Calendar;

import javax.swing.JPanel;

import DataBase.Student;
import notification.Thread_runnable;
public class Event {
	private String title;//�ƥ�W��
	private String note;//�ƥ�Ƶ�
	private EventPanel panel;
	private ArrayList<String> timeArray;//�ƥ󪺮ɶ�
	private String tag;//�ƥ�����tag
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
	public int callThread(ArrayList<String> timeArray,String title,String text) {//����event panel���I�s�A�i��ɶ����p��éI�s�����禡�A�H�����q��
		int time1Hour = Integer.parseInt(timeArray.get(0));//���ʪ��}�l�ɶ�
		int time1Min = Integer.parseInt(timeArray.get(1));
		int time2Hour = Integer.parseInt(timeArray.get(2));//���ʪ������ɶ�
		int time2Min = Integer.parseInt(timeArray.get(3));		
		
		int year = this.getDate().getYear();//���odate�����
		int month = this.getDate().getMonth();
		int date = this.getDate().getDate();
		//System.out.println(month+" "+date+" ");
		Calendar cal = Calendar.getInstance();//������ʶ}�l�ɩM�{�b�ɶ��A�Y��{�b�ɶ����A�hreturn�ȵo�Xĵ�T
		cal.set(Calendar.YEAR, year);
		cal.set(Calendar.MONTH, month-1);
		cal.set(Calendar.DATE, date);
		cal.set(Calendar.HOUR_OF_DAY,time1Hour);
		cal.set(Calendar.MINUTE,time1Min);
		cal.set(Calendar.SECOND, 0);
		long waitTime = (long)((cal.getTimeInMillis()-Calendar.getInstance().getTimeInMillis()));
		
		boolean timePriority = false;//���ʶ}�l�ɶ��O�_�񵲧��ɶ��ߡA�Y���ߡA�hreturn�ȵo�Xĵ�T
		if(time1Hour < time2Hour)timePriority = true;
		else if(time1Hour == time2Hour) {
			if(time1Min <= time2Min)timePriority = true;
		}
		
		if(timePriority == false){
			return -2;//�o�X�}�l�ɶ��񵲧��ɶ��ߪ�ĵ�T
		}else {
			Thread_runnable th = new Thread_runnable(waitTime,title,text);
			thread = new Thread(th);
			thread.start();//���B�|���ͤ@�Ӱ�����A����waitTime(�@��)��N�|�o�X�q��
			return 1;
		}
			
	}
}
