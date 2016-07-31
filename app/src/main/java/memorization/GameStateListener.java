package memorization;

public interface GameStateListener {

    void onLoad();
    void onMemorizationStart();
    void onTimeExpired();
    void onTransitionToRecall();
    void onNextRow();
    void onSubmitRow();
}
