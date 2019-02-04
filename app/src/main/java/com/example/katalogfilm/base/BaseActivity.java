package com.example.katalogfilm.base;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;

import butterknife.Unbinder;

public abstract class BaseActivity extends AppCompatActivity {

    private Unbinder unbinder;

    public abstract void setUp();

    public void setUnbinder(Unbinder unbinder) {
        this.unbinder = unbinder;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(unbinder!=null) unbinder.unbind();
    }


}
