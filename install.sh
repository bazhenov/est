#/bin/sh

OUT_FILE=/usr/local/bin/est.jar

curl -o $OUT_FILE https://dl.dropboxusercontent.com/u/16254496/est.jar
echo "#\!/bin/sh
java -jar /usr/local/bin/est.jar \$@" > /usr/local/bin/est
chmod a+x /usr/local/bin/est