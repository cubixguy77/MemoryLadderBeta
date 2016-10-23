package speednumbers.mastersofmemory.challenges.presentation.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import speednumbers.mastersofmemory.challenges.domain.model.Setting;
import speednumbers.mastersofmemory.com.presentation.R;

public class DigitsPerGroupView extends BaseSettingView {
    @BindView(R.id.onePerGroup) TextView onePerGroup;
    @BindView(R.id.twoPerGroup) TextView twoPerGroup;
    @BindView(R.id.threePerGroup) TextView threePerGroup;

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
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.viewgroup_setting_digits_per_group, this);
        ButterKnife.bind(this, view);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        selectDigitsPerGroup(this.setting.getValue());
    }

    public void setModel(Setting setting) {
        this.setting = setting;
        selectDigitsPerGroup(this.setting.getValue());
    }

    private void deselectDigits() {
        onePerGroup.setTextSize(20);
        twoPerGroup.setTextSize(20);
        threePerGroup.setTextSize(20);
    }

    private void selectDigitsPerGroup(int digitsPerGroup) {
        deselectDigits();
        switch (digitsPerGroup) {
            case 1: onePerGroup.setTextSize(40); break;
            case 2: twoPerGroup.setTextSize(40); break;
            case 3: threePerGroup.setTextSize(40); break;
            default: break;
        }
    }

    private void onSelectValue(int value) {
        if (this.setting.getValue() != value) {
            this.setting.setValue(value);
            selectDigitsPerGroup(value);
            updateSettingValue(setting);
        }
    }

    @OnClick(R.id.onePerGroup) void onSelectOne() {
        onSelectValue(1);
    }

    @OnClick(R.id.twoPerGroup) void onSelectTwo() {
        onSelectValue(2);
    }

    @OnClick(R.id.threePerGroup) void onSelectThree() {
        onSelectValue(3);
    }
}