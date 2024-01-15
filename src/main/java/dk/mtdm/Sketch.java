package dk.mtdm;

import processing.core.PApplet;

public class Sketch extends PApplet {
  private int gravityTimer = 0;
  final int indexWidth = 20;
  Color[][] SubMap = new Color[20][10];
  Color[][] topMap;
  boolean[][] lockMap;
  boolean makePiece = true;
  SubSquare activePiece;
  PieceGenerator pg = new PieceGenerator();
  public void keyPressed() {
    if (key == 'a') {
      System.out.println("a");
    }
  }
  public void draw(){
    if(makePiece){
      creator();
    }else{
      mover();
    }
  }
  
  @Override
  public void settings() {
    size(400,400);
  }
  
  private void creator() {
    if(activePiece == null){
      activePiece = pg.popPiece();
    }
    if(gravityTimer == 0){
      if(activePiece.y+1 > 10){
        activePiece.Place(SubMap);
        activePiece = null;
        return;
      }
      activePiece.gravity();
    }
    incrimentGravity();
    activePiece.draw(g, indexWidth);
  }
  
  private void incrimentGravity() {
    gravityTimer++;
    gravityTimer %= 10;
  }
  private void mover() {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'mover'");
  }
}
