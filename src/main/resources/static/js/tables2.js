//左侧导航栏js
$("#shrink").on("click",function () {
    var content	=document.getElementById("content");
    content.style="margin-left: 40px;"
    var leftul =document.getElementById("left-ul");
    leftul.style="margin-left: -150px;";
    var unfold = document.getElementById("unfold");
    unfold.style="display:block;";
    var shrink = document.getElementById("shrink");
    shrink.style="display: none;";
})
$("#unfold").on("click",function () {
    var content	=document.getElementById("content");
    content.style="margin-left:145px;"
    var leftul =document.getElementById("left-ul");
    leftul.style="display:block;";
    var unfold = document.getElementById("unfold");
    unfold.style="display:none;";
    var shrink = document.getElementById("shrink");
    shrink.style="display:block;";
})

	function ulshrink(){
	var content	=document.getElementById("content");
		content.style="margin-left: 40px;"
	var leftul =document.getElementById("left-ul");
		leftul.style="margin-left: -150px;";
	var unfold = document.getElementById("unfold");
		unfold.style="display:block;";
	var shrink = document.getElementById("shrink");
		shrink.style="display: none;";
	}
	function ulunfold(){
	var content	=document.getElementById("content");
		content.style="margin-left:145px;"
	var leftul =document.getElementById("left-ul");
		leftul.style="display:block;";
	var unfold = document.getElementById("unfold");
		unfold.style="display:none;";
	var shrink = document.getElementById("shrink");
		shrink.style="display:block;";
	}
jQuery( document ).ready(function( $ ) {
   $('.dataTable').tooltip();
});
//分页按钮 点击js
$(document).ready(function(){
		$(".fg-button").first().addClass("ui-state-disabled");
		$(".fg-button").click(function(){
			$(this).addClass("ui-state-disabled").siblings().removeClass("ui-state-disabled");
		})
	})
// 班次按钮 点击js
$(document).ready(function(){
		$(".licontent").first().addClass("active");
		$(".licontent").click(function(){
			$(this).addClass("active").siblings().removeClass("active");
		})
	})
// 左侧导航栏一级目录点击js
$(document).ready(function(){
	$(".btn").click(function(){
		$(this).addClass("btn-success").siblings().removeClass("btn-success");
	})
	})
// 获取当前系统时间
$(document).ready(function(){
	var date = new Date();
	var year = date.getFullYear()+"/";
	var month = date.getMonth()+1+"/";
	var day = date.getDate()+" ";
	var hh =date.getHours()+":";
	var mm = date.getMinutes();
    	mm=mm<10 ? ('0'+mm):mm;
	var ss = date.getSeconds();
    var rq =year+month+day+hh+mm;
		$("#datespan").html(rq);
})