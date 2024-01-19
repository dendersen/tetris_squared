package dk.mtdm;

public class PieceGenerator {
  int maxValue = 7 + (int) (5 * Math.random());
  int currentPiece = (int) (Math.random() * maxValue);
  int skipValue = (int) (Math.random() * maxValue);
  public SubSquare popPiece() {
    SubSquare temp = getPiece(0);
    currentPiece += skipValue;
    currentPiece %= maxValue;
    return temp;
  }
  public SubSquare getPiece(int index) {
    return new SubSquare(Shape.values()[generatePiece(index)],5,0);
  }
  private int generatePiece(int index) {
    int val = currentPiece;
    for (int i = 0; i < index; i++) {
      val += skipValue;
      val %= maxValue;
    }
    return val%Shape.values().length;
  }
}
