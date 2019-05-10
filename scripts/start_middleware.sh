#!/bin/bash

# remove current postgres container
docker rm -f postgres

docker run -d --name postgres --restart always -p 5432:5432 postgres:11