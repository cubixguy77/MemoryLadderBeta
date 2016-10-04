package speednumbers.mastersofmemory.challenges.presentation.views;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

import javax.inject.Inject;

import speednumbers.mastersofmemory.challenges.MyApplication;
import speednumbers.mastersofmemory.challenges.domain.model.Setting;
import injection.components.SettingComponent;
import injection.modules.SettingModule;
import speednumbers.mastersofmemory.challenges.domain.interactors.UpdateSettingInteractor;
import injection.components.DaggerSettingComponent;

public abstract class BaseSettingView extends RelativeLayout {

    @Inject public UpdateSettingInteractor updateSettingInteractor;

    public BaseSettingView(Context context) {
        super(context);
        inject(context);
    }

    public BaseSettingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        inject(context);
    }

    public BaseSettingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        inject(context);
    }

    public void inject(Context context) {
        SettingComponent c = DaggerSettingComponent.builder()
                .applicationComponent(MyApplication.get(context).getApplicationComponent())
                .settingModule(new SettingModule())
                .build();
        c.inject(this);
    }

    public void updateSettingValue(Setting setting) {
        updateSettingInteractor.updateSetting(setting);
    }
}