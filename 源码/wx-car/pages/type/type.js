// pages/type/type.js
Page({

  /**
   * 页面的初始数据
   */
  data: {
    array: [
      "zhangsna", "zhangsna", "zhangsna", "zhangsna", "zhangsna",
      "zhangsna", "zhangsna", "zhangsna", "zhangsna", "zhangsna",
      "zhangsna", "zhangsna", "zhangsna", "zhangsna", "zhangsna",
      "zhangsna", "zhangsna", "zhangsna", "zhangsna", "lisi"
    ],
    view: "5",
    user: {
      username: "zhangsan",
      age: 18
    }
  },
  bindViewTap: function (event) {
    // event.target.dataset.alphaBeta === 1 // - 会转为驼峰写法
    // event.target.dataset.alphabeta === 2 // 大写会转为小写
    console.log(event.target.dataset.alphaBeta);
    console.log(event.target.dataset.alphabeta);
  },
  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function(options) {

  },

  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady: function() {

  },

  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function() {

  },

  /**
   * 生命周期函数--监听页面隐藏
   */
  onHide: function() {

  },

  /**
   * 生命周期函数--监听页面卸载
   */
  onUnload: function() {

  },

  /**
   * 页面相关事件处理函数--监听用户下拉动作
   */
  onPullDownRefresh: function() {

  },

  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom: function() {

  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function() {

  }
})