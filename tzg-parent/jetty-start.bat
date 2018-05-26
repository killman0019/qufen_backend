@echo off
%~d0
cd %~dp0

 
start cmd /k %~dp0\Tzg-rmi-server\jetty.bat

ping -n 10 127.1>null

start cmd /k %~dp0\Tzg-fixedBao\Tzg-fixedBao-provider\jetty.bat
ping -n 10 127.1>null
 
start cmd /k %~dp0Tzg-currentBao\Tzg-currentBao-provider\jetty.bat
ping -n 10 127.1>null
 
start cmd /k %~dp0\Tzg-pyramid\Tzg-pyramid-provider\jetty.bat
 

