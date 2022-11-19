package GUI;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import Crawer.SultElements;
import java.awt.*;
import java.util.ArrayList;
import java.util.Map;
public class CurriculumPanel extends JPanel {
    final static int FRAME_WIDTH=600;
    final static int FRAME_HEIGHT=272;
    private SetData setData;
    private Map<String,Integer> map;
    private Map<String,Integer> map2;
    private JTable t;
    ArrayList<String> courseID;
    ArrayList<String> courseName;
    ArrayList<String> teacher;
    ArrayList<String> courseInfo;
    ArrayList<String> courseDay;
    public CurriculumPanel(){
        setData=new SetData();
        map=setData.getCourHourMap();
        map2=setData.getCourDayMap();
        courseID=new ArrayList<>();
        courseName=new ArrayList<>();
        teacher=new ArrayList<>();
        courseInfo=new ArrayList<>();
        courseDay=new ArrayList<>();
        this.setSize(FRAME_WIDTH,FRAME_HEIGHT);
        t = new JTable(16 ,8);
        createTable();
        JScrollPane scrollPane=new JScrollPane(t);
        this.add(scrollPane);
    }
    public void createTable(){
        t.setSize(FRAME_WIDTH,FRAME_HEIGHT);
        t.getTableHeader().setReorderingAllowed(false);
        DefaultTableModel tableModel =new DefaultTableModel();
        tableModel.setRowCount(16);
        tableModel.setColumnCount(8);
        tableModel.setColumnIdentifiers (new Object[]{ "","一","二","三","四","五","六","日"});
        t.setModel(tableModel);
        t.setValueAt("A",0,0);
        t.setValueAt("B",1,0);
        t.setValueAt("1",2,0);
        t.setValueAt("2",3,0);
        t.setValueAt("3",4,0);
        t.setValueAt("4",5,0);
        t.setValueAt("C",6,0);
        t.setValueAt("D",7,0);
        t.setValueAt("5",8,0);
        t.setValueAt("6",9,0);
        t.setValueAt("7",10,0);
        t.setValueAt("8",11,0);
        t.setValueAt("E",12,0);
        t.setValueAt("F",13,0);
        t.setValueAt("G",14,0);
        t.setValueAt("H",15,0);
        t.setRowHeight(20);
        Font font=new Font(Font.DIALOG,Font.PLAIN,10);
        t.setFont(font);
    }
    public SultElements setTable(String pageAsXml){
        SultElements se=new SultElements(pageAsXml);
        courseDay=se.sultCourDay();
        courseID=se.sultCourID();
        courseName=se.sultCourName();
        teacher=se.sultTeacher();
        for(String s:courseName){
            ArrayList<String> courseHour=se.sultCourHour(courseName.indexOf(s));
            for(int j=0;j<16;j++){
                for(String ch:courseHour){
                    if(map.get(ch).equals(j)){
                        for(int k=0;k<7;k++) {
                            if (map2.get(courseDay.get(courseName.indexOf(s))).equals(k)) {
                                String[] name = s.split("　");
                                t.setValueAt(name[0]+"\n",j,k+1);
                            }
                        }
                    }
                }
            }
        }
        t.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        return se;
    }
    public void setTable(ArrayList<String> courseDay,ArrayList<String> courseName,ArrayList<String> teacher,ArrayList<String> courh){
        this.courseDay=courseDay;
        this.teacher=teacher;
        this.courseName=courseName;
        
        for(String s:courseName){
            ArrayList<String> courseHour=sultCourHour(courseName.indexOf(s),courh);
            for(int j=0;j<16;j++){
                for(String ch:courseHour){
                    if(map.get(ch).equals(j)){
                        for(int k=0;k<7;k++) {
                            if (map2.get(courseDay.get(courseName.indexOf(s))).equals(k)) {
                                t.setValueAt(s+"\n",j,k+1);
                            }
                        }
                    }
                }
            }
        }
        t.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
    }
    public ArrayList<String> sultCourHour(int index,ArrayList<String> courh){
        ArrayList<String> ch=new ArrayList<String>();
        String string=courh.get(index);
            for (int i = 0; i < string.length(); i++) {
                ch.add(string.substring(i, i + 1));
        }
        return ch;
    }
}

