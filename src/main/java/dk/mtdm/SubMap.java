package dk.mtdm;

import processing.core.PGraphics;

public class SubMap extends SubSquare {
  private Color[][] map;
  public SubMap(int x, int y, Color[][] map) {
    super(Shape.Z, x, y);
    //clean sides short
    int left = 0, right = 0, top = 0, bottom = 0;
    for (int i = 0; i < map.length; i++) {
      for (int j = 0; j < map[i].length; j++) {
        if(map[i][j] != null){
          left = i;
          break;
        }
      }
      if(left != 0) break;
    }
    for (int i = 0; i < map.length; i++) {
      for (int j = map[i].length - 1; j >= 0; j--) {
        if(map[i][j] != null){
          right = i;
          break;
        }
      }
      if(right != 0) break;
    }
    for (int i = 0; i < map[0].length; i++) {
      for (int j = 0; j < map.length; j++) {
        if(map[j][i] != null){
          top = i;
          break;
        }
      }
      if(top != 0) break;
    }
    for (int i = 0; i < map[0].length; i++) {
      for (int j = map.length - 1; j < 0; j++) {
        if(map[j][i] != null){
          bottom = i;
          break;
        }
      }
      if(bottom != 0) break;
    }
    this.map = new Color[right - left][bottom - top];
    this.build = new boolean[right - left][bottom - top];
    for (int i = left; i < map.length; i++) {
      for (int j = top; j < bottom; j++) {
        this.map[i-left][j-top] = map[i][j];
        this.build[i-left][j-top] = map[i][j] != null;
      }
    }
  }
  @Override
  public void draw(PGraphics g, int indexWidth){
    Color previous = null;
    for (int i = 0; i < build.length; i++) {
      for (int j = 0; j < build[i].length; j++) {
        if(build[i][j]){
          if(previous == map[i][j]){
            g.fill(map[i][j].getRed(),map[i][j].getGreen(),map[i][j].getBlue());
            g.strokeWeight(3);
            g.stroke(Math.max(map[i][j].getRed()-20,0),Math.max(map[i][j].getGreen()-20,0),Math.max(map[i][j].getBlue()-20,0));
          }
          g.rect(indexWidth * (x + i) +2 ,indexWidth * (y + j) + 2,indexWidth - 4,indexWidth - 4);
        }
      }
    }
  }
}
