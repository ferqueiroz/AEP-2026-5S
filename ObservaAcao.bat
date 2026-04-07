@echo off
chcp 65001

echo Rodando sistema...
echo.

mvn clean compile
echo.

mvn exec:java -Dexec.mainClass="org.ObservaAcao.Main"
echo.

echo Fim da execucao
pause