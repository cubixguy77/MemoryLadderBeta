package speednumbers.mastersofmemory.com.presentation.injection.components;

import dagger.Component;
import speednumbers.mastersofmemory.com.presentation.BaseSettingView;
import speednumbers.mastersofmemory.com.presentation.injection.PerActivity;
import speednumbers.mastersofmemory.com.presentation.injection.modules.SettingModule;

@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = {SettingModule.class})
public interface SettingComponent {
  void inject(BaseSettingView baseSettingView);
}
