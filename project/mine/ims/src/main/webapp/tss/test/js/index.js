
$(document).ready(function(){
 // 中奖信息
    $(".go_btn").click(function(event) {
    $(".layer").fadeIn(30)
    $(".layer-bg").fadeIn(30)
});
$(".layer .close ,.layer-bg").click(function(event) {
    $(".layer").fadeOut(30)
    $(".layer-bg").fadeOut(30)

});
// 未中奖信息
 
$(".close_no ,.no-bg").click(function(event) {
    $(".layer_no").fadeOut(30)
    $(".no-bg").fadeOut(30)

});

// 活动规则
  $(".act_about").click(function(event) {
    $(".act_txt").fadeIn(30)
    $(".act_txt-bg").fadeIn(30)
});
$(".act_txt .close ,.act_txt-bg").click(function(event) {
    $(".act_txt").fadeOut(30)
    $(".act_txt-bg").fadeOut(30)

});   

});