package dk.mtdm;

import processing.core.PApplet;

public class Sketch extends PApplet {
  final int indexWidth = 20;
  SubSquare[][] SubMap;
  SubSquare[][] topMap;
  boolean[][] lockMap;
  boolean makePiece = true;
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
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'creator'");
  }
  private void mover() {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'mover'");
  }
}
