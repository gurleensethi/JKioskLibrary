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
compile 'com.github.gurleensethi:JKioskLibrary:v1.0.0-alpha3'
```
## Usage and API
All the apis can be accessed by using the `JKiosk` object, so suppose to access all semesters of a user, write `JKiosk.getSemestersApi()`. It will return the `KioskSemester` object which can be used to reterive the list of all semesters.

All the Api's will return a `Kiosk` object, this object contains the function to get the required data. To get the data a callback has to be registered, all the `Kiosk` objects takes an object of `ResultCallbackContract` that can be registered using `addResultCallback(ResultCallbackContract<T> callback)`. The callback has has two methods, `onResult(T result)` and `onError(Exception e)`. `onResult(T result)` will provide a `Result` object containing the required data.

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

#### Index
* [Best Practices](#best-practices)
* [WebkioskCredentials](#webkioskcredentials)
* [Login](#login)

### WebkioskCredentials
`WebkioskCredentials` is a java object that is packaged with the library and is required by all the API's for proper functioning. Its contructor takes 3 parameters: `new WebkioskCredentials(enrollmentNumber, dateOfBirth, password)`. All three parameters are of type `String`. The dateOfBirth has to be passed in the format: `dd-mm-yyyy`.

### Login
Obtain the `KioskLogin` object from `JKiosk` by calling the `getLoginApi()` method.
`KioskLogin` contains a function named `login(WebkioskCredentials)` that takes WebkioskCredentials as a parameter. Add a callback to get the returned data from login API.
```java
JKiosk.getLoginApi()
       .login(new WebkioskCredentials("username", "dd-mm-yyyy", "password"))
       .addResultCallback(new ResultCallbackContract<LoginResult>() {
            @Override
            public void onResult(LoginResult result) {
                if (result.isValidCredentials()) {
                    //Login successful
                } else {
                    //Wrong credentials
                }
             }

             @Override
             public void onError(Exception e) {
                //Handle any error here
             }
       });
```
The `LoginResult` object contains the result of login as a boolean value returned by the function `isValidCredentials()`.
###### Go to the [Best Practices](#best-practices) section to learn and leverage the API in a better way.

## Best Practices
Coming Soon!
