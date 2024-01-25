#!/usr/bin/env bash

TOKEN=$1

sed -i'' -e "s/{TELEGRAM_BOT_TOKEN}/$TOKEN/g" api/src/main/resources/telegram.properties
