package scores;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import speednumbers.mastersofmemory.com.presentation.R;

public class ScoreListFragment extends ListFragment {

    private GetScoreListInteractor getScoreListInteractor;
    private ScoreListAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_score_list, container, false);
        setupListAdapter();
        return root;
    }

    public void provideDependencies(GetScoreListInteractor getScoreListInteractor) {
        this.getScoreListInteractor = getScoreListInteractor;
        requestScoreList();
    }

    private void setupListAdapter()
    {
        adapter = new ScoreListAdapter(getActivity(), R.layout.viewgroup_score_list_item, new ArrayList<Score>());
        setListAdapter(adapter);
    }

    private void requestScoreList() {
        getScoreListInteractor.setCallback(new GetScoreListInteractor.Callback() {
            @Override
            public void onScoresLoaded(List<Score> scores) {
                System.out.println("Score List: " + scores.size() + " score received!");
                refreshAdapterData(scores);
            }
        });

        getScoreListInteractor.execute();
    }

    private void refreshAdapterData(List<Score> scores)
    {
        adapter.setNotifyOnChange(false);
        adapter.clear();
        adapter.addAll(scores);
        adapter.notifyDataSetChanged();
    }
}