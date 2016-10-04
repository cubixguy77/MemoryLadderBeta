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
        setLayoutParams(new AbsListView.LayoutParams(130, 190));
        setInputType(InputType.TYPE_CLASS_NUMBER);
        setKeyListener(DigitsKeyListener.getInstance("0123456789"));
        setFilters(new InputFilter[] {new InputFilter.LengthFilter(numDigitsPerCell)});
        setTextSize(TypedValue.COMPLEX_UNIT_PX, 60);
        setGravity(Gravity.CENTER);
        setImeOptions(EditorInfo.IME_FLAG_NO_EXTRACT_UI | EditorInfo.IME_FLAG_NO_FULLSCREEN);
        setSelectAllOnFocus(true);
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

    public void addRecallTextWatcher(final RecallTextWatcher watcher) {
        addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override public void afterTextChanged(Editable s) {
                if (watcher != null) {
                    //System.out.println("Change focus to position " + position);
                    watcher.onTextChanged(position, s.toString());
                }
            }});
    }

    @Override
    public void setOnFocusChangeListener(OnFocusChangeListener l) {
        super.setOnFocusChangeListener(l);
    }

    public void setRecallFocusChangeListener(final PositionChangeListener listener) {
        setOnFocusChangeListener(new OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus && position != highlightedPosition) {
                    listener.onPositionChange(position);
                }
            }
        });
    }
}
