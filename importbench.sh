#!/bin/bash
set -e
set -u

BERKELEY_DIR=`sed -rn 's/.*String BERKELEY_PATH = "(.*)";.*$/\1/p' src/main/java/com/optimaize/titangraph/importbench/Config.java`

[ -z "$BERKELEY_DIR" ] && { echo "Failed to detect BDB directory"; exit 1; }

rm -rf $BERKELEY_DIR
mkdir -p $BERKELEY_DIR
mvn exec:java -Dexec.mainClass=com.optimaize.titangraph.importbench.App 2>&1 | tee importbench.log
echo 'txcount,ms' > importbench.csv
cat importbench.log | sed -rn 's/.* \.\.\. done ([0-9]+) so far ([0-9]+)ms.*$/\1,\2/p' >> importbench.csv
R --no-save < plotbench.R
