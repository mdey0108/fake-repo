@echo off
set JAVA_HOME=C:\path\to\java\version
set LOG_FILE_LOCATION=C:\path\to\logs\application.log

%JAVA_HOME%\bin\java -Dlog.file.location=%LOG_FILE_LOCATION% -jar runreport.jar --spring.config.location=C:/config/app.config