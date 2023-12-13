#include <Arduino.h> // Include the Arduino.h header file

#include "TurnAction.h"

#include <list>

// Number of hours multiplied with number of seconds in a hour
const unsigned long MAX_AGE = 24 * 60 * 60;

// const unsigned long MAX_AGE = 60;

// Variable to keep track if the patient has turned 
unsigned long currentTurn = 0;

// List to hold all the timestamps the patient turned
std::list<unsigned long> previousTimestamps = {};

void updateTurns() {

  // Check if the patient turned
  if(currentTurn != lastTurn) {
    addCurrentTimestamp();
    currentTurn = lastTurn;
  }

  // Remove any timestamps older that MAX_AGE (24 hours)
  removeOldTimestamps();
  
  // Get the amount of turns made in the last MAX_AGE
  turns = previousTimestamps.size();

}

// Function to add the current timestamp to the list
void addCurrentTimestamp() {
  unsigned long currentTimestamp = timeClient.getEpochTime(); // Get the current timestamp in seconds
  previousTimestamps.push_back(currentTimestamp);
}

// Function to remove old timestamps from the list
void removeOldTimestamps() {
  unsigned long currentTimestamp = timeClient.getEpochTime(); // Get the current timestamp in seconds

  // Loop through the List and check if there is any date older than MAX_AGE
  for (auto it = previousTimestamps.begin(); it != previousTimestamps.end();) {
    if (currentTimestamp - *it >= MAX_AGE) {
      it = previousTimestamps.erase(it);
    } else {
      ++it;
    }
  }
}