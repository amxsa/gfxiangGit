/**
 * 
 */

var serviceMap = null;//地图对象
var service = null;
var resultArray = new Array();//结果数组
var infowindow;

function setAutoZoom(markers,map){
	if(markers!=null&&markers.length>1){
		var i=markers.length,bounds = new google.maps.LatLngBounds();
	while(i--){
		bounds.extend(markers[i].getPosition());
	}
	map.fitBounds(bounds);
	map.setCenter(bounds.getCenter());
	map.panToBounds(bounds);
	}else{
		map.panTo(markers[0].getPosition());
	}
}	

function mapinit(lat,lon,zoom,mapid){
 	var latlng = new google.maps.LatLng(lat,lon);
 	var myOptions = {
 	  panControl:false,
      zoom: zoom,
      center: latlng,
      mapTypeId: google.maps.MapTypeId.ROADMAP
    };
 	//var winHeight;
 	var winWidth;
   $("#"+mapid).css("height",470);
   return new google.maps.Map(document.getElementById(mapid), myOptions);
     
}

function addMarker(event){
	//google.maps.event.clearInstanceListeners(serviceMap);
	var diag = new top.Dialog();
	diag.Title = "添加社区服务站";
	diag.URL = "/mms/addStation.html?lonlat="+event.latLng.toUrlValue();
	diag.Width = 470;
	diag.Height=280;
	//diag.MessageTitle="表单标题";
	//diag.Message="这里写一些填表说明";
	//diag.CancelEvent = function(){
	//	diag.innerFrame.contentWindow.location.reload();
	//	diag.close();
	//	};
	diag.show();


}

function searchPlace(){
	var place = $("#place").val();
	if(place==null||place==''){
		top.Dialog.alert("请填写搜索地点");
		return;
	}else{
		if(resultArray.length>0){
			for(var i = 0;i<resultArray.length;i++){
				resultArray[i].setMap(null);
			}
			resultArray = new Array();
		}
		 var request = {
				    location:  new google.maps.LatLng(23.020182,113.120041),
				    radius: '50000',
				    query: place
				  };
		 infowindow = new google.maps.InfoWindow();
		 service = new google.maps.places.PlacesService(serviceMap);
		 service.textSearch(request, placeBack);
	}
	
}

function createMarker(place) {
	  var placeLoc = place.geometry.location;
	  var marker = new google.maps.Marker({
	    map: serviceMap,
	    position: place.geometry.location
	  });
	  resultArray.push(marker);
	  google.maps.event.addListener(marker, 'click', function() {
	    infowindow.setContent(place.name);
	    infowindow.open(serviceMap, this);
	  });
}

function placeBack(results, status){
	if (status == google.maps.places.PlacesServiceStatus.OK) {
	    for (var i = 0; i < results.length; i++) {
	      var place = results[i];
	      createMarker(results[i]);
	    }
	    setAutoZoom(resultArray,serviceMap);
	  }
}

$(function(){
	 serviceMap = mapinit(23.020182,113.120041,12,"mapbox");
	 if(serviceMap!=null){
			google.maps.event.addListener(serviceMap, 'rightclick', addMarker);
	 }
	
});