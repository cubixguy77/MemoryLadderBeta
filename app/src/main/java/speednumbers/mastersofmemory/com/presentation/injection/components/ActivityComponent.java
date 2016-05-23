package speednumbers.mastersofmemory.com.presentation.injection.components;

import android.app.Activity;


import dagger.Component;
import speednumbers.mastersofmemory.com.presentation.injection.PerActivity;
import speednumbers.mastersofmemory.com.presentation.injection.modules.ActivityModule;

@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {
  //Exposed to sub-graphs.
  Activity activity();
}
