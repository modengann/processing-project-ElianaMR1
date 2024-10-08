

import processing.core.*;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.text.html.HTMLDocument.HTMLReader.CharacterAction;

import java.io.FileWriter;
import java.io.IOException;

public class App extends PApplet {
    Box myBox; //creating objects in my classes
    Box secondBox;
    Box thirdBox;
    Box typerBox;
    Player player;
    GoldFlag goldFlag;
    int gravity = 0; // A gravity modifier for downwards movement
    float floorY = height - 10; // a couple helpful lines that I will end up using a couple times 
    public int stage = 1; // stage counter
    BouncePad pad1;
    int deaths = 0; // death counter
    DeathZone Zone1;
    DeathZone Zone2;
    DeathZone Zone3;
    DeathZone Zone4;
    DeathZone Zone5;
    BouncePad pad2;
    boolean easyMode = false; // for easy mode
    boolean easyModeWasUsed = false; // victory text
    String stageText = "Stage 1";
    String stageText2 = " "; // for stage text and tutorial
    String stageText3 = "";
    String file = "boxes.txt";
    long startTime = 0;
    long endtime = 0;
    boolean ScoreCheckerWasUsed = false;
    boolean endtimeCalced = false;
    boolean typingMode;
    String name = "";
    boolean doneWithNaming = false;
    

    public static void main(String[] args) {
        PApplet.main("App");
    }

    public void settings() {
        size(1000, 600);
    }

    public void setup() {
        background(255);
        myBox = new Box(50, 500, 100, 50, this, true);
        float middleX = width / 2 - 100;
        float middleY = height / 2 + 50; // makes my life easier centering
        secondBox = new Box(middleX, middleY, 100, 50, this, true);
        thirdBox = new Box(700, 200, 100, 50, this, true);
        typerBox = new Box(0, 500, 1000, 50, this, true);
        player = new Player(100, 450, this);
        goldFlag = new GoldFlag(750, 150, this, true);
        pad1 = new BouncePad(middleX, 500, 100, 50, this, true);
        pad2 = new BouncePad(middleX - 50, 500, 150, 50, this, true);
        Zone1 = new DeathZone(425, 100, 150, 25, this, true);
        Zone2 = new DeathZone(575, 100, 20, 500, this, true);
        Zone3 = new DeathZone(0, 100, 300, 25, this, true);
        Zone4 = new DeathZone(250, 100, 20, 500, this, true);
        Zone5 = new DeathZone(675, 0, 150, 25, this, true);
        startTime = System.currentTimeMillis();
        
       
    }

    public void draw() {
        background(255);

        if (goldFlag.isCollidingWithPlayer(player)) { // checks for contact with flag to go to next stage
            goldFlag.turnGreen(); 
            moveToNextstage();
        }
        if (isColliding(player, myBox) || isColliding(player, secondBox) || isColliding(player, thirdBox)) { // landing on boxes
            if (gravity > 0) {
                gravity = 0;
            }
        } else {
            gravity += 1; // gravity actually moving the player
        }
        if (keyPressed && key == 'a') { // base movement
            player.x -= 15; 
        } else if (keyPressed && key == 'd') {
            player.x += 15; 
        } 
        if (keyPressed && key == 'w' && isCollidingWithAnyBox(player, myBox, secondBox, thirdBox, typerBox) ) {
            gravity -=25;
        }
        else if (keyPressed && key == ' ' && isCollidingWithAnyBox(player, myBox, secondBox, thirdBox, typerBox) ) {
            gravity -=25;
        }
        else if (keyPressed && key == 's' && isCollidingWithAnyBox(player, myBox, secondBox, thirdBox, typerBox) ) { 
            player.y += 2;
        }
        
        stroke(255, 0, 0);
        line(0, 590, 1000, 590); // death line at the bottom
        noStroke();
        fill(0);
        textSize(32);
        textAlign(CENTER, CENTER);
        text(stageText, width / 2, height / 2 - 20); //stage text
        text(stageText2, width / 2, height / 2 + 10);
        text(stageText3, width/2, height/2 - 50);
        text("Deaths: " + deaths, width / 2 - 400, height / 2 -250);
        if (player.y < 0 && easyMode) { // easy mode for an indicator on where you are while off the screen
            fill(128, 128, 128);
            float ellipseSize = max(30 + abs(player.y / 3), 0); // I wanted to make the marker get smaller based on how far above the screen
            // the player is, but this isn't  working yet. I use max, min, and absolute value so whatever math I end up using can't make the game nonfunctional
            //while trying out things
            ellipseSize = min(ellipseSize, 30);
            ellipse(player.x, 0, ellipseSize, ellipseSize);
        }
        if (player.x < 0 && easyMode) {
            if (player.y < 0) {
                fill(128, 128, 128);
                ellipse(0, 0, 30, 30);
            }
            else {
                fill(128, 128, 128);
                ellipse(0, player.y, 30, 30);
            }
            
        }
        else if (player.x > 1000 && easyMode) {
            if (player.y < 0) {
                fill(128, 128, 128);
                ellipse(0, 0, 30, 30);
            }
            else {
                fill(128, 128, 128);
                ellipse(1000, player.y, 30, 30);
            }
        }
    

        if (player.y > 580 || isCollidingWithAnyZone(player, Zone1, Zone2, Zone3, Zone4, Zone5)) { // death zones and bottom line work
            player.y = 450;
            player.x = 100;
            gravity = 0;
            deaths ++;
        }
       if (pad1.isCollidingWithPlayer(player, pad1) || pad2.isCollidingWithPlayer(player, pad2)) {
        gravity = -30; // make bouncepads work
       } 
       player.y += gravity;

       
        displayAll();
        setStage();
       } 
        
       public void displayAll(){
        myBox.display(); //displaying my objects
        secondBox.display();
        thirdBox.display();
        typerBox.display();
        goldFlag.display();
        pad1.display();
        pad2.display();
        Zone1.display();
        Zone2.display();
        Zone3.display();
        Zone4.display();
        Zone5.display();
        player.display();
       }
       

    
    
    boolean isCollidingWithAnyBox(Player player, Box... boxes) { // a way to check for collision with large numbers of boxes
        for (Box box : boxes) {
            if (isColliding(player, box)) {
                return true;
            }
        }
        return false;
    }

 boolean isColliding(Player player, Box box) { //checks to see if player is in contact with a box
        if (box.isVisible) {
            return player.x + player.diameter / 2 > box.x &&
               player.x - player.diameter / 2 < box.x + box.boxWidth &&
               player.y + player.diameter / 2 > box.y &&
               player.y - player.diameter / 2 < box.y + box.boxHeight;
        }
        else {
            return false;
        }
    }
    boolean isCollidingWithAnyZone(Player player, DeathZone... deathZones) { //same as boxes but for death zones
        for (DeathZone deathZone : deathZones) {
            if (isCollidingWithZone(player, deathZone)) {
                return true;
            }
        }
        return false;
    }
    boolean isCollidingWithZone(Player player, DeathZone deathZone) {
        if (deathZone.isVisible) {
            return player.x + player.diameter / 2 > deathZone.x &&
               player.x - player.diameter / 2 < deathZone.x + deathZone.deathZoneWidth &&
               player.y + player.diameter / 2 > deathZone.y &&
               player.y - player.diameter / 2 < deathZone.y + deathZone.deathZoneHeight;
        }
        else {
            return false;
        }
    }
   public static String[] scanner(String file, int line) {
    try {
        Scanner scanner = new Scanner(Paths.get(file));
        for (int i = 1; i < line; i++) {
            scanner.nextLine();
        }
        String text = scanner.nextLine();
        String[] parts = text.split(" ");
        return parts;
        

        }
        catch (Exception e) {
            String[] str = {"false"};
            return str;
        }
    }
   
    
    public void moveToNextstage(){ // a method for moving to the next stage
        player.x = 100;
        player.y = 450;
        gravity = 0;
        stage ++;
        
    }
    public void setStage(){
        switch (stage) {
            case 1:
            stageText = "Stage 1. Use 'a' and 'd' to move, and 'w' to jump";
            stageText2 = "(or space bar to jump)";
            typerBox.setVisibility(false); 
            pad1.setVisibility(false); //toggling visibility to make stages
             pad2.setVisibility(false);
             Zone1.setVisibility(false);
             Zone2.setVisibility(false);
             Zone3.setVisibility(false);
             Zone4.setVisibility(false);
             Zone5.setVisibility(false);
             myBox.setVisibility(true);
             secondBox.setVisibility(true);
             thirdBox.setVisibility(true);
             goldFlag.setVisibility(true);
                break;
            case 2:
            stageText = "Stage 2. Jump on the blue bouncepad for a boost";
            stageText2 = "";
            secondBox.isVisible = false;
            pad1.setVisibility(true);
                break;
            case 3:
            myBox.display();
            thirdBox.display();
            goldFlag.display();
            pad1.setVisibility(false);
            stageText = "Stage 3. Press 's' to grab on to a box to get a super jump!";
            stageText2 = "(press 'L' for easy mode)";
                break;
            case 4:
            Zone1.setVisibility(true);
            pad1.setVisibility(true);
            Zone2.setVisibility(true);
            Zone3.setVisibility(true);
            stageText = "Stage 4. Avoid the death zones";
            stageText2 = "";
            break;
            case 5:
            Zone4.setVisibility(true);
            Zone3.setVisibility(false);
            Zone5.setVisibility(true);
            pad2.setVisibility(true);
            stageText = "Stage 5. Use what you already know to make it to the goal";
            break;
            default:
            // victory screen
            Zone1.setVisibility(false); 
            Zone2.setVisibility(false);
            Zone3.setVisibility(false);
            Zone4.setVisibility(false);
            Zone5.setVisibility(false);
            goldFlag.setVisibility(false);
            thirdBox.setVisibility(false);
            pad1.setVisibility(false);
            pad2.setVisibility(false);
            if (deaths == 0) { // A different victory screen depending on how well you did bc it makes me happy
                if(!endtimeCalced) {
                    long endtime = System.currentTimeMillis() - startTime;
                    endtimeCalced = true;
                }
                stageText = "You won with no deaths! please enter a name to match with your highscore";
                if (!easyModeWasUsed && !ScoreCheckerWasUsed) {
                    file = "Highscore.txt";
                    typingMode = true;
                    ScoreChecker(System.currentTimeMillis() - startTime);
                    ScoreCheckerWasUsed = true;
                }
                
                



            }
            else if (deaths == 1) {
                stageText = "You won with only 1 death!";
            }
            else if (deaths <= 10) {
                stageText = "You won with only " + deaths + " deaths!";
            }
            else if (deaths <= 50) {
                stageText = "You won with " + deaths + " deaths. Could have been worse.";
    
            }
            else {
                stageText = "You won with " + deaths + " deaths.";
            }
            if (easyModeWasUsed) {
                stageText2 = "You did use easy mode though. Maybe next time try without it?";
            }
            
           }
    }
    public void addtext(String text, String file){
        String fileName = file;
        try {
            FileWriter writer = new FileWriter(fileName, true); // true parameter appends to the file
            writer.write("\n");
            writer.write(text);
            writer.close();
        } catch (IOException e) {
        }
    }

    
    public void keyPressed() { 
        Character[] insertName = new Character[6];
        insertName[0] = '_';
        insertName[1] = '_';
        insertName[2] = '_';
        insertName[3] = '_';
        insertName[4] = '_';
        insertName[5] = '_';
        int CharOn = 0;
        
       if (typingMode) { //typing mode!!
        
        name = "" + insertName[0] + insertName[1] + insertName[2] + insertName[3] + insertName[4] + insertName[5];
        if (key == BACKSPACE && CharOn > 0) {
            CharOn --;
            insertName[CharOn] = '_';
        }
        else {
            char myChar = key;
            insertName[CharOn] = myChar;
            CharOn++;
            
        }
        stageText3 = name;
        if (CharOn == 5) {
            typingMode = false;
            doneWithNaming = true;
        }
       }
       
        else {if (key == 'p') { // cheat code for testing later levels
            stage++;
        }
        if (key == 'o') {
            deaths = 0;
        }
        if (key == 'l') { // easy mode
            if (easyMode) {
                easyMode = false;
            }
            else {
                easyMode = true;
                easyModeWasUsed = true;
            }
        }}

    }
    
    public void ScoreChecker(long time) {
        typingMode = true;
        file = "Highscore.txt";
        String[] parts = {""};
        if (doneWithNaming) {
            for (int i = 1; i < 100; i++) {
                parts = scanner(file, i);
                if (parts[0].equals("false")) {
                    addtext(String.valueOf(time + " " + name), file);
                    stageText2 = "You placed " + util.numbers(i) + " with a time of " + util.timeConverter(time) + "!";
                    break;
                }
                else if (Integer.valueOf(parts[0]) < time) {
                } 
                else {
                    insertLine(file, i, String.valueOf(time) + " " + name);
                    stageText2 = "You placed " + util.numbers(i) + " with a time of " + util.timeConverter(time);
                    break;
                }
    
    
                
            }
        }



    } 
    
    public void insertLine(String file, int lineNumb, String addition) {
        ArrayList<String> lines = new ArrayList<>();
        int j = 1;
        String[] parts = {""};
        for (int i = 0; i < lineNumb; i++) {
            parts = scanner(file, i+1);
            lines.add(parts[0]);
        }
        lines.add(addition);
        while(true){
            parts = scanner(file, j);
            if (parts[0].equals("false")) {
                break;
            }
            else {
                lines.add(parts[0]);
                
            }
            
        }
        try {
            FileWriter fileWriter = new FileWriter(file, false);
            for (int i = 0; i < lines.size(); i++) {
                fileWriter.write(lines.get(i));
                fileWriter.write("\n");
            }
            fileWriter.close();
        } catch (Exception e) {
            // TODO: handle exception
            //I'm good
        }
    }
}
