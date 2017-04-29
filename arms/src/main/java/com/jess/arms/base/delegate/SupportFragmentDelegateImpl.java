package com.jess.arms.base.delegate;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.View;

/**
 * Created by jess on 29/04/2017 16:12
 * Contact with jess.yan.effort@gmail.com
 */

public class SupportFragmentDelegateImpl implements FragmentDelegate {
    private android.support.v4.app.FragmentManager mFragmentManager;
    private android.support.v4.app.Fragment mFragment;


    public SupportFragmentDelegateImpl(FragmentManager fragmentManager, Fragment fragment) {
        this.mFragmentManager = fragmentManager;
        this.mFragment = fragment;
    }

    @Override
    public void onAttach(Context context) {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

    }

    @Override
    public void onActivityCreate(Bundle savedInstanceState) {

    }

    @Override
    public void onCreateView(View view, Bundle savedInstanceState) {

    }

    @Override
    public void onStart() {

    }

    @Override
    public void onResume() {

    }

    @Override
    public void onPause() {

    }

    @Override
    public void onStop() {

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {

    }

    @Override
    public void onDestroyView() {

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public void onDetach() {

    }
}
