package com.jess.arms.integration.lifecycle;

import com.trello.rxlifecycle2.android.FragmentEvent;

/**
 * ================================================
 * 让 Fragment 实现此接口,即可正常使用 {@link com.trello.rxlifecycle2.RxLifecycle}
 *
 * Created by JessYan on 26/08/2017 17:14
 * Contact with jess.yan.effort@gmail.com
 * Follow me on https://github.com/JessYanCoding
 * ================================================
 */

public interface FragmentLifecycleable extends Lifecycleable<FragmentEvent> {
}
