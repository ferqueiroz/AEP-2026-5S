@echo off
cls

call mvn clean compile
cls

if %errorlevel% neq 0 (
    echo ❌ Erro na compilacao
    pause
    exit /b
)

cls
call mvn exec:java -Dexec.mainClass="org.ObservaAcao.Main"
cls

pause