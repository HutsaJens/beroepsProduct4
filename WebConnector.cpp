#include <Arduino.h> // Include the Arduino.h header file

#include "WebConnector.h"

#include <ArduinoJson.h>
#include <ESP8266WiFi.h>
#include <ESP8266WebServer.h>
#include <ESP8266mDNS.h>

// Hostname
#define HOST "bp4"

// Wifi creds
// const char* ssid = "Ziggo-Bertens-2.4Ghz";
// const char* password = "BertensWifi2018";
const char* ssid = "X240";
const char* password = "pindakaas";

ESP8266WebServer server(80);

// Manage not found URL
void handleNotFound() {
  String message = "File Not Found\n\n";
  message += "URI: ";
  message += server.uri();
  message += "\nMethod: ";
  message += (server.method() == HTTP_GET) ? "GET" : "POST";
  message += "\nArguments: ";
  message += server.args();
  message += "\n";
  for (uint8_t i = 0; i < server.args(); i++) {
    message += " " + server.argName(i) + ": " + server.arg(i) + "\n";
  }
  server.send(404, "text/plain", message);
}

void setupWifi() {
  // Wifi setup
  WiFi.mode(WIFI_STA);
  WiFi.begin(ssid, password);

  // Wait for connection
  Serial.println("");
  while (WiFi.status() != WL_CONNECTED) {
    delay(500);
    Serial.print(".");
  }

  // Print connection details
  Serial.println("");
  Serial.print("Connected to ");
  Serial.println(ssid);
  Serial.print("IP address: ");
  Serial.println(WiFi.localIP());

  // Activate mDNS this is used to be able to connect to the server
  MDNS.addService("http", "tcp", 80);
  if (MDNS.begin(HOST)) {
    Serial.println("MDNS responder started");
  }
}

  // Update MDNS
void updateMDNS() {
  MDNS.update();
}

  // Start the rime client and update the time
void setupTime() {
  timeClient.begin();
  timeClient.update();
}

// Update the time
void updateTime() {
  timeClient.update();
}

void setupServer() {
  // Set not found response
  server.onNotFound(handleNotFound);

  // Start server
  server.begin();
  Serial.println("HTTP server started");
}

// Handle the server connection
void handleServerClient() {
  server.handleClient();
}



// Define routing
void restServerRouting() {
  server.on("/", HTTP_GET, []() {
    server.send(200, F("text/html"),
                F("Welcome to the REST Web Server"));
  });
  server.on(F("/data"), HTTP_GET, setData);
}

// Set the Json data to the web server
void setData() {
  // Mke a json object to store the Json data
  StaticJsonDocument<64> doc;

  // The data that gets uploaded to the json data server
  doc["tolong"] = tolong;
  doc["turns"] = turns;
  doc["lastturn"] = lastTurn;

  // Print the Json data to the server
  Serial.print(F("Stream..."));
  String buf;
  serializeJson(doc, buf);
  server.send(200, F("application/json"), buf);
  Serial.print(F("done."));
}