import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import controlP5.*; 
import sojamo.drop.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class fluxAbstractions extends PApplet {

//fastco


ControlP5 controlP5;


SDrop drop;
SDrop dropTwo;

float thickness, speed, angle, topRight, topLeft, bottomRight, bottomLeft, speeder;
PImage imgOne, imgTwo;

MyDropListener dropTarget;
MyDropListener dropTargetTwo;

public void setup() {
  size(1280, 800, P2D);
  background(255);
  smooth();
  noStroke();
  imgOne = loadImage("emptypic.jpg");
  imgTwo = loadImage("emptypic.jpg");
  setupGUI();
}

public void draw() {
  background(255);
  drawGUI();

  dropTarget.draw(49, 15, 86, 48);
  if (imgOne !=null) {
  }

  dropTargetTwo.draw(149, 15, 86, 48);
  if (imgTwo !=null) {
  }

  for (int i = -50; i < 150; i = i + 2) {
    renderOne(i, imgOne);
    renderTwo(i, imgTwo);
  }
}

public void setupGUI() {
  drop = new SDrop(this);
  dropTarget = new MyDropListener(49, 15, 86, 48);
  drop.addDropListener(dropTarget);

  dropTwo = new SDrop(this);
  dropTargetTwo = new MyDropListener(149, 15, 86, 48);
  dropTwo.addDropListener(dropTargetTwo);

  controlP5 = new ControlP5(this);
  PFont GUIfont = createFont("arial", 12);
  controlP5.setControlFont(GUIfont);
  //buttonstuff ("function it fires", ?, XPOS, YPOS, Width, Height).setLabel("LABEL"); 
  controlP5.addButton("item1", 50, width - 150, 25, 100, 20).setLabel("Export .gif");

  controlP5.addSlider("speed").setPosition(450, 25).setSize(150, 20).setRange(-50, 50).setValue(2);
  controlP5.addSlider("angle").setPosition(650, 25).setSize(150, 20).setRange(-width/2, width/2).setValue(85).setLabel("slant");
  controlP5.addSlider("thickness").setPosition(850, 25).setSize(150, 20).setRange(15, width/2).setValue(100);

  controlP5.getController("speed").getCaptionLabel().align(ControlP5.LEFT, ControlP5.BOTTOM_OUTSIDE).setPaddingX(0);
  controlP5.getController("angle").getCaptionLabel().align(ControlP5.LEFT, ControlP5.BOTTOM_OUTSIDE).setPaddingX(0);
  controlP5.getController("thickness").getCaptionLabel().align(ControlP5.LEFT, ControlP5.BOTTOM_OUTSIDE).setPaddingX(0);
}

public void drawGUI() {
  drawGUIBackground();

  //drag and drop targets
  textureMode(NORMAL);
  textureWrap(REPEAT); 
  beginShape();
  texture(imgOne);
  vertex(49, 15, 0, 0);
  vertex(135, 15, 1, 0);
  vertex(135, 63, 1, 1);
  vertex(49, 63, 0, 1);
  endShape(CLOSE);

  //drag and drop targets
  textureMode(NORMAL);
  textureWrap(REPEAT); 
  beginShape();
  texture(imgTwo);
  vertex(149, 15, 0, 0);
  vertex(235, 15, 1, 0);
  vertex(235, 63, 1, 1);
  vertex(149, 63, 0, 1);
  endShape(CLOSE);
}

public void drawGUIBackground() {
  fill(0);
  rect(0, 0, width, 80);
}


public void dropEvent(DropEvent theDropEvent) {
  if (theDropEvent.isImage()) {
      imgOne = theDropEvent.loadImage();
    }
}

  class MyDropListener extends DropListener {

    int boxX, boxY, boxWidth, boxHeight, myColor;

    MyDropListener(int boxX, int boxY, int boxWidth, int boxHeight) {
      myColor = color(0xffFFFFFF);
      // set a target rect for drop event.
      setTargetRect(boxX, boxY, boxWidth, boxHeight);
    }

    public void draw(int boxX, int boxY, int boxWidth, int boxHeight) {
      noFill();
      stroke(myColor);
      rect(boxX, boxY, boxWidth, boxHeight);
    }

    public void dropEnter() {
      myColor = color(0xffFF0000);
    }

    public void dropLeave() {
      myColor = color(255);
    }

    public void dropEvent(DropEvent theEvent) {
      println("Dropped on MyDropListener");
    }
  }

public void renderOne(int offset, PImage img) {
  float topRight = (thickness * offset) + angle + thickness;
  float topLeft = topRight - thickness;
  float bottomLeft = thickness * offset - angle;
  float bottomRight = bottomLeft + thickness;

  float trMap = map(topRight, 0, 1280, 0, 1);
  float tlMap = map(topLeft, 0, 1280, 0, 1);
  float blMap = map(bottomLeft, 0, 1280, 0, 1);
  float brMap = map(bottomRight, 0, 1280, 0, 1);
  float spdMap = map(speed, 0, 100, 0, .0001f);


  textureMode(NORMAL);
  textureWrap(REPEAT); 

  beginShape();
  texture(img);
  vertex(topLeft, 80, tlMap + speeder, 0);
  vertex(topRight, 80, trMap + speeder, 0);
  vertex(bottomRight, height, brMap + speeder, 1);
  vertex(bottomLeft, height, blMap + speeder, 1);
  endShape(CLOSE);

  if (speeder < 1) {
    speeder = speeder + spdMap;
  } 
  else {
    speeder = 0;
  }
}

public void renderTwo(int offset, PImage img) {
  float topRight = (thickness * offset) + angle + (2 * thickness);
  float topLeft = topRight - thickness;
  float bottomLeft = thickness * offset - angle + thickness;
  float bottomRight = bottomLeft + thickness;

  float trMap = map(topRight, 0, 1280, 0, 1);
  float tlMap = map(topLeft, 0, 1280, 0, 1);
  float blMap = map(bottomLeft, 0, 1280, 0, 1);
  float brMap = map(bottomRight, 0, 1280, 0, 1);
  float spdMap = map(speed, 0, 100, 0, .0001f);

  textureMode(NORMAL);
  textureWrap(REPEAT);

  beginShape();
  texture(img);
  vertex(topLeft, 80, tlMap + speeder, 0);
  vertex(topRight, 80, trMap + speeder, 0);
  vertex(bottomRight, height, brMap + speeder, 1);
  vertex(bottomLeft, height, blMap + speeder, 1);
  endShape(CLOSE);
}

  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "fluxAbstractions" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
