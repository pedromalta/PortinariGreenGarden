# PortinariGreenGarden

##ESP8266 Based Green Garden Watering System

## Configuration

Before you start you have to update `config.lua` file and set WiFi credentials:

```lua
module.SSID["NAME"] = "PASSWORD"
```
Pins used are 1 and 2 but you can change and/or add at `sprinklers.lua`.

Also at the Android Client change the server IP. 
You can access the menu typing calling the number `*3123456#` on the phone standard dialer. 
The ESP8266 will print its IP on bootup.

![Android Client](https://github.com/pedromalta/PortinariGreenGarden/raw/master/Pictures/app2.jpeg)

![ESP8266](https://github.com/pedromalta/PortinariGreenGarden/raw/master/Pictures/20181124_133128.jpg)

![Water Valve](https://github.com/pedromalta/PortinariGreenGarden/raw/master/Pictures/20181124_110628.jpg)


