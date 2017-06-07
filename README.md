# JKioskLibrary
All the JIIT Webkiosk data in a nice lib for Android Developers

#### To use this library in your project, do as follows:

1. In your top level `build.gradle` file, in the `repository` section add the `maven { url 'https://jitpack.io' }` as shown below
```gradle
allprojects {
  repositories {
    ...
    maven { url 'https://jitpack.io' }
  }
}
```
2. Add the `JKioskLibrary` dependency in your app level `build.gradle` file
```gradle
compile 'com.github.gurleensethi:JKioskLibrary:v1.0.0-alpha2'
```
## Usage and API
All the apis can be accessed by using the `JKiosk` object, so suppose to access all semesters of a user, write `JKiosk.getSemestersApi()`. It will return the `KioskSemester` object which can be used to reterive the list of all semesters.

All the Api's will return a `Kiosk` object, this object contains the function to get the required data. To get the data a callback has to be registered, all the `Kiosk` objects takes an object of `ResultCallbackContract` that can be registered using `addResultCallback(ResultCallbackContract<T> callback)`. The callback has has two methods that, `onResult(T result)` and `onError(Exception e)`. `onResult(T result)` will provide a `Result` object containing the required data.

```java
JKiosk.getSomeApi()
      .getData(WebkioskCredentails)
      .addResultCallback(new ResultCallbackContract<ResultObject>() {
          @Override
          public void onResult(ResultObject) {
              //Retrieve data take action
          }
          
          @Override
          public void onError(Exception e) {
              //Notify user about error
          }
      });
```

To remove a callback just call the `removeCallback()` function on the Kiosk object.
#### Note
Since it takes a couple of seconds to get data from Webkiosk, JKiosk does all the processing in a background Thread and provides the result on Android's `MainThread` so you don't need to handle any kind of threading.
