#include <Arduino.h>  // Include the Arduino.h header file

#include "ButtonAction.h"

// Time stuff
#include "NTPClient.h"
#include <time.h>

const unsigned long DEBOUNCE_DELAY = 50;  // Adjust the value as needed
const unsigned long OUTPUT_DELAY = 5000;  // Adjust the value as needed
int activeButton = -1;                    // Current active button
unsigned long lastButtonPressTime = 0;    // Time since a button was pressed

// Debounce Variables for the buttons
unsigned long lastDebounceTimeLeft = 0;
unsigned long lastDebounceTimeRight = 0;
unsigned long lastDebounceTimeMiddle = 0;

// Variable to keep track if the potiometer has been turned
float previousValue = 0.25;

// Function to read and convert the potmeter value
float readPot() {

  // Read the value of the potentiometer connected to A0
  int potValue = analogRead(A0);

  // Map the potentiometer value directly to the desired range (0.25-6) with intervals of 0.25
  float finalValue = potValue / 1023.0 * 5.75 + 0.25;

  // Round the final value to the nearest multiple of 0.25
  finalValue = round(finalValue / 0.25) * 0.25;

  // Check if the current finalValue is different from the previous value
  if (finalValue != previousValue) {
    // Set enableAutoTurn to true if the value has changed
    enableAutoTurn = true;

    // Update the previousValue with the current finalValue
    previousValue = finalValue;
  }

  // Return the mapped and rounded value
  return finalValue;
}

// Update the autoTurnTime
void updateTurnTime() {
  autoTurnTime = readPot();
}

void clearButtonsPressed() {
  digitalWrite(pinOutTurnLeft, LOW);
  digitalWrite(pinOutTurnRight, LOW);
  digitalWrite(pinOutPump, LOW);
}

void readButtons() {

  // Check left button
  int leftButtonState = digitalRead(pinButtonTurnLeft);
  // Serial.print("Links: ");
  // Serial.println(leftButtonState);
  if (leftButtonState == LOW && (millis() - lastDebounceTimeLeft) > DEBOUNCE_DELAY) {
    if (activeButton == 0 && (millis() - lastButtonPressTime < OUTPUT_DELAY)) {
      // Left button is already active and the delay has passed, no need to do anything
      return;
    }
    if (activeButton != 0 && activeButton != -1) {
      // Clear previous active button outputs if the delay has passed
      if (millis() - lastButtonPressTime >= OUTPUT_DELAY) {
        clearButtonsPressed();
        activeButton = -1;
      }
      return;  // Exit the function to prevent activating button 1 outputs
    }
    digitalWrite(pinOutTurnLeft, HIGH);
    digitalWrite(pinOutPump, HIGH);
    enableAutoTurn = false;
    currentSide = 0;
    lastDebounceTimeLeft = millis();
    lastButtonPressTime = millis();
    activeButton = 0;  // Set left button as the active button
    lastTurn = timeClient.getEpochTime();
  }

  yield();

  // Check right button
  int rightButtonState = digitalRead(pinButtonTurnRight);
  if (rightButtonState == LOW && (millis() - lastDebounceTimeRight) > DEBOUNCE_DELAY) {
    if (activeButton == 1 && (millis() - lastButtonPressTime < OUTPUT_DELAY)) {
      // Left button is already active and the delay has passed, no need to do anything
      return;
    }
    if (activeButton != 1 && activeButton != -1) {
      // Clear previous active button outputs if the delay has passed
      if (millis() - lastButtonPressTime >= OUTPUT_DELAY) {
        clearButtonsPressed();
        activeButton = -1;
      }
      return;  // Exit the function to prevent activating button 2 outputs
    }
    // Turn on the outputs
    digitalWrite(pinOutTurnRight, HIGH);
    digitalWrite(pinOutPump, HIGH);

    // Disable AutoTurn
    enableAutoTurn = false;

    // Set the current side
    currentSide = 1;

    // Variables for debounce and 1 button pressed max
    lastDebounceTimeRight = millis();
    lastButtonPressTime = millis();
    activeButton = 1;  // Set right button as the active button

    // Update the lastturn time
    lastTurn = timeClient.getEpochTime();
  }

  yield();

  // Check middle button
  int middleButtonState = digitalRead(pinButtonTurnMiddle);
  // Serial.print("Midden: ");
  // Serial.println(middleButtonState);
  if (middleButtonState == HIGH && (millis() - lastDebounceTimeMiddle) > DEBOUNCE_DELAY) {
    if (activeButton == 2 && (millis() - lastButtonPressTime < OUTPUT_DELAY)) {
      // Left button is already active and the delay has passed, no need to do anything
      return;
    }
    if (activeButton != 2 && activeButton != -1) {
      // Clear previous active button outputs if the delay has passed
      if (millis() - lastButtonPressTime >= OUTPUT_DELAY) {
        clearButtonsPressed();
        activeButton = -1;
      }
      return;  // Exit the function to prevent activating button 3 outputs
    }
    digitalWrite(pinOutTurnLeft, HIGH);
    digitalWrite(pinOutTurnRight, HIGH);
    digitalWrite(pinOutPump, HIGH);
    enableAutoTurn = false;
    currentSide = 2;
    lastDebounceTimeMiddle = millis();
    lastButtonPressTime = millis();
    activeButton = 2;  // Set middle button as the active button
    lastTurn = timeClient.getEpochTime();
  }

  // Clear outputs if the delay has passed since the last button press
  if ((millis() - lastButtonPressTime >= OUTPUT_DELAY ) && enableAutoTurn == false) {
    // Serial.println("Clearing outputs");
    clearButtonsPressed();
    activeButton = -1;
  }
}