#!/bin/bash

./gradlew --version

./gradlew build || exit 1
./gradlew cloverAggregateReports || exit 1
ls -l
scripts/coverage_summary.sh
ls -l /
ls -l /coverage-out/
cp -r build/reports/clover/html/* /coverage-out/ || exit 1
