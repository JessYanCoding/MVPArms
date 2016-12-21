# MVPArms 
[![Chrome Web Store](https://img.shields.io/chrome-web-store/stars/nimelepbpejjlbmoobocpfnjhihnpked.svg)]()
[![License](http://img.shields.io/badge/License-Apache%202.0-blue.svg?style=flat-square)](http://www.apache.org/licenses/LICENSE-2.0)
[![API](https://img.shields.io/badge/API-15%2B-blue.svg?style=flat-square)](https://developer.android.com/about/versions/android-4.0.3.html)

##a common Architecture for Android Applications developing based on MVP，integrates many Open Source Projects( like Dagger2,Rxjava,Retrofit... ),to make your developing quicker and easier. 

[中文说明](MVPArms.md)

##Architectural
<img src="https://github.com/JessYanCoding/MVPArms/raw/master/image/Architecture.png" width="80%" height="80%">

##Usage
> New Project
>> If you are building a new project, directly to the entire project **clone** (or download), as **Demo** as the main module, then the package name into their own package name , **Demo** contains the package structure can be used directly, a mainstream `MVP` +` Dagger2` + `Retrofit` +` Rxjava` framework so easy to build successful, and now you refer **Demo Mvp** Package under the **UserActivity** format,[Use Template to automatically generate MVP, Dagger2 related classes under the corresponding package](http://www.jianshu.com/p/56cf17ab896d),With access to [Wiki documents] (https://github.com/JessYanCoding/MVPArms/wiki) slowly grasp the framework to see more articles as soon as possible in the project to use it, in practice, learning is the fastest

> Old Project
>> [Old projects would like to introduce this framework, you can refer to the Wiki documentation, written in great detail](https://github.com/JessYanCoding/MVPArms/wiki)

##Wiki
[Detailed usage reference Wiki](https://github.com/JessYanCoding/MVPArms/wiki)

##Notice

* The use of these technologies for the latter part of the project maintenance and iterative, especially large projects is very helpful, but is to develop a pre-write a page to write a lot of `MVP`,` Dagger2` class and interface, which is indeed a headache for the development of pre- Now the framework has been able to [mvp_generator_solution](https://github.com/JessYanCoding/MVPArms/blob/master/MVP_generator_solution) automatically generate some `MVP`,` Dagger2` template code, and now we can very easily use the framework.

* First run **app**, the cold start will be a long time, this is not because of this framework, because the `Instant run` in **Debug** mode will start the first deployment of some resources, if the signature to play **Release** package is not There will be a cold start is very slow, so do not worry about affecting the user experience.

* If you rename **WEApplication**, the following error occurs in the startup **app**: `Unable to instantiate application com.android.tools.fd.runtime.BootstrapApplication`, please close the instant run.

* AppComponent is a management of all single-instance object class, the use of dagger2 management, DaggerAppComponent for Dagger2 automatically generated using apt if missing, the first part of the error comment out, and then compile the project, DaggerAppComponent automatically generated, this time to open the Notes on Can, the specific use of dagger2 please refer to the document.

* Use this frame comes with automatic adaptation function, please refer to [AndroidAutoLayout](https://github.com/hongyangAndroid/AndroidAutoLayout).

* This framework uses `RxPermissions` for rights management (adaptation android6.0), and provides a PermissionUtil tool class line of code to implement the permission request.

* This framework does not provide any third-party libraries associated with the **UI**(except for the [`AndroidAutoLayout`](https://github.com/hongyangAndroid/AndroidAutoLayout) screen adaptation scheme).


##Functionality & Libraries
1. [`Mvp` Google's official` Mvp` architecture project, which contains several different schema branches (this is the Dagger branch).](https://github.com/googlesamples/android-architecture/tree/todo-mvp-dagger/)
2. [`Dagger2`](https://github.com/google/dagger)
3. [`Rxjava`](https://github.com/ReactiveX/RxJava)
4. [`RxAndroid`](https://github.com/ReactiveX/RxAndroid)
5. [`Rxlifecycle`](https://github.com/trello/RxLifecycle)
6. [`Rxbinding`](https://github.com/JakeWharton/RxBinding)
7. [`RxCache`](https://github.com/VictorAlbertos/RxCache)
8. [`Retrofit`](https://github.com/square/retrofit)
9. [`Okhttp`](https://github.com/square/okhttp)
10. [`Autolayout`](https://github.com/hongyangAndroid/AndroidAutoLayout)
11. [`Gson`](https://github.com/google/gson)
12. [`Butterknife`](https://github.com/JakeWharton/butterknife)
13. [`Androideventbus`](https://github.com/hehonghui/AndroidEventBus)
14. [`Timber`](https://github.com/JakeWharton/timber)
15. [`Glide`](https://github.com/bumptech/glide)
16. [`LeakCanary`](https://github.com/square/leakcanary)
17. [`RxErroHandler`](https://github.com/JessYanCoding/RxErrorHandler)


##Update
* Thursday, 15 December 2016: [**AppManager**](https://github.com/JessYanCoding/MVPArms/wiki#3.11)


##TODO
* Improve RxErrorHandler
* Improve RequestIntercept


##Acknowledgements 
Thanks to all the three libraries used in this framework **Author**, and all for the 'Open Sourece' selfless contributions **Developer** and **Organizations**, so that we can better work and study, I will also spare time return to the open source community

##About Me
* **Email**: jess.yan.effort@gmail.com  

##License
``` 
 Copyright 2016, jessyan       
  
   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at 
 
       http://www.apache.org/licenses/LICENSE-2.0 

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
```
