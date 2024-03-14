import processing.core.*;

public class GoldFlag {
   
        private float x, y;
        float flagWidth, flagHeight;
        int flagColor;
        PApplet canvas;
        public boolean isVisible = true;

        public GoldFlag(float x, float y, PApplet p, boolean isVisible) { 
            canvas = p;
            this.x = x;
            this.y = y;
            this.flagWidth = 30;
            this.flagHeight = 50;
            this.flagColor = canvas.color(255, 215, 0);
            
            
        }
        public void display() {
            if (isVisible) {
            canvas.fill(flagColor);
            canvas.rect(x, y, flagWidth, flagHeight);
            }
        }

        public void turnGreen() { // because color is a part of the assignment
            flagColor = canvas.color(0, 255, 0);

        }
        public boolean isCollidingWithPlayer(Player player) { // check for collision with player
            if (isVisible) {
                return player.x + player.diameter / 2 > x &&
                player.x - player.diameter / 2 < x + flagWidth &&
                player.y + player.diameter / 2 > y &&
                player.y - player.diameter / 2 < y + flagHeight;
            }
            else {
                return false;
            }
           
        }
        void setVisibility(boolean isVisible) {
            this.isVisible = isVisible; 
        }
    }
    
