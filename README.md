# bgbilling-servlet-demo-clojure

## Что это?

bgbilling-servlet-demo-clojure - это Clojure-версия демонстрационной реализации сервлетов и фильтров для использования совместно с сервером [BGBilling](https://bgbilling.ru/). 

## Требования

* git
* JDK 8
* [Maven](https://maven.apache.org/)

## Как это установить? 

Выполните команды:

```
git clone https://github.com/alexanderfefelov/bgbilling-servlet-demo-clojure
cd bgbilling-servlet-demo-clojure
mvn package
```

Скопируйте jar-файл, созданный в результате в каталоге `target`, в каталог `lib/custom` вашего экземпляра BGBilling.

В конфигурацию BGBilling добавьте:

```
custom.servlet.keys=DemoServletClojure
#                   │                │
#                   └─────┬──────────┘
#                         │
#                   Ключ сервлета                                 Класс сервлета
#                         │                                              │
#              ┌──────────┴─────┐       ┌────────────────────────────────┴──────────────────────────────────┐
#              │                │       │                                                                   │
custom.servlet.DemoServletClojure.class=com.github.alexanderfefelov.bgbilling.servlet.demo.DemoServletClojure
custom.servlet.DemoServletClojure.mapping=/demo-servlet-clojure
#                                         │                   │
#                                         └─────────┬─────────┘
#                                                   │
#                                       Часть URL после контекста
```

Перезапустите BGBilling.

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

Для того, чтобы собирать логи сервлетов в отдельный файл, необходимо изменить `data/log4j.xml`.

Добавьте новый аппендер:

```xml
<appender name="SERVLET" class="org.apache.log4j.RollingFileAppender">
    <param name="File" value="log/servlet.log"/>
    <param name="MaxFileSize" value="50MB"/>
    <param name="MaxBackupIndex" value="3"/>
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
    <appender-ref ref="ERROR"/>
    <appender-ref ref="MQ"/>
    <appender-ref ref="SCRIPT"/>
    <appender-ref ref="SERVLET"/>
</appender>
```

## Что дальше?

* Ознакомьтесь с [описанием технологии Servlet](https://docs.oracle.com/javaee/7/tutorial/servlets.htm).
* Посмотрите аналогичные проекты на других языках:
  * Java - https://github.com/alexanderfefelov/bgbilling-servlet-demo,
  * Kotlin - https://github.com/alexanderfefelov/bgbilling-servlet-demo-kotlin,
  * Scala - https://github.com/alexanderfefelov/bgbilling-servlet-demo-scala.
