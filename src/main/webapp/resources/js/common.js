
$(document).ready(function() {
	var a = $("ul.menu a[href$='" + document.location.pathname + "']");
	a.addClass('active');
});



var start=new Array();

function startThumbChange(imgSrc, id, i){
	if(i == 0) {
		start[id] = 0;
	}
	if(i == 16) {
		i = 0;
	}     
	i++;
	var ind = imgSrc.indexOf(".jpg");
	var imgUrl = imgSrc.substring(0, ind);
	var url = imgUrl + i + ".jpg";
	document.getElementById(id).src = url;
	if (start[id] != 1) {
		setTimeout("startThumbChange('"+imgUrl+".jpg', '"+id+"', '"+i+"')", 700);
	} else {
		document.getElementById(id).src = imgUrl + ".jpg";
	}
	
}
function endThumbChange(imgUrl, id){
	
	start[id]=1;
	
	document.getElementById(id).src= imgUrl;

}