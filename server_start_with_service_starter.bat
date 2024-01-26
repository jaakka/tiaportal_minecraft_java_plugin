@echo off
start "Minecraft Server" java -Xmx1G -jar paper-1.20.4-365.jar nogui
start "Tiaportal communicte helper" node tiaportal_helper/tiaportalservice.js