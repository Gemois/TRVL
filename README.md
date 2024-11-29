# TRVL

This is a simple **Travel Agency** app developed as part of an **Android Development Course** at **International Hellenic University**.

**TRVL** offers a range of features to manage and plan your travels, including:

- Add, update, or delete **Travel Agency offices**.
- Add, update, or delete **tour destinations**.
- Add, update, or delete **hotels** tied to specific tour destinations.
- Add, update, or delete **vacation packages**.
- Add, update, or delete **vacation reservations**.
- **Locate your destination** using **Google Maps**.
- Notifications for every action.
- Intuitive gesture-based interaction for all actions.

---

## Screenshots

[<img src="/demo_images/menu.png" align="left"
width="200"
    hspace="10" vspace="10">](/demo_images/menu.png)

[<img src="/demo_images/agencies.png" align="left"
width="200"
    hspace="10" vspace="10">](/demo_images/agencies.png)

[<img src="/demo_images/packages.png" align="left"
width="200"
    hspace="10" vspace="10">](/demo_images/packages.png)

[<img src="/demo_images/add_package.png"  align="left"
width="200"
    hspace="10" vspace="10">](/demo_images/add_package.png)

[<img src="/demo_images/add_package2.png" align="left"
width="200"
    hspace="10" vspace="10">](/demo_images/add_package2.png)

[<img src="/demo_images/help.png"
width="200"
    hspace="10" vspace="10">](/demo_images/help.png)

## Features

- **Google Maps Integration**: Locate and explore travel destinations directly within the app using Google Maps.
- **Notifications**: Receive notifications for all critical actions (Add, Update, Delete).
- **Gestures**: Every action is triggered by intuitive gestures, enhancing the user experience.

---

## Architecture

The app follows the **Clean MVVM architectural pattern** to ensure maintainability and scalability, using the following components:

- **Room**: For local data storage. [Room Documentation](https://developer.android.com/training/data-storage/room)
- **Firebase Firestore**: For remote database storage. [Firebase Firestore Documentation](https://firebase.google.com/docs/firestore)
- **Navigation**: For seamless screen navigation. [Navigation Documentation](https://developer.android.com/guide/navigation)
- **LiveData**: For observing and updating UI data in a lifecycle-conscious way. [LiveData Documentation](https://developer.android.com/topic/libraries/architecture/livedata)
- **ViewModel**: To manage UI-related data in a lifecycle-conscious way. [ViewModel Documentation](https://developer.android.com/topic/libraries/architecture/viewmodel)
- **ViewBinding**: To handle UI element binding more safely and efficiently. [ViewBinding Documentation](https://developer.android.com/topic/libraries/view-binding)

---

### Prerequisites

1. **Google Maps API Key**:  
   To use Google Maps functionality, you'll need a valid **Google Maps API Key**. Follow these steps to obtain it:
   - Visit the [Google Cloud Console](https://console.cloud.google.com/).
   - Create a new project.
   - Navigate to **APIs & Services > Credentials**.
   - Create an **API Key** and enable the **Google Maps SDK for Android**.
   
   Once you have your key, add it to your project by creating a `local.properties` file in the root directory of your project (if it doesn’t already exist) and adding the following line:
   
   ```properties
   MAPS_API_KEY=your_api_key_here
    ```

2. **Firebase Firestore Setup**:  
    - Go to the [Firebase Console](https://console.firebase.google.com/).
    - Create a new project.
    - Add an Android app to the Firebase project.
    - Download the `google-services.json` file from Firebase Console.
    - Place the `google-services.json` file inside the `app/` directory of your Android project.
    - Ensure you have enabled **Firestore** in the Firebase Console under the Firestore Database section.
    - If prompted, make sure to add the Firebase SDK dependencies to your `build.gradle` files.
The google-services.json file ensures the app can connect to Firebase services.


### Run the App

1. **Open the project in Android Studio**:
2. **Ensure that your SDK and Android Studio are up to date**:
3. **Sync Gradle**:
4. **Check for any missing dependencies**:
5. **Connect an Android device or launch an emulator**:
6. **Build and run the app**:



