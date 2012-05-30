import java.util.ArrayList;
import java.util.Random;
import java.awt.Point;

public class ArrayListUnhappy implements UnhappyCollection{
  private ArrayList<Point> uh;
  private Random rand;

  public ArrayListUnhappy(){
    uh = new ArrayList<Point>();
    rand = new Random();
  }

  public void remove(int x, int y){
    int index = uh.indexOf(new Point(x,y));
    uh.remove(index);
  }

  public void add(int x, int y){
    uh.add(new Point(x,y));
  }

  public Point getRandom(){
    int index = rand.nextInt(uh.size());
    return uh.get(index);
  }

  public boolean isEmpty(){
    return uh.isEmpty();
  }

  public String toString(){
    return "The unhappy elements of this type are " + uh.toString();
  }
}
