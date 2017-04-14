#!/bin/bash

rm ~/.neo4j/known_hosts

docker run --rm -p 7474:7474 -p 7687:7687 --env=NEO4J_AUTH=none neo4j:3.0
