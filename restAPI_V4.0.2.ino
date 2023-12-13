#include "Arduino.h"
#include <ESP8266WiFi.h>
#include <WiFiUdp.h>

// Time stuff
#include "NTPClient.h"
#include <time.h>

// Current Date/Time
// Define NTP Client to get time
WiFiUDP ntpUDP;
NTPClient timeClient(ntpUDP, "nl.pool.ntp.org");


// JSON data types
int turns = 0;
bool tolong = false;
unsigned long lastTurn = 0;

// Inputs
const byte pinButtonTurnLeft = 0;     // D3
const byte pinButtonTurnRight = 3;    // RX
const byte pinButtonTurnMiddle = 15;  // D8

// Outputs
const byte pinOutTurnLeft = 14;   // D5
const byte pinOutTurnRight = 12;  // D6
const byte pinOutPump = 13;       // D7

// I2C
const byte SDA_DATA = 4;   // D2
const byte SCL_CLOCK = 5;  // D1

// Variables for turning
bool enableAutoTurn = false;
byte currentSide = -1;
float autoTurnTime = 0.25;
const float MaxTime = 6.0;

// Init functions
void checkToLong();
void setupDisplay();
void updateDisplay();

// WebConnector.h
void handleNotFound();
void setupWifi();
void setupServer();
void updateMDNS();
void handleServerClient();
void setupTime();
void updateTime();
void restServerRouting();
void setData();

// ButtonAction.h
float readPot();
void clearButtonsPressed();
void updateTurnTime();
void readButtons();

// DisplayAction.h
void setupDisplay();
void updateDisplay();

// TurnAction.h
void updateTurns();
void addCurrentTimestamp();
void removeOldTimestamps();

void setup() {

  // Start Serial
  Serial.begin(115200);

  // Pins
  pinMode(pinButtonTurnLeft, INPUT);
  pinMode(pinButtonTurnRight, INPUT_PULLUP);
  pinMode(pinButtonTurnMiddle, INPUT);

  pinMode(pinOutTurnLeft, OUTPUT);
  pinMode(pinOutTurnRight, OUTPUT);
  pinMode(pinOutPump, OUTPUT);

  // Setup LCD
  setupDisplay();

  // Setup wifi
  setupWifi();

  // Set server routing
  restServerRouting();

  setupServer();

  setupTime();
}

void loop() {

  // Serial.println("Hello World");

  // Serial.println("Before handleClient");
  handleServerClient();

  // Serial.println("Before updateMDNS");
  updateMDNS();

  // Serial.println("Before timeclient update");
  timeClient.update();

  // Serial.println("Before ReadButtons");
  readButtons();

  // Serial.println("Before updateTurnTime");
  updateTurnTime();

  // Serial.println("Before updateDisplay");
  updateDisplay();

  // Serial.println("Before updateTurns");
  updateTurns();

  autoTurnAction();
  // Serial.println(enableAutoTurn);
}

void checkToLong() {
  // If the current time that patient has been on one side (plus 30 seconds) is longer than the specified time it sets "tolong" to true
  tolong = (timeClient.getEpochTime() + 30 > (lastTurn + (autoTurnTime * 60 * 60)));
}

void autoTurnAction() {
  const unsigned long OUTPUT_DELAY = 500;  // Adjust the value as needed (in milliseconds)

  // Calculate the switch delay based on autoTurnTime
  unsigned long switchDelay = autoTurnTime * 60;
  Serial.println(switchDelay);

  static unsigned long lastTurn = 0;
  static unsigned long turnOffTime = 0;
  static bool isTurning = false;

  if (enableAutoTurn) {
    if (!isTurning) {
      if (millis() >= turnOffTime) {
        isTurning = true;
        digitalWrite(pinOutPump, HIGH);

        if (currentSide == 0) {
          digitalWrite(pinOutTurnLeft, LOW);
          digitalWrite(pinOutTurnRight, HIGH);
          currentSide = 1; // Switch to RIGHT
        } else {
          digitalWrite(pinOutTurnLeft, HIGH);
          digitalWrite(pinOutTurnRight, LOW);
          currentSide = 0; // Switch to LEFT
        }

        lastTurn = timeClient.getEpochTime(); // Save the current epoch time as the last turn
        turnOffTime = millis() + OUTPUT_DELAY;
      }
    } else {
      if (millis() >= turnOffTime) {
        digitalWrite(pinOutTurnLeft, LOW);
        digitalWrite(pinOutTurnRight, LOW);
        digitalWrite(pinOutPump, LOW);
        // isTurning = false;
        turnOffTime = millis() + OUTPUT_DELAY;
      } else if (timeClient.getEpochTime() >= (lastTurn + switchDelay)) {
        isTurning = false;
        turnOffTime = millis() + OUTPUT_DELAY;
      }
    }
  } else {
    isTurning = false;
  }
}



