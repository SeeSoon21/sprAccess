package access.websocket;

import access.service.HelpRecordService;
import com.google.gson.Gson;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class NewRecordWebsocket extends TextWebSocketHandler {
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        System.out.println("соединение установлено!");
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String className = message.getPayload();
        ArrayList<String> string_fields_list = new ArrayList<>();
        //получаем по имени класса(который пришёл после нажатия кнопки) список всех полей класса
        ArrayList<?> fields = HelpRecordService.getStringFieldsOfClass(className);
        //искличаем id -- он инкреметируется сам, поэтому с ним работать не будем
        for (int i = 1; i < fields.size(); i++) {
            string_fields_list.add(fields.get(i).toString());
        }

        //преобразуем в строку для дальнейшей обработки в js-скрипте
        String completeString = string_fields_list.stream().map(Object::toString).collect(Collectors.joining(", "));

        System.out.println("classname: " + className);

        session.sendMessage(new TextMessage(completeString));
    }
}
