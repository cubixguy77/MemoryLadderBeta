package speednumbers.mastersofmemory.challenges.domain.interactors;

import interactors.base.Interactor;
import speednumbers.mastersofmemory.challenges.domain.model.Setting;

public interface UpdateSettingInteractor extends Interactor {
    void updateSetting(Setting setting);
}
