function onloadEvent(func){
var one=window.onload
if(typeof window.onload!='function'){
window.onload=func
}
else{
window.onload=function(){
one();
func();
}
}
}
function showtable(){
var tableid='table_hover';		//����id
var overcolor='#FDFDFD';	//��꾭����ɫ
var color1='#FDFFFF';		//��һ����ɫ
var color2='#fff';		//�ڶ�����ɫ
var tablename=document.getElementById(tableid)
var tr=tablename.getElementsByTagName("tr")
for(var i=1 ;i<tr.length;i++){
tr[i].onmouseover=function(){
this.style.backgroundColor=overcolor;
}
tr[i].onmouseout=function(){
if(this.rowIndex%2==0){
this.style.backgroundColor=color1;
}else{
this.style.backgroundColor=color2;
}
}
if(i%2==0){
tr[i].className="color1";
}else{
tr[i].className="color2";
}
}
}
onloadEvent(showtable);