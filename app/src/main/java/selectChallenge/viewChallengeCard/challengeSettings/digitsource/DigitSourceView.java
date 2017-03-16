package speednumbers.mastersofmemory.challengelist.challenge.settings.digitsource;

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

public class DigitSourceView extends BaseSettingView {
    @BindView(R.id.digitSourceValue) TextView digitSourceValueText;

    private Context context;
    private Setting setting;

    public DigitSourceView(Context context) {
        super(context);
        this.setting = new Setting(1,1,0,"Test1",1,true);
        initializeViews(context);
    }

    public DigitSourceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.setting = new Setting(1,1,0,"Test2",1,true);
        initializeViews(context);
    }

    public DigitSourceView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.setting = new Setting(1,1,0,"Test3",1,true);
        initializeViews(context);
    }

    private void initializeViews(Context context) {
        this.context = context;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.viewgroup_setting_digit_source, this);
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
        switch (DigitSource.values()[value]) {
            case RANDOM: digitSourceValueText.setText(getResources().getString(R.string.random)); break;
            case PI: digitSourceValueText.setText(getResources().getString(R.string.pi)); break;
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
        builder.setTitle(getResources().getString(R.string.digit_source));
        final CharSequence[] items = {
                getResources().getString(R.string.random),
                getResources().getString(R.string.pi)
        };
        builder.setItems(items, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int selectionIndex) {
                onSelectValue(selectionIndex);
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }

    @OnClick(R.id.digitSourceValue) void onSelectSettingValue() {
        showDialog();
    }
    @OnClick(R.id.digitSourceTitle) void onSelectSettingTitle() { showDialog(); }
}