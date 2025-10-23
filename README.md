Компиляция
===

```shell
mvn clean
mvn compile
```

Соберем полностью готовое приложение к распространению

```shell
mvn javafx:jlink
```

Соберем пакет (**JAR**)

```shell
mvn package
```

Запуск
===

Запуск из **mvn**
---

```shell
mvn javafx:run
```

Запуск скомпилированного проекта
---

Тут отдельной опцией **-cp** укажем Class Path В конце указываем MAIN-класс

```shell
alexey@honor:~/jsrc/TeezD$ java -p $FX -cp target/classes --add-modules ALL-MODULE-PATH  fx.Main
```

Мы собирали модульный проект. Тут при запуске указываем Class Path сразу в опции **-p** \
В опции **-m** указываем модуль и Main-class

```shell
alexey@honor:~/jsrc/TeezD$ java -p $FX:target/classes --add-modules ALL-MODULE-PATH -m fx/fx.Main
```

Запуск из **JAR**
---

```shell
alexey@honor:~/jsrc/TeezD$ java --module-path $FX --add-modules javafx.controls,javafx.fxml -jar target/TeezD-1.jar
```

Короткая форма. Вместо нужных нам модулей используем **ALL-MODULE-PATH**

```shell
alexey@honor:~/jsrc/TeezD$ java -p $FX --add-modules ALL-MODULE-PATH -jar target/TeezD-1.jar
```

Подключение jaxb-api
---

```
<dependency>
    <groupId>javax.xml.bind</groupId>
    <artifactId>jaxb-api</artifactId>
    <version>2.3.1</version>
</dependency>
```

После подключения в **pom.xml** получаем ошибку отсутствия библиотек при запуске из командной строки

```shell
alexey@honor:~/jsrc/addr$ java -p $FX:target/classes --add-modules ALL-MODULE-PATH -m fx/fx.Main
```

Можно добавить эти библиотеки в путь **$FX**

```shell
alexey@honor:~/jsrc/addr$ ll ~/jfx/*api*
-rw-rw-r-- 1 alexey  56K окт 19 12:03 /home/alexey/jfx/javax.activation-api-1.2.0.jar
-rw-rw-r-- 1 alexey 126K окт 19 12:03 /home/alexey/jfx/jaxb-api-2.3.1.jar
```

Можно создать специально для этих библиотек отдельный каталог **/home/alexey/jlib/xml** 
положить туда нужные библиотеки

```shell
alexey@honor:~/jsrc/addr$ ll ~/jlib/xml/
итого 192K
drwxrwxr-x 2 alexey 4,0K окт 19 12:56 ./
drwxrwxr-x 3 alexey 4,0K окт 19 12:48 ../
-rw-rw-r-- 1 alexey  56K окт 19 12:03 javax.activation-api-1.2.0.jar
-rw-rw-r-- 1 alexey 126K окт 19 12:03 jaxb-api-2.3.1.jar
```

Запускать командой с указанием пути к библиотекам:

```shell
alexey@honor:~/jsrc/addr$ java -p $FX:/home/alexey/jlib/xml:target/classes --add-modules ALL-MODULE-PATH -m fx/fx.Main
```

Написать файлик запуска **start.sh**

```shell
#!/bin/sh

# java -p $FX:$XML:target/classes --add-modules ALL-MODULE-PATH -m fx/fx.Main

CLASSES=./target/classes
FXX=/home/alexey/jfx
XMLL=/home/alexey/jlib/xml

java -p $CLASSES:$FXX:$XMLL \
    --add-modules ALL-MODULE-PATH \
    -m fx/fx.Main
```

