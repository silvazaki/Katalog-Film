package com.example.katalogfilm.util;

import android.content.Context;
import android.support.v7.app.AlertDialog;

import com.example.katalogfilm.R;

/**
 * Created by User on 2/4/2019.
 */

public class DialogHelper {

    public static AlertDialog loading(Context context){

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setView(R.layout.dialog_loading);
        return builder.create();

    }

}
