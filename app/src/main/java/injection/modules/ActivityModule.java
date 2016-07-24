
package injection.modules;

import android.app.Activity;

import dagger.Module;
import dagger.Provides;
import injection.PerActivity;

/**
 * A module to wrap the Activity state and expose it to the graph.
 */
@Module
public class ActivityModule {
  private final Activity activity;

  public ActivityModule(Activity activity) {
    this.activity = activity;
  }

  @Provides
  @PerActivity
  Activity activity() {
    return this.activity;
  }
}
