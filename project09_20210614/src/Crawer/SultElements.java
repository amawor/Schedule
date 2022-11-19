package Crawer;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.util.ArrayList;
public class SultElements {
    private String pageHTML;
    private Document document;
    private ArrayList<org.jsoup.select.Elements> elementsList;
    private ArrayList<String> courseID;
    private ArrayList<String> courseName;
    private ArrayList<String> teacher;
    private ArrayList<String> courseInfo;
    private ArrayList<String> courseHour;
    private ArrayList<String> courseDay;
    public SultElements(String pageHTML){
        this.pageHTML=pageHTML;
        courseID=new ArrayList<>();
        courseName=new ArrayList<>();
        teacher=new ArrayList<>();
        courseInfo=new ArrayList<>();
        document= Jsoup.parse(pageHTML);
        courseDay=new ArrayList<>();
        sultHTML();
    }
    public void sultHTML(){
        Elements table = document.select("div.maintain_profile_content_table>div>table>tbody");
        Elements result= table.get(0).getAllElements();
        Elements courses=new Elements();
        for(int i=1;i<result.size();i+=24) {
            courses.add(result.get(i));
        }
        ArrayList<Elements> elementsArrayList=sultElements(courses);
        for(Elements e:elementsArrayList){
            courseID.add(e.get(1).text());
            courseName.add(e.get(3).text());
            teacher.add(e.get(5).text());
            courseInfo.add(e.get(6).text());
        }
    }
    public ArrayList<org.jsoup.select.Elements> sultElements(Elements courses){
        elementsList=new ArrayList<>();
        for(Element e:courses){
            org.jsoup.select.Elements element=e.getAllElements();
            elementsList.add(element);
        }
        return elementsList;
    }
    public ArrayList<String> sultCourID(){
        return courseID;
    }
    public ArrayList<String> sultCourName(){
        ArrayList<String> name=new ArrayList<String>();
        for(String s:courseName){
            String[] string = s.split("¡@");
            name.add(string[0]);
        }
        return name;
    }
    public ArrayList<String> sultTeacher(){
        return teacher;
    }
    public ArrayList<String> sultCourDay(){
        for(String s:courseInfo) {
            String[] string = s.split(" / ");
            courseDay.add(string[0].substring(0,1));
        }
        return courseDay;
    }
    public ArrayList<String> sultCourHour(int index){
        courseHour=new ArrayList<String>();
        String[] string = courseInfo.get(index).split(" / ");
            for (int i = 1; i < string[0].length(); i++) {
                courseHour.add(string[0].substring(i, i + 1));
        }
        return courseHour;
    }
}
