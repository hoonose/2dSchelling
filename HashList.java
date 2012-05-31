import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.Random;
import java.awt.Point;

public class HashList implements UnhappyCollection{
  private List<Point> uhList;
  private Map<Point,Integer> uhMap;
  private Random rand;

  public HashList(){
    uhList = new ArrayList<Point>();
    uhMap = new HashMap<Point,Integer>();
    rand = new Random();    
  }

  public void remove(int x, int y){
    Point p = new Point(x,y);
    Integer pos = uhMap.remove(p);
    Point p2 = uhList.remove(uhList.size()-1);
    if (pos != uhList.size()){
      uhMap.put(p2,pos);
      uhList.set(pos,p2);
    }
  }

  public void add(int x, int y){
    Point p = new Point(x,y);
    uhMap.put(p,uhList.size());
    uhList.add(p);
  }

  public Point getRandom(){
    int index = rand.nextInt(uhList.size());
    return uhList.get(index);
  }

  public int size(){
    return uhList.size();
  }

  public boolean isEmpty(){
    return uhList.isEmpty();
  }

  public String toString(){
    return "The unhappy elements of this type are " + uhList.toString();
  }  
}
