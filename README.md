# QyUi

#### 介绍
这是一个创建安卓基础项目的框架

#### 软件架构
当前最新版本是v = 1.0.2


#### 安装教程

1.   
   添加仓库:
   
    repositories {
           google()
           jcenter()
           maven { url 'https://jitpack.io' }
           }
           
2. 
   在项目更目录下创建gradle配置文件 例如创建一个qyconfig.gradle文件将下面的内容复制即可
   
          project.ext {

                 javaVersion = 8
                 javaMaxHeapSize = '4G'

                 compileSdkVersion = 29
                 buildToolsVersion = "29.0.3"
                 minSdkVersion = 21
                 targetSdkVersion = 29
                 constraintlayout = "1.1.3"
                 androidx_appcompat = "1.0.2"

                 minifyEnable = true
                 shrinkResEnable = minifyEnable

                 sourceCompatibility = this.&getJavaVersion()
                 targetCompatibility = this.&getJavaVersion()

                 glide = "4.10.0"
                 recyclerview = "1.2.0-alpha01"
                 picture_library = "v2.4.6"
                 gson = "2.6.2"
                 avi = "2.1.3"

                 retrofit = "2.6.2"
                 convertergson = "2.4.0"
                 rxandroid = "2.0.1"
                 rxjava = "2.1.7"
                 adapterrxjava = "2.3.0"
                 logging_interceptor = "3.5.0"

                 eventbus = "3.1.1"
                }

        def getJavaVersion() {
                switch (project.ext.javaVersion) {
                case "6":
                return JavaVersion.VERSION_1_6
                case "7":
                return JavaVersion.VERSION_1_7
                case "8":
                return JavaVersion.VERSION_1_8
                default:
                return JavaVersion.VERSION_1_8
              }
       }

3.
   在项目的build.gradle配置文件的顶部加入：
   
    subprojects {
    
           apply from: "${project.rootDir}/你创建的gradle配置文件的名称"
           
              }
                           
4.
   添加依赖implementation 'com.github.qinyangQY:QyUi:v'
   v是最新版本号，最新版本号在上面
   
5. 需要统一所有的依赖库的版本号,具体的build.gradle的配置如下可直接复制：


       apply plugin: 'com.android.application'

       android {
                compileSdkVersion project.ext.compileSdkVersion
                buildToolsVersion project.ext.buildToolsVersion

                defaultConfig {
                applicationId "xxxx"
                minSdkVersion project.ext.minSdkVersion
                targetSdkVersion project.ext.targetSdkVersion
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
