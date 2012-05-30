import java.util.Random;
import java.awt.Point;

public class Schelling{
	public static enum type {X,O};
  private int N, W;
  private Random rand;
  type[][] torus;
  int[][] biases;
  ArrayListUnhappy uhx, uho;

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
      uhx = new ArrayListUnhappy();
      uho = new ArrayListUnhappy();

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

  //Compute biases for the initial configuration  
  private void initBiases(){
    for (int i = 0; i < N; i++){
      for (int j = 0; j < N; j++){
        biases[i][j] = 0;
        for (int x = -W; x <= W; x++){
          for (int y = -W; y <= W; y++){
            if (torus[mod(i+x,N)][mod(j+y,N)] == type.X)
              biases[i][j]++;
            else
              biases[i][j]--;
          }
        }
        if (biases[i][j] < 0 && torus[i][j] == type.X)
            uhx.add(i,j);
        else if (biases[i][j] > 0 && torus[i][j] == type.O)
            uho.add(i,j);
      }
    }
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

    int bias, i, j;
    for (int mi = xi-W; mi <= xi+W; mi++){
      i = mod(mi,N);
      for (int mj = xj-W; mj <= xj+W; mj++){
        j = mod(mj,N);
        bias = 0;
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

    for (int mi = oi-W; mi <= oi+W; mi++){
      i = mod(mi,N);
      for (int mj = oj-W; mj <= oj+W; mj++){
        j = mod(mj,N);
        bias = 0;
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

  public static void main(String args[]){
    int n = 100;
    int w = 3;
    int numSteps = 0;
    Schelling torus = new Schelling(n,w);
    while (!torus.isDone()){
      torus.singleStep();
      numSteps++;
      System.out.println(numSteps);
    }
  }
}
