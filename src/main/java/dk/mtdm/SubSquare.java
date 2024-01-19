package dk.mtdm;

import processing.core.PGraphics;

public class SubSquare {
  Shape form;
  int x,y;
  boolean[][] build;
  public SubSquare(Shape form,int x,int y) {
    this.form = form;
    build = form.build;
    this.x = x;
    this.y = y;
  }

  public void draw(PGraphics g, int indexWidth) {
    for (int i = 0; i < build.length; i++) {
      for (int j = 0; j < build[i].length; j++) {
        if(build[i][j]){
          g.fill(form.color.getRed(),form.color.getGreen(),form.color.getBlue());
          g.stroke(Math.max(form.color.getRed()-20,0),Math.max(form.color.getGreen()-20,0),Math.max(form.color.getBlue()-20,0));
          g.rect(indexWidth * (x+i),indexWidth * (y+j),indexWidth,indexWidth);
        }
      }
    }
  }
  public void gravity() {
    this.y++;
  }
  public void Place(Color[][] map) {
    for (int i = 0; i < build.length; i++) {
      for (int j = 0; j < build[i].length; j++) {
        if(!build[i][j]){
          continue;
        }
        if(map[x+i][y+j] != null){
          throw new RuntimeException("Piece placed on top of another piece");
        }
        map[x+i][y+j] = form.color;
      }
    }
  }

  public void moveLeft(Color[][] map) {
    this.x--;
    if(collides(map)){
      this.x++;
    }
  }
  public void moveRight(Color[][] map) {
    this.x++;
    if(collides(map)){
      this.x--;
    }
  }
  public void rotateRight(Color[][] map){
    rotateRight(map, false);
  }
  public void rotateRight(Color[][] map, boolean loop){
    boolean[][] temp = new boolean[build[0].length][build.length];
    for (int i = 0; i < build.length; i++) {
      for (int j = 0; j < build[i].length; j++) {
        temp[j][build.length-i-1] = build[i][j];
      }
    }
    build = temp;
    if(collides(map) && !loop){
      rotateLeft(map, true);
    }
  }
  public void rotateLeft(Color[][] map){
    rotateLeft(map, false);
  }
  private void rotateLeft(Color[][] map, boolean loop){
    boolean[][] temp = new boolean[build[0].length][build.length];
    for (int i = 0; i < build.length; i++) {
      for (int j = 0; j < build[i].length; j++) {
        temp[build[i].length-j-1][i] = build[i][j];
      }
    }
    build = temp;
    if(collides(map) && !loop){
      rotateRight(map, true);
    }
  }
  public int getHeight() {
    return build[0].length;
  }

  public boolean willCollide(Color[][] map) {
    y++;
    boolean collide = collides(map);
    y--;
    return collide;
  }

  public boolean collides(Color[][] map) {
    for (int i = 0; i < build.length; i++) {
      for (int j = 0; j < build[i].length; j++) {
        if(!build[i][j]){
          continue;
        }
        if(x+i < 0 || x+i >= map.length || map[x+i][y+j] != null){
          return true;
        }
      }
    }
    return false;
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
