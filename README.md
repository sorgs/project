# 引言
自己动手搭建一个属于自己的远程基础仓库

不管是开发新项目亦或者是自己写demo练练手之类的。都需要建立工程，然后开始拷贝工具类，然后在啪啦啪啦引用必须的三方库，建立Base基类等等。

搞了一段时间之后，觉得实在是太麻烦了，为什么我不建立一个基础工程。然后做成一个远程库，每次建立新的工程之后，直接就引用这个库。一下子自己熟手的工具类，Base基类，甚至常用一些第三方库都OK了。

在平时写代码的时候，也注意收集，比较顺手的东西，直接放到基础库当中去，以后对新工程简直太方便了。

这里只说简单说下流程遇到坑，

# 建立基础库
1.  建立工程，再new一个module，选择android Library，然后开始编写和搭建自己想放到基础工程的东西。
2.  发布到github上面
3.  在github上面首页点击release-create a new releases(后续发布新版本点击draft a new release),相当于是打一个tag。在Tag version写上版本号，比如 V1.0之类的。下面可以自己随便写点描述。
4.  上[https://jitpack.io/](https://jitpack.io/ "https://jitpack.io/") ,输入自己的git仓库，点击look up即可。选择自己发布的版本，点击get it即可。
5.  然后在自己新建工程引用就可以开心使用了。
6.  鉴于网上很多教程，这里不再细说过程，可以直接到[https://www.jianshu.com/p/49ea4fa47037](https://www.jianshu.com/p/49ea4fa47037 "如何发布你的GitHub开源库")

# 问题
> 这里想简单说下自己遇到的一些问题


1.  既然是自己建立的基础工程，就不需要app目录等其他module之类的
2.  一定不要在build.gradle里面配置优化压缩等
```
buildTypes {
        release {
            //Zipalign优化
            zipAlignEnabled true
            //去除无用资源
            shrinkResources true
            //签名
            signingConfig signingConfigs.release
            //混淆
            minifyEnabled true
            proguardFiles getDefaultProguardFile('proguard-android.txt'), 'proguard-rules.pro'
        }
        debug {
            //签名
            signingConfig signingConfigs.release
        }
    }
```
这些是不需要的。尤其是最优化之类的，很容易造成的问题就是，打aar包没有部分代码打进去，因为在优化之后，没有被调用的函数和类是会被忽略的，而工具类就很容易被优化处理了。混淆的也是不太需要配置，如果一定要配置混淆，则需要注意，把混淆中压缩优化等去除掉。

# 结语
- 自己简单的搭建了一个基础工程，目前里面包含了一些常用的第三方库，BaseActivity，BaseFragment等。还有一些常用工具类等，比如Log，Toast等
- 欢迎大家一起维护和优化，[https://github.com/sorgs/project](https://github.com/sorgs/project "https://github.com/sorgs/project") 。如果能够任何地方能够帮助到您，希望可以给个start鼓励鼓励。感谢！