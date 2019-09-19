![Logo](image/arms_banner_v1.0.jpg)
![Official](image/official.jpeg)

<p align="center">
   <a href="https://bintray.com/jessyancoding/maven/MVPArms/_latestVersion">
    <img src="https://img.shields.io/badge/Jcenter-v2.5.2-brightgreen.svg?style=flat-square" alt="Latest Stable Version" />
  </a>
  <a href="https://travis-ci.org/JessYanCoding/MVPArms">
    <img src="https://travis-ci.org/JessYanCoding/MVPArms.svg?branch=master" alt="Build Status" />
  </a>
  <a href="https://developer.android.com/about/versions/android-4.0.html">
    <img src="https://img.shields.io/badge/API-14%2B-blue.svg?style=flat-square" alt="Min Sdk Version" />
  </a>
  <a href="http://www.apache.org/licenses/LICENSE-2.0">
    <img src="http://img.shields.io/badge/License-Apache%202.0-blue.svg?style=flat-square" alt="License" />
  </a>
  <a href="https://www.jianshu.com/u/1d0c0bc634db">
    <img src="https://img.shields.io/badge/Author-JessYan-orange.svg?style=flat-square" alt="Author" />
  </a>
  <a href="https://shang.qq.com/wpa/qunwpa?idkey=7e59e59145e6c7c68932ace10f52790636451f01d1ecadb6a652b1df234df753">
    <img src="https://img.shields.io/badge/QQ%E7%BE%A4-455850365%20%7C%20301733278-orange.svg?style=flat-square" alt="QQ Group" />
  </a>
</p>

<h2 align="center">一个整合了大量主流开源项目高度可配置化的 Android MVP 快速集成框架</h2>

## Usage
> New Project (**以下步骤太麻烦? 现在可直接在新建的新工程中使用新功能 [一键生成 app Module](https://github.com/JessYanCoding/MVPArms-Module-Template), 让您免于项目繁琐的配置，快速开启 MVPArms 的世界**)
>> 如果您想构建一个全新的项目，直接将整个项目 **clone** (或者下载) 下来，再将 **demo** 作为主 **Module** (建议将 **arms Module** 删除，并使用 **Gradle** [远程依赖](https://github.com/JessYanCoding/MVPArms/wiki#1.1) 本框架，便于更新，删除前请务必先查看 [这里](https://github.com/JessYanCoding/MVPArms/wiki/Issues#2))，最后将项目包名改成自己的包名，**demo Module** 包含可以直接使用的包结构，一个主流的 `MVP`+`Dagger2`+`Retrofit`+`RxJava` 框架就这样轻松的构建成功了，现在您再参考 **Mvp** 包下的 **UserActivity** 的格式，[使用 Template 一键生成 MVP、Dagger2 相关的所有类](https://github.com/JessYanCoding/MVPArmsTemplate)，配合查阅 [Wiki 文档](https://github.com/JessYanCoding/MVPArms/wiki) 慢慢掌握本框架，看再多文章不如早点在项目中使用它，在实践中学习总是最快的
 
> Old Project
>> [老项目想引入此框架，可以参考 Wiki 文档，写的非常详细](https://github.com/JessYanCoding/MVPArms/wiki)

## Wiki
[详细使用方法及扩展功能，请参照 Wiki (**开发前必看!!!**)](https://github.com/JessYanCoding/MVPArms/wiki)

## Notice

* [**MVPArms 官方组件化方案 ArmsComponent**](https://github.com/JessYanCoding/ArmsComponent/wiki)

* [MVPArms 学习项目](https://github.com/JessYanCoding/MVPArms/blob/master/CONTRIBUTING_APP.md)

* [意见收集](https://github.com/JessYanCoding/MVPArms/issues/40)

* [更新日志](https://github.com/JessYanCoding/MVPArms/wiki/UpdateLog)

* [常见 Issues](https://github.com/JessYanCoding/MVPArms/wiki/Issues)

* [我们为什么要把 Dagger2，MVP 以及 RxJava 引入项目中?](http://www.jianshu.com/p/91c2bb8e6369)

* 看了上面的文章，对为什么使用这些技术应该比较了解了，使用这些技术对项目后期的维护和迭代特别是大型项目非常有帮助，但是在开发前期每写一个页面要多写很多  `MVP`、`Dagger2` 的类和接口，这对于开发前期确实比较头疼，现在本框架已经可以通过 [Template](https://github.com/JessYanCoding/MVPArmsTemplate) 自动生成一些 `MVP`，`Dagger2` 的模版代码，现在大家可以非常轻松的使用本框架.

* 使用此框架自带自动屏幕适配功能 (可不使用)，请参考 [AndroidAutoSize 使用方法](https://github.com/JessYanCoding/AndroidAutoSize).

* 作为通用框架，本框架不提供与 **UI** 有关的任何第三方库.

## Functionality & Libraries
1. [`Mvp` 是 Google 官方出品的 `Mvp` 架构项目，含有多个不同的架构分支(此为 Dagger 分支).](https://github.com/googlesamples/android-architecture/tree/todo-mvp-dagger/)
2. [`Dagger2` 是 Google 根据 Square 的 Dagger1 出品的依赖注入框架，通过 Apt 编译时生成代码，性能优于使用运行时反射技术的依赖注入框架.](https://github.com/google/dagger)
3. [`RxJava` 提供优雅的响应式 API 解决异步请求以及事件处理.](https://github.com/ReactiveX/RxJava)
4. [`RxAndroid` 为 Android 提供响应式 API.](https://github.com/ReactiveX/RxAndroid)
5. [`Rxlifecycle`，在 Android 上使用 `RxJava` 都知道的一个坑，就是生命周期的解除订阅，这个框架通过绑定 Activity 和 Fragment 的生命周期完美解决该问题.](https://github.com/trello/RxLifecycle)
6. [`RxCache` 是使用注解，为 `Retrofit` 加入二级缓存 (内存，磁盘) 的缓存库.](https://github.com/VictorAlbertos/RxCache)
7. [`RxErroHandler` 是 `RxJava` 的错误处理库，可在出现错误后重试.](https://github.com/JessYanCoding/RxErrorHandler)
8. [`RxPermissions` 用于处理 Android 运行时权限的响应式库.](https://github.com/tbruyelle/RxPermissions)
9. [`Retrofit` 是 Square 出品的网络请求库，极大的减少了 Http 请求的代码和步骤.](https://github.com/square/retrofit)
10. [`Okhttp` 同样 Square 出品，不多介绍，做 Android 的都应该知道.](https://github.com/square/okhttp)
11. [`AndroidAutoSize` 是今日头条屏幕适配方案终极版，一个极低成本的 Android 屏幕适配方案，该库没有引入到 `Arms`，所以框架使用者可自由选择屏幕适配方案.](https://github.com/JessYanCoding/AndroidAutoSize)
12. [`Gson` 是 Google 官方的 Json Convert 框架.](https://github.com/google/gson)
13. [`Butterknife` 是 JakeWharton 大神出品的 View 注入框架.](https://github.com/JakeWharton/butterknife)
14. [`AndroidEventBus` 是一个轻量级的 EventBus，该库没有引入到 `Arms`，所以框架使用者可自由选择 EventBus.](https://github.com/hehonghui/AndroidEventBus)
15. [`Timber` 是 JakeWharton 大神出品的 Log 框架容器，内部代码极少，但是思想非常不错.](https://github.com/JakeWharton/timber)
16. [`Glide` 是本框架默认封装到扩展库 `arms-imageloader-glide` 中的图片加载库，可参照着 Wiki 更改为其他的图片加载库，`Glide` 的 API 和 `Picasso` 差不多，缓存机制比 `Picasso` 复杂，速度快，适合处理大型图片流，支持 gif 图片，`Fresco` 太大了！在 5.0 以下优势很大，5.0 以上系统默认使用的内存管理和 `Fresco` 类似.](https://github.com/bumptech/glide)
17. [`LeakCanary` 是 Square 出品的专门用来检测 `Android` 和 `Java` 的内存泄漏，并通过通知栏提示内存泄漏信息.](https://github.com/square/leakcanary)

## Who is using MVPArms?

**MVPArms** 从诞生之初, 一直真诚的为开发者做着力所能及的事, 从详细的 [**Wiki**文档](https://github.com/JessYanCoding/MVPArms/wiki) 到高效的 [代码生成器](https://github.com/JessYanCoding/MVPArmsTemplate), 无一不透露着 **MVPArms** 对开发者诚挚的付出和关怀

**MVPArms** 经过近两年时间殷勤的耕耘, 逐渐变得成熟, 稳定, 这不得不归功于 **MVPArms** 大家庭中每一位成员一直以来真诚的反馈和建议, 在此由衷的感谢他们为 **MVPArms** 做出的不可磨灭的贡献  

但是 **MVPArms** 远不止于此, 还有更多的路要走, 还会继续成长, 变得更加强大, 现在我们诚挚的邀请您也成为咱们 **MVPArms** 大家庭中的一员  

**天府通** | **小顶家装 工长端** | **小顶家装 工人端** | **小顶家装 材料端** | **小顶网** |
:-------------------------------------------------------------------:|:----------:|:---------------:|:--------:|:--------------:|
[<img src="image/tianfutong_logo.png" width="80" height="80">](https://android.myapp.com/myapp/detail.htm?apkName=com.chinarainbow.tft) | [<img src="image/xiaoding_foreman_logo.png" width="80" height="80">](http://www.dggxdjz.com) | [<img src="image/xiaoding_worker_logo.png" width="80" height="80">](http://www.dggxdjz.com) | [<img src="image/xiaoding_material_logo.png" width="80" height="80">](http://www.dggxdjz.com) | [<img src="image/top_net_work_logo.png" width="80" height="80">](http://www.dgg.net/appload.htm) |
**天天视频** | **天天直播** | **中斗通航** | **中斗祥云** | **麋鹿旅行** |
[<img src="image/tiantian_video_logo.png" width="80" height="80">](http://sj.qq.com/myapp/detail.htm?apkName=com.dzwh.ttys) | [<img src="image/tiantian_live_logo.png" width="80" height="80">](http://www.25pp.com/android/detail_7611392/) | [<img src="image/tong_hang_logo.png" width="80" height="80">](https://fir.im/3176) | <img src="image/xiang_yun_logo.png" width="80" height="80">  | [<img src="image/mi_lu_logo.png" width="80" height="80">](http://android.myapp.com/myapp/detail.htm?apkName=com.elk.tourist) |
**汇财富** | **觅窝** | **晒墨宝** | **智播**  | **(Your App ...)** |
[<img src="image/hui_cai_fu_logo.png" width="80" height="80">](http://android.myapp.com/myapp/detail.htm?apkName=com.tahone.client) | [<img src="image/mi_wo_logo.png" width="80" height="80">](http://miwo.ai/) | [<img src="image/shaimobao_logo.png" width="80" height="80">](http://sj.qq.com/myapp/search.htm?kw=%E6%99%92%E5%A2%A8%E5%AE%9D)  | [<img src="image/zhibo_logo.png" width="80" height="80">](http://www.zhibocloud.cn/) | <img src="image/android_logo.png" width="80" height="80"> |  
 

## Acknowledgements 
感谢本框架所使用到的所有三方库的 **Author** ,以及所有为 **Open Source** 做无私贡献的 **Developer** 和 **Organizations** ,使我们能更好的工作和学习,本人也会将业余时间回报给开源社区

## Donate
如果您认可 **MVPArms** 的代码质量,并使用 **MVPArms** 在实际开发中切实的提升了您的工作效率和开发能力,请您点击右上角 **Star** 支持一下谢谢!

## About Me
* **Email**: <jess.yan.effort@gmail.com>  
* **Home**: <http://jessyan.me>
* **掘金**: <https://juejin.im/user/57a9dbd9165abd0061714613>
* **简书**: <https://www.jianshu.com/u/1d0c0bc634db>

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
