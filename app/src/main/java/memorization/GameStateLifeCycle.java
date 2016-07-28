package memorization;

public interface GameStateLifeCycle {

    void onLoad();
    void onMemorizationStart();
    void onTimeExpired();
    void onTransitionToRecall();

}
