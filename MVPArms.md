# MVPArms 
[ ![Bintray](https://img.shields.io/badge/bintray-v1.6.3-brightgreen.svg) ](https://bintray.com/jessyancoding/maven/MVPArms/1.6.3/link)
[ ![Build Status](https://travis-ci.org/JessYanCoding/MVPArms.svg?branch=master) ](https://travis-ci.org/JessYanCoding/MVPArms)
[ ![API](https://img.shields.io/badge/API-15%2B-blue.svg?style=flat-square) ](https://developer.android.com/about/versions/android-4.0.3.html)
[ ![License](http://img.shields.io/badge/License-Apache%202.0-blue.svg?style=flat-square) ](http://www.apache.org/licenses/LICENSE-2.0)
[ ![Support](https://img.shields.io/badge/Support-RxJava2-blue.svg) ](https://github.com/JessYanCoding/MVPArms/tree/master)
[ ![QQGroup](https://img.shields.io/badge/QQ群-301733278-ff69b4.svg) ](https://shang.qq.com/wpa/qunwpa?idkey=1a5dc5e9b2e40a780522f46877ba243eeb64405d42398643d544d3eec6624917)

## 一个整合了大量主流开源项目的Android Mvp快速搭建框架

## Usage
> New Project
>> 如果你是构建一个全新的项目,直接将整个项目**clone**(或者下载)下来,直接将**Demo**当成主**Module**,再将项目包名改成自己的包名,**Demo**包含可以直接使用的包结构,一个主流的`MVP`+`Dagger2`+`Retrofit`+`Rxjava`框架就这样轻松的构建成功了,现在你参考**Demo Mvp**包下的**UserActivity**的格式,[使用Template在对应包下自动生成MVP,Dagger2相关类](http://www.jianshu.com/p/56cf17ab896d),配合查阅[Wiki文档](https://github.com/JessYanCoding/MVPArms/wiki)慢慢掌握本框架,看再多文章不如早点在项目中使用它,在实践中学习总是最快的
 
> Old Project
>> [老项目想引入此框架,可以参考Wiki文档,写的非常详细](https://github.com/JessYanCoding/MVPArms/wiki)

## Wiki
[详细使用方法及扩展功能,请参照Wiki](https://github.com/JessYanCoding/MVPArms/wiki)

<!--##The project used [In Progress]
[Inspired by eyepetizer（灵感来源于开眼视频,开发中...）](https://github.com/JessYanCoding/WideEyes) -->


## Notice

* [意见收集](https://github.com/JessYanCoding/MVPArms/issues/40)

* [更新日志](https://github.com/JessYanCoding/MVPArms/wiki/UpdateLog)

* [常见issues](https://github.com/JessYanCoding/MVPArms/wiki/Issues)

* [版本更新](https://github.com/JessYanCoding/MVPArms/wiki#1.6) 

* [我们为什么要把Dagger2,MVP以及Rxjava引入项目中?](http://www.jianshu.com/p/91c2bb8e6369)

* 看了上面的文章,对为什么使用这些技术应该比较了解了,使用这些技术对项目后期的维护和迭代特别是大型项目非常有帮助,但是就是开发前期每写一个页面要多写很多`MVP`,`Dagger2`的类和接口,这对于开发前期确实比较头疼,现在本框架已经可以通过[Template](http://www.jianshu.com/p/56cf17ab896d)自动生成一些`MVP`,`Dagger2`的模版代码,现在大家可以非常轻松的使用本框架.

* 使用此框架自带自动适配功能，请参考 [AutoLayout使用方法](https://github.com/hongyangAndroid/AndroidAutoLayout).

* 此框架使用`RxPermissions`用于权限管理(适配android6.0),并提供PermissionUtil工具类一行代码实现权限请求. 

* 作为通用框架,本框架不提供与**UI**有关的任何第三方库(除了`AutoLayout`屏幕适配方案).


## Feature

* **网络请求层**: 默认使用Retrofit，如今主流的网络请求框架有`Volley`,`Okhttp`,`Retrofit`(`Android-async-http`停止维护了)，因为这个是库基于Rxjava， Retrofit支持Rxjava，默认使用Okhttp请求(Okhttp使用Okio，Okio基于IO和NIO所以性能优于Volley,Volley内部封装有Imageloader,支持扩展Okhttp，封装和扩展比Okhttp好，但是比较适合频繁，数据量小得网络请求)，所以此库默认直接通过Dagger注入Retrofit实例给开发者.

* **图片加载**:因为图片加载框架各有优缺点，`Fresco`,`Picasso`,`Glide`这些都是现在比较主流得图片加载框架，所以为了扩展性本库提供一个统一的管理类Imageloader,使用策略者模式，使用者只用实现接口，就可以动态替换图片框架，外部提供统一接口加载图片，替换图片加载框架毫无痛点,并且为了快速实现,默认提供一个Glide的实现类,有其它需求可以参照[**Wiki**](https://github.com/JessYanCoding/MVPArms/wiki#3.4)替换为别的图片加载框架

* **Model层** 优秀的数据库太多，`GreenDao`,`Realm`,`SqlBrite`（Square公司出品，对SQLiteOpenHelper封装，提供响应式api访问数据库）,`SqlDelight`,`Storio`,`DBFlow`，每个框架的使用方法都不一样，本框架只提供一个管理类CacheManager里面默认封装了RxCache(此库根据`Retrofit`Api实现了缓存逻辑,并提供响应式接口)，有其他需求的可以自己使用数据库实现缓存逻辑并替换，通过Dagger2向Model层提供ServiceManager（网络请求，Retrofit Api）和CacheManager（数据持久层）,来提供给开发者，这样的好处的是上层（activity/fragment/presenter）不需要知道数据源的细节（来自于网络、数据库，亦或是内存等等），底层可以根据需求修改（缓存的实现细节）上下两层分离互不影响.

## Functionality & Libraries
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
15. [`Glide`此库为本框架默认封装图片加载库，可参照着例子更改为其他的库，Api和`Picasso`差不多,缓存机制比`Picasso`复杂,速度快，适合处理大型图片流，支持gfit，`Fresco`太大了！，在5.0以下优势很大，5.0以上系统默认使用的内存管理和`Fresco`类似.](https://github.com/bumptech/glide)
16. [`Realm`速度和跨平台性使它成为如今最火的数据库,美中不足的就是so库太大](https://realm.io/docs/java/latest/#getting-started)
17. [`LeakCanary`Square出品的专门用来检测`Android`和`Java`的内存泄漏,通过通知栏提示内存泄漏信息](https://github.com/square/leakcanary)
18. [`RxErroHandler``Rxjava`错误处理库,可在出现错误后重试](https://github.com/JessYanCoding/RxErrorHandler)
 
 
## Update
* Monday, 24 April 2017: [**AppDelegate**](https://github.com/JessYanCoding/MVPArms/wiki#3.12)
* Thursday, 13 April 2017: [**RepositoryManager**](https://github.com/JessYanCoding/MVPArms/wiki#2.3)
* Thursday, 15 December 2016: [**AppManager**](https://github.com/JessYanCoding/MVPArms/wiki#3.11)
* Sunday, 25 December 2016: [**GlobeConfigModule**](https://github.com/JessYanCoding/MVPArms/wiki#3.1)
* Monday, 26 December 2016: [**Version Update**](https://github.com/JessYanCoding/MVPArms/wiki#1.6)


## Acknowledgements 
感谢本框架所使用到的所有三方库的**Author**,以及所有为`Open Sourece`做无私贡献的**Developer**和**Organizations**,使我们能更好的工作和学习,本人也会将业余时间回报给开源社区


## About Me
* **Email**: <jess.yan.effort@gmail.com>  
* **Home**: <http://jessyan.me>
* **掘金**: <https://gold.xitu.io/user/57a9dbd9165abd0061714613>
* **简书**: <http://www.jianshu.com/u/1d0c0bc634db>

## License
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
