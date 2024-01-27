# Siemens logic to minecraft
### Welcome:
With this add-on you can connect automation logic to your minecraft factory and can control it like a real factory. I'm an automation engineering student and I've programmed this kind of logic, so I got the idea to use it to control minecraft
This plugin supports many logics and since this is a server plugin you can also use this with other players and they too can add their own logic. however, this requires that the logics are in the same network

The main purpose of this project was to practice java coding. If you liked this project i hope u check my other projects in [my yt channel](https://youtube.com/jaakko2012). If you have questions about this project, you can contact me on instagram @jaakkawinterroadofficial.

## Test factory video (Click to open yt)
[![IMAGE ALT TEXT HERE](https://img.youtube.com/vi/vgkOpz_0OGI/0.jpg)](https://www.youtube.com/watch?v=vgkOpz_0OGI)    
in this video you can see logic realtime status from this:   
  ![image](https://github.com/jaakka/tiaporta_minecraft_java_plugin/assets/25456491/492b7933-fc50-4b7f-b851-c62d691a5b71)

## Warning! you need siemens tiaportal v18 license... (not free)
So this project is mainly aimed at automation installers and engineers.
Maybe later i make other "control source" project

### How its works?

Nodejs code create local websocket server and minecraft plugin connect to nodejs code. After this minecraft sends commands and logic add command ask open new window in "browser" and it open "logic remote controll panel" and automacally login with account. After this it only wait value changes.

![image](https://github.com/jaakka/tiaporta_minecraft_java_plugin/assets/25456491/15fa4778-a1ea-45b3-8a07-0740317c58fd)


### Setup:

#### steps in nut cover:
1. Download nodejs - this can run javascript "on your desktop"
2. Create folder for "logic communicate helper application"
3. Run cmd in folder and install libraries with ``npm install``
4. Download tiaportalservice.js
5. Now you can start service with cmd ``node tiaportalservice.js`` 
6. Before that create delicated minecraft server, i used "paper" for 1.20.4
7. Download my tiaportal plugin for server
8. if tiaportalservice.js running you can start server. You can manually connect to service with mc-command  ``/tiaportal reconnect``
9. remember start logic simulator or logic and test webpage before you try add to logic this.
10. Enjoy


You need install node js for your computer where you host mc-server.
In this version you need run this service same device than mc-server.  
![image](https://github.com/jaakka/tiaporta_minecraft_java_plugin/assets/25456491/2e537fdd-9661-4b32-b519-0ce3fc62d462)

You need install these libraries for nodejs:  

I used on my pc:  
npm version: 10.2.5  
nodejs version: 20.10.7  
  

  ![image](https://github.com/jaakka/tiaporta_minecraft_java_plugin/assets/25456491/54acc353-1ddd-4018-880b-927653ed36e8)

  
ws / websocket library version : 8.16.0


  install command:  
  
``npm install ws``  

  
![image](https://github.com/jaakka/tiaporta_minecraft_java_plugin/assets/25456491/d6c59ef4-9680-4fad-8fc9-994161e93011)  
  
  puppeteer library version : 21.7.0

install command:  
  
``npm install puppeteer``  

  ![image](https://github.com/jaakka/tiaporta_minecraft_java_plugin/assets/25456491/787cf45d-d9f7-4207-97cf-961cd7ed3c68)

This code communicate with mc-server and logic remote page.  
[Download nodejs helper](https://github.com/jaakka/tiaporta_minecraft_java_plugin/blob/master/tiaportalservice.js)

run this code with command:  

(in same folder, run libraries intall commands in same folder)  
``node tiaportalservice.js``  

This is server plugin for mc 1.20.4    
[Download tiaportal plugin](https://github.com/jaakka/tiaporta_minecraft_java_plugin/blob/master/tiaportal.jar)

I recommend to use this resource pack for this plugin to get full experiense.  
![image](https://github.com/jaakka/tiaporta_minecraft_java_plugin/assets/25456491/884aa6b3-0dc0-484e-bc45-767d0024dee8)

[Download resource pack for mc 1.20.4](https://github.com/jaakka/tiaporta_minecraft_java_plugin/files/14069900/tiaportal_plugin.zip)

  
For this example tiaportal project, webserver username and password 
  what you need in minecraft is admin and Password2024
  
![image](https://github.com/jaakka/tiaporta_minecraft_java_plugin/assets/25456491/ac9b0afc-9a39-4cb4-a103-7fce32648850)  
[exampleprojectfortiaportal18_zap.zip](https://github.com/jaakka/tiaporta_minecraft_java_plugin/files/14069941/exampleprojectfortiaportal18_zap.zip)
Before you try add logic to minecraft, test it first with your browser.   if it works, its open logic remote controll panel.



### In game & in use:
#### Basic commands:
Commands in minecraft server.  
`` /tiaportal `` or `` /tia ``  
if you run this command without subcommand, you get command list.
  
You can use subcommands  
`` /tiaportal <subcommand> ``  
  
and here is subcommands...   

Test minecraft server connection to nodejs service.  
``/tiaportal test ``
server test connection every time if you join the server.

#### Logic commands:

Add siemens logic to server.   
``/tiaportal login add <LogicNameForMinecraft> <LogicWebserverIp> ``  
You need start logic webserver before command.  
You can add real or virtual logics from local network.

Get list of added logics.   
``/tiaportal logic list ``  

Get tools for logics blocks 
``/tiaportal logic tools ``  

### Tools
![image](https://github.com/jaakka/tiaporta_minecraft_java_plugin/assets/25456491/c41e59c8-73a1-49bd-a555-db69e64d4617)
  
  With ``Fluke`` you can get block status and io name

![image](https://github.com/jaakka/tiaporta_minecraft_java_plugin/assets/25456491/41888130-434c-4205-ae39-e0f7e0cb30d4)
  
  With ``Logic tool`` you can get logic blocks with correct data.  
if you broke without tool, data will lost.


#### Logic io blocks commands:
Add input for logic
``/tiaportal io add output <LogicNameForMinecraft> <ioName>``  

Add output for logic
``/tiaportal io add input <LogicNameForMinecraft> <ioName>`` 

Warning: ioName = Logic-io-Name , or you can use io address from siemens logic.

Get logic io-list
``/tiaportal io list <LogicNameForMinecraft> ``
Get io-list for logic.

Get io blocks for logic
``/tiaportal io get blocks <LogicNameForMinecraft> ``

You get all added io for this logic to inventory.

In this image i added two outputs and tree inputs:  
  
![image](https://github.com/jaakka/tiaporta_minecraft_java_plugin/assets/25456491/e76af808-a2d0-47b8-aa29-d5d1d824fec7)

#### if you dont want all io blocks, You can use:
Get one output block  
``/tiaportal io get blocks <LogicNameForMinecraft> <ioName>``
  
![image](https://github.com/jaakka/tiaporta_minecraft_java_plugin/assets/25456491/38df201f-c4e6-49c7-91e8-53b30bef74e6)


Get one input block  
``/tiaportal io get blocks <LogicNameForMinecraft> <ioName> ``
  
![image](https://github.com/jaakka/tiaporta_minecraft_java_plugin/assets/25456491/ac94ab8b-0f81-402d-ac8c-283d9178fb23)

Remember subscribe my yt.

## Some testing videos
### Minecraft tiaportal plugin v1 - factory test
[![IMAGE ALT TEXT HERE](https://img.youtube.com/vi/vgkOpz_0OGI/0.jpg)](https://www.youtube.com/watch?v=vgkOpz_0OGI)

### Minecraft tiaportal plugin testing v00003 working in & out
[![IMAGE ALT TEXT HERE](https://img.youtube.com/vi/TU8-NgXsraQ/0.jpg)](https://www.youtube.com/watch?v=TU8-NgXsraQ)

### Tiaportal plugin for minecraft v0000004 & texturepack
[![IMAGE ALT TEXT HERE](https://img.youtube.com/vi/NGNyPkQIfpA/0.jpg)](https://www.youtube.com/watch?v=NGNyPkQIfpA)

### Tiaportal plugin for minecraft v0000003 & texturepack
[![IMAGE ALT TEXT HERE](https://img.youtube.com/vi/rE5Qsaq6u_g/0.jpg)](https://www.youtube.com/watch?v=rE5Qsaq6u_g)

### Minecraft tiaportal plugin dev v0000002
[![IMAGE ALT TEXT HERE](https://img.youtube.com/vi/TxTMIjJDcBU/0.jpg)](https://www.youtube.com/watch?v=TxTMIjJDcBU)

### Tiaportal plugin for minecraft v0000001 (First version for mc)
[![IMAGE ALT TEXT HERE](https://img.youtube.com/vi/61Q87OmctKQ/0.jpg)](https://www.youtube.com/watch?v=61Q87OmctKQ)
