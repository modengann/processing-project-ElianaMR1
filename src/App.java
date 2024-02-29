import processing.core.*;

public class App extends PApplet {
    Box myBox;

    public static void main(String[] args) {
        PApplet.main("App");
    }

    public void settings() {
        size(1000, 600);
    }

    public void setup() {
        background(255);
        myBox = new Box(50, 500, 100, 50); // Specify width and height for the box
    }

    public void draw() {
        background(255);
        myBox.display();
    }

    class Box {
        float x, y;
        float boxWidth, boxHeight; // Separate variables for width and height

        Box(float x, float y, float boxWidth, float boxHeight) {
            this.x = x;
            this.y = y;
            this.boxWidth = boxWidth;
            this.boxHeight = boxHeight;
        }

        void display() {
            fill(0);
            rect(x, y, boxWidth, boxHeight);
        }
    }
}
