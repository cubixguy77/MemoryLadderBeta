package framework.injection.components;

import android.app.Activity;


import dagger.Component;
import framework.injection.PerActivity;
import framework.injection.modules.ActivityModule;

@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {
  //Exposed to sub-graphs.
  Activity activity();
}
