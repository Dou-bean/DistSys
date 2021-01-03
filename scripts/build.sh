#!/bin/bash

[ ! -d "../outputs" ] && mkdir ../outputs

cd ..
mvn package
