package com.example.binderclient;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.data.Person;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private IService mRemote;
    private TextView mTv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTv = (TextView) findViewById(R.id.text);
        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mRemote != null){
                  /* String str =  mRemote.test();
                    if(str != null){
                        mTv.setText(str);
                        Log.d("Ivanwu","result:" + str);
                    }*/

                    Person person = new Person();
                    person.setName("Ivanwu");
                    person.setAge(26);
                    mRemote.PassPerson(person);

                }
            }
        });

        Intent intent = new Intent("ivanwu.binder.test");
        this.bindService(createExplicitFromImplicitIntent(this, intent),connection, Context.BIND_AUTO_CREATE);
    }

    public static Intent createExplicitFromImplicitIntent(Context context, Intent implicitIntent) {
        // Retrieve all services that can match the given intent
        PackageManager pm = context.getPackageManager();
        List<ResolveInfo> resolveInfo = pm.queryIntentServices(implicitIntent, 0);

        // Make sure only one match was found
        if (resolveInfo == null || resolveInfo.size() != 1) {
            return null;
        }

        // Get component info and create ComponentName
        ResolveInfo serviceInfo = resolveInfo.get(0);
        String packageName = serviceInfo.serviceInfo.packageName;
        String className = serviceInfo.serviceInfo.name;
        ComponentName component = new ComponentName(packageName, className);

        // Create a new intent. Use the old one for extras and such reuse
        Intent explicitIntent = new Intent(implicitIntent);

        // Set the component to be explicit
        explicitIntent.setComponent(component);

        return explicitIntent;
    }

    ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.d("Ivanwu","onServiceConnected:" + service);
            if(service != null){
                mRemote =  ServiceManager.asInterface(service);
                Log.d("Ivanwu","onServiceConnected mRemote:" + mRemote);
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };


}
