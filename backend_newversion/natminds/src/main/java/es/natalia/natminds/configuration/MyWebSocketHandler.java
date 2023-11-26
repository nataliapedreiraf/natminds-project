package es.natalia.natminds.configuration;

import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

public class MyWebSocketHandler extends TextWebSocketHandler {
  @Override
  public void handleTextMessage(WebSocketSession session, TextMessage message) {
    // Manejar el mensaje recibido
    System.out.println("Mensaje recibido: " + message.getPayload());
    // Puedes procesar el mensaje y enviar una respuesta si es necesario
    // ...
  }

  @Override
  public void afterConnectionEstablished(WebSocketSession session) {
    // Se llama cuando se establece la conexión WebSocket
    System.out.println("Conexión establecida: " + session.getId());
    // Puedes realizar acciones adicionales cuando se establece la conexión
    // ...
  }

  @Override
  public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
    // Se llama cuando se cierra la conexión WebSocket
    System.out.println("Conexión cerrada: " + session.getId());
    // Puedes realizar acciones adicionales cuando se cierra la conexión
    // ...
  }
}
