//index.js
var util = require('../../utils/util.js')

var reg_mobile = /(^0?[1][358][0-9]{9}$)/;//手机号
var reg_phone = /^(0[0-9]{2,3}\-)?([2-9][0-9]{6,7})+(\-[0-9]{1,4})?$/;//电话号码
var reg_mail = /^[a-zA-Z0-9_\.\-]+\@([a-zA-Z0-9\-]+\.)+[a-zA-Z0-9]{2,4}$/;//email
var reg_ip = /^(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$/;//ip
var reg_cn = /^[\u4e00-\u9fa5]+$/;//中文
var reg_code = /^[1-9]\d{5}$/;//邮政编码
var reg_number = /^[0-9]+$/;//数字
var reg_en = /^[a-zA-Z]+$/;//英文
var reg_enOrNumber = /^[0-9a-zA-Z]+$/;//英文或数字
//获取应用实例
var app = getApp()
Page({
  data: {
    motto: '欢迎来到珊珊的小程序',
    userInfo: {}
  },
  //事件处理函数
  bindViewTap: function () {
    // wx.navigateTo({
    //   url: '../pick/pick'
    // })
    check("1592033001813", reg_mobile, "错啦", true, function () { });
  },
  getCard: function (e) {
    var s = e.detail.value;
    showModal(s)
  },
  formSubmit:function(e) {

  // mobile
  var mobile = e.detail.value.mobile;
  var flag=check(mobile,reg_mobile,"手机号错啦",false)
  if(!flag){return false}
   var phone = e.detail.value.phone;
  var flag=check(phone,reg_phone,"电话号码错啦",false)
 if(!flag){return false}
 wx.showToast({
  title: '成功',
  icon: 'success'
  
})
}
})

function check(obj, pattern, tip, isCancle) {
  if (!pattern.test(obj)) {
    showModal(tip, isCancle);
    return false;
  }
  return true;
}
function showModal(tip, isCancel) {
  wx.showModal({
    title: '提示',
    content: tip,
    showCancel: isCancel
  })
}
