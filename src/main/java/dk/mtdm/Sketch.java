package dk.mtdm;

import processing.core.PApplet;

public class Sketch extends PApplet {
  int worldHeight = 20, worldWidth = 10;
  private int gravityTimer = 0;
  final int indexWidth = 20;
  Color[][] SubMap = new Color[worldWidth][worldHeight];
  Color[][] topMap;
  boolean[][] lockMap;
  boolean makePiece = true;
  SubSquare activePiece;
  PieceGenerator pg = new PieceGenerator();

  public void draw(){
    background(255);
    for (int x = 0; x < SubMap.length; x++) {
      for (int y = 0; y < SubMap[x].length; y++) {
        if(SubMap[x][y] == null){
          continue;
        }
        g.fill(SubMap[x][y].getRed(), SubMap[x][y].getGreen(), SubMap[x][y].getBlue());
        g.stroke(Math.max(SubMap[x][y].getRed()-20, 0), Math.max(SubMap[x][y].getGreen()-20, 0), Math.max(SubMap[x][y].getBlue()-20, 0));
        g.rect(indexWidth * x, indexWidth * y, indexWidth, indexWidth);
      }
    }

    if(makePiece){
      creator();
    }else{
      mover();
    }
    for (int x = 0; x < SubMap.length; x++) {
      for (int y = 0; y < SubMap[x].length; y++) {
        stroke(0, 0, 0, 20);
        strokeWeight(1);
        line(0, y*indexWidth, worldWidth*indexWidth, y*indexWidth);
        line(x*indexWidth, 0, x*indexWidth, worldHeight*indexWidth);
      }
    }
  }
  
  @Override
  public void settings() {
    size(worldWidth*indexWidth,worldHeight*indexWidth);
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
      aplyGravity();
    }
  }

  private void creator() {
    if(activePiece == null){
      activePiece = pg.popPiece();
      activePiece.draw(g, indexWidth);
      return;
    }
    if(gravityTimer == 0){
      aplyGravity();
    }
    if(activePiece != null){
      activePiece.draw(g, indexWidth);
    }
    incrimentGravity();
  }
  
  private void aplyGravity() {
    if(activePiece == null){
      return;
    }
    if(activePiece.y+activePiece.getHeight()+1 > worldHeight || activePiece.willCollide(SubMap)){
      activePiece.Place(SubMap);
      activePiece.draw(g, indexWidth);
      activePiece = null;
      return;
    }
    activePiece.gravity();
  }

  private void incrimentGravity() {
    gravityTimer++;
    gravityTimer %= 30;
  }
  
  private void mover() {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'mover'");
  }
}
