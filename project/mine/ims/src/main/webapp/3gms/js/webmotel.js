

/**
 * text 弹窗内容
 */
function layerBox(text, func) {
    var temp ='';
    temp ='<div class="layer" style="width: 800px;height: 500px;background:#fff;position: fixed;left:50%;top:50%;margin-left: -400px;margin-top: -250px;z-index:100; ">';
    temp+='<div class="layer_hd" style="height: 40px;line-height: 40px;background: #f4f5f9;">';
    temp+='<p>选择卡券类型</p>';
    temp+='<span class="close" style="width: 40px;height: 40px;position: absolute;right:0;top:0;background: url(images/icon-close.png) no-repeat center center;cursor: pointer;"></span>';
    temp+='</div>';
    temp+='<div class="layer_bd ">';
    temp+='</div>';

    temp+='<div class="layer_ft" style="width: 100%;height: 50px;background: #f4f5f9;position: absolute;left: 0;bottom: 0;text-align: center;line-height: 50px;">';
    temp+='<input type="reset" value="取消"  class="layer_ft_no" style="width: 80px;height: 30px;text-align: center;cursor:pointer;border-radius: 4px;"/>';
    temp+='<input type="submit" value="确定" class="layer_ft_yes" style="width: 80px;height: 30px;text-align: center;cursor:pointer;border-radius: 4px;margin-left: 20px;background: #9ccc39;color: #fff;"/>';
    temp+='</div>';
    temp+='</div>';
    temp+='<div class="layer-bg" style="width:100%;height:100%;background: rgba(0,0,0,0.4); position: fixed;left: 0; top: 0;z-index:99;"></div>';

   
    // 插入到body底部
    $("body").after(temp);
    
    $('.close,.layer-bg,.layer_ft_no').click(function(event) {
        $('.layer,.layer-bg').hide(); 
        $(".layer").remove();
        $(".layer-bg").remove();     
    });
    
        // 显示完要移除掉


  

    };





