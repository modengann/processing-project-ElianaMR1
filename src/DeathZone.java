import processing.core.*;

public class DeathZone {
    float x, y;
    float deathZoneWidth, deathZoneHeight;
    PApplet canvas;
    public boolean isVisible = true;
    //again, same as the past few sets of things

    public DeathZone(float x, float y, float deathZoneWidth, float deathZoneHeight, PApplet p, boolean isVisible) {
        this.x = x;
        this.y = y;
        this.deathZoneWidth = deathZoneWidth;
        this.deathZoneHeight = deathZoneHeight;
        canvas = p;
    }
    void display() {
        if (isVisible) {
        canvas.fill(255, 0, 0);
        canvas.rect(x, y, deathZoneWidth, deathZoneHeight);
        }
    }
    void setVisibility(boolean isVisible) {
        this.isVisible = isVisible; 
    }
}