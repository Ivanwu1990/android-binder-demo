package com.example.binderservice;

import android.os.IInterface;

import com.example.data.Person;

/**
 * Created by Ivanwu on 2017/2/8.
 */

public interface IService extends IInterface{

    String test();

    void PassPerson(Person person);
}
