package speednumbers.mastersofmemory.challengelist.challenge.settings.digitgrouping;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import speednumbers.mastersofmemory.challengelist.challenge.settings.BaseSettingView;
import speednumbers.mastersofmemory.challengelist.challenge.settings.Setting;
import speednumbers.mastersofmemory.com.presentation.R;

public class DigitsPerGroupView extends BaseSettingView {
    @BindView(R.id.digitsPerGroupValue) TextView digitsPerGroupValue;

    private Context context;
    private Setting setting;

    public DigitsPerGroupView(Context context) {
        super(context);
        this.setting = new Setting(1,1,3,"Test1",1,true);
        initializeViews(context);
    }

    public DigitsPerGroupView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.setting = new Setting(1,1,3,"Test2",1,true);
        initializeViews(context);
    }

    public DigitsPerGroupView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.setting = new Setting(1,1,3,"Test3",1,true);
        initializeViews(context);
    }

    private void initializeViews(Context context) {
        this.context = context;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.viewgroup_setting_digits_per_group, this);
        ButterKnife.bind(this, view);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        displayValue(this.setting.getValue());
    }

    public void setModel(Setting setting) {
        this.setting = setting;
        displayValue(this.setting.getValue());
    }

    private void displayValue(int value) {
        switch (value) {
            case 1: digitsPerGroupValue.setText(getResources().getString(R.string.challengeList_numDigits, "1")); break;
            case 2: digitsPerGroupValue.setText(getResources().getString(R.string.challengeList_numDigits, "2")); break;
            case 3: digitsPerGroupValue.setText(getResources().getString(R.string.challengeList_numDigits, "3")); break;
            default: break;
        }
    }

    private void onSelectValue(int value) {
        if (this.setting.getValue() != value) {
            this.setting.setValue(value);
            displayValue(value);
            updateSettingValue(setting);
        }
    }

    private void showDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(getResources().getString(R.string.digit_grouping));
        final CharSequence[] items = {
                getResources().getString(R.string.digitsPerGroup, "1"),
                getResources().getString(R.string.digitsPerGroup, "2"),
                getResources().getString(R.string.digitsPerGroup, "3")
        };
        builder.setItems(items, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int selectionIndex) {
                switch (selectionIndex+1) {
                    case 1: onSelectValue(1); break;
                    case 2: onSelectValue(2); break;
                    case 3: onSelectValue(3); break;
                    default: break;
                }
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }

    @OnClick(R.id.digitsPerGroupValue) void onSelectSettingValue() {
        showDialog();
    }
    @OnClick(R.id.digitsPerGroupTitle) void onSelectSettingTitle() { showDialog(); }
}