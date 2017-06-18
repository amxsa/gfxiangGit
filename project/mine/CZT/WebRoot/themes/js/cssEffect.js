function chageIndex(indexNum,num,menuName,contentName,myClassName){
 			var i="";
 			for (i=1;i<=num;i++){
  			document.getElementById(menuName+i).className=myClassName+"2";
  			document.getElementById(contentName+i).style.display="none";
 			}
			document.getElementById(menuName+indexNum).className=myClassName+"1";
 			document.getElementById(contentName+indexNum).style.display="block";
}