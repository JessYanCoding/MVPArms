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

package com.jess.arms.integration.store.lifecyclemodel;

/**
 * LifecycleModel is a class that is responsible for preparing and managing the data for
 * an {@link android.app.Activity Activity} or a {@link android.support.v4.app.Fragment Fragment}.
 * It also handles the communication of the Activity / Fragment with the rest of the application
 * (e.g. calling the business logic classes).
 * <p>
 * A LifecycleModel is always created in association with a scope (an fragment or an activity) and will
 * be retained as long as the scope is alive. E.g. if it is an Activity, until it is
 * finished.
 * <p>
 * In other words, this means that a LifecycleModel will not be destroyed if its owner is destroyed for a
 * configuration change (e.g. rotation). The new instance of the owner will just re-connected to the
 * existing LifecycleModel.
 * <p>
 * The purpose of the LifecycleModel is to acquire and keep the information that is necessary for an
 * Activity or a Fragment. The Activity or the Fragment should be able to observe changes in the
 * LifecycleModel. ViewModels usually expose this information via {@link LiveData} or Android Data
 * Binding. You can also use any observability construct from you favorite framework.
 * <p>
 * LifecycleModel's only responsibility is to manage the data for the UI. It <b>should never</b> access
 * your view hierarchy or hold a reference back to the Activity or the Fragment.
 * <p>
 * Typical usage from an Activity standpoint would be:
 * <pre>
 * public class UserActivity extends Activity {
 *
 *     {@literal @}Override
 *     protected void onCreate(Bundle savedInstanceState) {
 *         super.onCreate(savedInstanceState);
 *         setContentView(R.layout.user_activity_layout);
 *         final UserModel viewModel = LifecycleModelProviders.of(this).get(UserModel.class);
 *         viewModel.userLiveData.observer(this, new Observer<User>() {
 *            {@literal @}Override
 *             public void onChanged(@Nullable User data) {
 *                 // update ui.
 *             }
 *         });
 *         findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
 *             {@literal @}Override
 *             public void onClick(View v) {
 *                  viewModel.doAction();
 *             }
 *         });
 *     }
 * }
 * </pre>
 *
 * LifecycleModel would be:
 * <pre>
 * public class UserModel extends LifecycleModel {
 *     public final LiveData&lt;User&gt; userLiveData = new LiveData<>();
 *
 *     public UserModel() {
 *         // trigger user load.
 *     }
 *
 *     void doAction() {
 *         // depending on the action, do necessary business logic calls and update the
 *         // userLiveData.
 *     }
 * }
 * </pre>
 *
 * <p>
 * ViewModels can also be used as a communication layer between different Fragments of an Activity.
 * Each Fragment can acquire the LifecycleModel using the same key via their Activity. This allows
 * communication between Fragments in a de-coupled fashion such that they never need to talk to
 * the other Fragment directly.
 * <pre>
 * public class MyFragment extends Fragment {
 *     public void onStart() {
 *         UserModel userModel = LifecycleModelProviders.of(getActivity()).get(UserModel.class);
 *     }
 * }
 * </pre>
 * </>
 *
 * Created by JessYan on 21/11/2017 16:57
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 */
public interface LifecycleModel {
    /**
     * This method will be called when this LifecycleModel is no longer used and will be destroyed.
     * <p>
     * It is useful when LifecycleModel observes some data and you need to clear this subscription to
     * prevent a leak of this LifecycleModel.
     */
    @SuppressWarnings("WeakerAccess")
     void onCleared();
}
