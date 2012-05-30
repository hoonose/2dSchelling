import java.awt.Point;
interface UnhappyCollection{
  public void remove(int x, int y);
  public void add(int x, int y);
  public Point getRandom();
  public boolean isEmpty();
  public String toString();
}
