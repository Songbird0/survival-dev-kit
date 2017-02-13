#!/bin/bash

mvn -v
mvn -X -e clean compile javadoc:javadoc package
