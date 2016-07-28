package memorization;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.AbsListView;
import android.widget.TextView;

import speednumbers.mastersofmemory.com.presentation.R;

public class Cell extends TextView {

    private boolean isSelected;

    public Cell(Context context, AttributeSet attrs) {
        super(context, attrs);
        setText("12");
        setTextSize(30);
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

    public void setSelected(boolean isSelected) {

        this.isSelected = isSelected;

        if (this.isSelected) {
            setTypeface(null, Typeface.BOLD);
            setTextColor(Color.WHITE);
            setBackgroundResource(R.drawable.shape_rounded_corners);
        }
        else {
            setTypeface(null, Typeface.NORMAL);
            setTextColor(Color.BLACK);
            setBackgroundResource(0);
        }

    }
}
