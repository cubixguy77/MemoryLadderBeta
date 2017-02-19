package memorization;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.BindView;
import butterknife.ButterKnife;
import gridAdapter.NumberGridView;
import keyboard.NumericKeyboardView;
import memorization.navigationPanel.NavigationPanel;
import review.Result;
import speednumbers.mastersofmemory.challenges.domain.model.Challenge;
import speednumbers.mastersofmemory.com.presentation.R;
import timer.TimerView;

public class MemoryFragment extends Fragment implements GameStateListener {

    @BindView(R.id.numberGrid)
    NumberGridView grid;
    @BindView(R.id.timerView) TimerView timer;
    @BindView(R.id.navigationPanel) NavigationPanel navigationPanel;
    @BindView(R.id.keyboard) NumericKeyboardView keyboard;

    public MemoryFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d("ML.MemoryFragment", "onCreateView()");

        View view = inflater.inflate(R.layout.fragment_memory_numbers, container, false);
        ButterKnife.bind(this, view);

        Bus.getBus().subscribe(this);
        initializeViews();

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.d("ML.MemoryFragment", "onViewCreated()");
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.d("ML.MemoryFragment", "onActivityCreated()");
    }

    private void initializeViews() {
        grid.init();
        timer.init();
        navigationPanel.init();
        keyboard.init();

        timer.subscribe();
        navigationPanel.subscribe();
        keyboard.subscribe();
    }

    @Override
    public void onLoad(Challenge challenge, Bundle savedInstanceState) {
        Log.d("ML.MemoryFragment", "onLoad()");
    }

    @Override
    public void onMemorizationStart() {}

    @Override
    public void onTimeExpired() {}

    @Override
    public void onTransitionToRecall() {}

    @Override
    public void onRecallComplete(Result result) {}

    @Override
    public void onPlayAgain() {}

    @Override
    public void onShutdown() {}
}
