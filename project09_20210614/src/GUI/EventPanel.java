package GUI;

import DataBase.Student;
import DataBase.ExecSQL;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
public class EventPanel extends JPanel{
	private static final int FIELD_WIDTH=20;
	private Event event;
	private String title,note,tag;
	private JTextField titleField;
	private JLabel titleLabel,startLabel,colonLabel_Start,endLabel,colonLabel_End;
	private JPanel titlePanel,startPanel,endPanel,tagPanel,infoPanel;
	private JTextArea noteArea;
	private JButton saveButton;
	private JComboBox tagCombo;
	private ArrayList<String> tags;
	private ArrayList<String> timeArray;
	private ArrayList<JComboBox> timeBox;
	private DateFrame frame;
	private Student student;
	private ExecSQL exec;
	public EventPanel(Event event,DateFrame frame,Student student) {
		this.setLayout(new BorderLayout());
		this.student=student;
		exec=new ExecSQL();
		this.frame=frame;
		this.event=event;
		this.title=event.getTitle();
		this.note=event.getNote();
		tags=new ArrayList<String>();
		setTags();
		this.timeArray=event.getTime();
		createInfoPanel();
		createNoteArea();
		createTag();
	}
	public void setTags() {
		this.tags=exec.getCourse(student.getID());
	}
	public void createInfoPanel() {
		GridBagConstraints grid=new GridBagConstraints();
		infoPanel=new JPanel();
		infoPanel.setLayout(new GridBagLayout());
		grid.gridx=2;
		grid.gridy=0;
		grid.gridwidth=3;
		grid.gridheight=1;
		createTitlePanel(grid);
		grid.gridx=6;
		grid.gridwidth=1;
		createSaveButton(grid);
		grid.gridx=1;
		grid.gridy=1;
		grid.gridwidth=5;
		createTimePanel(grid);
		this.add(infoPanel,BorderLayout.NORTH);
	}
	public void createTitlePanel(GridBagConstraints grid) {
		titlePanel=new JPanel();
		titleLabel=new JLabel("Title:");
		titleField=new JTextField(title,FIELD_WIDTH);
		titlePanel.add(titleLabel);
		titlePanel.add(titleField);
		infoPanel.add(titlePanel,grid);
	}
	public void createTimePanel(GridBagConstraints grid) {
		timeBox = new ArrayList<JComboBox>();
		startLabel=new JLabel("From:");
		colonLabel_Start=new JLabel(":");
		for(int i = 0;i<4;i++) {
			timeBox.add(new JComboBox());
		}
		for(int i = 0;i<24;i++) {
			if(i<10) {
				timeBox.get(0).addItem("0"+String.valueOf(i));		
			}else {
				timeBox.get(0).addItem(String.valueOf(i));
			}
		}
		for(int i = 0;i<12;i++) {
			if(i<2) {
				timeBox.get(1).addItem("0"+String.valueOf(i*5));		
			}else {
				timeBox.get(1).addItem(String.valueOf(i*5));
			}
			
		}
		startPanel= new JPanel();
		startPanel.add(startLabel);
		startPanel.add(timeBox.get(0));
		startPanel.add(colonLabel_Start);
		startPanel.add(timeBox.get(1));
		endLabel=new JLabel("T o  :");
		colonLabel_End=new JLabel(":");
		for(int i = 0;i<24;i++) {
			if(i<10) {
				timeBox.get(2).addItem("0"+String.valueOf(i));		
			}else {
				timeBox.get(2).addItem(String.valueOf(i));
			}		
		}
		for(int i = 0;i<12;i++) {
			if(i<2) {
				timeBox.get(3).addItem("0"+String.valueOf(i*5));		
			}else {
				timeBox.get(3).addItem(String.valueOf(i*5));
			}
		}
		endPanel=new JPanel();
		endPanel.add(endLabel);
		endPanel.add(timeBox.get(2));
		endPanel.add(colonLabel_End);
		endPanel.add(timeBox.get(3));
		for(int i = 0;i<4;i++) {
			timeBox.get(i).setSelectedItem(String.valueOf(event.getTime().get(i)));
		}
		infoPanel.add(startPanel,grid);
		grid.gridy=2;
		infoPanel.add(endPanel,grid);
	}
	public void createNoteArea() {
		noteArea=new JTextArea(note,20,30);
		noteArea.setSize(400,300);
		add(noteArea,BorderLayout.CENTER);
	}
	public void createTag() {
		tagCombo=new JComboBox();
		for(String t:tags) {
			tagCombo.addItem(t);
		}
		tagCombo.setSelectedItem(tag);
		add(tagCombo,BorderLayout.SOUTH);
		tagCombo.setSelectedItem(event.getTag());
	}
	public void createSaveButton(GridBagConstraints grid) {
		saveButton=new JButton("Save");
		infoPanel.add(saveButton,grid);
		class SaveListener implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				title=titleField.getText();
				tag=(String) tagCombo.getSelectedItem();
				note=noteArea.getText();
				try {
					exec.deleteEvent(student, event);
				} catch (SQLException e2) {
					System.out.println(e2.getErrorCode());
					System.out.println(e2.getMessage());
				}
				for(int i=0;i<4;i++) {
					timeArray.set(i,(String)timeBox.get(i).getSelectedItem());
				}
				int threadInt = event.callThread(timeArray,title,note);//呼叫通知
				if(threadInt == -2) {//發出開始時間比結束時間晚的警訊
					JOptionPane.showMessageDialog(frame, "The start time should early than the end time.");
					return;
				}
				event.save(title, tag, note,timeArray);
				try {
					exec.addEvent(student, event);
				} catch (SQLException e1) {
					System.out.println(e1.getErrorCode());
					System.out.println(e1.getMessage());
				}
				if(!event.getDate().setEvent(event)) {
					frame.addEventButton(event);
				}
				
				frame.backToDatePanel();
			}
		}
		ActionListener save=new SaveListener();
		saveButton.addActionListener(save);
	}
}
