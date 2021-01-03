#!/bin/bash

cd ../classes/
if [ "$#" -ne 1 ]; then
    echo "Illegal number of inputs. Usage: ./run-server.sh port_number"
else
    java edu.illinois.web.distgrep.server.Server $1
fi
