package cn.com.chenby.lcoal.test.websocket.controller.websocket;

import cn.com.chenby.lcoal.test.websocket.config.MySpringConfigurator;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @ServerEndpoint 注解是一个类层次的注解，它的功能主要是将目前的类定义成一个websocket服务器端,
 * 注解的值将被用于监听用户连接的终端访问URL地址,客户端可以通过这个URL来连接到WebSocket服务器端
 */
@ServerEndpoint(value = "/websocket/screen/{costemId}", configurator = MySpringConfigurator.class)
@Component
@Scope("prototype")
public class ScreenWebsocket {
    //静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。
    private static int onlineCount = 0;

    //concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。若要实现服务端与单一客户端通信的话，可以使用Map来存放，其中Key可以为用户标识
    private static ConcurrentHashMap<String, ScreenWebsocket> webSocketMap = new ConcurrentHashMap<>();

    //与某个客户端的连接会话，需要通过它来给客户端发送数据
    private Session session;

    //websocket连接客户端ID
    private String costomId;

    /**
     * 连接建立成功调用的方法
     * @param session  可选的参数。session为与某个客户端的连接会话，需要通过它来给客户端发送数据
     */
    @OnOpen
    public void onOpen(@PathParam(value="costemId") String costemId, Session session){
        this.session = session;
        this.costomId = costemId;
        webSocketMap.put(costemId,this);     //加入set中
        changeOnlineCount(1);           //在线数加1
        System.out.println("大屏——有新连接加入！当前在线人数为" + getOnlineCount());
        System.out.println("当前接入大屏sessionId:>>>>" + session.getId());
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose(){
        webSocketMap.remove(this.costomId);  //从set中删除
        changeOnlineCount(-1);           //在线数减1
        System.out.println("大屏——有一连接关闭！当前在线人数为" + getOnlineCount());
    }

    /**
     * 收到客户端消息后调用的方法
     * @param message 客户端发送过来的消息
     * @param session 可选的参数
     */
    @OnMessage
    public void onMessage(String message, Session session) {
        System.out.println("来自客户端的消息:" + message);
        //群发消息
        for(Map.Entry<String, ScreenWebsocket> item: webSocketMap.entrySet()){
            try {
                item.getValue().sendMessage(message);
            } catch (IOException e) {
                e.printStackTrace();
                continue;
            }
        }
    }

    /**
     * 发生错误时调用
     * @param session
     * @param error
     */
    @OnError
    public void onError(Session session, Throwable error){
        System.out.println("发生错误");
        error.printStackTrace();
    }

    /**
     * 这个方法与上面几个方法不一样。没有用注解，是根据自己需要添加的方法。
     * @param message
     * @throws IOException
     */
    public void sendMessage(String message) throws IOException {
        this.session.getBasicRemote().sendText(message);
        //this.session.getAsyncRemote().sendText(message);
    }

    //往所有大屏客户端发消息
    public static void sendAll(String message) throws IOException {
        //群发消息
        for(Map.Entry<String, ScreenWebsocket> item: webSocketMap.entrySet()){
            try {
                item.getValue().sendMessage(message);
            } catch (IOException e) {
                e.printStackTrace();
                continue;
            }
        }
    }

    public static synchronized int getOnlineCount() {
        return onlineCount;
    }

    public static synchronized void changeOnlineCount(int add) {
        if(add > 0){
            ScreenWebsocket.onlineCount++;
        }else{
            ScreenWebsocket.onlineCount--;
        }
    }

}
