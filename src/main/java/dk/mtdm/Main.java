package dk.mtdm;

import processing.core.PApplet;
public class Main {
    public static void main(String[] args) {
        PApplet sketch = new Sketch();
        PApplet.runSketch(new String[]{"window"},sketch);
    }
}