package access.websocket;

import access.service.DomainSelector;
import com.google.gson.Gson;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.ArrayList;
import java.util.Map;

/**
 * Обработчик для get_record.js скрипта
 */
public class DeleteRecordWebsocket extends TextWebSocketHandler {
    DomainSelector domainSelector = new DomainSelector();

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String payload = message.getPayload();

        //парсим, т.к. с функции возвращается json
        Map temp = new Gson().fromJson(payload, Map.class);
        ArrayList<String> values = new ArrayList<>();
        for (var key: temp.keySet()) {
            values.add(temp.get(key).toString());
        }

        System.out.println(
                "\nvalues(0)" + values.get(0) +
                ", values(1)" + values.get(1) +
                ", values(2)" + values.get(2)
                );
        domainSelector.postDelete(values.get(0), values.get(1), values.get(2));
    }
}
