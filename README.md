# MVPArms [beta] 
##a common Architecture for Android Applications developing based on MVP，integrates many Open Source Projects ,to make your developing quicker and easier. 
一个整合了大量主流开源项目的Android Mvp快速搭建框架

##Wiki
[Detailed usage reference Wiki(详细使用方法,请参照Wiki)](https://github.com/JessYanCoding/MVPArms/wiki)

##The project used [In Progress]
[Inspired by eyepetizer（灵感来源于开眼视频,开发中...）](https://github.com/JessYanCoding/WideEyes) 


##Notice
* 使用框架必须有Dagger2，Rxjava的基础.  

* AppComponent是一个管理所有单例对象的类,使用dagger2管理, DaggerAppComponent为Dagger2使用apt自动生成如果缺少的话，先把报错的部分注释掉，然后编译下项目, DaggerAppComponent就自动生成了，这个时候打开注释就可以了,具体用法请参照dagger2的文档

* 使用框架必需继承`BaseApplication``BaseActivity``BaseFragment``MVP基类`，还提供有一些 `Adapter``ViewHodler`等的基类.

* 使用此框架自带自动适配功能，请参考 [AutoLayout使用方法](https://github.com/hongyangAndroid/AndroidAutoLayout).

* 此框架使用`RxPermissions`用于权限管理(适配android6.0),并提供PermissionUtil工具类一行代码实现权限请求. 

* 本框架不提供与**UI**有关的任何第三方库(除了`AutoLayout`屏幕适配方案).

* **网络请求层**: 默认使用Retrofit，如今主流的网络请求框架有`Volley`,`Okhttp`,`Retrofit`(`Android-async-http`停止维护了)，因为这个是库基于Rxjava， Retrofit支持Rxjava，默认使用Okhttp请求(Okhttp使用Okio，Okio基于IO和NIO所以性能优于Volley,Volley内部封装有Imageloader,支持扩展Okhttp，封装和扩展比Okhttp好，但是比较适合频繁，数据量小得网络请求)，所以此库默认直接通过Dagger注入Retrofit实例给开发者.

* **图片加载**:因为图片加载框架各有优缺点，`Fresco`,`Picasso`,`Glide`这些都是现在比较主流得图片加载框架，所以为了扩展性本库提供一个统一的管理类Imageloader,使用策略者模式，使用者只用实现接口，就可以动态替换图片框架，外部提供统一接口加载图片，替换图片加载框架毫无痛点,并且为了快速实现,默认提供一个Glide的实现类,有其它需求可以参照[**Wiki**](https://github.com/JessYanCoding/MVPArms/wiki)替换为别的图片加载框架

* **Model层** 优秀的数据库太多，`GreenDao`,`Realm`,`SqlBrite`（Square公司出品，对SQLiteOpenHelper封装，提供响应式api访问数据库）,`SqlDelight`,`Storio`,`DBFlow`，每个框架的使用方法都不一样，本框架只提供一个管理类CacheManager里面默认封装了RxCache(此库根据`Retrofit`Api实现了缓存逻辑,并提供响应式接口)，有其他需求的可以自己使用数据库实现缓存逻辑并替换，通过Dagger2向Model层提供ServiceManager（网络请求，Retrofit Api）和CacheManager（数据持久层）,来提供给开发者，这样的好处的是上层（activity/fragment/presenter）不需要知道数据源的细节（来自于网络、数据库，亦或是内存等等），底层可以根据需求修改（缓存的实现细节）上下两层分离互不影响.

##Functionality & Libraries
1. [`Mvp`Google官方出品的`Mvp`架构项目，含有多个不同的架构分支(此为Dagger分支).](https://github.com/googlesamples/android-architecture/tree/todo-mvp-dagger/)
2. [`Dagger2`Google根据Square的Dagger1出品的依赖注入框架，通过apt动态生成代码，性能优于用反射技术依赖注入的框架.](https://github.com/google/dagger)
3. [`Rxjava`提供优雅的响应式Api解决异步请求.](https://github.com/ReactiveX/RxJava)
4. [`RxAndroid`为Android提供响应式Api.](https://github.com/ReactiveX/RxAndroid)
5. [`Rxlifecycle`在Android上使用rxjava都知道的一个坑，就是生命周期的解除订阅，这个框架通过绑定activity和fragment的生命周期完美解决.](https://github.com/trello/RxLifecycle)
6. [`Rxbinding`JakeWharton大神的View绑定框架，优雅的处理View的响应事件.](https://github.com/JakeWharton/RxBinding)
7. [`RxCache`是使用注解为Retrofit加入二级缓存(内存,磁盘)的缓存库](https://github.com/VictorAlbertos/RxCache)
8. [`Retrofit`Square出品的网络请求库，极大的减少了http请求的代码和步骤.](https://github.com/square/retrofit)
9. [`Okhttp`同样Square出品，不多介绍，做Android都应该知道.](https://github.com/square/okhttp)
10. [`Autolayout`鸿洋大神的Android全尺寸适配框架.](https://github.com/hongyangAndroid/AndroidAutoLayout)
11. [`Gson`Google官方的Json Convert框架.](https://github.com/google/gson)
12. [`Butterknife`JakeWharton大神出品的view注入框架.](https://github.com/JakeWharton/butterknife)
13. [`Androideventbus`一个轻量级使用注解的Eventbus.](https://github.com/hehonghui/AndroidEventBus)
14. [`Timber`JakeWharton大神出品Log框架，内部代码极少，但是思想非常不错.](https://github.com/JakeWharton/timber)
15. [`Glide`此库为本框架默认封装图片加载库，可参照着例子更改为其他的库，Api和`Picasso`差不多,缓存机制比`Picasso`复杂,速度快，适合处理大型图片流，支持gfit，`Fresco`太大了！，在5.0一下优势很大，5.0以上系统默认使用的内存管理和`Fresco`类似.](https://github.com/bumptech/glide)
16. [`Realm`速度和跨平台性使它成为如今最火的数据库,美中不足的就是so库太大](https://realm.io/docs/java/latest/#getting-started)
17. [`LeakCanary`Square出品的专门用来检测`Android`和`Java`的内存泄漏,通过通知栏提示内存泄漏信息](https://github.com/square/leakcanary)
18. [`RxErroHandler``Rxjava`错误处理库,可出现错误后重试](https://github.com/JessYanCoding/RxErrorHandler)
 
##Acknowledgements 
感谢本框架所使用到的所有三方库的**Author**,以及所有为`Open Sourece`做无私贡献的**Developer**和**Organizations**,使我们能更好的工作和学习,本人也会将业余时间回报给开源社区


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
