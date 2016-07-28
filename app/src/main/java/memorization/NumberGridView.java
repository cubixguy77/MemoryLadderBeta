package memorization;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

public class NumberGridView extends GridView {

    public NumberGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void onNextClick() {
        ((NumberGridAdapter) getAdapter()).onNextClick();
    }
}
