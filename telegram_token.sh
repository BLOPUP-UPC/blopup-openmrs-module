TOKEN=$1

sed -i'' -e "s/YOUR_TOKEN_HERE/$TOKEN/g" api/src/main/resources/application.properties