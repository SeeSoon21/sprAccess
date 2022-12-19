package access.websocket;

import access.service.DomainSelector;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

/**
 * Класс-контроллер, отвечающий за сохранение новых записей в БД
 */
public class SaveNewRecordWebsocket extends TextWebSocketHandler {
    DomainSelector domainSelector = new DomainSelector();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        System.out.println("Установили соединение");
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        System.out.println("Пришедшее message:" + message.getPayload());
        String[] field_array = message.getPayload().split(":");

        //проверяем первое поле после id: если оно != null, то значит, что хотя бы одно поле заполнено
        if (!field_array[2].isEmpty()) {
            System.out.println("Первое поле после id != null");
            domainSelector.postInsert(field_array);
        }
    }
}
