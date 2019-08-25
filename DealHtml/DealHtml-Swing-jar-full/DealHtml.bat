@echo off
::lib目录需添加此处，并加上 “ \* ”
set CLASSPATH=%CLASSPATH%;  
set PATH=%PATH%  
set JAVA_HOME=%JAVA_HOME%
::退出默认c盘路径进入盘
d:
::打开指定目录
cd D:\A-可执行工具jar
::初次使用需编译（稳定后需关闭编译）
java -jar deal-html-swing-full.jar
exit
@pause