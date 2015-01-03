#/bin/sh
OUT_FILE=/usr/local/bin/est

curl -o $OUT_FILE https://raw.githubusercontent.com/bazhenov/est/master/cardinality
chmod +x $OUT_FILE