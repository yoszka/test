package pl.xt.jokii.events;

public interface WebSocketEventListener {
    public void handleWebSocketEvent();
    public void handleSendEvent(String msg);
}
