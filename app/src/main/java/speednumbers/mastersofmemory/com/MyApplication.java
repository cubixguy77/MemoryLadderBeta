package speednumbers.mastersofmemory.com;

import android.app.Application;
import android.content.Context;

import speednumbers.mastersofmemory.com.presentation.injection.components.ApplicationComponent;
import speednumbers.mastersofmemory.com.presentation.injection.components.DaggerApplicationComponent;
import speednumbers.mastersofmemory.com.presentation.injection.modules.ApplicationModule;

public class MyApplication extends Application {

    private ApplicationComponent applicationComponent;
    private static Context context;

    public void onCreate(){
        super.onCreate();
        MyApplication.context = getApplicationContext();
        initializeInjector();
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
