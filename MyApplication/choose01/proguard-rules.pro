## Add project specific ProGuard rules here.
## By default, the flags in this file are appended to flags specified
## in D:\AndroidStudio\SDK/tools/proguard/proguard-android.txt
## You can edit the include path and order by changing the proguardFiles
## directive in build.gradle.
##
## For more details, see
##   http://developer.android.com/guide/developing/tools/proguard.html
#
## Add any project specific keep options here:
#
## If your project uses WebView with JS, uncomment the following
## and specify the fully qualified class name to the JavaScript interface
## class:
##-keepclassmembers class fqcn.of.javascript.interface.for.webview {
##   public *;
##}
-dontoptimize
-dontusemixedcaseclassnames
-dontskipnonpubliclibraryclasses
-dontpreverify
-verbose
-optimizations !code/simplification/arithmetic,!field/*,!class/merging/*
-allowaccessmodification
-printmapping map.txt
-optimizationpasses 7
-dontskipnonpubliclibraryclassmembers
-dontwarn com.alibaba.wireless.**
-dontwarn com.ut.mini.**
-dontwarn com.taobao.**
-dontwarn com.alibaba.sdk.android.**
-dontwarn com.alibaba.fastjson.**
-dontwarn org.apache.http.**
-dontwarn android.webkit.**
-dontwarn com.alibaba.mobileim.kit.chat.ChattingFragment
-dontwarn com.alibaba.mobileim.lib.model.provider.IMCursor
-dontwarn com.alibaba.openim.kit.**
-dontwarn com.google.gson.**
-dontwarn com.alibaba.wxlib.store.PersistManager
#-keep public class com.example.asus.myapplication.ui.dialog.ChooseContentDialog extends android.app.DialogFragment