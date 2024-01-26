<h1>Siemens logic to minecraft</h1>

### Setup:

You need install node js for your computer where you host mc-server.
In this version you need run this service same device than mc-server.  
![image](https://github.com/jaakka/tiaporta_minecraft_java_plugin/assets/25456491/2e537fdd-9661-4b32-b519-0ce3fc62d462)

You need install these libraries for nodejs:  

I used on my pc:  
npm version: 10.2.5  
nodejs version: 20.10.7  
  

  ![image](https://github.com/jaakka/tiaporta_minecraft_java_plugin/assets/25456491/54acc353-1ddd-4018-880b-927653ed36e8)

  
ws / websocket library version : 8.16.0

  
![image](https://github.com/jaakka/tiaporta_minecraft_java_plugin/assets/25456491/d6c59ef4-9680-4fad-8fc9-994161e93011)  
  
  puppeteer library version : 21.7.0

  ![image](https://github.com/jaakka/tiaporta_minecraft_java_plugin/assets/25456491/787cf45d-d9f7-4207-97cf-961cd7ed3c68)

This code communicate with mc-server and logic remote page.  
[Download nodejs helper](https://github.com/jaakka/tiaporta_minecraft_java_plugin/tiaportalservice.js)

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
