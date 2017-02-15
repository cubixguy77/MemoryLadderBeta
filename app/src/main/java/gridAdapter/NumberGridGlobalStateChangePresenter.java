package gridAdapter;

import android.os.Bundle;

import memorization.Bus;
import memorization.GameStateListener;
import memorization.GridData;
import memorization.GridEvent;
import memorization.NumberMemoryModel;
import recall.RecallData;
import review.Result;
import speednumbers.mastersofmemory.challenges.domain.model.Challenge;

class NumberGridGlobalStateChangePresenter implements GameStateListener {

    private GridEvent.Grid grid;
    private NumberMemoryModel model;

    NumberGridGlobalStateChangePresenter(GridEvent.Grid grid, NumberMemoryModel model) {
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