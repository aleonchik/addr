#!/bin/sh

java -classpath /home/alexey/.m2/repository/org/openjfx/javafx-controls/21.0.8/javafx-controls-21.0.8.jar:\
    /home/alexey/.m2/repository/org/openjfx/javafx-graphics/21.0.8/javafx-graphics-21.0.8.jar:\
    /home/alexey/.m2/repository/org/openjfx/javafx-base/21.0.8/javafx-base-21.0.8.jar:\
    /home/alexey/.m2/repository/org/openjfx/javafx-fxml/21.0.8/javafx-fxml-21.0.8.jar \
    -p /home/alexey/.m2/repository/org/glassfish/jaxb/jaxb-core/4.0.0/jaxb-core-4.0.0.jar:\
    /home/alexey/.m2/repository/com/sun/istack/istack-commons-runtime/4.1.1/istack-commons-runtime-4.1.1.jar:\
    /home/alexey/.m2/repository/org/eclipse/angus/angus-activation/1.0.0/angus-activation-1.0.0.jar:\
    /home/alexey/.m2/repository/org/openjfx/javafx-graphics/21.0.8/javafx-graphics-21.0.8-linux.jar:\
    /home/alexey/.m2/repository/org/glassfish/jaxb/jaxb-runtime/4.0.0/jaxb-runtime-4.0.0.jar:\
    /home/alexey/.m2/repository/org/openjfx/javafx-base/21.0.8/javafx-base-21.0.8-linux.jar:\
    /home/alexey/.m2/repository/jakarta/xml/bind/jakarta.xml.bind-api/4.0.0/jakarta.xml.bind-api-4.0.0.jar:\
    /home/alexey/.m2/repository/org/glassfish/jaxb/txw2/4.0.0/txw2-4.0.0.jar:\
    /home/alexey/jsrc/addr/target/classes:\
    /home/alexey/.m2/repository/org/openjfx/javafx-fxml/21.0.8/javafx-fxml-21.0.8-linux.jar:\
    /home/alexey/.m2/repository/org/openjfx/javafx-controls/21.0.8/javafx-controls-21.0.8-linux.jar:\
    /home/alexey/.m2/repository/jakarta/activation/jakarta.activation-api/2.1.0/jakarta.activation-api-2.1.0.jar \
    -m fx/fx.Main

