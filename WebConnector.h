#ifndef WEBCONNECTOR_H
#define WEBCONNECTOR_H

#include <ESP8266WiFi.h>
#include <ESP8266mDNS.h>
#include <ArduinoJson.h>
#include "NTPClient.h"

extern NTPClient timeClient;

extern unsigned int turns;
extern bool tolong;
extern unsigned long lastTurn;

void handleNotFound();
void setupWifi();
void updateMDNS();
void setupTime();
void updateTime();
void setupServer();
void handleServerClient();
void restServerRouting();
void setData();


#endif