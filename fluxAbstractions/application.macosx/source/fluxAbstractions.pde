//fastco

import controlP5.*;
ControlP5 controlP5;

import sojamo.drop.*;
SDrop drop;

float thickness, speed, angle, topRight, topLeft, bottomRight, bottomLeft, speeder;
PImage imgOne, imgTwo;

MyDropListener dropTarget;

void setup() {
  size(1280, 800, P2D);
  background(255);
  smooth();
  noStroke();
  imgOne = loadImage("emptypic.jpg");
  imgTwo = loadImage("emptypic.jpg");
  setupGUI();
}

void draw() {
  background(255);
  drawGUI();

  dropTarget.draw();
  if (imgOne !=null) {
  }

  for (int i = -50; i < 150; i = i + 2) {
    renderOne(i, imgOne);
    renderTwo(i, imgTwo);
  }
}

