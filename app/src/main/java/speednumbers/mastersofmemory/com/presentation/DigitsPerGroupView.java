package speednumbers.mastersofmemory.com.presentation;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import speednumbers.mastersofmemory.com.domain.model.Setting;

public class DigitsPerGroupView extends RelativeLayout {
    @BindView(R.id.onePerGroup) TextView onePerGroup;
    @BindView(R.id.twoPerGroup) TextView twoPerGroup;
    @BindView(R.id.threePerGroup) TextView threePerGroup;

    private Setting setting;

    public DigitsPerGroupView(Context context) {
        super(context);
        this.setting = new Setting(1,1,3,"Test",1,true);
        initializeViews(context);
    }

    public DigitsPerGroupView(Context context, Setting setting) {
        super(context);
        this.setting = setting;
        System.out.println("Setting provided: " + setting.getSettingName() + " " + setting.getValue());
        initializeViews(context);
    }

    public DigitsPerGroupView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.setting = new Setting(1,1,3,"Test",1,true);
        initializeViews(context);
    }

    public DigitsPerGroupView(Context context, AttributeSet attrs, int defStyle) { super(context, attrs, defStyle); }

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

    @OnClick(R.id.onePerGroup) void onSelectOne() {
        selectDigitsPerGroup(1);
    }

    @OnClick(R.id.twoPerGroup) void onSelectTwo() {
        selectDigitsPerGroup(2);
    }

    @OnClick(R.id.threePerGroup) void onSelectThree() {
        selectDigitsPerGroup(3);
    }
}