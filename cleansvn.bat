@echo off   
set beginDir="."  
rem echo Search Dir is %beginDir%   
echo.   
for /f "tokens=* delims=" %%i in ('dir /ad /b/s %beginDir% ^| findstr "\\\.svn$"') do (   
 rmdir /S /Q "%%i"  
 echo "%%i"  
)   
echo.   
echo Deleted successful.   
echo.   
pause