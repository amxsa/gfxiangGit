/* 
* @Author: Marte
* @Date:   2016-06-03 10:42:03
* @Last Modified by:   Marte
* @Last Modified time: 2016-06-06 14:19:47
*/

$(document).ready(function(){
    $(".tab_top li").click(function(e) {
       $(this).addClass("now").siblings("li").removeClass("now")
        var index = $(this).index()
        //alert(index)
        $(".centent_bd").eq(index).addClass("current").siblings("div").removeClass("current")
    });
    // =========弹窗1============
    $('.give').click(function(event) {
        $('.layer').fadeIn(100);        
        $('.layer-bg').fadeTo(100,0.5)  // 半透明
    });

    // 点击关闭按钮，关闭窗口，     点击背景也关闭  
    $('.close,.layer-bg,.layer_ft_no').click(function(event) {
        $('.layer,.layer-bg').hide();      
    });
    // =========弹窗2============
    $('.see').click(function(event) {
        $('.layer2').fadeIn(100);        
        $('.layer-bg2').fadeTo(100,0.5)  // 半透明
    });

    // 点击关闭按钮，关闭窗口，     点击背景也关闭  
    $('.close2,.layer-bg2,.layer_ft_no').click(function(event) {
        $('.layer2,.layer-bg2').hide();      
    });
    // =========弹出大照片============
    var picH=$(".big_photo").height();
    //alert(picH)
    var picW=$(".big_photo").width();
    $(".bigpic_box").width=picW;
    $(".bigpic_box").height=picH;
    $('.photo_ul img').click(function(event) {
        $('.bigpic_box').fadeIn(100);        
        $('.bigpic_box_pic').fadeTo(100,0.5)  // 半透明
        $('.layer2').fadeOut(100);
        $('.layer-bg2').fadeOut(100);
    });

    // 点击关闭按钮，关闭窗口，     点击背景也关闭  
    $('.close_pic,.bigpic_box_pic').click(function(event) {
        $('.bigpic_box,.bigpic_box_pic').hide();
         $('.layer2').show();
        $('.layer-bg2').show();      
    });
    // 点击三角排序
    $(".first_ul li").click(function(event) {
        $(this).children('i').toggleClass('down');
    });

});