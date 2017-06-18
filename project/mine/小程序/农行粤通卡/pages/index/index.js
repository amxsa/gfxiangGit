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
    array: ['请选择区', '番禺', '天河', '萝岗', '海珠'],
    lobby: ['请选择网点', '网点', '网点2', '巴西', '日本'],
    plate: ['粤', '湘', '鄂'],
    index: 0,
    date: '2017-01-18',
    time: '12:01',
    phone: '',
    name: ''
  },
  //输入框事件
  bindPickerChange: function (e) {
    this.setData({
      index: e.detail.value
    })
  },
  bindDateChange: function (e) {
    this.setData({
      date: e.detail.value
    })
  },
  phoneInput: function (e) {
    this.setData({
      phone: e.detail.value
    })
  },
  nameInput: function (e) {
    this.setData({
      name: e.detail.value
    })
  },

  //提交
  submitMsg: function () {
    if (this.data.name==null||this.data.name=="") {
      wx.showModal({
        title: '提示',
        content: '名称不可为空',
        showCancel: false,
        success: function (res) {
          if (res.confirm) {
            return;
          }
        }
      })
      return false;
    }
    if (this.data.phone.length != 11) {
      wx.showModal({
        title: '提示',
        content: '请输入正确的手机号码',
        showCancel: false,
        success: function (res) {
          if (res.confirm) {
            return;
          }
        }
      })
      return false;
    }
    
    var that = this//不要漏了这句，很重要
    var openId = wx.getStorageSync('openId');
    var header = { 'Content-Type': 'application/json' }
    wx.request({
      url: '',
      data: {
        'name': this.data.name,
        'openId': openId,
        'phone': this.data.phone
      },
      headers: header,
      success: function (res) {
        if (res.data.success) {
          wx.navigateTo({
            url: ''
          })
        } else {
          wx.showModal({
            title: '提示',
            content: res.data.msg,
            showCancel: false,
            success: function (res) {
              if (res.confirm) {
                return;
              }
            }
          })
        }
      }, fail: function (res) {
        console.log(res);
      }
    })




  }



















})
