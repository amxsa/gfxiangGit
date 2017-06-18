//index.js
//获取应用实例
var app = getApp()
Page({
  data: {
    motto: 'Hello World',
    userInfo: {},
    userList: 'aaa'
  },
  //事件处理函数
  bindViewTap: function () {
    wx.navigateTo({
      url: '../login/login'
    })
  },
  onLoad: function () {
    console.log('onLoad')
    var that = this
    //调用应用实例的方法获取全局数据
    app.getUserInfo(function (userInfo) {
      //更新数据
      that.setData({
        userInfo: userInfo
      })
    })
  },
  getUser: function (event) {
    wx.request({
      url: 'http://127.0.0.1/ims/testServlet',
      data: {
        body: { 'name': 'hahah' },
        header: { 'token': '1111', 'time_stamp': 1111 }
      },
      header: {
        'content-type': 'application/json'
      },
      method: 'POST',
      success: function (res) {
        console.log(JSON.stringify(res.data))

        // var list=JSON.stringify(res.data.list);
        var list = res.data.list;

        console.log(list[0].nickName)
        wx.showToast(list[0].nickName)
      }
    })
    //console.log("getUser")
  },

})
