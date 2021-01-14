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

## Привет, мир!

- [HelloWorldClojure.clj](src/main/clojure/com/github/alexanderfefelov/bgbilling/servlet/demo/HelloWorldClojure.clj)
- [TerryPratchettFilterClojure.clj](src/main/clojure/com/github/alexanderfefelov/bgbilling/servlet/demo/TerryPratchettFilterClojure.clj)

В конфигурацию BGBilling добавьте:

```properties
# Servlet: Привет, мир!
#
custom.servlet.keys=HelloWorldClojure
#                   │               │
#                   └────┬──────────┘
#                        │
#                  Ключ сервлета                                 Класс сервлета
#                        │                                              │
#              ┌─────────┴─────┐       ┌────────────────────────────────┴─────────────────────────────────┐
#              │               │       │                                                                  │
custom.servlet.HelloWorldClojure.class=com.github.alexanderfefelov.bgbilling.servlet.demo.HelloWorldClojure
custom.servlet.HelloWorldClojure.mapping=/demo-servlet/hello-world-clojure
#                                        │                               │
#                                        └───────────────┬───────────────┘
#                                                        │
#                                            Часть URL после контекста
#
custom.servlet.HelloWorldClojure.filter.keys=TerryPratchettClojure
#                                            │                   │
#                                            └──────┬────────────┘
#                                                   │
#                                              Ключ фильтра
#                                                   │
#                                       ┌───────────┴───────┐
#                                       │                   │
custom.servlet.HelloWorldClojure.filter.TerryPratchettClojure.name=TerryPratchettClojure
custom.servlet.HelloWorldClojure.filter.TerryPratchettClojure.class=com.github.alexanderfefelov.bgbilling.servlet.demo.TerryPratchettFilterClojure
#                                                                   │                                                                            │
#                                                                   └──────────────────────────────────┬─────────────────────────────────────────┘
#                                                                                                      │
#                                                                                                Класс фильтра
```

Перезапустите BGBilling.

Если всё в порядке, в логах можно будет увидеть:

```
01-14/22:09:18  INFO [main] Server - Add custom servlet from setup...
01-14/22:09:18  INFO [main] Server - Custom.servlet.keys => HelloWorldClojure
01-14/22:09:18  INFO [main] Server - Custom.servlet.class => com.github.alexanderfefelov.bgbilling.servlet.demo.HelloWorldClojure
01-14/22:09:18  INFO [main] Server - Custom.servlet.mapping => /demo-servlet/hello-world-clojure
01-14/22:09:21  INFO [main] Server - Add mapping: com.github.alexanderfefelov.bgbilling.servlet.demo.HelloWorldClojure to /demo-servlet/hello-world-clojure
01-14/22:09:21  INFO [main] Server - Add mapping: com.github.alexanderfefelov.bgbilling.servlet.demo.TerryPratchettFilterClojure to /demo-servlet/hello-world-clojure
```

Теперь выполните:

```
http --verbose --check-status \
  GET http://bgbilling-server.backpack.test:63081/billing/demo-servlet/hello-world-clojure
```

В результате на запрос:

```
GET /billing/demo-servlet/hello-world-clojure HTTP/1.1
Accept: */*
Accept-Encoding: gzip, deflate
Connection: keep-alive
Host: bgbilling-server.backpack.test:63081
User-Agent: HTTPie/1.0.3
```

будет получен ответ:

```
HTTP/1.1 200 OK
Content-Length: 14
Date: Thu, 14 Jan 2021 19:19:27 GMT
X-Clacks-Overhead: GNU Terry Pratchett

Hello, World!
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

В результате после перезапуска BGBilling в файле log/servlet.log можно будет увидеть что-то вроде:

```
01-14/22:19:26 TRACE [localhost.localdomain-startStop-1] TerryPratchettFilterClojure - init
01-14/22:19:27 TRACE [http-nio-0.0.0.0-8080-exec-1] HelloWorldClojure - init
01-14/22:19:27 TRACE [http-nio-0.0.0.0-8080-exec-1] TerryPratchettFilterClojure - doFilter
01-14/22:19:27 TRACE [http-nio-0.0.0.0-8080-exec-1] HelloWorldClojure - doGet
```

## Что дальше?

* Ознакомьтесь с [описанием технологии Servlet](https://docs.oracle.com/javaee/7/tutorial/servlets.htm).
* Изучите [список фильтров, встроенных в Tomcat 8.5](https://tomcat.apache.org/tomcat-8.5-doc/config/filter.html).
* Посмотрите аналогичные проекты на других языках:
  * Java - https://github.com/alexanderfefelov/bgbilling-servlet-demo,
  * Kotlin - https://github.com/alexanderfefelov/bgbilling-servlet-demo-kotlin,
  * Scala - https://github.com/alexanderfefelov/bgbilling-servlet-demo-scala.
