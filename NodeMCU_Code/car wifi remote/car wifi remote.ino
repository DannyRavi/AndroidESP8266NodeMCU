#include <ESP8266WiFi.h>
#include <ESP8266WebServer.h>

//Our Wi-Fi ssdid and password
char *ssid = "*****";/// اینجا اسم وای فای رو بده
char *password = "*****";//اینجا رمز وای فای رو بزن
String engine = "0";
String doors = "0";
String head_light = "0";
String tail_light = "0";
int bluepin = 13;
int greenpin = 12;
int redpin = 15;

IPAddress ip(192, 168, 0, 106);     //set static ip
IPAddress gateway(192, 168, 0, 1);  //set getteway
IPAddress subnet(255, 255, 255, 0); //set subnet

ESP8266WebServer server; //server variable

void setup()
{
  initializePin(); //call function

  //Making Connection With netword
  WiFi.begin(ssid, password);
  WiFi.config(ip, gateway, subnet);
  Serial.begin(115200);
  Serial.println("");
  Serial.println("");
  Serial.println(ssid);
  Serial.println("");
  Serial.print("Searching Connection");
  Serial.println("");
  Serial.print("IP Address: ");
  Serial.println("");
  Serial.print(WiFi.localIP());
  Serial.println("");
  Serial.print(WiFi.localIP());
  delay(500);
  Serial.print("Searching Connection");
  while (WiFi.status() != WL_CONNECTED)
  {
    Serial.print(".");
    delay(500);
  }

  Serial.println("");
  Serial.print("IP Address: ");
  Serial.print(WiFi.localIP());

  serverSection();
}

void loop()
{
  // put your main code here, to run repeatedly:
  server.handleClient();
}

void initializePin()
{

  pinMode(bluepin, OUTPUT);
  pinMode(greenpin, OUTPUT);
  pinMode(redpin, OUTPUT);
  //pinMode(D8, OUTPUT);

  digitalWrite(bluepin, LOW);
  digitalWrite(greenpin, LOW);
  digitalWrite(redpin, LOW);
  //digitalWrite(D8, LOW);
}

void serverSection()
{
  server.on("/", []() {
    server.send(200, "text/html", "<!DOCTYPE html><html><meta charset='UTF-8'><head></head><body><h2>Dzulkiflee's Room</h2><h3><a href='/engine'>Room Light</a></h3><br><h3><a href='/doors'>Mirror Light</a></h3><br><h3><a href='/head_light'>Bed Light</a></h3><br><h3><a href='/tail_light'>tail_light</a></h3><br></body></html>");
  });

  server.on("/engine", engine_state);
  server.on("/doors", doors_state);
  server.on("/head_light", head_light_state);
  server.on("/tail_light", tail_light_state);

  server.on("/status", all_state);

  server.begin();
}

void engine_state()
{
  if (engine == "0")
  {
    engine = "1";
    digitalWrite(bluepin, HIGH);
    server.send(200, "text/html", engine);
  }
  else
  {
    engine = "0";
    digitalWrite(bluepin, LOW);
    server.send(200, "text/html", engine);
  }
}

void doors_state()
{
  if (doors == "0")
  {
    doors = "1";
    digitalWrite(greenpin, HIGH);
    server.send(200, "text/html", doors);
  }
  else
  {
    doors = "0";
    digitalWrite(greenpin, LOW);
    server.send(200, "text/html", doors);
  }
}

void head_light_state()
{
  if (head_light == "0")
  {
    head_light = "1";
    digitalWrite(redpin, HIGH);
    server.send(200, "text/html", head_light);
  }
  else
  {
    head_light = "0";
    digitalWrite(redpin, LOW);
    server.send(200, "text/html", head_light);
  }
}

void tail_light_state()
{
  if (tail_light == "0")
  {
    tail_light = "1";
    //  digitalWrite(D8, HIGH);
    server.send(200, "text/html", tail_light);
  }
  else
  {
    tail_light = "0";
    //  digitalWrite(D8, LOW);
    server.send(200, "text/html", tail_light);
  }
}

void all_state()
{

  server.send(200, "text/html", "{'rl':'" + engine + "','ml':'" + doors + "','bl':'" + head_light + "','tail_light':'" + tail_light + "'}");
}

//   ████████╗██╗  ██╗███████╗    ███████╗███╗   ██╗██████╗ 
//   ╚══██╔══╝██║  ██║██╔════╝    ██╔════╝████╗  ██║██╔══██╗
//      ██║   ███████║█████╗      █████╗  ██╔██╗ ██║██║  ██║
//      ██║   ██╔══██║██╔══╝      ██╔══╝  ██║╚██╗██║██║  ██║
//      ██║   ██║  ██║███████╗    ███████╗██║ ╚████║██████╔╝
//      ╚═╝   ╚═╝  ╚═╝╚══════╝    ╚══════╝╚═╝  ╚═══╝╚═════╝ 
//                                                          
