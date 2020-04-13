# QyUi

#### 介绍
这是一个创建安卓基础项目的框架

#### 软件架构
当前最新版本是1.0.2


#### 安装教程

1.   
   repositories {
        google()
        jcenter()
        maven { url 'https://jitpack.io' }
    }
2. implementation 'com.github.qinyangQY:QyUi:1.0.2'
3. 需要统一所有的依赖库的版本号,具体的build.gradle的配置如下可直接复制：
    apply plugin: 'com.android.application'

android {
    compileSdkVersion 29
    buildToolsVersion "29.0.3"

    defaultConfig {
        applicationId "xxxxx"
        minSdkVersion 21
        targetSdkVersion 29
        versionCode 1
        versionName "1.0"
        consumerProguardFiles 'consumer-rules.pro'
        javaCompileOptions { annotationProcessorOptions { includeCompileClasspath = true } }
    }

    buildTypes {
        release {
            minifyEnabled false
            proguardFiles getDefaultProguardFile('proguard-android-optimize.txt'), 'proguard-rules.pro'
        }
    }
    //强制让所有依赖保持一致
    configurations.all {
        resolutionStrategy.eachDependency { DependencyResolveDetails details ->
            def requested = details.requested
            if (requested.group == 'com.android.support') {
                if (!requested.name.startsWith("multidex")) {
                    details.useVersion "${qyconfig.androidSupportVersion}"
                }
            }
        }
    }

    compileOptions {
        sourceCompatibility JavaVersion.VERSION_1_8
        targetCompatibility JavaVersion.VERSION_1_8
    }
}

dependencies {
    implementation fileTree(dir: 'libs', include: ['*.jar'])
    implementation 'com.github.qinyangQY:QyUi:1.0.2'
}

#### 使用说明

1. xxxx
2. xxxx
3. xxxx

#### 参与贡献

1. Fork 本仓库
2. 新建 Feat_xxx 分支
3. 提交代码
4. 新建 Pull Request


#### 码云特技

1. 使用 Readme\_XXX.md 来支持不同的语言，例如 Readme\_en.md, Readme\_zh.md
2. 码云官方博客 [blog.gitee.com](https://blog.gitee.com)
3. 你可以 [https://gitee.com/explore](https://gitee.com/explore) 这个地址来了解码云上的优秀开源项目
4. [GVP](https://gitee.com/gvp) 全称是码云最有价值开源项目，是码云综合评定出的优秀开源项目
5. 码云官方提供的使用手册 [https://gitee.com/help](https://gitee.com/help)
6. 码云封面人物是一档用来展示码云会员风采的栏目 [https://gitee.com/gitee-stars/](https://gitee.com/gitee-stars/)
