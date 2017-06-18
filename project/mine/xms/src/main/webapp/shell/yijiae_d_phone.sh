#!/bin/sh
#
# 移动yijiae_d_phone.apk文件
#
oldFileName="$1"
NewFileName="yijiae_d_phone.apk"
pathCore="/usr/local/resin-pro-4.0.32/webapps/chims/upload/soft"
pathNew="/usr/local/nginx/html/download"
echo "${pathNew}/${NewFileName}"
echo "${pathCore}/${oldFileName}"
rm -f ${pathNew}/${NewFileName}
mv ${pathCore}/${oldFileName} ${pathNew}/${NewFileName}


