<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <base href="<%=basePath%>">

    <title>微信扫码登录</title>

</head>

<body>
<div  id="ma">
</div>
<script type="application/javascript">
    $(function(){
        $.post("<%=basePath%>index/weixin",function(data){
            var ma = data.shorturl;
            var srcMa = "http://qr.topscan.com/api.php?text="+ma;
            var imgg = "<img class='h-img' alt='' src="+srcMa+">";
            //将生成的二维码放到div里
            $("#ma").empty().append(imgg);
        });
        //置初始值
        $.post("<%=basePath%>index/type",{"a":0});
        panduan();
    })
    //微信扫码是否成功的判断
    var test = 0;
    function panduan(){
        $.post("<%=basePath%>index/successDL",function(data){

            if(data.type==1){

                window.location.href='<%=basePath%>index/fangSession';
            }else if(data.type==0 && test!=300){
                //没扫码成功，将test+1,达到三百次（150秒），就不扫了。
                test = test+1;
                panduan();
            }else if(test==300){
                alert("登录码已失效，请刷新页面更新验证码！");
                $.post("<%=basePath%>index/type",{"a":5});
            }
        });
    }
</script>
</body>
</html>
