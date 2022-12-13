package access.config;

import access.websocket.DataBaseWebSocketSelectAll;
import access.websocket.SelectConditionDatabaseWebSocket;
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

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(dataBaseWebSocket(), "/db_action");
        registry.addHandler(selectConditionDatabaseWebSocket(), "/record");
    }
}
