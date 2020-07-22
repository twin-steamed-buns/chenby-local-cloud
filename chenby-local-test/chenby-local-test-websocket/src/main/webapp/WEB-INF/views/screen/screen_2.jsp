<%@ page language="java" contentType="text/html;charset=UTF-8" %>
<%String path = request.getContextPath() + "/";%>
<c:set var="ctx" value="<%=path %>" />
<!DOCTYPE html>
<html>

<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="renderer" content="webkit">
    <meta name="viewport" content="width=device-width,initial-scale=1.0,user-scalable=no">
    <title>大屏_2</title>
    <script>
        var ctx = '${ctx}';
    </script>
</head>

<body>
大屏ID: <div id="costemId">wolegeca</div>
<button onclick="closeWebSocket()">关闭WebSocket连接</button><br/>
<h1>状态变化</h1>
<div id="sys"></div>
<h1>接收到的遥控器指令</h1>
<div id="message"></div>
</body>
</html>
<script type="text/javascript">
    var websocket = null;
    var costemId = document.getElementById('costemId').innerHTML;
    //判断当前浏览器是否支持WebSocket
    if ('WebSocket' in window) {
        websocket = new WebSocket("ws://" + window.location.host + "/websocket/screen/" + costemId);
    } else {
        alert('当前浏览器 Not support websocket')
    }

    //连接发生错误的回调方法
    websocket.onerror = function () {
        setSysInnerHTML("WebSocket连接发生错误");
    };

    //连接成功建立的回调方法
    websocket.onopen = function () {
        setSysInnerHTML("WebSocket连接成功");
    }

    //接收到消息的回调方法
    websocket.onmessage = function (event) {
        setMessageInnerHTML(event.data);
    }

    //连接关闭的回调方法
    websocket.onclose = function () {
        setSysInnerHTML("WebSocket连接关闭");
    }

    //监听窗口关闭事件，当窗口关闭时，主动去关闭websocket连接，防止连接还没断开就关闭窗口，server端会抛异常。
    window.onbeforeunload = function () {
        closeWebSocket();
    }

    //将消息显示在网页上
    function setMessageInnerHTML(sendMessage) {
        document.getElementById('message').innerHTML += sendMessage + '<br/>';
    }

    //将消息显示在网页上
    function setSysInnerHTML(sendMessage) {
        document.getElementById('sys').innerHTML += sendMessage + '<br/>';
    }

    //关闭WebSocket连接
    function closeWebSocket() {
        websocket.close();
    }

    //发送消息
    function send() {
        var message = document.getElementById('text').value;//要发送的消息内容
        // var now = getNowFormatDate();//获取当前时间
        // document.getElementById('message').innerHTML += (now + "发送人：" + userno + '<br/>' + "---" + message) + '<br/>';
        // document.getElementById('message').style.color = "red";
        // var ToSendUserno = document.getElementById('usernoto').value; //接收人编号：4567
        // message = message + "|" + ToSendUserno//将要发送的信息和内容拼起来，以便于服务端知道消息要发给谁
        websocket.send(message);
    }

    //获取当前时间
    function getNowFormatDate() {
        var date = new Date();
        var seperator1 = "-";
        var seperator2 = ":";
        var month = date.getMonth() + 1;
        var strDate = date.getDate();
        if (month >= 1 && month <= 9) {
            month = "0" + month;
        }
        if (strDate >= 0 && strDate <= 9) {
            strDate = "0" + strDate;
        }
        var currentdate = date.getFullYear() + seperator1 + month + seperator1 + strDate
            + " " + date.getHours() + seperator2 + date.getMinutes()
            + seperator2 + date.getSeconds();
        return currentdate;
    }
</script>