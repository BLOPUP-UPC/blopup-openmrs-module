TOKEN=$1

sed -i'' -copy "s/YOUR_TOKEN_HERE/$TOKEN/g" api/src/main/resources/telegram.properties