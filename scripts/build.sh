#!/bin/bash

[ ! -d "../classes" ] && mkdir ../classes
[ ! -d "../outputs" ] && mkdir ../outputs

javac ../src/edu/illinois/web/distgrep/*.java -d ../classes/ -cp ../classes/
javac ../src/edu/illinois/web/distgrep/client/*.java -d ../classes/ -cp ../classes/
javac ../src/edu/illinois/web/distgrep/server/*.java -d ../classes/ -cp ../classes/
