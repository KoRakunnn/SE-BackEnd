package swiftescaper.backend.swiftescaper.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.AbstractWebSocketHandler;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import swiftescaper.backend.swiftescaper.domain.entity.Location;
import swiftescaper.backend.swiftescaper.domain.entity.Tunnel;
import swiftescaper.backend.swiftescaper.repository.LocationRepository;
import swiftescaper.backend.swiftescaper.repository.TunnelRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Map;

@Component
public class LocationWebSocketHandler extends TextWebSocketHandler {

    private final ObjectMapper objectMapper = new ObjectMapper();
    private String token = "";
    private String tunnel = "";

    @Autowired
    private LocationRepository locationRepository;

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        // Test : WebSocket 연결 성공여부
        System.out.println("WebSocket 연결 성공: " + session.getId());
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        // 클라이언트로부터 받은 메시지 JSON(lat, lng, tunnelId, token)
        String payload = message.getPayload();
        System.out.println("Received: " + payload);

        // JSON -> Map
        Map<String, Object> locationData = objectMapper.readValue(payload, Map.class);

        // lat, lng, tunnelId, token 추출
        Double position = Double.parseDouble(locationData.get("position").toString());
        tunnel = locationData.get("tunnelId").toString();
        token = locationData.get("fcmToken").toString();

        System.out.println("Received: " + position +","+tunnel +"," +token);

        // Location 엔티티 생성 및 데이터베이스에 저장
        Location location = Location.builder()
                .position(position)
                .token(token)
                .tunnel(tunnel)
                .build();

        if (locationRepository.existsLocationByTokenAndTunnel(token, tunnel)) {
            Location location1 = locationRepository.findLocationByTokenAndTunnel(token, tunnel);
            location1.setPosition(position);
            locationRepository.save(location1);
        } else {
            locationRepository.save(location);
        }

        // Test : DB 저장 확인
        System.out.println("데이터베이스에 저장된 위치 정보 - Position: " + position + ", TunnelId: " + tunnel + ", Token: " + token);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        // WebSocket 연결이 종료될 때 실행되는 로직
        System.out.println("WebSocket 연결 종료: " + session.getId() + " 상태: " + status);
        locationRepository.deleteLocationByTokenAndTunnel(token, tunnel);
    }
}
