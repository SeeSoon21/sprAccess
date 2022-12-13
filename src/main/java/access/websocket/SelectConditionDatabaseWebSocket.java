package access.websocket;

import access.service.HelpRecordService;
import com.google.gson.JsonObject;
import org.aspectj.weaver.bcel.Utility;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.HashMap;

/**
 * Класс, расширяющий класс веб-сокет-апи.
 * Позволит нам построить связь
 */
public class SelectConditionDatabaseWebSocket extends TextWebSocketHandler {


    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        session.sendMessage(new TextMessage("Connection established(чё делать будем)"));
        //вот здесь мы сразу будем располагать элементы(названия полей и их значения)
        //HelpRecordService.getStringFieldsOfClass(className)

        //значение класса можно получить при открытии соединения на js(клиентской стороне)
        //session.sendMessage(HelpRecordService.getStringFieldsOfClass(className));
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String accessedJson = message.getPayload();
        System.out.println("class from js: " + accessedJson);


    }
}
