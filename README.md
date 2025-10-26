addr
===

Пример программы [по учебнику](https://code.makery.ch/ru/library/javafx-tutorial/) Учебник писался давно, 
поэтому к текущему моменту - 2025-й год кое что не работает. Работа с XML перекочевала в Java-EE 
поэтому в **pom.xml** нужно подключать зависимости **Jakarta**

Класс **LocalDate** не хочет корректно сохраняться в XML точнее вообще не сохраняется. 
Надо переписать на просто строку?

Переписал дату рождения в **Person** на простую строку...


Компиляция
===

```shell
mvn clean
mvn compile
```

Соберем полностью готовое приложение к распространению. Собралось все в архив, 
но работать с файлами отказывается... Не хватает каких то зависимостей.

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

После подключения в **pom.xml** нужных библиотек их можно добавить в каталог **/home/alexey/jlib/xml**:

```shell
alexey@honor:~/jsrc/addr$ ll /home/alexey/jlib/xml/
итого 1,4M
drwxrwxr-x 2 alexey 4,0K окт 20 20:18 ./
drwxrwxr-x 3 alexey 4,0K окт 19 12:48 ../
-rw-rw-r-- 1 alexey  26K мар 28  2022 istack-commons-runtime-4.1.1.jar
-rw-rw-r-- 1 alexey  62K дек  2  2021 jakarta.activation-api-2.1.0.jar
-rw-rw-r-- 1 alexey 125K окт 19 19:14 jakarta.xml.bind-api-4.0.0.jar
-rw-rw-r-- 1 alexey 138K мая 20  2022 jaxb-core-4.0.0.jar
-rw-rw-r-- 1 alexey 897K мая 20  2022 jaxb-runtime-4.0.0.jar
-rw-rw-r-- 1 alexey  73K мая 20  2022 txw2-4.0.0.jar
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

