@echo off
setlocal enabledelayedexpansion

set API_KEY=%VIRUS_TOTAL_API_KEY%
if "%API_KEY%" == "" (
    echo API key not found. Please set VIRUS_TOTAL_API_KEY environment variable.
    exit /b 1
)

set "file_path=%~1"
set "file_path=!file_path:"=!"

"%~dp0jre\bin\java" -jar "%~dp0VirusTotalAdapter.jar" "!file_path!"