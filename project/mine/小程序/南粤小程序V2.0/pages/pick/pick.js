//index.js
//获取应用实例
var app = getApp()
Page({
  data: {
    motto: '欢迎来到珊珊的小程序',
    userInfo: {}
  },
  //事件处理函数
  bindViewTap: function() {
    wx.navigateTo({
      url: '../photo/photo'
    })
  }
})
