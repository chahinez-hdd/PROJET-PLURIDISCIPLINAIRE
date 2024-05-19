@echo off
rem Compiler les fichiers Java
javac -cp lib\javaparser-core-3.23.1.jar -d bin src\*.java

rem Vérifier si la compilation a réussi
if %errorlevel% neq 0 (
    echo Compilation failed.
    exit /b %errorlevel%
)

rem Exécuter le programme
java -cp bin;lib\javaparser-core-3.23.1.jar MVCinfos
