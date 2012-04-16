
$(document).ready(function() {
	console.log(document.location.pathname);
	var a = $("ul.menu a[href$='" + document.location.pathname + "']");
	a.addClass('active');
});



var start=new Array();

function startThumbChange(imgUrl, id, i){
	if(i == 0) {
		start[id] = 0;
	}
	if(i == 16) {
		i = 0;
	}     
	i++;
	var url = imgUrl + i + ".jpg";
	document.getElementById(id).src = url;
	console.log(url);
	if (start[id] != 1) {
		setTimeout("startThumbChange('"+imgUrl+"', '"+id+"', '"+i+"')", 700);
	} else {
		document.getElementById(id).src = imgUrl + ".jpg";
	}
	
}
function endThumbChange(imgUrl, id){
	
	start[id]=1;
	
	document.getElementById(id).src= imgUrl + ".jpg";;

}