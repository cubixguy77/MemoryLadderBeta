package selectChallenge;

import android.app.Application;
import android.content.Context;
import android.support.v7.app.AppCompatDelegate;

import com.facebook.stetho.Stetho;
import com.squareup.leakcanary.LeakCanary;

import framework.injection.components.ApplicationComponent;
import framework.injection.components.DaggerApplicationComponent;
import framework.injection.modules.ApplicationModule;

public class MyApplication extends Application {

    private ApplicationComponent applicationComponent;
    private static Context context;

    public void onCreate(){
        super.onCreate();

        if (LeakCanary.isInAnalyzerProcess(this)) {
            return;
        }
        
        LeakCanary.install(this);
        Stetho.initializeWithDefaults(this);
        MyApplication.context = getApplicationContext();
        initializeInjector();

        /* Adding this line to prevent vector asset errors on pre SDK 21 devices */
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }

    private void initializeInjector() {
        this.applicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .build();

    }

    public static MyApplication get(Context context) {
        return (MyApplication) context.getApplicationContext();
    }

    public ApplicationComponent getApplicationComponent() {
        return this.applicationComponent;
    }

    public static Context getAppContext() {
        return MyApplication.context;
    }
}
