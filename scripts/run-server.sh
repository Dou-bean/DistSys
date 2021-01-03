#!/bin/bash

cd ../target/classes/
if [ "$#" -ne 1 ]; then
    echo "Illegal number of inputs. Usage: ./run-server.sh port_number"
else
    java -classpath .:./../lib/fastjson-1.2.47.jar edu.illinois.web.distgrep.server.ServerUserInterface $1
fi
