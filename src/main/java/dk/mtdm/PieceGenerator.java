package dk.mtdm;

public class PieceGenerator {
  int[] bag = {0,1,2,3,4,5,6,0,1,2,3,4,5,6};
  int index = 7;
  public PieceGenerator() {
    fillBag();
    fillBag();
  }
  public SubSquare popPiece() {
    if(index >= 7) fillBag();
    SubSquare temp = getPiece(0);
    index++;
    return temp;
  }
  public SubSquare getPiece(int i) {
    if(index + i > bag.length) fillBag();
    if(index + i > bag.length) return null;
    return new SubSquare(Shape.values()[generatePiece(index + i)],5,0);
  }
  private int generatePiece(int index) {
    int temp = bag[index++];
    return temp;
  }
  public void fillBag(){
    int[] temp = {0,1,2,3,4,5,6};
    for (int j = 0; j < temp.length; j++) {
      for (int i = 0; i < temp.length; i++) {
        int newIndex = (int)(Math.random() * temp.length);
        int save = temp[i];
        temp[i] = temp[newIndex];
        temp[newIndex] = save;
      }
    }
    for (int i = index; i < bag.length; i++) {
      bag[i-index] = bag[i];
    }
    for (int i = 0; i < temp.length; i++) {
      bag[i+index] = temp[i];
    }
    index = 0;
  }
}
