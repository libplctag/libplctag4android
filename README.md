# libplctag4android

- [libplctag4android](#libplctag4android)
  - [Libplctag for Android](#libplctag-for-android)
  - [Credits](#credits)
  - [Building the Project](#building-the-project)
  - [Contact and Support](#contact-and-support)
    - [libplctag Forum](#libplctag-forum)
    - [GitHub](#github)
  - [Known Problems](#known-problems)

## Libplctag for Android

This is an example project repository showing how to use the Java wrapper for the [libplctag](https://github.com/libplctag/libplctag) PLC communication library.

This is ongoing work, as we learn about Android and its build system, so expect frequent updates.  We welcome any patches or pull requests for this project!

## Credits

This is a direct copy of the great work done by @GitHubDragonFly with some minor tweaks to use the core C library and the build configuration.   Without @GitHubDragonFly, this example and libplctag on Android would not exist!

## Building the Project

You will need an Internet connection.

1. Install Android Studio
2. Install the Android SDK if it is not installed by Android Studio
3. Install the Android NDK.
4. Install git source control tools for your platform.
5. Check out this repository: `git clone https://github.com/libplctag/libplctag4android`
6. Start up Android Studio and point it at the location where you checked out this repository.
7. Wait until Android Studio has finished scanning the project.  There will be an error about the NDK.
8. Go to the File -> Project Structure menu entry.
9. In the pop-up window, select "SDK Location" in the left side of the window.
10. In the right side of the Window, modify the Android NDK Location to point to where you installed the NDK.

Once all this is done, you should be able to build the APK for the project.   **Note** that you will likely see a warning 
about not finding or being able to resolve JNA.   That's OK and it will resolve when you try to build the first time.

## Contact and Support

There are two ways to ask for help or contact us.

### libplctag Forum

If you have questions or comments about the this example project, the libplctag
library, its use, or about one of the wrapper libraries, please join the Google group
[libplctag](https://groups.google.com/forum/#!forum/libplctag)!

The forum is open to all, but is by request only to keep the spammers down.  The traffic is fairly
light with usually a small number of emails per month.  It is our primary means for users to
ask questions and for discussions to happen.   Announcements about releases happen on the forum.

### GitHub

If you find bugs or other problems, please file them on GitHub's issue tracker for
this project.

Especially with this project, we would love to get pull requests and patches!

## Known Problems

- An Internet connection is necessary for all builds, not just the first one.  We will be working on eliminating this requirement after all the dependencies are loaded as a future update to the build system.
- The org.libplctag.Tag Java file is a copy of the one in [libplctag4j](https://github.com/libplctag/libplctag4j).  One of the urgent TODO items is to find a way to either pull the source from the [libplctag4j](https://github.com/libplctag/libplctag4j) GitHub repository or use a minimal JAR from the releases of that project.
- The tag strings are hard coded in the Java code.   Ideally there would be a way within the app to edit them or at least to pull them from a properties file in the project.
- Unlike the core library and some of the wrappers, this project does not have GitHub Action CI integration.  That will be an ongoing task.
