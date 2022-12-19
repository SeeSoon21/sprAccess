package access.config;

import access.websocket.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    @Bean
    public DataBaseWebSocketSelectAll dataBaseWebSocket() {
        return new DataBaseWebSocketSelectAll();
    }

    @Bean
    public SelectConditionDatabaseWebSocket selectConditionDatabaseWebSocket() { return new SelectConditionDatabaseWebSocket(); }

    @Bean
    public ChangeRecordWebSocket changeRecordWebSocket() { return new ChangeRecordWebSocket(); }

    @Bean
    public NewRecordWebsocket newRecordWebsocket() { return new NewRecordWebsocket(); }

    @Bean
    SaveNewRecordWebsocket saveNewRecordWebsocket() { return new SaveNewRecordWebsocket(); }

    @Bean
    DeleteRecordWebsocket deleteRecordWebsocket() { return new DeleteRecordWebsocket(); }


    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(dataBaseWebSocket(), "/db_action");
        registry.addHandler(selectConditionDatabaseWebSocket(), "/record");
        registry.addHandler(changeRecordWebSocket(), "/change");
        registry.addHandler(newRecordWebsocket(), "/insert");
        registry.addHandler(saveNewRecordWebsocket(), "/save");
        registry.addHandler(deleteRecordWebsocket(), "/delete");
    }
}
