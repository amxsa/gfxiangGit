function formatTime(date) {
  var year = date.getFullYear()
  var month = date.getMonth() + 1
  var day = date.getDate()

  var hour = date.getHours()
  var minute = date.getMinutes()
  var second = date.getSeconds()


  return [year, month, day].map(formatNumber).join('/') + ' ' + [hour, minute, second].map(formatNumber).join(':')
}

function formatNumber(n) {
  n = n.toString()
  return n[1] ? n : '0' + n
}

module.exports = {
  formatTime: formatTime
}

// "telephone":{
// 						"regex":"/^(0[0-9]{2,3}\-)?([2-9][0-9]{6,7})+(\-[0-9]{1,4})?$/",
// 						"alertText":"* 请输入有效的电话号码,如:010-29292929."},
// 					"mobilephone":{
// 						"regex":"/(^0?[1][358][0-9]{9}$)/",
// 						"alertText":"* 请输入有效的手机号码."},	
// 					"email":{
// 						"regex":"/^[a-zA-Z0-9_\.\-]+\@([a-zA-Z0-9\-]+\.)+[a-zA-Z0-9]{2,4}$/",
// 						"alertText":"* 请输入有效的邮件地址."},	
// 					"date":{
//                          "regex":"/^(([0-9]{3}[1-9]|[0-9]{2}[1-9][0-9]{1}|[0-9]{1}[1-9][0-9]{2}|[1-9][0-9]{3})-(((0[13578]|1[02])-(0[1-9]|[12][0-9]|3[01]))|((0[469]|11)-(0[1-9]|[12][0-9]|30))|(02-(0[1-9]|[1][0-9]|2[0-8]))))|((([0-9]{2})(0[48]|[2468][048]|[13579][26])|((0[48]|[2468][048]|[3579][26])00))-02-29)$/",
//                          "alertText":"* 请输入有效的日期,如:2008-08-08."},
// 					"ip":{
//                          "regex":"/^(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$/",
//                          "alertText":"* 请输入有效的IP."},
// 					"chinese":{
// 						"regex":"/^[\u4e00-\u9fa5]+$/",
// 						"alertText":"* 请输入中文."},
// 					"url":{
// 						"regex":"/^[a-zA-z]:\\/\\/[^s]$/",
// 						"alertText":"* 请输入有效的网址."},
// 					"zipcode":{
// 						"regex":"/^[1-9]\d{5}$/",
// 						"alertText":"* 请输入有效的邮政编码."},
// 					"qq":{
// 						"regex":"/^[1-9]\d{4,9}$/",
// 						"alertText":"* 请输入有效的QQ号码."},
// 					"onlyNumber":{
// 						"regex":"/^[0-9]+$/",
// 						"alertText":"* 请输入数字."},
// 					"onlyLetter":{
// 						"regex":"/^[a-zA-Z]+$/",
// 						"alertText":"* 请输入英文字母."},
// 					"noSpecialCaracters":{
// 						"regex":"/^[0-9a-zA-Z]+$/",
// 						"alertText":"* 请输入英文字母或数字."},	

//http://www.cnblogs.com/phpshen/p/6054557.html
//http://www.jb51.net/article/103175.htm
//http://blog.csdn.net/zgmu/article/details/53389854