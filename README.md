# bgbilling-servlet-demo-clojure

## Что это?

bgbilling-servlet-demo-clojure -- это Clojure-версия демонстрационной реализации сервлета для использования совместно
с сервером [BGBilling](https://bgbilling.ru/). 

## Требования

* git
* JDK 8
* [Maven](https://maven.apache.org/)

## Как это запустить? 

```
git clone https://github.com/alexanderfefelov/bgbilling-servlet-demo-clojure.git
cd bgbilling-servlet-demo-clojure
mvn package
```

jar-файл, созданный в результате в каталоге `target`, скопируйте в каталог `lib/app` сервера BGBilling.

В конфигурацию BGBilling добавьте:

```
custom.servlet.keys=DemoServletClojure
#                    /                                     Fully qualified class name
#                   |                                                  |
#                   v                   /------------------------------+------------------------------------\
custom.servlet.DemoServletClojure.class=com.github.alexanderfefelov.bgbilling.servlet.demo.DemoServletClojure
custom.servlet.DemoServletClojure.mapping=/demo-servlet-clojure
#                                         \---------+---------/
#                                                   |
#                                    Part of URL after /bgbilling
```
Перезапустите сервер BGBilling.

Для проверки выполните:

```
curl --request GET --include http://YOUR.BGBILLING.HOST:8080/bgbilling/demo-servlet-clojure
```

В ответ вы получите что-то вроде такого:

```
HTTP/1.1 200 OK
Content-Length: 52
Date: Thu, 28 Mar 2019 08:56:34 GMT
Connection: close

Hello, World!
kernel 7.1.1112 / 15.03.2019 16:43:56
```

## Логи

С дефолтными настройками BGBilling все логи из сервлета будут попадать в файл `log/server.log`.
Для того, чтобы логи собирались в отдельном файле, необходимо изменить `data/log4j.xml`.

Добавьте новый аппендер:

```xml
<appender name="SERVLET" class="org.apache.log4j.RollingFileAppender">
    <param name="File" value="${log.dir.path}${log.prefix}.servlet.log"/>
    <param name="MaxFileSize" value="100MB"/>
    <param name="MaxBackupIndex" value="2"/>
    <param name="Append" value="true"/>

    <layout class="org.apache.log4j.PatternLayout">
        <param name="ConversionPattern" value="%d{MM-dd/HH:mm:ss} %5p [%t] %c{1} - %m%n"/>
    </layout>

    <filter class="ru.bitel.common.logging.Log4JMDCFilter">
        <param name="key" value="nestedContext"/>
        <param name="value" value="servlet"/>
    </filter>
</appender>
```

и исправьте имеющийся:

```xml
<appender name="ASYNC" class="ru.bitel.common.logging.Log4jAsyncAppender">
    <appender-ref ref="APPLICATION"/>
    <appender-ref ref="MQ"/>
    <appender-ref ref="SERVLET"/>
    <appender-ref ref="SCRIPT"/>
    <appender-ref ref="ERROR"/>
</appender>
```

## Что дальше?

* Ознакомьтесь с [описанием технологии Servlet](https://docs.oracle.com/javaee/7/tutorial/servlets.htm).
* Посмотрите аналогичные проекты на языках [Java](https://github.com/alexanderfefelov/bgbilling-servlet-demo),
  [Scala](https://github.com/alexanderfefelov/bgbilling-servlet-demo-scala)
  и [Kotlin](https://github.com/alexanderfefelov/bgbilling-servlet-demo-kotlin).
