<%@ page language="java" contentType="text/html; charset=utf-8;"
    pageEncoding="utf-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Search Web</title>
</head>
<body>
  <form action="${pageContext.request.contextPath }/upload.model" method="post" enctype="multipart/form-data">
   <h2>文件检索图片上传</h2>
                 文件:<input type="file" name="uploadFile"/><br/><br/>
      <input type="submit" value="上传"/>
   </form>
   <form action="${pageContext.request.contextPath }/objupload.model" method="post" enctype="multipart/form-data">
   <h2>文件检索模型上传</h2>
                 文件:<input type="file" name="uploadobj"/><br/><br/>
      <input type="submit" value="上传"/>
   </form>	
   <form action="${pageContext.request.contextPath }/nameupload.model" method="post" accept-charset="GBK" onsubmit="document.charset='GBK';">
   <h2>关键字检索</h2>
                 文件:<input type="text" name="uploadname"/><br/><br/>
      <input type="submit" value="检索"/>
   </form>	
   <a href="output.html"><input type="button" value="用户登陆"></input></a>		
</body>
</html>