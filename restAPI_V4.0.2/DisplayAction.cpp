#include <Arduino.h> // Include the Arduino.h header file

#include "DisplayAction.h"

#include <LiquidCrystal_I2C.h>

// LCD variables
const int lcdColumns = 16;
const int lcdRows = 2;

// New lcd Object
LiquidCrystal_I2C lcd(0x27, lcdColumns, lcdRows);


// Setup the display, inilize the object and start the backlight
void setupDisplay() {
  lcd.init();
  lcd.backlight();  
}

// Function for updating the display information
void updateDisplay() {
  lcd.clear();

  // Place the cursor in the top left corner (first row and first column)
  lcd.setCursor(0, 0);
  // if(enableAutoTurn == true) {
  //   lcd.print("Auto Draai: Aan");
  // } else {
  //   lcd.print("Auto Draai: Uit");
  // }

  // If EnableAutoTurn is true print it is on else print it if off
  lcd.print("Auto Draai: ");
  enableAutoTurn ? lcd.print("Aan") : lcd.print("Uit");

  //Move to second row and print the current autoTurnTime
  lcd.setCursor(0, 1);
  lcd.print("Draaitijd: ");
  lcd.print(autoTurnTime);
}