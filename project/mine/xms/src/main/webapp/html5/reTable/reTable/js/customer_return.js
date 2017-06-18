/* 
* @Author: Marte
* @Date:   2016-06-03 10:42:03
* @Last Modified by:   Marte
* @Last Modified time: 2016-06-06 14:23:21
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
    // 点击三角排序
    $(".first_ul li").click(function(event) {
        $(this).children('i').toggleClass('down');
    });
});