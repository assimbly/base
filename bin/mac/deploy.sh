#!/bin/bash
parent_path=$( cd "$(dirname "${BASH_SOURCE[0]}")" ; pwd -P )

cd "$parent_path" & 

mvn -f ../../activemqBase/pom.xml clean deploy &

mvn -f ../../camelBase/pom.xml clean deploy &

mvn -f ../../camelComponents/pom.xml clean deploy &

mvn -f ../../commonBase/pom.xml clean deploy &

mvn -f ../../databaseDrivers/pom.xml clean deploy &

mvn -f ../../extra/pom.xml clean deploy &

mvn -f ../../springBase/pom.xml clean deploy &

mvn -f ../../testBase/pom.xml clean deploy &

mvn -f ../../utils/pom.xml clean deploy