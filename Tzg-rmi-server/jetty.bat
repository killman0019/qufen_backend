@echo off
%~d0
cd %~dp0
mvn jetty:run -Dorg.eclipse.jetty.annotations.maxWait=160
