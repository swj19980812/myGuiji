var userName = getCookie("userName");
if(userName === ""){
	console.log("no userName");
	$("#subTitle").text("您还未登录，即将跳转...");
	setTimeout(function(){
		window.location.href = "login"
	}, 3000);
}
console.log(userName);
$.get('http://47.102.152.12:8080/sport/mySports', {
	'userName': userName
}, function(res) {
	console.log(res);
	var html = "";
	for(var i in res) {
		html += template('tmpl', res[i])
	}
	document.getElementById("mySports").innerHTML = html
});


function writeCokie(sportTag) {
	console.log(sportTag);
	document.cookie = "sportTag=" + sportTag
}
function writeNameCokie() {
	document.cookie = "userName= "
}