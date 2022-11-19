package GUI;
import java.util.HashMap;
import java.util.Map;
public class SetData {
    private Map<String,Integer> map,map2;
    public SetData(){
        map=new HashMap<>();
        map2=new HashMap<>();
        setCourHourMap();
        setCourDayMap();
    }
    public Map<String,Integer> getCourDayMap(){
        return map2;
    }
    public Map<String,Integer> getCourHourMap(){
        return map;
    }
    public void setCourDayMap(){
        map2.put("一",0);
        map2.put("二",1);
        map2.put("三",2);
        map2.put("四",3);
        map2.put("五",4);
        map2.put("六",5);
        map2.put("日",6);
    }
    public void setCourHourMap(){
        map.put("A",0);
        map.put("B",1);
        map.put("1",2);
        map.put("2",3);
        map.put("3",4);
        map.put("4",5);
        map.put("C",6);
        map.put("D",7);
        map.put("5",8);
        map.put("6",9);
        map.put("7",10);
        map.put("8",11);
        map.put("E",12);
        map.put("F",13);
        map.put("G",14);
        map.put("H",15);
    }
}
