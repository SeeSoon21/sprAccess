package access.websocket;

import access.service.DomainSelector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.ArrayList;

public class ChangeRecordWebSocket extends TextWebSocketHandler {
    @Autowired
    private DomainSelector domainSelector;

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        System.out.println("connection established");
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String inputMessage = message.getPayload();
        String[] fields = inputMessage.split(":");

        domainSelector.postUpdate(fields);
    }
}
