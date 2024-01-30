package dk.mtdm;

public enum Shape{
  O(new boolean[][] {
                    {true ,true},
                    {true ,true}
                  },Color.yellow),
  I(new boolean[][] {
                    {true,true,true,true}
                  },Color.lightBlue),
  S(new boolean[][] {
                    {false,true },
                    {true ,true },
                    {true ,false}
                  },Color.red),
  Z(new boolean[][] {
                    {true ,false},
                    {true ,true },
                    {false,true }
                  },Color.green),
  L(new boolean[][] {
                    
                    {false,true },
                    {false,true },
                    {true ,true }
                  },Color.orange),
  J(new boolean[][] {
                    {true ,false},
                    {true ,false},
                    {true ,true }
                  },Color.darkBlue),
  T(new boolean[][] {
                    {true ,false},
                    {true ,true },
                    {true ,false}
                  },Color.purple);
  final public boolean[][] build;
  final public Color color;
  private Shape(boolean[][] build, Color color){
    this.build = build;
    this.color = color;
  }
}
