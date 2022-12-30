package access.websocket;

import access.service.DomainSelector;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class QueryByUserWebSocket extends TextWebSocketHandler {
    @Autowired
    DomainSelector domainSelector;

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        System.out.println("connection estb");
    }

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String json = message.getPayload();
        System.out.println("message query:" + message.getPayload());

        Map map = new GsonBuilder().setDateFormat("yyyy-mm-dd").create().fromJson(json, HashMap.class);
        ArrayList<String> values = new ArrayList<>();
        for (var key: map.keySet()) {
            values.add(map.get(key).toString());
        }

        String answerToClient = domainSelector.selectByParameter(values);

        session.sendMessage(new TextMessage(answerToClient));
    }
}
