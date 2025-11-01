#!/bin/sh

# java -p $FX:$XML:target/classes --add-modules ALL-MODULE-PATH -m fx/fx.Main

CLASSES=./target/classes
FXX=/home/alexey/jfx
XMLL=/home/alexey/jlib/xml
LIB=./target/classes/lib

#java    -p $CLASSES:$FXX:$XMLL \
#        --add-modules ALL-MODULE-PATH \
#        -m fx/fx.Main
        
java    -p  $CLASSES:$FXX:$XMLL \
        --add-modules ALL-MODULE-PATH \
        -m fx/fx.Main
