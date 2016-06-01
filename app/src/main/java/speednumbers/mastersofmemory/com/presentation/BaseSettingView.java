package speednumbers.mastersofmemory.com.presentation;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

import javax.inject.Inject;

import speednumbers.mastersofmemory.com.MyApplication;
import speednumbers.mastersofmemory.com.domain.interactors.UpdateSettingInteractor;
import speednumbers.mastersofmemory.com.domain.model.Setting;
import speednumbers.mastersofmemory.com.presentation.injection.components.DaggerChallengeComponent;
import speednumbers.mastersofmemory.com.presentation.injection.components.DaggerSettingComponent;
import speednumbers.mastersofmemory.com.presentation.injection.components.SettingComponent;
import speednumbers.mastersofmemory.com.presentation.injection.modules.ChallengeModule;
import speednumbers.mastersofmemory.com.presentation.injection.modules.SettingModule;

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
