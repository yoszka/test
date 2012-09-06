package pl.xt.jokii.carserv;

import org.acra.ACRA;
import org.acra.annotation.ReportsCrashes;

import android.app.Application;

@ReportsCrashes(formKey = "dG83empXa0NwSHhudnk4dS1rRV9FT2c6MQ")
public class MyApplication extends Application{

	@Override
    public void onCreate() {
        // The following line triggers the initialization of ACRA
        ACRA.init(this);
        super.onCreate();
    }
}
