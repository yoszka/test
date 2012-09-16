package pl.xt.jokii.events;

public interface WebSocketEventListener {
    public void handleOpenEvent();
    public void handleCloseEvent();
    public void handleSendEvent(String msg);
}
