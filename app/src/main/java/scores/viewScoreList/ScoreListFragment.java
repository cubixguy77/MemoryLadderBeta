package scores.viewScoreList;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import playChallenge.writtenNumbersChallenge.memorization.NumberMemoryActivity;
import scores.getScoreList.GetScoreListInteractor;
import speednumbers.mastersofmemory.com.presentation.R;

public class ScoreListFragment extends ListFragment {

    private GetScoreListInteractor getScoreListInteractor;
    private ScoreListAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d("ML.ScoreListFragment", "onCreateView()");

        View root = inflater.inflate(R.layout.fragment_score_list, container, false);
        setupListAdapter();

        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        Log.d("ML.ScoreListFragment", "onActivityCreated()");
        super.onActivityCreated(savedInstanceState);

        requestScoreList();
    }

    public void provideDependencies(GetScoreListInteractor getScoreListInteractor) {
        Log.d("ML.ScoreListFragment", "provideDependencies()");

        this.getScoreListInteractor = getScoreListInteractor;
    }

    private void setupListAdapter()
    {
        adapter = new ScoreListAdapter(getActivity(), R.layout.viewgroup_score_list_item, new ArrayList<Score>());
        setListAdapter(adapter);
    }

    private void requestScoreList() {
        Log.d("ML.ScoreListFragment", "requestScoreList()");

        if (this.getScoreListInteractor == null) {
            Log.d("ML.ScoreListFragment", "Interactor was null, fetching from activity.");
            getScoreListInteractor = ((NumberMemoryActivity) getActivity()).getScoreListInteractor;
        }

        getScoreListInteractor.setCallback(new GetScoreListInteractor.Callback() {
            @Override
            public void onScoresLoaded(final List<Score> scores) {

                /* Posting on the main thread, as the adapter framework was bombing out without this, yielding a blank list */
                new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Log.d("ML.ScoreListFragment", scores.size() + " score received!");
                        refreshAdapterData(scores);
                    }
                }, 200);
            }
        });

        getScoreListInteractor.execute();
    }

    private void refreshAdapterData(List<Score> scores)
    {
        adapter.clear();
        adapter.add(new Score(0,0,0,new Date())); // Dummy record serves as header
        adapter.addAll(scores);
        adapter.notifyDataSetChanged();
    }
}