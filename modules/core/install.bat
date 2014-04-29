@echo off
echo [INFO] Install Core module jar to local repository.

cd %~dp0
call mvn clean install
pause