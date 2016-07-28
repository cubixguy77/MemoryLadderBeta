package memorization;

import android.app.Activity;
import android.os.Bundle;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import speednumbers.mastersofmemory.com.presentation.R;

public class NumberMemoryActivity extends Activity {

    @BindView(R.id.numberGrid) NumberGridView grid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memory_numbers);
        ButterKnife.bind(this);

        grid.setAdapter(new NumberGridAdapter(this, new GridData(500, 2)));
    }

    @OnClick(R.id.nextGroupButton) void onNextClick() {
        grid.onNextClick();
    }
}
