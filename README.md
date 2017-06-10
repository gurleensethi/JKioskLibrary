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
compile 'com.github.gurleensethi:JKioskLibrary:v1.0.0-alpha4'
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
* [Semesters](#semesters)
* [Subjects](#subjects)
* [Subject Faculty](#subject-faculty)
* [Attendance](#attendance)

### WebkioskCredentials
`WebkioskCredentials` is a java object that is packaged with the library and is required by all the API's for proper functioning. Its contructor takes 3 parameters: `new WebkioskCredentials(enrollmentNumber, dateOfBirth, password)`. All three parameters are of type `String`. The dateOfBirth has to be passed in the format: `dd-mm-yyyy`.

### Login
Obtain the `KioskLogin` object from `JKiosk` by calling the `getLoginApi()` method.
`KioskLogin` contains a function named `login(WebkioskCredentials)` that takes WebkioskCredentials as a parameter. Add a callback to get the response from login API.
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

### Semesters
Obtain the `KioskSemesters` object from `JKiosk` by calling the `getSemestersApi()` method.
`KioskSemesters` contains a function named `getSemesters(WebkioskCredentials)` that takes WebkioskCredentials as a parameter. Add a callback to get the response from semesters API.

The semesters are in format like `2016EVESEM` `2016ODDSEM` `2017ODDSEM`. These semester codes will be required in further API's to get semester specific data.
```java
JKiosk.getSemestersApi()
       .getSemesters(new WebkioskCredentials("username", "dd-mm-yyyy", "password"))
       .addResultCallback(new ResultCallbackContract<SemestersResult>() {
            @Override
            public void onResult(SemestersResult result) {
                for (String semester : result.getSemesters()) {
                    Log.d("Semester", semester);
                }
            }

            @Override
            public void onError(Exception e) {
                //Handle any error here
            }
       });
```
The `SemestersResult` object contains a list of semesters which can be accessed by calling `getSemesters()`.
###### Go to the [Best Practices](#best-practices) section to learn and leverage the API in a better way.

### Subjects
Obtain the `KioskSubjects` object from `JKiosk` by calling the `getSubjectsApi()` method.
`KioskSubjects` contains two functions named `getSubjects(WebkioskCredentials)` and `getSubjects(WebkioskCredentials, Semester)`. Add a callback to get the response from subjects API.

`getSubjects(WebkioskCredentials)` takes a WebkioskCredentials object and returns the default data of the current semester.

`getSubjects(WebkioskCredentials, Semester)` takes and extra parameter `semester` which is the code for the semester you want the details for. Semesters codes can be obtained from the [Semesters](#semesters) API. So to fetch details for the semester with code `2015EVESEM` the function will be:

```java
getSubjects(WebkioskCredentials, "2015EVESEM");
```


```java
JKiosk.getSubjectsApi()
       .getSubjects(new WebkioskCredentials("username", "dd-mm-yyyy", "password"))
       .addResultCallback(new ResultCallbackContract<SubjectResult>() {
            @Override
            public void onResult(SubjectResult result) {
                for (SubjectFaculty subject : result.getSubjects()) {
                    subject.getSubjectName();
                    subject.getSubjectCredits();
                    subject.getSubjectType();
                    subject.getSubjectCode();
                }
            }

            @Override
            public void onError(Exception e) {
                //Handle any error here
            }
       });
```
The `SubjectResult` object contains a list of `SubjectFaculty` which can be accessed by calling `getSubjects()`.
###### Go to the [Best Practices](#best-practices) section to learn and leverage the API in a better way.


### Subject Faculty
Obtain the `KioskSubjectFaculty` object from `JKiosk` by calling the `getSubjectFacultyApi()` method.
`KioskSubjectFaculty` contains two functions named `getSubjectFaculty(WebkioskCredentials)` and `getSubjectFaculty(WebkioskCredentials, Semester)`. Add a callback to get the response from subject faculty API.

`getSubjectFaculty(WebkioskCredentials)` takes a WebkioskCredentials object and returns the default data of the current semester.

`getSubjectFaculty(WebkioskCredentials, Semester)` takes and extra parameter `semester` which is the code for the semester you want the details for. Semesters codes can be obtained from the [Semesters](#semesters) API. So to fetch details for the semester with code `2015EVESEM` the function will be:

```java
getSubjectFaculty(WebkioskCredentials, "2015EVESEM");
```


```java
JKiosk.getSubjectFacultyApi()
       .getSubjectFaculty(new WebkioskCredentials("username", "dd-mm-yyyy", "password"))
       .addResultCallback(new ResultCallbackContract<SubjectFacultyResult>() {
            @Override
            public void onResult(SubjectFacultyResult result) {
                for (SubjectFaculty subjectFaculty : result.getSubjectFaculties()) {
                    subjectFaculty.getSubjectName();
                    subjectFaculty.getLectureFaculty();
                    subjectFaculty.getTutorialFaculty();
                    subjectFaculty.getPracticalFaculty();
                    subjectFaculty.getSubjectCode();
                }
            }

            @Override
            public void onError(Exception e) {
                //Handle any error here
            }
       });
```
The `SubjectFacultyResult` object contains a list of `SubjectFaculty` which can be accessed by calling `getSubjectFaculties()`.
###### Go to the [Best Practices](#best-practices) section to learn and leverage the API in a better way.

### Attendance
Obtain the `KioskAttendance` object from `JKiosk` by calling the `getAttendanceApi()` method.
`KioskAttendance` contains two functions named `getAttendance(WebkioskCredentials)` and `getAttendance(WebkioskCredentials, Semester)`. Add a callback to get the response from attendance API.

`getAttendance(WebkioskCredentials)` takes a WebkioskCredentials object and returns the default data of the current semester.

`getAttendance(WebkioskCredentials, Semester)` takes and extra parameter `semester` which is the code for the semester you want the details for. Semesters codes can be obtained from the [Semesters](#semesters) API. So to fetch details for the semester with code `2015EVESEM` the function will be:

```java
getAttendance(WebkioskCredentials, "2015EVESEM");
```


```java
JKiosk.getAttendanceApi()
       .getAttendance(new WebkioskCredentials("username", "dd-mm-yyyy", "password"))
       .addResultCallback(new ResultCallbackContract<AttendanceResult>() {
            @Override
            public void onResult(AttendanceResult result) {
                for (AttendanceResult attendance : result.getAttendances()) {
                    attendance.getSubjectName();
                    attendance.getSubjectCode();
                    attendance.getOverallAttendance();
                    attendance.getLectureAttendance();
                    attendance.getTutorialAttendance();
                    attendance.getPracticalAttendance();
                    attendance.getDetailAttendanceUrl();
                }
            }

            @Override
            public void onError(Exception e) {
                //Handle any error here
            }
       });
```
The `AttendanceResult` object contains a list of `Attendance` which can be accessed by calling `getAttendances()`.

##### Note
To get the detailed attendance of a subject, grab the *detail attendance url* of that subject by calling the `getDetailAttendanceUrl()` and use it in the [Detail Attendance](#detail-attendance) Api.

###### Go to the [Best Practices](#best-practices) section to learn and leverage the API in a better way.

### Detail Attendance

## Best Practices
Coming Soon!
