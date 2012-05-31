import java.util.Random;
import java.awt.Point;

public class Schelling{
  private static enum type {X,O};
  private int N, W;
  private Random rand;
  type[][] torus;
  int[][] biases;
  UnhappyCollection uhx, uho;

  //Java's mod sucks
  private int mod(int a, int b){
    int c = a % b;
    return (c >= 0) ? c : c+b;
  }

  public Schelling(int n, int w){
    N = n;
    W = w;
    rand = new Random();
    torus = new type[n][n];
    biases = new int[n][n];
    uhx = new HashList();
    uho = new HashList();
    initTorus();
    initBiases();
  }

  //Populates the torus randomly
  private void initTorus(){
    for (int i = 0; i < N; i++){
      for (int j = 0; j < N; j++){
        if (rand.nextBoolean())
          torus[i][j] = type.X;
        else
          torus[i][j] = type.O;
      }
    }
  }

  private void recalcBiases(int minX, int maxX, int minY, int maxY){
    for (int mi = minX; mi < maxX; mi++){
      int i = mod(mi,N);
      for (int mj = minY; mj < maxY; mj++){
        int j = mod(mj,N);
        int bias = 0;
        for (int x = -W; x <= W; x++){
          for (int y = -W; y <= W; y++){
            if (torus[mod(i+x,N)][mod(j+y,N)] == type.X)
              bias++;
            else
              bias--;
          }
        }
        if (biases[i][j] < 0 && torus[i][j] == type.X && bias >= 0)
          uhx.remove(i,j);
        else if (biases[i][j] > 0 && torus[i][j] == type.O && bias <= 0)
          uho.remove(i,j);
        else if (biases[i][j] >= 0 && torus[i][j] == type.X && bias < 0)
          uhx.add(i,j);
        else if (biases[i][j] <= 0 && torus[i][j] == type.O && bias > 0) 
          uho.add(i,j);
        biases[i][j] = bias;
      }
    }
  }

  //Compute biases for the initial configuration  
  private void initBiases(){
    recalcBiases(0,N,0,N);
  }

  private void singleStep(){
    Point xToSwap = uhx.getRandom();
    Point oToSwap = uho.getRandom();
    int xi = (int) xToSwap.getX();
    int xj = (int) xToSwap.getY();
    int oi = (int) oToSwap.getX();
    int oj = (int) oToSwap.getY();

    torus[xi][xj] = type.O;
    torus[oi][oj] = type.X;
    uhx.remove(xi,xj);
    uho.remove(oi,oj);

    recalcBiases(xi-W,xi+W,xj-W,xj+W);
    recalcBiases(oi-W,oi+W,oj-W,oj+W);
  }

  private boolean isDone(){
    return uhx.isEmpty() || uho.isEmpty();
  }

  private void printTorus(){
    for (int i = 0; i < N; i++){
      for (int j = 0; j < N; j++){
        System.out.print(torus[i][j]);
      }
      System.out.print("\n");
    }
    System.out.print("\n");
  }

  private void printUnhappies(){
    System.out.println(uhx);
    System.out.println(uho);
  }

  private void printNumUnhappies(){
    int x = uhx.size();
    int o = uho.size();
    System.out.println("x: " + x + ", o: " + o);
  }

  public static void main(String args[]){
    int n = 1000;
    int w = 1;
    int numSteps = 0;
    Schelling torus = new Schelling(n,w);
    while (!torus.isDone()){
      torus.singleStep();
      numSteps++;
      System.out.println(numSteps);
      torus.printNumUnhappies();
    }
  }
}
