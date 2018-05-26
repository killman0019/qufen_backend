@echo off
%~d0
start%~dp0


start %~dp0\Tzg-rmi-server\stop.bat



start %~dp0\Tzg-fixedBao\Tzg-fixedBao-provider\stop.bat


 
start %~dp0Tzg-currentBao\Tzg-currentBao-provider\stop.bat

 
start %~dp0\Tzg-pyramid\Tzg-pyramid-provider\stop.bat
 

