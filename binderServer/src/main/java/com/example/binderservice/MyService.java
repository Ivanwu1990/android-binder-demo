package com.example.binderservice;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.data.Person;

/**
 * Created by Ivanwu on 2017/2/8.
 */

public class MyService extends Service{


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new Stub();
    }

    public static  class Stub extends Binder implements IService{

        public Stub() {
            this.attachInterface(this, "com.ivanwu.test");
        }


        @Override
        protected boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            if(code == 202){
               String request = data.readString();
                Log.d("Ivanwu","rcv :" + request);
                reply.writeNoException();
                reply.writeString(test());
                return true;
            }if(code == 203){
                Person person = data.readParcelable(Person.class.getClassLoader());
                Log.d("Ivanwu","rcv person name:" + person.getName());
                Log.d("Ivanwu","rcv person age:" + person.getAge());
                return true;
            }else {
                return super.onTransact(code, data, reply, flags);
            }
        }



        @Override
        public IBinder asBinder() {
            return this;
        }

        @Override
        public String test() {
            return "hello world!!!!!!";
        }

        @Override
        public void PassPerson(Person person) {

        }
    }


}
