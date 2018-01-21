<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Label</title>
<style type="text/css">
body,h1,h2,h3,h4,h5,h6,hr,p,blockquote,dl,dt,dd,ul,ol,li,pre,form,fieldset,legend,button,input,textarea,th,td{margin:0;padding:0}
html{color:#000;overflow-y:scoll;overflow:-moz-scrollbars-vertical}
.div{position:absolute; border:3px dashed red; width:0px; height:0px;left:0px; top:0px; overflow:hidden;}
.rect{position:absolute; border:3px dashed red; overflow:hidden; background-color: rgba(255,255,255,0.5);}
</style>
</head>
<body>
	<form id="formid" method ='post' action='index'>
	<input type="radio" name="Cartype" value="car1" checked>car1 
	<input type="radio" name="Cartype" value="car2">car2  
	<input type="radio" name="Cartype" value="car3">car3 
	<input type="radio" name="Cartype" value="car4">car4 
	<input type="radio" name="Cartype" value="car5">car5 
	<input type="radio" name="Cartype" value="car6">car6<br> 
	<input type="radio" name="Colr" value="color1" checked>color1 
	<input type="radio" name="Colr" value="color2">color2 
	<input type="radio" name="Colr" value="color3">color3 
	<input type="radio" name="Colr" value="color4">color4<br> 
	<!-- <select id="Cartype" onchange="initColr()" name="Cartype">
        <option value="0">请选择车型</option>
    </select>
    <select id="Colr" name="Colr">
        <option value="0">请选择颜色</option>
    </select> -->
    <input type="button" value="未标注" id="btn_unlable"/>
    <input type="button" value="提交" id="btn_sub"/>
    <input type="button" value="下一个" id="btn_next"/>
    <input type="button" value="取消" id="btn_delete"/>
    <input type="button" value="浏览" id="btn_browse"/>
    <input type="button" value="撤销" id="btn_cancel"/>
    
    <div id="labeling" style="width: 1366px; height: 768px; top:50px; left: 0px;background-image:url(http://localhost:8080/CarLabel/resource/2.jpg);"></div>
    <!-- src="/CarLabel/resource/labeling.jpg"  -->
    </form>
</body>
<script language="javascript" type="text/javascript" src="https://cdn.bootcss.com/jquery/1.12.4/jquery.min.js"></script>
<script type="text/javascript" src="http://html2canvas.hertzen.com/build/html2canvas.js"></script>  
<script language="javascript" type="text/javascript" src="/CarLabel/resource/DrawRectangle.js"></script>
<script>
window.onload = init;
function init(){
    //initCartype();
    DrawRectangle('labeling');
    clickButton();
}
function initCartype(){
    $.get('/CarLabel/resource/TypeAndColor.xml',function(msg){
        xmldom = msg;
        $(xmldom).find('Cartype').each(function(v,k){//遍历型号
            var nm = $(this).attr('Cartype');
            var id = $(this).attr('CartypeID');
            $('#Cartype').append("<option value='"+id+"'>"+nm+"</option>");
        });
    },'xml');
}
function initColr(){
    var cid=$('#Cartype option:selected').val();
    //获得选取车型下的颜色信息
    var two_cid = cid.substr(0,2);//前两位
    //遍历
    $('#Colr').empty();
    $('#colr').append('<option value="0">请选择颜色</option>');
    $(xmldom).find('Colr[ColrID^='+two_cid+']').each(function(){
        var nm = $(this).attr('Colr');//获取对应车型下所有颜色
        var id = $(this).attr('ColrID');
        $('#Colr').append("<option value='"+id+"'>"+nm+"</option>");
    });
}

function clickButton(){

	$("#btn_unlable").click(function(){
		var picture = document.getElementById("labeling");
		ma = document.createElement("div");  
	    ma.id = picture.id; 
	    ma.style.width = picture.style.width;  
	    ma.style.height = picture.style.height;  
	    ma.style.left = picture.style.left;  
	    ma.style.top = picture.style.top;
	    picture.parentNode.appendChild(ma);  
	    picture.parentNode.removeChild(picture);
		//向后台请求数据
		var xhr = new XMLHttpRequest();
		xhr.onreadystatechange=function(){
            if(xhr.readyState==4 && xhr.status==200){
                ma.style.backgroundImage ="url("+xhr.responseText+")";      
            }
        }
        xhr.open("GET","http://localhost:8080/CarLabel/unlabeledImage.model",true);
        xhr.send();
        setTimeout("DrawRectangle('labeling')","200");
        //DrawRectangle('labeling');
	});
	$("#btn_delete").click(function(){
		$('#labeling div:last-child').remove();
	});
	$("#btn_sub").click(function(){
			var form = new FormData(document.getElementById("formid"));
			var picture = document.getElementById("labeling");
			var url=$(picture).css('background-image');
			url = url.split("(")[1].split(")")[0];//获取地址
			//url = url.substr(42,4);
			var temp = $('#labeling div:last-child')
			//var temp = JSON.parse(localStorage.getItem("temp"));//转成对象
			//console.log(temp);
			$.ajax({
				url:"http://localhost:8080/CarLabel/submit.model",
				type:"post",
				data:{
					imagename:url,
					X:temp.css('marginLeft'),
					Y:temp.css('marginTop'),
					Width:temp.css('width'),
					Height:temp.css('height'), 
					/* X:temp['startX'],
					Y:temp['startY'],
					Width:temp['rectWidth'],
					Height:temp['rectHeight'],  */
					Cartype:$("input[name='Cartype']:checked").val(),
					Colr:$("input[name='Colr']:checked").val()
					/* Cartype:$("#Cartype option:selected").text(),
					Colr:$("#Colr option:selected").text() */
				},
				dataType:"text",
		        success:function(data){
		            console.log("成功");
		        },
		        error:function(e){
		        	console.log(e);
		            //window.clearInterval(timer);
		        }
			});
	});
	
	$("#btn_next").click(function(){
		var picture = document.getElementById("labeling");
/* 		var url=$(picture).css('background-image');
		url = url.split("(")[1].split(")")[0];//获取地址
		$.ajax({
			url:"http://localhost:8080/CarLabel/browseImage.model",
			type:"get",
			data:{
				imagename:url,
			},
			dataType:"text",
	        success:function(data){
	            console.log("成功");
	        },
	        error:function(e){
	        	console.log(e);
	            //window.clearInterval(timer);
	        }
		}
				); */
		ma = document.createElement("div");  
	    ma.id = picture.id; 
	    ma.style.width = picture.style.width;  
	    ma.style.height = picture.style.height;  
	    ma.style.left = picture.style.left;  
	    ma.style.top = picture.style.top;
	    picture.parentNode.appendChild(ma);  
	    picture.parentNode.removeChild(picture);
		//向后台请求数据
		var xhr = new XMLHttpRequest();
		xhr.onreadystatechange=function(){
            if(xhr.readyState==4 && xhr.status==200){
                ma.style.backgroundImage ="url("+xhr.responseText+")";      
            }
        }
        xhr.open("GET","http://localhost:8080/CarLabel/nextImage.model",true);
        xhr.send();
        setTimeout("DrawRectangle('labeling')","200");
	});
	
	$("#btn_browse").click(function(){
		//向后台请求数据
		var xhr = new XMLHttpRequest();
		xhr.onreadystatechange=function(){
            if(xhr.readyState==4 && xhr.status==200){
            	picture = document.getElementById("labeling");
        		ma = document.createElement("div");  
        	    ma.id = picture.id; 
        	    ma.style.width = picture.style.width;  
        	    ma.style.height = picture.style.height;  
        	    ma.style.left = picture.style.left;  
        	    ma.style.top = picture.style.top;
                //ma.style.backgroundImage ="url("+xhr.responseText+")";
                obj = JSON.parse(xhr.responseText);
                console.log(obj[0]);
                ma.style.backgroundImage = "url("+obj[0].imagename+")";
                picture.parentNode.appendChild(ma);
                picture.parentNode.removeChild(picture);
                for(var i=0;i<obj.length;i++){
                	rectangle = document.createElement("div");
            	    rectangle.className = 'rect';
                    rectangle.style.width = obj[i].width;  
                    rectangle.style.height = obj[i].height;  
                    rectangle.style.marginLeft = obj[i].x;  
                    rectangle.style.marginTop = obj[i].y;
                    ma.appendChild(rectangle);
                }
                
                //console.log(rectangle);
            }
        }
        xhr.open("GET","http://localhost:8080/CarLabel/browseImage.model",true);
        xhr.send();
        setTimeout("DrawRectangle('labeling')","200");
	});
	
	
	$("#btn_cancel").click(function(){
		$('#labeling').empty();
		var xhr = new XMLHttpRequest();
		xhr.open("GET","http://localhost:8080/CarLabel/deleteLabel.model",true);
		xhr.send();
	});
}
</script>
</html>