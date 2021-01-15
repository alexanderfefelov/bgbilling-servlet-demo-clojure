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

## О системе

- [SysInfoClojure.clj](src/main/clojure/com/github/alexanderfefelov/bgbilling/servlet/demo/SysInfoClojure.clj)
- [UptimePuncherFilterClojure.clj](src/main/clojure/com/github/alexanderfefelov/bgbilling/servlet/demo/UptimePuncherFilterClojure.clj)

Добавьте в конфигурацию BGBilling:

```properties
# Servlet: О системе
#
custom.servlet.keys=SysInfoClojure
#                   │            │
#                   └───┬───────┘
#                       │
#                 Ключ сервлета                              Класс сервлета
#                       │                                           │
#              ┌────────┴───┐       ┌───────────────────────────────┴───────────────────────────────┐
#              │            │       │                                                               │
custom.servlet.SysInfoClojure.class=com.github.alexanderfefelov.bgbilling.servlet.demo.SysInfoClojure
custom.servlet.SysInfoClojure.mapping=/demo-servlet/sys-info-clojure
#                                     │                            │
#                                     └──────────────┬─────────────┘
#                                                    │
#                                        Часть URL после контекста
#
custom.servlet.SysInfoClojure.filter.keys=UptimePuncherClojure,TerryPratchettClojure,CORS
#                                         │                  │ │                   │ │  │
#                                         └──────┬───────────┘ └─────────┬─────────┘ └─┬┘
#                                                │                       │             │
#                                           Ключ фильтра            Ещё фильтр       И ещё один
#                                                │
#                                    ┌───────────┴──────┐
#                                    │                  │
custom.servlet.SysInfoClojure.filter.UptimePuncherClojure.name=UptimePuncherClojure
custom.servlet.SysInfoClojure.filter.UptimePuncherClojure.class=com.github.alexanderfefelov.bgbilling.servlet.demo.UptimePuncherFilterClojure
#                                                               │                                                                           │
#                                                               └──────────────────────────────────┬────────────────────────────────────────┘
#                                                                                                  │
#                                                                                            Класс фильтра
custom.servlet.SysInfoClojure.filter.TerryPratchettClojure.name=TerryPratchettClojure
custom.servlet.SysInfoClojure.filter.TerryPratchettClojure.class=com.github.alexanderfefelov.bgbilling.servlet.demo.TerryPratchettFilterClojure
custom.servlet.SysInfoClojure.filter.CORS.name=CORS
custom.servlet.SysInfoClojure.filter.CORS.class=org.apache.catalina.filters.CorsFilter
custom.servlet.SysInfoClojure.filter.CORS.init-param.keys=AllowedOrigins
#                                                         │            │
#                                                         └───┬────────┘
#                                                             │
#                                                       Ключ параметра    Название параметра
#                                                             │                    │
#                                                    ┌────────┴───┐      ┌─────────┴────────┐
#                                                    │            │      │                  │
custom.servlet.SysInfoClojure.filter.CORS.init-param.AllowedOrigins.name=cors.allowed.origins
custom.servlet.SysInfoClojure.filter.CORS.init-param.AllowedOrigins.value=*
#                                                                         │
#                                                                         │
#                                                                Значение параметра
```

Перезапустите BGBilling.

Теперь в логах будет так:

```
01-15/07:36:36  INFO [main] Server - Add custom servlet from setup...
01-15/07:36:36  INFO [main] Server - Custom.servlet.keys => SysInfoClojure
01-15/07:36:36  INFO [main] Server - Custom.servlet.class => com.github.alexanderfefelov.bgbilling.servlet.demo.SysInfoClojure
01-15/07:36:36  INFO [main] Server - Custom.servlet.mapping => /demo-servlet/sys-info-clojure
01-15/07:36:38  INFO [main] Server - Add mapping: com.github.alexanderfefelov.bgbilling.servlet.demo.SysInfoClojure to /demo-servlet/sys-info-clojure
01-15/07:36:38  INFO [main] Server - Add mapping: com.github.alexanderfefelov.bgbilling.servlet.demo.UptimePuncherFilterClojure to /demo-servlet/sys-info-clojure
01-15/07:36:38  INFO [main] Server - Add mapping: com.github.alexanderfefelov.bgbilling.servlet.demo.TerryPratchettFilterClojure to /demo-servlet/sys-info-clojure
01-15/07:36:38  INFO [main] Server - Add mapping: org.apache.catalina.filters.CorsFilter to /demo-servlet/sys-info-clojure
```

и в ответ на запрос:

```
http --verbose --check-status \
  GET http://bgbilling-server.backpack.test:63081/billing/demo-servlet/sys-info-clojure \
    "Origin: http://example.com"
```

```
GET /billing/demo-servlet/sys-info-clojure HTTP/1.1
Accept: */*
Accept-Encoding: gzip, deflate
Connection: keep-alive
Host: bgbilling-server.backpack.test:63081
Origin: http://example.com
User-Agent: HTTPie/1.0.3
```

вы получите:

```
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

В результате после перезапуска BGBilling в файле `log/servlet.log` можно будет увидеть что-то вроде:

```
01-15/07:52:33 TRACE [localhost.localdomain-startStop-1] UptimePuncherFilterClojure - init
01-15/07:52:33 TRACE [localhost.localdomain-startStop-1] TerryPratchettFilterClojure - init
01-15/07:52:33 TRACE [http-nio-0.0.0.0-8080-exec-1] SysInfoClojure - init
01-15/07:52:33 TRACE [http-nio-0.0.0.0-8080-exec-1] UptimePuncherFilterClojure - doFilter
01-15/07:52:33 TRACE [http-nio-0.0.0.0-8080-exec-1] TerryPratchettFilterClojure - doFilter
01-15/07:52:33 TRACE [http-nio-0.0.0.0-8080-exec-1] SysInfoClojure - doGet
```

## Что дальше?

* Ознакомьтесь с [описанием технологии Servlet](https://docs.oracle.com/javaee/7/tutorial/servlets.htm).
* Изучите [список фильтров, встроенных в Tomcat 8.5](https://tomcat.apache.org/tomcat-8.5-doc/config/filter.html).
* Посмотрите аналогичные проекты на других языках:
  * Java - https://github.com/alexanderfefelov/bgbilling-servlet-demo,
  * Kotlin - https://github.com/alexanderfefelov/bgbilling-servlet-demo-kotlin,
  * Scala - https://github.com/alexanderfefelov/bgbilling-servlet-demo-scala.
