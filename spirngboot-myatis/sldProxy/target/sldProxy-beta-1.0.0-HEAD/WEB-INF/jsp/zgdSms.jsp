<%@ page contentType="text/html;charset=utf-8" language="java"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>同步短信</title>
    <script src="http://i1.ule.com/j/lib/jquery.js"></script>

</head>
<body>
 <h1 style="text-align: center">成功调用次数 ： <em id="em1">0</em></h1>
 <h2 style="text-align: center">失败调用次数 ： <em id="em2"></em></h2>

</body>
<script>
    var siNow = 0,
        eInow = 0;
    var $em1 = $('#em1');
    var $em2 = $('#em2');

    function requestAjax() {
        $.ajax({
            url : 'http://soa.beta.uledns.com/sldProxy/zgsms/syncSmsFromCse.do',
            dataType : 'json',
            success : function (data) {
            	 if (data.code == '0000') {
            		 $em1.html(++siNow);
                 }else{
                	 $em2.html(++eInow);
                     }
            },
            error : function () {
                $em2.html(++eInow);
            }
        })
    }
    requestAjax();
    setInterval(function () {
        requestAjax()
    },30000)

</script>
</html>