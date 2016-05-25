package speednumbers.mastersofmemory.com.presentation;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

import javax.inject.Inject;

import speednumbers.mastersofmemory.com.domain.interactors.UpdateSettingInteractor;
import speednumbers.mastersofmemory.com.domain.model.Setting;

public abstract class BaseSettingView extends RelativeLayout {

    @Inject public UpdateSettingInteractor updateSettingInteractor;

    public BaseSettingView(Context context) {
        super(context);
    }

    public BaseSettingView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BaseSettingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void updateSettingValue(Setting setting) {
        updateSettingInteractor.updateSetting(setting);
    }
}
