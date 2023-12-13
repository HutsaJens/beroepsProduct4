#ifndef BUTTONACTION_H
#define BUTTONACTION_H

#include <Arduino.h>

// Time stuff
#include "NTPClient.h"
#include <time.h>

// Inputs
extern const byte pinButtonTurnLeft = 0;    // D3
extern const byte pinButtonTurnRight = 3;   // RX
extern const byte pinButtonTurnMiddle = 15;  // D8

// Outputs
extern const byte pinOutTurnLeft = 14;   // D5
extern const byte pinOutTurnRight = 12;  // D6
extern const byte pinOutPump = 13;      // D7

extern unsigned long lastTurn;
extern float autoTurnTime;
extern bool enableAutoTurn;
extern byte currentSide;

extern NTPClient timeClient;

float readPot();
void clearButtonsPressed();
void updateTurnTime();
void readButtons();

#endif