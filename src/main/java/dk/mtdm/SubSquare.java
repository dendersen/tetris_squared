package dk.mtdm;

import processing.core.PGraphics;

public class SubSquare {
  Shape form;
  int x,y;
  
  public SubSquare(Shape form,int x,int y) {
    this.form = form;
    this.x = x;
    this.y = y;
  }

  public void draw(PGraphics g, int indexWidth) {
    for (int i = 0; i < form.build.length; i++) {
      for (int j = 0; j < form.build[i].length; j++) {
        if(form.build[i][j]){
          g.fill(form.color.getRed(),form.color.getGreen(),form.color.getBlue());
          g.stroke(Math.max(form.color.getRed()-20,0),Math.max(form.color.getGreen()-20,0),Math.max(form.color.getBlue()-20,0));
          g.rect(indexWidth * (x+j),indexWidth * (y+i),indexWidth,indexWidth);
        }
      }
    }
  }
  public void gravity() {
    this.y++;
  }
  public void Place(Color[][] map) {
    for (int i = 0; i < form.build.length; i++) {
      for (int j = 0; j < form.build[i].length; j++) {
        if(!form.build[i][j]){
          continue;
        }
        if(map[y+i][x+j] != null){
          throw new RuntimeException("Piece placed on top of another piece");
        }
        map[y+i][x+j] = form.color;
      }
    }
  }

}
enum Color{
  yellow(255,255,0),
  lightBlue(100,100,255),
  red(255,0,0),
  green(0,255,0),
  orange(255,100,0),
  darkBlue(0,0,200),
  purple(255,0,255);
  
  private final int r,g,b;
  
  private Color(int r,int g,int b){
    this.r = r;
    this.g = g;
    this.b = b;
  };
  public int getRed() {
    return r;
  }
  public int getGreen() {
    return g;
  }
  public int getBlue() {
    return b;
  }
}
enum Shape{
  O(new boolean[][] {
                    {true ,true},
                    {true ,true}
                  },Color.yellow),
  I(new boolean[][] {
                    {true},
                    {true},
                    {true},
                    {true}
                  },Color.lightBlue),
  S(new boolean[][] {
                    {true ,false},
                    {true ,true },
                    {false,true }
                  },Color.red),
  Z(new boolean[][] {
                    {false,true },
                    {true ,true },
                    {true ,false}
                  },Color.green),
  L(new boolean[][] {
                    {true ,false},
                    {true ,false},
                    {true ,true }
                  },Color.orange),
  J(new boolean[][] {
                    {false,true },
                    {false,true },
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
