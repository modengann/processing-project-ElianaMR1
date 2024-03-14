import processing.core.*;


public class Box {
     float x, y;
     float boxWidth, boxHeight;
     PApplet canvas;
    public boolean isVisible = true; // lets me turn on and off objects in each stage

    public Box(float x, float y, float boxWidth, float boxHeight, PApplet p, boolean isVisible) {
        this.x = x;
        this.y = y;
        this.boxWidth = boxWidth;
        this.boxHeight = boxHeight;
        canvas = p;
    }

    void display() { 
        if (isVisible) {
        canvas.fill(0);
        canvas.rect(x, y, boxWidth, boxHeight);
        }
    }
    void setVisibility(boolean isVisible) {
        this.isVisible = isVisible; 
    }
}