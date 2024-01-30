package dk.mtdm;

import processing.core.PApplet;

public class Sketch extends PApplet {
  int worldHeight = 20, worldWidth = 10;
  private int gravityTimer = 0;
  final int indexWidth = 20;
  Color[][] SubMap = new Color[worldWidth][worldHeight];
  Color[][] topMap = new Color[worldWidth*3][worldHeight*3];
  boolean[][] lockMap;
  boolean makePiece = true;
  SubSquare activePiece;
  PieceGenerator pg = new PieceGenerator();
  
  public void draw(){
    Color[][] map = makePiece ? SubMap : topMap;
    clearLines(map);
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
        line(0, y*indexWidth, worldWidth*indexWidth, y*indexWidth);
        line(x*indexWidth, 0, x*indexWidth, worldHeight*indexWidth);
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
    size(worldWidth*indexWidth * (makePiece ? 1 : 3),worldHeight*indexWidth * (makePiece ? 1 : 3));
  }
  
  public void keyPressed(){
    if(key != CODED){
      return;
    }
    gravityTimer = 1;
    if(keyCode == LEFT){
      activePiece.moveLeft(SubMap);
    }
    if(keyCode == RIGHT){
      activePiece.moveRight(SubMap);
    }
    if(keyCode == UP){
      activePiece.rotateLeft(SubMap);
    }
    if(keyCode == DOWN){
      try{
        aplyGravity(makePiece ? SubMap : topMap);
      }catch(RuntimeException e){
        if(makePiece){
          SubMap = null;
          makePiece = false;
          settings();
        }else{
          throw e;
        }
      }
    }
  }
  
  private void creator() {
    if(activePiece == null){
      activePiece = pg.popPiece();
      activePiece.draw(g, indexWidth);
      return;
    }
    if(gravityTimer == 0){
      aplyGravity(SubMap);
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
      activePiece.draw(g, indexWidth);
      activePiece = null;
      makePiece = true;
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
      aplyGravity(topMap);
    }
    if(activePiece != null){
      activePiece.draw(g, indexWidth);
    }
    incrimentGravity();
  }
}
