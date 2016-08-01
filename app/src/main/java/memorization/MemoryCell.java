package memorization;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.AbsListView;
import android.widget.TextView;

import speednumbers.mastersofmemory.com.presentation.R;

public class MemoryCell extends TextView {

    private boolean isSelected;

    public MemoryCell(Context context, AttributeSet attrs) {
        super(context, attrs);
        setTextSize(30);
        setGravity(Gravity.CENTER);
        //setLayoutParams(new AbsListView.LayoutParams(AbsListView.LayoutParams.WRAP_CONTENT, AbsListView.LayoutParams.MATCH_PARENT));
        //AbsListView.LayoutParams params = (AbsListView.LayoutParams) getLayoutParams();
        //params.height = getWidth();
        //setLayoutParams(params);
        setLayoutParams(new AbsListView.LayoutParams(190, 190));
        isSelected = false;
    }

    public void setAsRowMarker() {
        setTextColor(Color.MAGENTA);
    }

    public void setAsDataCell() {
        setTextColor(Color.BLACK);
    }

    public void setAsHiddenDataCell() {
        setAsDataCell();
        setText("__");
    }

    public void setSelected(boolean isSelected) {

        this.isSelected = isSelected;

        if (this.isSelected) {
            setTextColor(Color.WHITE);
            setBackgroundResource(R.drawable.shape_rounded_corners);
        }
        else {
            setTextColor(Color.BLACK);
            setBackgroundResource(0);
        }

    }
}
