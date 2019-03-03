// pages/main/pass/pass.js
Page({

  /**
   * 页面的初始数据
   */
  data: {
    page_type:"send_code"
  },
  to_login_pag() {
    wx.redirectTo({
      url: '../login/login',
    })
  },
  to_regis_pag() {
    wx.redirectTo({
      url: '../register/register',
    })
  },
  to_change_pass(){
    this.setData({
      page_type:"chg_pass"
    });
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {

  },

  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady: function () {

  },

  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function () {

  },

  /**
   * 生命周期函数--监听页面隐藏
   */
  onHide: function () {

  },

  /**
   * 生命周期函数--监听页面卸载
   */
  onUnload: function () {

  },

  /**
   * 页面相关事件处理函数--监听用户下拉动作
   */
  onPullDownRefresh: function () {

  },

  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom: function () {

  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function () {

  }
})