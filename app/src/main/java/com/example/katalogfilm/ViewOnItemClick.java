package com.example.katalogfilm;

import android.view.View;

/**
 * Created by User on 1/13/2019.
 */

public class ViewOnItemClick {

    public interface MovieItemCallback {
        void onItemClick(int position, View view);
    }

}
