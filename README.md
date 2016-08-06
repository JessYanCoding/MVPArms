# MVPArms
##a common Architecture for Android Applications developing based on MVP，integrates many Open Source Projects ,to make your developing quicker and easier. ( 一个整合了大量主流开源项目的Android Mvp快速搭建框架)

##Example
[source from eyepetizer（仿照开眼视频的项目）](https://github.com/JessYanCoding/WideEyes) 

##Notice
* 使用框架必须有Dagger2，Rxjava的基础.

* 使用框架必需继承`BaseApplication``BaseActivity``BaseFragment``MVP基类`，还提供有一些 `Adapter``ViewHodler`等的基类.

* 测试相关框架正在研发中.
* 使用此框架自带自动适配功能，请参考 [AutoLayout使用方法](https://github.com/hongyangAndroid/AndroidAutoLayout).
* 本框架不提供与Ui有关的任何第三方库.

* **网络请求层**: 默认使用Retrofit，如今主流的网络请求框架有`Volley`,`Okhttp`,`Retrofit`(`Android-async-http`停止维护了)，因为这个是库基于Rxjava， Retrofit支持Rxjava，默认使用Okhttp请求(Okhttp使用Okio，Okio基于IO和NIO所以性能优于Volley,Volley内部封装有Imageloader,支持扩展Okhttp，封装和扩展比Okhttp好，但是比较适合频繁，数据量小得网络请求)，所以此库默认直接通过Dagger注入Retrofit实例给开发者.

* **图片加载**:因为图片加载框架各有优缺点，`Fresco`,`Picasso`,`Glide`这些都是现在比较主流得图片加载框架，所以为了扩展性本库不默认封装，提供一个统一得管理类Imageloader,使用策略者模式，使用者只用实现接口，就可以动态替换图片框架，外部提供统一接口加载图片，替换图片加载框架毫无痛点.

* **Model层数据库** 同样优秀的数据库太多，`GreenDao`,`Realm`,`SqlBrite`（Square公司出品的rx响应式api数据库）,`SqlDelight`，各有各爱，所以本框架只提供一个统一得管理类DataManager，提供统一的接口CRUD,同样使用策略者模式，可以动态替换数据库，Model层提供ServiceManager（网路请求，Retrofit Api）和DataManager（数据持久层）,来提供给开发者，实现缓存和网络请求切换.

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
