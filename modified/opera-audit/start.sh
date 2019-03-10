#!/bin/bash

rm my.apk
apktool b opera-source -o my.apk
jarsigner -keystore resign.keystore my.apk alias_name < pass.txt
adb install -r my.apk