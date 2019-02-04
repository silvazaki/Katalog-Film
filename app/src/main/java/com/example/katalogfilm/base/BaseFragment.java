package com.example.katalogfilm.base;

import android.support.v4.app.Fragment;

import butterknife.Unbinder;

public abstract class BaseFragment extends Fragment {

    protected Unbinder unbinder;

    protected void setUnbinder(Unbinder unbinder){
        this.unbinder = unbinder;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if(unbinder!=null) unbinder.unbind();

    }

}
