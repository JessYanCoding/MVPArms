/*
  * Copyright 2017 JessYan
  *
  * Licensed under the Apache License, Version 2.0 (the "License");
  * you may not use this file except in compliance with the License.
  * You may obtain a copy of the License at
  *
  *      http://www.apache.org/licenses/LICENSE-2.0
  *
  * Unless required by applicable law or agreed to in writing, software
  * distributed under the License is distributed on an "AS IS" BASIS,
  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  * See the License for the specific language governing permissions and
  * limitations under the License.
  */
package com.jess.arms.integration.lifecycle;

import com.trello.rxlifecycle2.android.ActivityEvent;

/**
 * ================================================
 * 让 Activity 实现此接口,即可正常使用 {@link com.trello.rxlifecycle2.RxLifecycle}
 *
 * Created by JessYan on 26/08/2017 17:14
 * Contact with jess.yan.effort@gmail.com
 * Follow me on https://github.com/JessYanCoding
 * ================================================
 */

public interface ActivityLifecycleable extends Lifecycleable<ActivityEvent> {
}
