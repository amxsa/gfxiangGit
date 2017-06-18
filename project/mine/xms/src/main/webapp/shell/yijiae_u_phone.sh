#!/bin/sh
#
# 移动yijiae_u_phone.apk文件
#
oldFileName="$1"
NewFileName="yijiae_u_phone.apk"
pathCore="/usr/local/resin-pro-4.0.32/webapps/chims/upload/soft"
pathNew="/usr/local/nginx/html/download"
echo "${pathNew}/${NewFileName}"
echo "${pathCore}/${oldFileName}"
rm -f ${pathNew}/${NewFileName}
mv ${pathCore}/${oldFileName} ${pathNew}/${NewFileName}


