
# 创建第一个Android Kotlin应用

### 创建第一个Kotlin应用程序
1. 创建一个新工程
![创建一个新工程](./pic/1_1.png)

### 遇到的问题及解决

```

Duplicate class androidx.lifecycle.ViewModelLazy found in modules jetified-lifecycle-viewmodel-ktx-2.3.1-runtime (androidx.lifecycle:lifecycle-viewmodel-ktx:2.3.1) and lifecycle-viewmodel-2.4.0-runtime (androidx.lifecycle:lifecycle-viewmodel:2.4.0)
Duplicate class androidx.lifecycle.ViewModelProviderKt found in modules jetified-lifecycle-viewmodel-ktx-2.3.1-runtime (androidx.lifecycle:lifecycle-viewmodel-ktx:2.3.1) and lifecycle-viewmodel-2.4.0-runtime (androidx.lifecycle:lifecycle-viewmodel:2.4.0)
Duplicate class androidx.lifecycle.ViewTreeViewModelKt found in modules jetified-lifecycle-viewmodel-ktx-2.3.1-runtime (androidx.lifecycle:lifecycle-viewmodel-ktx:2.3.1) and lifecycle-viewmodel-2.4.0-runtime (androidx.lifecycle:lifecycle-viewmodel:2.4.0)

```

解决：

1. 依赖项解析错误，提醒为重复类异常，实际上就是使用kotlin相关类库的版本问题
2. 若项目中未设置过kotlin相关依赖库版本，可在build.gradle文件中的dependencies 中添加如下代码

```
implementation 'androidx.lifecycle:lifecycle-viewmodel-ktx:2.4.0'
```

3. 若项目中已经设置过相关依赖库版本，在build.gradle文件中找到设置的依赖库位置，更改对应的版本号即可，异常中提示更改为“2.4.0”，若你的异常提醒为其他版本，道理是一样的，改为项目提示的异常更高级版本号即可