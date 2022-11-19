package GUI;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JTextArea;

import DataBase.ExecSQL;
import DataBase.Student;

import javax.swing.JCheckBox;
import java.util.ArrayList;
import java.util.Locale;
import java.awt.GridLayout;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.text.DateFormatSymbols;
import java.awt.event.ActionEvent;
public class DateFrame extends JFrame{
	private static final int FRAME_WIDTH=400;
	private static final int FRAME_HEIGHT = 600;
	private CardLayout card=new CardLayout();
	private JPanel datePanel,overallPanel,eventPanel,buttonPanel;
	private JButton newButton,deleteButton;
	private Date date;
	private Student student;
	private ExecSQL exec;
	private ArrayList<JCheckBox> boxArray;
	private DateFormatSymbols format=new DateFormatSymbols(Locale.US);
	public DateFrame(Date date,Student student) {
		exec=new ExecSQL();
		setSize(FRAME_WIDTH,FRAME_HEIGHT);
		this.setTitle(format.getMonths()[date.getMonth()-1]+" "+date.getLabel());
		this.student=student;
		this.date = date;
		createComp();
		card.show(overallPanel,"Date");
	}
	public void createComp() {
		createOverallPanel();
		this.add(overallPanel);
	}
	public void createOverallPanel() {
		overallPanel=new JPanel();
		overallPanel.setLayout(card);
		boxArray = new ArrayList<JCheckBox>();//宣告勾選欄
		createDatePanel();
	}
	public void createDatePanel() {
		datePanel=new JPanel();
		datePanel.setLayout(new BorderLayout());
		createEventPanel();
		createButtonPanel();
		overallPanel.add(datePanel,"Date");
	}
	public void createEventPanel() {
		eventPanel=new JPanel(new GridLayout(10, 1));
		new ArrayList<JLabel>();
		createEventButton();
		datePanel.add(eventPanel,BorderLayout.CENTER);
	}
	public void createButtonPanel() {
		buttonPanel=new JPanel();
		createNewButton();
		createDeleteButton();
		datePanel.add(buttonPanel,BorderLayout.SOUTH);
	}
	public void createNewButton() {
		newButton=new JButton("New Event");
		class ClickListener implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				Event newEvent=new Event(date,student);
				overallPanel.add(newEvent.getPanel(),"New");
				card.show(overallPanel, "New");
			}
		}
		ActionListener buttonListener=new ClickListener();
		newButton.addActionListener(buttonListener);
		buttonPanel.add(newButton);
	}
	public void createDeleteButton() {
		deleteButton=new JButton("Delete");
		class ClickListener implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				for(int i = date.getEvents().size()-1;i>=0;i--) {
					if(boxArray.get(i).isSelected()) {
						try {
							exec.deleteEvent(student, date.getEvents().get(i));
						} catch (SQLException e1) {
							System.out.println(e1.getErrorCode());
						}
						date.getEvents().remove(i);//將該event刪除
					}
				}
				updateEventPanel();//將未被刪除的event重新繪出
			}
		}
		ActionListener buttonListener=new ClickListener();
		deleteButton.addActionListener(buttonListener);
		buttonPanel.add(deleteButton);
	}
	public void createEventButton() {
		for(Event e:date.getEvents()) {
			JPanel eventP=new JPanel(new FlowLayout(FlowLayout.CENTER));
			JLabel time=new JLabel(e.getTime().get(0)+":"+e.getTime().get(1)+"~"+e.getTime().get(2)+":"+e.getTime().get(3));
			JButton eventButton=new JButton(e.getTitle());
			JLabel tagLabel = new JLabel(e.getTag());
			JCheckBox check = new JCheckBox();//新增勾選欄位
			boxArray.add(check);
			eventP.add(check);
			eventP.add(time);
			eventP.add(eventButton);
			eventP.add(tagLabel);
			eventP.setSize(400, 100);
			eventPanel.add(eventP);
			overallPanel.add(e.getPanel(),e.getTitle());
			class ClickListener implements ActionListener{
				public void actionPerformed(ActionEvent event) {
					card.show(overallPanel,e.getTitle());
				}
			}
			ActionListener buttonListener=new ClickListener();
			eventButton.addActionListener(buttonListener);
		}
	}
	public void addEventButton(Event e) {
		JPanel eventP=new JPanel(new FlowLayout(FlowLayout.CENTER));
		JLabel time=new JLabel(e.getTime().get(0)+":"+e.getTime().get(1)+"~"+e.getTime().get(2)+":"+e.getTime().get(3));
		JButton eventButton=new JButton(e.getTitle());
		JLabel tagLabel = new JLabel(e.getTag());
		JCheckBox check = new JCheckBox();//新增勾選欄位
		boxArray.add(check);
		eventP.add(check);
		eventP.add(time);
		eventP.add(eventButton);
		eventP.add(tagLabel);
		eventButton.setSize(400, 100);
		eventPanel.add(eventP);
		overallPanel.add(e.getPanel(),e.getTitle());
		class ClickListener implements ActionListener{
			public void actionPerformed(ActionEvent event) {
				card.show(overallPanel,e.getTitle());
			}
		}
		ActionListener buttonListener=new ClickListener();
		eventButton.addActionListener(buttonListener);
		
		
	}
	public void backToDatePanel() {
		updateEventPanel();
		card.show(overallPanel,"Date");
	}
	public void updateEventPanel() {//更新EventPanel:將eventPanel內的所有欄位都刪除，並從data.getEvents()的數據，再製出新的欄位
		boxArray.removeAll(boxArray);//移除所有的勾選按鈕
		eventPanel.removeAll();//移除所有的event欄位
		for(int i = 0; i< date.getEvents().size();i++) {
			addEventButton(date.getEvents().get(i));
		}
		eventPanel.revalidate();//重新繪製
		eventPanel.repaint();
	}
}
