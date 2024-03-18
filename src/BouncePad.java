import processing.core.*;

public class BouncePad {
    float x, y;
    float bouncePadWidth, BouncePadHeight;
    public boolean isVisible = true;
    PApplet canvas;
// this is the same set of things
    public BouncePad(float x, float y, float BouncePadWidth, float BouncePadHeight,PApplet p, boolean isVisible) {
        this.x = x;
        this.y = y;
        this.bouncePadWidth = BouncePadWidth;
        this.BouncePadHeight = BouncePadHeight;
        canvas = p;
    }

    void display() {
        if (isVisible) {
            canvas.fill(0, 0, 255);
            canvas.rect(x, y, bouncePadWidth, BouncePadHeight);
        }
        
    }
    public boolean isCollidingWithPlayer(Player player, BouncePad bouncePad) {
        if (bouncePad.isVisible) {
            return player.x + player.diameter / 2 > x &&
               player.x - player.diameter / 2 < x + bouncePadWidth &&
               player.y + player.diameter / 2 > y &&
               player.y - player.diameter / 2 < y + BouncePadHeight;
        }
        else {
            return false;
        }
    }
    void setVisibility(boolean isVisible) {
        this.isVisible = isVisible; 
    }
}
