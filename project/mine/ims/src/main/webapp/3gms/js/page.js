/* 
* @Author: Marte
* @Date:   2016-10-27 14:40:51
* @Last Modified by:   Marte
* @Last Modified time: 2016-10-27 14:40:55
*/

$(document).ready(function(){
    $(function(){
        var currentPageNo=1;
        setPageNo(1);
        $("#currentPageNo").val(currentPageNo);
    });
    function setPageNo(currentPageNo){
        if (currentPageNo<=3) {
            for (var i = 1; i < 6; i++) {
                $("#c"+i).text(i).removeClass("now_page");
            }
            $("#c"+currentPageNo).addClass("now_page");
        }else{
            for (var i = 1; i < 6; i++) {
                $("#c"+i).text(parseInt(currentPageNo)+i-3).removeClass("now_page");
            }
            $("#c3").addClass("now_page");
        }
    }
    function check(ele){
        var pageNo=$(ele).text();
        $("#currentPageNo").val(pageNo);
        setPageNo(pageNo);
    }
    function changePageNo(ele){
        var no=parseInt($(ele).val());
        if (!isNaN(no)) {
            $("#currentPageNo").val(no);
            setPageNo(parseInt(no));
        }
        $(ele).val('');
    }
    function submit(type){
        var currentPageNo=$("#currentPageNo").val();
        if (type=="S") {//首页
            currentPageNo=1;
            $("#currentPageNo").val(currentPageNo);
            setPageNo(currentPageNo);
        }else if (type=="P") {//上一页
            if (parseInt(currentPageNo)<=1) {
                
            }else{
                $("#currentPageNo").val(parseInt(currentPageNo)-1);
                setPageNo(parseInt(currentPageNo)-1);
            }
        }else if (type=="N") {//下一页
            $("#currentPageNo").val(parseInt(currentPageNo)+1);
            setPageNo(parseInt(currentPageNo)+1);
        }else if (type=="E") {//尾页
            
        }
    }
});