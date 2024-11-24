package Tracker.Mensuration.Configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfiguration implements WebSocketMessageBrokerConfigurer {

//    private final UserSessionInterceptor userSessionInterceptor;
//
//    public WebSocketConfiguration(UserSessionInterceptor userSessionInterceptor) {
//        this.userSessionInterceptor = userSessionInterceptor;
//    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/socket-connection").withSockJS();
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.enableSimpleBroker("/returnMessage");
        registry.setApplicationDestinationPrefixes("/sendMessage");
    }



//    @Override
//    public void configureClientInboundChannel(ChannelRegistration registration) {
//        registration.interceptors(userSessionInterceptor); // Add interceptor
//    }
}
