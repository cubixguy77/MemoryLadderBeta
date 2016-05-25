package speednumbers.mastersofmemory.com.domain.interactors;

import speednumbers.mastersofmemory.com.domain.interactors.base.Interactor;
import speednumbers.mastersofmemory.com.domain.model.Setting;

public interface UpdateSettingInteractor extends Interactor {
    void updateSetting(Setting setting);
}
