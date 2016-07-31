package recall;

import android.content.Context;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.inputmethod.EditorInfo;
import android.widget.AbsListView;
import android.widget.EditText;
import android.widget.TextView;

import memorization.RecallEntryView;
import memorization.RecallTextWatcher;

public class RecallCell extends EditText {

    private int position;
    private final int maxDigits = 2;
    private RecallTextWatcher watcher;

    public RecallCell(Context context, AttributeSet attrs) {
        super(context, attrs);
        setup();
    }

    public RecallCell(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setup();
    }

    private void setup() {
        setLayoutParams(new AbsListView.LayoutParams(130, 190));
        setInputType(InputType.TYPE_CLASS_NUMBER);
        setFilters(new InputFilter[] {new InputFilter.LengthFilter(2)});
        setTextSize(TypedValue.COMPLEX_UNIT_PX, 50);
        setGravity(Gravity.CENTER);
        setImeOptions(EditorInfo.IME_FLAG_NO_EXTRACT_UI);
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public void addRecallTextWatcher(final RecallTextWatcher watcher) {
        addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override public void afterTextChanged(Editable s) {
                if (watcher != null) {
                    watcher.onTextChanged(position, s.toString());
                }
            }});
    }

    public void removeRecallTextWatcher() {
        watcher = null;
    }


}
