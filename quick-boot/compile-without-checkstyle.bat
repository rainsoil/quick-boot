@echo off
echo 正在编译项目（跳过Checkstyle检查）...

REM 设置Java环境
set JAVA_HOME=C:\Program Files\Java\jdk-17

REM 编译项目，跳过Checkstyle和测试
mvn clean compile -DskipTests -Dmaven.checkstyle.skip=true -Dcheckstyle.skip=true

if %ERRORLEVEL% EQU 0 (
    echo 编译成功！
) else (
    echo 编译失败，错误代码: %ERRORLEVEL%
)

pause
