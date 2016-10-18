package recall;

import android.content.Context;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextWatcher;
import android.text.method.DigitsKeyListener;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AbsListView;
import android.widget.EditText;

import memorization.Bus;
import speednumbers.mastersofmemory.com.presentation.R;

public class RecallCell extends EditText {

    private int position;
    private int numDigitsPerCell;
    private int highlightedPosition;


    public RecallCell(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public RecallCell(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void finalizeSetup() {
        int sizeInPixels = (int) getResources().getDimension(R.dimen.number_grid_cellSize);
        setLayoutParams(new AbsListView.LayoutParams(sizeInPixels, sizeInPixels));

        int textSize = (int) getResources().getDimension(R.dimen.recall_cell_textSize);
        setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize);

        setInputType(InputType.TYPE_CLASS_NUMBER);
        setFilters(new InputFilter[] {new InputFilter.LengthFilter(numDigitsPerCell)});

        setGravity(Gravity.CENTER);
        setRawInputType(InputType.TYPE_CLASS_TEXT); // Disables keyboard
        setTextIsSelectable(true);
        setSelectAllOnFocus(true);
        setOnFocusChangeListener(new OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus && position != highlightedPosition) {
                    Bus.getBus().onPositionChange(position);
                }
            }
        });
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public void setHighlightedPosition(int highlightedPosition) {
        this.highlightedPosition = highlightedPosition;
    }

    public void setNumDigitsPerCell(int numDigitsPerCell) {
        this.numDigitsPerCell = numDigitsPerCell;
    }
}
