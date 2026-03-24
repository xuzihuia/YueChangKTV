# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in /sdk/tools/proguard/proguard-android.txt

# Keep data classes
-keep class com.yuechang.ktv.data.** { *; }

# Keep domain models
-keep class com.yuechang.ktv.domain.** { *; }

# Gson
-keepattributes Signature
-keepattributes *Annotation*
-keep class com.google.gson.** { *; }

# Retrofit
-keep class retrofit2.** { *; }
-keepclasseswithmembers class * {
    @retrofit2.http.* <methods>;
}

# OkHttp
-dontwarn okhttp3.**
-keep class okhttp3.** { *; }