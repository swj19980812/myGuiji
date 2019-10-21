var sportTag = getCookie("sportTag");
if(sportTag === ""){
	$("#subTitle").text("发生错误，即将跳转...");
	setTimeout(function(){
		window.location.href = "mysports"
	}, 3000);
}
var map = new BMap.Map("allmap");
map.enableScrollWheelZoom(); //启用滚轮放大缩小，默认禁用
map.enableContinuousZoom(); //启用地图惯性拖拽，默认禁用

// 定义一个控件类,即function
function ZoomControl() {
	// 默认停靠位置和偏移量
	this.defaultAnchor = BMAP_ANCHOR_TOP_LEFT;
	this.defaultOffset = new BMap.Size(10, 10);
}

// 通过JavaScript的prototype属性继承于BMap.Control
ZoomControl.prototype = new BMap.Control();

$.post('http://47.102.152.12:8080/sport/oneSport', {
	'sportTag': sportTag
}, function(res) {
	console.log(res);
	$("#sportTitle").text(res["sportTitle"]);
	$("#startTime").text("开始时间：\t" + res["startTime"]);
	$("#overTime").text("结束时间：\t" + res["overTime"]);
	$("#mode").text("运动方式：\t" + res["mode"]);
	$("#averageSpeed").text("平均速度：\t" + res["averageSpeed"] + " m/s");
	$("#maxSpeed").text("最快速度：\t" + res["maxSpeed"] + "m/s");
	$("#maxAltitude").text("最高海拔：\t" + res["maxAltitude"] + " m");
	$("#minAltitude").text("最低海拔：\t" + res["minAltitude"] + " m");
	$("#totalDistance").text("总里程：\t" + res["totalDistance"] + " m");
	$("#totalUp").text("累计上升：\t" + res["totalUp"] + " m");
	$("#totalDown").text("累计下降：\t" + res["totalDown"] + " m")
});

$.post('http://47.102.152.12:8080/sport/detail', {
	'sportTag': sportTag
}, function(res) {
	var location = res["location"];
	var speedAltitude = res["speedAltitude"];
	console.log(res);
	var times = [];
	var altitudes = [];
	var speeds = [];
	var points = [];
	if(JSON.stringify(location)==="{}") {
		document.getElementById('cube-wrapper').style.display = "none";
		document.getElementById('preloader-body').style.display = "none";
		$("#subTitle").text("本次运动无数据，即将跳转...");
		setTimeout(function(){
			window.location.href = "mysports"
		}, 3000);
		return;
	}
	$.each(location["values"], function(index, content) {
		points[index] = new BMap.Point(content[1], content[2]);
	});
	$.each(speedAltitude["values"], function(index, content) {
		times[index] = timeTranfrom(content[0]);
		altitudes[index] = content[2];
		speeds[index] = content[1]
	});
	initMap(points);
	initEchart(times, altitudes, speeds);
	document.getElementById('cube-wrapper').style.display = "none";
	document.getElementById('preloader-body').style.display = "none";
});

function timeTranfrom(time) {
	time = time.replace("T", " ");
	time = time.substring(0, time.lastIndexOf('.'));
	time =  time+" UTC";
	var newTime = Date.parse(new Date(time));
	return formatlistdate(newTime);
}
function formatlistdate(time) {
	var date = new Date(time);
	var y = date.getFullYear();
	var m = date.getMonth() + 1;
	var d = date.getDate();
	var hour = date.getHours().toString();
	var minutes = date.getMinutes().toString();
	var seconds = date.getSeconds().toString();
	if(hour < 10) {
		hour = "0" + hour;
	}
	if(minutes < 10) {
		minutes = "0" + minutes;
	}
	if(seconds < 10) {
		seconds = "0" + seconds;
	}
	return y + '-' + (m < 10 ? ('0' + m) : m) + '-' + (d < 10 ? ('0' + d) : d) + " " + hour + ":" + minutes + ":" + seconds;
}
function initMap(points) {
	// 百度地图API功能
	var point = new BMap.Point(points[0]);
	map.centerAndZoom(points[0], 15);

	var startMarker = new BMap.Marker(points[0]); // 创建点
	var endmarker = new BMap.Marker(points[points.length - 1]); // 创建点
	var sy = new BMap.Symbol(BMap_Symbol_SHAPE_BACKWARD_OPEN_ARROW, {
		scale: 0.6, //图标缩放大小
		strokeColor: '#fff', //设置矢量图标的线填充颜色
		strokeWeight: '2' //设置线宽
	});
	var icons = new BMap.IconSequence(sy, '10', '30');
	var polyline = new BMap.Polyline(points, {
		strokeColor: "#18a45b",
		enableEditing: false, //是否启用线编辑，默认为false
		enableClicking: true, //是否响应点击事件，默认为true
		icons: [icons],
		strokeWeight: '8', //折线的宽度，以像素为单位
		strokeOpacity: 0.8 //折线的透明度，取值范围0 - 1
	}); //创建折线

	var overView = new BMap.OverviewMapControl();
	var overViewOpen = new BMap.OverviewMapControl({
		isOpen: true,
		anchor: BMAP_ANCHOR_BOTTOM_RIGHT
	});
	map.addOverlay(startMarker); //增加点
	map.addOverlay(endmarker);
	map.addOverlay(polyline); //增加折线
	//添加地图类型控件
	map.addControl(new BMap.MapTypeControl({
		mapTypes: [
			BMAP_NORMAL_MAP,
			BMAP_HYBRID_MAP
		]
	}));
	map.addControl(overView); //添加默认缩略地图控件
	map.addControl(overViewOpen); //右下角，打开
	// 自定义控件必须实现自己的initialize方法,并且将控件的DOM元素返回
	// 在本方法中创建个div元素作为控件的容器,并将其添加到地图容器中
	ZoomControl.prototype.initialize = function(map) {
		// 创建一个DOM元素
		var div = document.createElement("div");
		// 添加文字说明
//		div.appendChild(document.createTextNode("test"));
		div.className = "fa fa-refresh";
		// 设置样式
		div.style.cursor = "pointer";
//		div.style.border = "1px solid gray";
		div.style.border = "0px";
		div.style.backgroundColor = "#FFFFFF00";
		// 绑定事件,点击一次放大两级
		div.onclick = function(e) {
			map.centerAndZoom(points[0], 15);
		};
		// 添加DOM元素到地图中
		map.getContainer().appendChild(div);
		// 将DOM元素返回
		return div;
	};
	// 创建控件
	var myZoomCtrl = new ZoomControl();
	// 添加到地图当中
	map.addControl(myZoomCtrl);
}

//echart
function initEchart(times, altitudes, speeds) {
	var dom = document.getElementById("echart");
	var myChart = echarts.init(dom);
	var app = {};
	option = null;

	option = {
		backgroundColor: '#ffffff87',
		tooltip: {
			trigger: 'axis',
			axisPointer: {
				type: 'cross',
				crossStyle: {
					color: '#999'
				}
			}
		},
		toolbox: {
			feature: {
				dataView: {
					show: true,
					readOnly: false
				},
				restore: {
					show: true
				},
				saveAsImage: {
					show: true
				}
			}
		},
		legend: {
			data: ['速度', '海拔']
		},

		xAxis: [{
			type: 'category',
			data: times,
			axisPointer: {
				type: 'shadow'
			}
		}],
		yAxis: [{
			type: 'value',
			name: '速度',
			axisLabel: {
				formatter: '{value} m/s'
			}
		},
			{
				type: 'value',
				name: '海拔',
				axisLabel: {
					formatter: '{value} m'
				}
			}
		],
		series: [{
			name: '速度',
			type: 'line',
			data: speeds
		},
			{
				name: '海拔',
				areaStyle: {},
				type: 'line',
				yAxisIndex: 1,
				data: altitudes
			}
		], //滑动条
		dataZoom: [{
			id: 'dataZoomX',
			xAxisIndex: 0,
			show: true, //是否显示 组件。如果设置为 false，不会显示，但是数据过滤的功能还存在。
			backgroundColor: "rgba(47,69,84,0)", //组件的背景颜色
			type: 'slider', //slider表示有滑动块的，inside表示内置的
			start: 0, //数据窗口范围的起始百分比,表示30%
			end: 100, //数据窗口范围的结束百分比,表示70%
			left: "center" //组件离容器左侧的距离,'left', 'center', 'right','20%'
		}]
	};

	if(option && typeof option === "object") {
		myChart.setOption(option, true);
	}

	window.onresize = function() {
		myChart.resize();
	}
}