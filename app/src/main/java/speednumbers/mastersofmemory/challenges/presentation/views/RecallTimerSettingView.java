package speednumbers.mastersofmemory.challenges.presentation.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Switch;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import speednumbers.mastersofmemory.challenges.domain.model.Setting;
import speednumbers.mastersofmemory.challenges.presentation.views.BaseSettingView;
import speednumbers.mastersofmemory.com.presentation.R;

public class RecallTimerSettingView extends BaseSettingView {
    @BindView(R.id.recallTimeText) TextView recallTimeText;
    @BindView(R.id.recallTimerSwitch) Switch recallTimerSwitch;

    private Setting setting;

    public RecallTimerSettingView(Context context) {
        super(context);
        this.setting = new Setting(1,1,3,"Test1",1,true);
        System.out.println("Recall Timer Constructor: 1");
        initializeViews(context);
    }

    public RecallTimerSettingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.setting = new Setting(1,1,3,"Test2",1,true);
        System.out.println("Recall Timer Constructor: 2");
        initializeViews(context);
    }

    public RecallTimerSettingView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.setting = new Setting(1,1,3,"Test3",1,true);
        System.out.println("Recall Timer Constructor: 3");
        initializeViews(context);
    }

    private void initializeViews(Context context) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.viewgroup_setting_recall_timer, this);
        ButterKnife.bind(this, view);
        System.out.println("Recall Timer: Views initialized");
    }

    @Override
    protected void onFinishInflate() {
        System.out.println("Recall Timer: on finish inflate");
        super.onFinishInflate();
        displaySettingValue(this.setting.getValue());
    }

    public void setModel(Setting setting) {
        System.out.println("Recall Timer: set model");
        this.setting = setting;
        displaySettingValue(this.setting.getValue());
    }

    private void displaySettingValue(int value) {
        recallTimeText.setText(value + " " + "seconds.");
        recallTimerSwitch.setChecked(enabled());
    }

    private void onSelectValue(int value) {
        if (this.setting.getValue() != value) {
            this.setting.setValue(value);
            displaySettingValue(value);
            updateSettingValue(setting);
        }
    }

    private boolean enabled() {
        return this.setting.getValue() > 0;
    }

    @OnClick(R.id.recallTimerSwitch) void onToggleTimer() {
        if (enabled())
            onSelectValue(0);
        else
            onSelectValue(90);
    }
}