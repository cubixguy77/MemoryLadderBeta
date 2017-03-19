package selectChallenge.viewChallengeCard.challengeSettings.memorizationtimer;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Switch;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import speednumbers.mastersofmemory.challengelist.challenge.settings.Setting;
import selectChallenge.viewChallengeCard.challengeSettings.BaseSettingView;
import speednumbers.mastersofmemory.com.presentation.R;
import playChallenge.timer.TimeFormat;


public class MemTimerSettingView extends BaseSettingView {
    @BindView(R.id.memorizationTimeText) TextView memorizationTimeText;
    @BindView(R.id.memorizationTimerSwitch) Switch memorizationTimerSwitch;

    private Context context;
    private Setting setting;

    public MemTimerSettingView(Context context) {
        super(context);
        this.setting = new Setting(1,1,3,"Test1",1,true);
        initializeViews(context);
    }

    public MemTimerSettingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.setting = new Setting(1,1,3,"Test2",1,true);
        initializeViews(context);
    }

    public MemTimerSettingView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.setting = new Setting(1,1,3,"Test3",1,true);
        initializeViews(context);
    }

    private void initializeViews(Context context) {
        this.context = context;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.viewgroup_setting_memorization_timer, this);
        ButterKnife.bind(this, view);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        displaySettingValue(this.setting.getValue());
    }

    public void setModel(Setting setting) {
        this.setting = setting;
        displaySettingValue(this.setting.getValue());
    }

    private void displaySettingValue(int value) {
        if (!isTimerEnabled()) {
            memorizationTimeText.setText(getResources().getString(R.string.Off));
            memorizationTimerSwitch.setChecked(false);
        }
        else {
            memorizationTimeText.setText(TimeFormat.formatIntoEnglishTime(value, getResources()));
            memorizationTimerSwitch.setChecked(true);
        }
    }

    private void onSelectValue(int value) {
        if (this.setting.getValue() != value) {
            this.setting.setValue(value);
            displaySettingValue(value);
            updateSettingValue(setting);
        }

        displaySettingValue(value);
    }

    private boolean isTimerEnabled() {
        return this.setting != null && this.setting.getValue() > 0;
    }

    @Override
    public void showDialog() {
        TimePickerDialog dialog = new TimePickerDialog(context, this.setting.getValue(), getResources().getString(R.string.memorizationTime));
        dialog.setDialogResult(new TimePickerDialog.OnMyDialogResultTime() {
            public void finish(int result) {
                onSelectValue(result);
            }
        });
        dialog.show();
    }

    @OnClick(R.id.memorizationTimerSwitch) void onToggleTimer() {
        if (isTimerEnabled())
            onSelectValue(0);
        else
            showDialog();
    }
}