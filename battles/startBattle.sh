#!/bin/bash

# Start battle and dump output and results to file

STAMP=`date +%H-%M-%S`
DIR=${STAMP}

RUN="java -cp ../libs/robocode.jar robocode.Robocode -battle havok.battle -recordXML $DIR/log.xml -nodisplay -results $DIR/results.txt"

$RUN
