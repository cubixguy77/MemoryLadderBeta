package speednumbers.mastersofmemory.com.presentation;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Switch;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import speednumbers.mastersofmemory.com.domain.model.Setting;

public class MemTimerSettingView extends BaseSettingView {
    @BindView(R.id.memorizationTimeText) TextView memorizationTimeText;
    @BindView(R.id.memorizationTimerSwitch) Switch memorizationTimerSwitch;

    private Setting setting;

    public MemTimerSettingView(Context context) {
        super(context);
        this.setting = new Setting(1,1,3,"Test1",1,true);
        System.out.println("Mem Timer Constructor: 1");
        initializeViews(context);
    }

    public MemTimerSettingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.setting = new Setting(1,1,3,"Test2",1,true);
        System.out.println("Mem Timer Constructor: 2");
        initializeViews(context);
    }

    public MemTimerSettingView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.setting = new Setting(1,1,3,"Test3",1,true);
        System.out.println("Mem Timer Constructor: 3");
        initializeViews(context);
    }

    private void initializeViews(Context context) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.viewgroup_setting_memorization_timer, this);
        ButterKnife.bind(this, view);
        System.out.println("Mem Timer: Views initialized");
        //onFinishInflate();
    }

    @Override
    protected void onFinishInflate() {
        System.out.println("Mem Timer: on finish inflate");
        super.onFinishInflate();
        displaySettingValue(this.setting.getValue());
    }

    public void setModel(Setting setting) {
        System.out.println("Mem Timer: set model");
        this.setting = setting;
        displaySettingValue(this.setting.getValue());
    }

    private void displaySettingValue(int value) {
        memorizationTimeText.setText(value + " " + "seconds.");
        memorizationTimerSwitch.setChecked(enabled());
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

    @OnClick(R.id.memorizationTimerSwitch) void onToggleTimer() {
        if (enabled())
            onSelectValue(0);
        else
            onSelectValue(90);
    }
}