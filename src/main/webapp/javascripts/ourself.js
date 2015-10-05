//文档动态添加dialog
$("body").append(
		"<div id='dialog-suredelete'>"+
			"<h3>提示</h3>"+
			"<div id='dialogson'>"+
				"<div id='msggrandpa'>"+
					"<div id='msgparent'>"+
						"<div id='whetherclose'>确定关闭吗？</div>"+
					"</div>"+
				"</div>"+
				"<div id='choosecontainer'>"+
				    "<div id='dialog-suredelete-sureclose' class='chooshine-btn'>确定</div>"+
				    "<div id='dialog-suredelete-cancel' class='chooshine-btn'>取消</div>"+
				"</div>"+
			"</div>"+
		"</div>");

//初始化加载后，设置dialog样式
window.onload = function(){
	$("#dialog-suredelete").dialog({
		dialogClass:"chooshine-dialog",
		autoOpen:false,
		width:350,
		height:250,
		modal:true
	});

	//去除title部分
	var parentNode = document.getElementById("dialog-suredelete").parentNode;
	var brotherNode = parentNode.childNodes[0];
	$(parentNode).css("padding","0px");
	$(brotherNode).css("display","none");
};

//定义自己的dialog方法，包括alert和confirm
var chooseContainerNode = document.getElementById("choosecontainer");
var whetherCloseNode = document.getElementById("whetherclose");
var sureNode = document.getElementById("dialog-suredelete-sureclose");
var cancelNode = document.getElementById("dialog-suredelete-cancel");

//定义自己的alert和confirm
jQuery.chooshine = {
	alert:function(txt) {
		//清除原有绑定点击事件
		sureNode.onclick = null;
		//设置提示信息
		whetherCloseNode.innerHTML = txt;
		//隐藏取消按钮
		cancelNode.style.display = "none";
		//设置确定按钮的margin-right为0
		sureNode.style.marginRight = "";
		//设置确定按钮的显示蓝色方块按钮
		sureNode.className = "chooshine-btn";
		//设置确定按钮的左边距
		sureNode.style.marginLeft = "100px";
		//显示dialog
		$("#dialog-suredelete").dialog("open");
		//为确定添加点击事件
		sureNode.onclick = function() {
			$("#dialog-suredelete").dialog("close");
		};
	},
	confirm:function(txt, fn) {
		//清除原有绑定点击事件
		sureNode.onclick = null;
		//设置提示信息
		whetherCloseNode.innerHTML = txt;
		//设置确定按钮的margin-right为20px
		sureNode.style.marginRight = "20px";
		//设置确定按钮的显示蓝色方块按钮
		sureNode.className = "chooshine-cancel";
		//设置确定按钮的左边距
		sureNode.style.marginLeft = "80px";
		//显示取消按钮
		cancelNode.style.display = "block";
		//显示dialog
		$("#dialog-suredelete").dialog("open");
		//如果点击了确定，就执行fn，并关闭dialog
		sureNode.onclick = function() {
			$("#dialog-suredelete").dialog("close");
			fn();
		};
		//如果点击了取消，就关闭dialog
		cancelNode.onclick = function() {
			$("#dialog-suredelete").dialog("close");
		};
	}
};


/**
 * 当浏览器向下滚动的距离大于节点初始化时距离可视窗口顶部的距离时，设置节点的样式及其下一个节点的样式
 * @param node 要设置的节点
 * @param startPos 节点距离可视窗口顶部的初始距离
 * @param className 浏览器滚动距离大于节点初始化时距离可视窗口顶部的距离时，节点的样式
 */
function fixDiv(node, startPos, className, nextClassName) {
	//得到文档相对于滚动条顶部滚动的距离
	var scrollDistance = $(document).scrollTop();
	var nextNode = $(node).next();
	//如果滚动条滚动距离大于startPos，则设置div的position为fixed，且top=0；反之，则取出fiexed
	if(scrollDistance > startPos) {
		$(node).addClass(className);
		$(nextNode).addClass(nextClassName);
	} else {
		$(node).removeClass(className);
		$(nextNode).removeClass(nextClassName);
	}
}
/**
 * 文本框不允许输入非数字，当按下的键盘的按键不是数字时，替换为空字符
 * @param node 要添加事件的文本框
 */
function disableInputKeyUpNoNumber(node) {
	$(node).keyup(function () {
        //如果输入非数字，则替换为''，如果输入数字
        $(this).val($(this).val().replace(/[^\d]/g, ''));
    });
}


//去除可编辑DIV的末位<br>
function cleanBr(str){
	return str.replace((/(<br>)+$/g),'');
}

//两个时间相减，时间可以为时间格式字符串或者秒数
function dateDiff(date1, date2){
	var type1 = typeof date1, type2 = typeof date2;
	if(type1 == 'string')
	date1 = stringToTime(date1);
	else if(date1.getTime)
	date1 = date1.getTime();
	if(type2 == 'string')
	date2 = stringToTime(date2);
	else if(date2.getTime)
	date2 = date2.getTime();
	return (date1 - date2)/1000;//结果是秒
}

//字符串转成Time(dateDiff)所需方法，比如，将"2014-06-03"转化成时间
function stringToTime(string){
	var f = string.split(' ', 2);
	var d =(f[0] ? f[0] : '').split('-', 3);
	var t = (f[1] ? f[1] : '').split(':', 3);
	return (new Date(
			    parseInt(d[0], 10) || null,
			    (parseInt(d[1], 10) || 1)-1,
			    parseInt(d[2], 10) || null,
			    parseInt(t[0], 10) || null,
			    parseInt(t[1], 10) || null,
			    parseInt(t[2], 10) || null
			    )
			).getTime();
}
//将秒数转为hh:MM:ss的格式
function secondsToDate(seconds) {
	var hh = Math.floor(seconds/3600);
	var MM = Math.floor((seconds-3600*hh)/60);
	var ss = seconds%60;
	
	if(hh < 10) {
		hh = "0"+hh;
	}
	if(MM < 10) {
		MM = "0"+MM;
	}
	if(ss < 10) {
		ss = "0"+ss;
	}
	return hh+":"+MM+":"+ss;
}

//格式化utc格式时间到yy:mm:DD hh:MM:ss格式
function formateUtcDate(utcDate) {
	var yy = utcDate.getFullYear();
	var month = utcDate.getMonth()+1;
	var date = utcDate.getDate();
	var hh = utcDate.getHours();
	var mm = utcDate.getMinutes();
	var ss = utcDate.getSeconds();
	
	if(month < 10) {
		month = "0"+month;
	}
	if(date < 10) {
		date = "0"+date;
	}
	if(hh < 10) {
		hh = "0"+hh;
	}
	if(mm < 10) {
		mm = "0"+mm;
	}
	if(ss < 10) {
		ss = "0"+ss;
	}
	
	return yy+"-"+month+"-"+date+" "+hh+":"+mm+":"+ss;
}

//得到当前时间的YY:MM:DD HH:mm:ss形式
function formatNowTime() {
	return formateUtcDate(new Date());
}


//自动触发事件的方法
function triggerMouseEvent(element, eventType) {
    /*if (element.fireEvent) {
        element.fireEvent('on' + eventType);
    } else {
        var evt = document.createEvent('MouseEvents');
        evt.initMouseEvent(eventType, true, true, document.defaultView, 1, 0, 0, 0, 0, false, false, false, false, 0, null);
        element.dispatchEvent(evt);
    }*/
    if (document.dispatchEvent) {
    	var evt = document.createEvent('MouseEvents');
    	evt.initMouseEvent(eventType, true, true, document.defaultView, 1, 0, 0, 0, 0, false, false, false, false, 0, null);
    	element.dispatchEvent(evt);
    } else {
    	element.fireEvent('on' + eventType);
    }
}

//选择时间的弹出框，设置中文样式
$.datepicker.regional['zh-CN'] = {
        clearText: '清除',  
        clearStatus: '清除已选日期',  
        closeText: '关闭',  
        closeStatus: '不改变当前选择',  
        prevText: '<上月',  
        prevStatus: '显示上月',  
        prevBigText: '<<',  
        prevBigStatus: '显示上一年',  
        nextText: '下月>',  
        nextStatus: '显示下月',  
        nextBigText: '>>',  
        nextBigStatus: '显示下一年',  
        currentText: '今天',  
        currentStatus: '显示本月',  
        monthNames: ['一月','二月','三月','四月','五月','六月', '七月','八月','九月','十月','十一月','十二月'],  
        monthNamesShort: ['一','二','三','四','五','六', '七','八','九','十','十一','十二'],  
        monthStatus: '选择月份',  
        yearStatus: '选择年份',  
        weekHeader: '周',  
        weekStatus: '年内周次',  
        dayNames: ['星期日','星期一','星期二','星期三','星期四','星期五','星期六'],  
        dayNamesShort: ['周日','周一','周二','周三','周四','周五','周六'],  
        dayNamesMin: ['日','一','二','三','四','五','六'],  
        dayStatus: '设置 DD 为一周起始',  
        dateStatus: '选择 m月 d日, DD',  
        dateFormat: 'yy-mm-dd',  
        firstDay: 1,
        initStatus: '请选择日期',  
        isRTL: false
 };  
 $.datepicker.setDefaults($.datepicker.regional['zh-CN']);
 
 
//将根路径替换为当前的根路径
function changeImgToNormal(imgLabel, root) {
	var patternSrc = /src="\S*\/images/;
	return imgLabel.replace(patternSrc, "src=\""+root+"/images");
}