package com.example.binderclient;

import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;

import com.example.data.Person;

/**
 * Created by Ivanwu on 2017/2/8.
 */

public class ServiceManager {


    static public IService asInterface(IBinder obj) {
        if (obj == null) {
            return null;
        }
        IService in =
                (IService)obj.queryLocalInterface("com.ivanwu.test");
        if (in != null) {
            return in;
        }
        return new Proxy(obj);

    }


    public static class Proxy implements IService{
        IBinder mRemote;
        public Proxy(IBinder binder){
            mRemote = binder;
        }

        @Override
        public String test() {
            Parcel data = Parcel.obtain();
            Parcel reply = Parcel.obtain();
            data.writeString("requesst test");
            try {
                mRemote.transact(202, data, reply, 0);

            } catch (RemoteException e) {
                e.printStackTrace();
            }
            reply.readException();
            String str = reply.readString();
            reply.recycle();
            data.recycle();
            return str;
        }

        @Override
        public void PassPerson(Person person) {
            Parcel data = Parcel.obtain();
            Parcel reply = Parcel.obtain();
            data.writeParcelable(person, 0);
            try {
                mRemote.transact(203, data, reply, 0);

            } catch (RemoteException e) {
                e.printStackTrace();
            }
            reply.recycle();
            data.recycle();
        }

        @Override
        public IBinder asBinder() {
            return mRemote;
        }
    }
}
