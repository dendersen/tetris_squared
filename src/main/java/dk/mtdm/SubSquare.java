package dk.mtdm;

import processing.core.PGraphics;

public class SubSquare {
  protected Shape form;
  protected int x,y;
  protected boolean[][] build;
  public SubSquare(Shape form,int x,int y) {
    this.form = form;
    this.build = form.build;
    this.x = x;
    this.y = y;
  }
  public void draw(PGraphics g, int indexWidth) {
    g.fill(form.color.getRed(),form.color.getGreen(),form.color.getBlue());
    g.strokeWeight(3);
    g.stroke(Math.max(form.color.getRed()-20,0),Math.max(form.color.getGreen()-20,0),Math.max(form.color.getBlue()-20,0));
    for (int i = 0; i < build.length; i++) {
      for (int j = 0; j < build[i].length; j++) {
        if(build[i][j]){
          g.rect(indexWidth * (x+i)+2,indexWidth * (y+j)+2,indexWidth-4,indexWidth-4);
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
