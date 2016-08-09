package memorization;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

import speednumbers.mastersofmemory.challenges.domain.model.Challenge;

public class NumberGridView extends GridView {

    private NumberGridAdapter adapter;

    public NumberGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setGameStateLifeCycleListener() {
        Bus.getBus().subscribe(this);
        adapter = new NumberGridAdapter(getContext());
        setAdapter(adapter);
    }
}
