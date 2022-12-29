package access.websocket;

import access.service.DomainSelector;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.ArrayList;
import java.util.Map;

public class SelectByButton extends TextWebSocketHandler {
    @Autowired
    DomainSelector domainSelector;

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        System.out.println("Соединение с select_by_button установлено");
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String payload = message.getPayload();
        Map temp = new Gson().fromJson(payload, Map.class);

        ArrayList<String> values = new ArrayList<>();
        for (var key: temp.keySet()) {
            values.add(temp.get(key).toString());
        }
        values.forEach(System.out::println);

        String json = domainSelector.postSelectByValues(values);
        System.out.println("GETTED GSON: " + json);

        session.sendMessage(new TextMessage(json));
    }
}
