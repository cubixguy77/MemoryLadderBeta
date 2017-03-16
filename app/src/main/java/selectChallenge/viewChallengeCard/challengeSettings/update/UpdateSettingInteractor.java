package speednumbers.mastersofmemory.challengelist.challenge.settings.update;

import framework.interactors.Interactor;
import speednumbers.mastersofmemory.challengelist.challenge.settings.Setting;

public interface UpdateSettingInteractor extends Interactor {
    void updateSetting(Setting setting);
}
