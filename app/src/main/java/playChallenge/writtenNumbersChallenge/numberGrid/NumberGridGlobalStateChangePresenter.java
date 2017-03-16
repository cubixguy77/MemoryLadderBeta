package playChallenge.writtenNumbersChallenge.numberGrid;

import android.os.Bundle;

import playChallenge.Bus;
import playChallenge.GameStateListener;
import playChallenge.writtenNumbersChallenge.memorization.GridData;
import playChallenge.writtenNumbersChallenge.memorization.SpeedNumbers;
import playChallenge.writtenNumbersChallenge.memorization.NumberMemoryModel;
import playChallenge.writtenNumbersChallenge.recall.RecallData;
import playChallenge.writtenNumbersChallenge.review.Result;
import selectChallenge.viewChallengeCard.Challenge;

class NumberGridGlobalStateChangePresenter implements GameStateListener {

    private SpeedNumbers.Grid grid;
    private NumberMemoryModel model;

    NumberGridGlobalStateChangePresenter(SpeedNumbers.Grid grid, NumberMemoryModel model) {
        this.grid = grid;
        this.model = model;
        Bus.getBus().subscribe(this);
    }

    @Override
    public void onLoad(Challenge challenge, Bundle savedInstanceState) {

        GridData memoryData;
        RecallData recallData;
        int highlightPosition;

        if (savedInstanceState != null) {
            memoryData = Bus.memoryData;
            recallData = Bus.recallData;
            highlightPosition = savedInstanceState.getInt("NumberGridMemoryAdapter.highlightPosition");
        }
        else {
            memoryData = new GridData(challenge);
            memoryData.loadData();
            recallData = new RecallData(challenge);
            highlightPosition = 1;
        }

        model.setMemoryData(memoryData);
        model.setRecallData(recallData);
        model.setHighlightPosition(highlightPosition);

        grid.setNumColumns(memoryData.numCols);
        grid.refresh();
    }

    @Override
    public void onMemorizationStart() {
        grid.refresh();
    }

    @Override
    public void onTimeExpired() {
    }

    @Override
    public void onTransitionToRecall() {
        model.setHighlightPosition(1);
        grid.refresh();
        grid.scrollToTop();
    }

    @Override
    public void onRecallComplete(Result result) {
    }

    @Override
    public void onPlayAgain() {
    }

    @Override
    public void onShutdown() {
    }
}