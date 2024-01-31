package dk.mtdm;

public enum Color{
  yellow(255,255,0),
  lightBlue(100,100,255),
  red(255,0,0),
  green(0,255,0),
  orange(255,100,0),
  darkBlue(0,0,200),
  purple(255,0,255),
  LOCKED(0,0,0);
  
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
