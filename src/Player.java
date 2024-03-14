import processing.core.*;

public class Player { 

     float x, y;
     float diameter;
    PApplet canvas;

        Player(float x, float y, PApplet p) {
            this.x = x;
            this.y = y;
            this.diameter = 30; 
            canvas = p; // need this to say which canvas to make things on
        }

        void display() {
            canvas.fill(128, 0, 128); // Purple color
            canvas.ellipse(x, y, diameter, diameter);
        }
    }
