#ifndef TURNACTION_H
#define TURNACTION_H

#include "NTPClient.h"

extern NTPClient timeClient;

extern int turns;
extern unsigned long lastTurn;

void updateTurns();
void addCurrentTimestamp();
void removeOldTimestamps();

#endif