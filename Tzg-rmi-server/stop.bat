@echo off
%~d0
cd %~dp0
mvn jetty:stop
exit
