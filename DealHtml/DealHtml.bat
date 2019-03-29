@echo off
::配置环境变量
set CLASSPATH=%CLASSPATH%;  
set PATH=%PATH%  
set JAVA_HOME=%JAVA_HOME%
::退出默认c盘路径进入xxx盘
e:
::打开指定目录
cd E:\utils\DealHtml
::初次使用需编译（稳定后可关闭编译）
javac  -encoding utf-8 DealHtml.java
java  DealHtml
::exit
@pause