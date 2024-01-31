package dk.mtdm;

import processing.core.PApplet;

public class Sketch extends PApplet {
  private int worldHeight = 20, worldWidth = 10;
  private int gravityTimer = 0;
  private final int indexWidth = 20;
  private Color[][] SubMap = new Color[worldWidth][worldHeight];
  private Color[][] topMap = new Color[worldWidth*3][worldHeight*3];
  private boolean makePiece = true;
  private SubSquare activePiece;
  private PieceGenerator pg = new PieceGenerator();
  
  public void draw(){
    Color[][] map = makePiece ? SubMap : topMap;
    background(255);
    for (int x = 0; x < map.length; x++) {
      for (int y = 0; y < map[x].length; y++) {
        if(map[x][y] == null){
          continue;
        }
        g.fill(map[x][y].getRed(), map[x][y].getGreen(), map[x][y].getBlue());
        g.stroke(Math.max(map[x][y].getRed()-20, 0), Math.max(map[x][y].getGreen()-20, 0), Math.max(map[x][y].getBlue()-20, 0));
        g.rect(indexWidth * x, indexWidth * y, indexWidth, indexWidth);
      }
    }
    
    if(makePiece){
      creator();
    }else{
      mover();
    }
    drawGrid(map);
  }
  public void drawGrid(Color[][] map){
    for (int x = 0; x < map.length; x++) {
      for (int y = 0; y < map[x].length; y++) {
        stroke(0, 0, 0, 20);
        strokeWeight(1);
        line(0, y*indexWidth, map.length*indexWidth, y*indexWidth);
        line(x*indexWidth, 0, x*indexWidth, map[0].length*indexWidth);
      }
    }
  }
  
  public void clearLines(Color[][] map){
    for (int y = 0; y < map[0].length; y++) {
      boolean clear = map[0][y] != null;
      for (int x = 1; x < map.length && clear; x++) {
        clear &= map[x][y] != null;
      }
      if(clear){
        for (int i = 0; i < map.length; i++) {
          for (int j = y; j > 0; j--) {
            map[i][j] = map[i][j-1];
          }
          map[i][0] = null;
        }
      }
    }
  }
  
  @Override
  public void settings() {
    size(worldWidth*indexWidth * 3, worldHeight*indexWidth *  3);
  }
  
  public void keyPressed(){
    if(key != CODED){
      return;
    }
    gravityTimer = 1;
    if(keyCode == LEFT){
      activePiece.moveLeft(makePiece ? SubMap : topMap);
    }
    if(keyCode == RIGHT){
      activePiece.moveRight(makePiece ? SubMap : topMap);
    }
    if(keyCode == UP){
      activePiece.rotateLeft(makePiece ? SubMap : topMap);
    }
    if(keyCode == DOWN){
      try{
        aplyGravity(makePiece ? SubMap : topMap);
      }catch(RuntimeException e){
        if(makePiece){
          SubMap = newSubmap(pg.popPiece());
          makePiece ^= true;
          settings();
        }else{
          throw e;
        }
      }
    }
  }
  
  private Color[][] newSubmap(SubSquare popPiece) {
    Color[][] temp = new Color[popPiece.build.length*5][popPiece.build[0].length*5];
    return temp;
  }
  private void creator() {
    if(activePiece == null){
      activePiece = pg.popPiece();
      activePiece.draw(g, indexWidth);
      return;
    }
    if(gravityTimer == 0){
      try{
        aplyGravity(SubMap);
      }catch(RuntimeException e){
        makePiece = false;
        activePiece = new SubMap(10, 10, SubMap);
        return;
      }
    }
    if(activePiece != null){
      activePiece.draw(g, indexWidth);
    }
    incrimentGravity();
  }
  
  private void aplyGravity(Color[][] map) {
    if(activePiece == null){
      return;
    }
    if(activePiece.y+activePiece.getHeight()+1 > worldHeight || activePiece.willCollide(map)){
      activePiece.Place(map);
      clearLines(map);
      activePiece.draw(g, indexWidth);
      activePiece = null;
      if(!makePiece){
        makePiece = true;
      }
      return;
    }
    activePiece.gravity();
  }
  
  private void incrimentGravity() {
    gravityTimer++;
    gravityTimer %= 30;
  }
  
  private void mover() {
    if(gravityTimer == 0){
      try{
        aplyGravity(topMap);
      }catch(RuntimeException e){
        System.out.println("Game Over");
        noLoop();
      }
    }
    if(activePiece != null){
      activePiece.draw(g, indexWidth);
    }
    incrimentGravity();
  }
}
