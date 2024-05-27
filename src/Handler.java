public class Handler {
    private RaceGround game;
    public Handler(RaceGround game) {
        this.game = game;
    }
    public KeyboardControl getKeyControl() {
        return game.getKeyManager();
    }
    public MouseControl getMouseManager() {
        return game.getMouseManager();
    }
    public RaceGround getGame() {
        return game;
    }
}

