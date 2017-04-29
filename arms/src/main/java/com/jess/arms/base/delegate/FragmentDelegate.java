package com.jess.arms.base.delegate;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

/**
 * Created by jess on 29/04/2017 14:30
 * Contact with jess.yan.effort@gmail.com
 */

public interface FragmentDelegate {

    void onAttach(Context context);

    void onCreate(Bundle savedInstanceState);

    void onActivityCreate(Bundle savedInstanceState);

    void onCreateView(View view, Bundle savedInstanceState);

    void onStart();

    void onResume();

    void onPause();

    void onStop();

    void onSaveInstanceState(Bundle outState);

    void onDestroyView();

    void onDestroy();

    void onDetach();
}
