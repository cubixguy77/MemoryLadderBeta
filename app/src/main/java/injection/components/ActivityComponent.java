package injection.components;

import android.app.Activity;


import dagger.Component;
import injection.PerActivity;
import injection.modules.ActivityModule;

@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {
  //Exposed to sub-graphs.
  Activity activity();
}
