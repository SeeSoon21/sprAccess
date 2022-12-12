package access.websocket;

import access.service.DatabaseRequestService;
import access.service.DomainSelector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.AbstractWebSocketHandler;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import com.google.gson.Gson;

import java.util.LinkedList;


public class DataBaseWebSocket extends TextWebSocketHandler {
    @Autowired
    private DomainSelector selector;

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        Gson gson = new Gson();

        System.out.println("message: " + message.getPayload());

        LinkedList<?> list = selector.defineClassToSelectAll(message.getPayload());
        System.out.println("ВЫВОД");
        list.forEach(System.out::println);
        String json = gson.toJson(list);
        System.out.println("json" + json);
        session.sendMessage(new TextMessage(json));
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        System.out.println("connection established");
    }



}
