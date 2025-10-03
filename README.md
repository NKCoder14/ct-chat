CTChat - Internal Team Communication App
CTChat is a feature-rich, real-time messaging application built for Android. This app was developed as an internal communication tool for the team at Career Tribe to foster seamless and efficient collaboration. It provides a simple and intuitive interface for one-on-one conversations and group chats, all powered by a robust Firebase backend.

✨ Features
User Authentication: Secure and separate flows for user registration and login using email and password.

Real-time Messaging: Instantaneous sending and receiving of messages in one-on-one chat rooms.

User & Group Lists: A tabbed interface that displays a list of all team members.

Live User Search: A search bar in the toolbar to quickly find and filter colleagues by username or email.

Group Chat Creation: A dedicated screen and a FloatingActionButton to create new group chats for specific projects or topics.

Message Timestamps: Every message is timestamped, providing clear context for conversations.

Custom UI: A polished user interface with custom theme colors, message bubble designs, and intuitive controls.

🛠️ Technology Stack
This project is built using modern Android development tools and practices.

Language: Kotlin

Architecture: Data Binding for connecting UI components to data sources.

Backend: Firebase

Authentication: For managing user accounts.

Cloud Firestore: As the real-time NoSQL database for users, messages, and groups.

UI Components:

AndroidX Libraries

Material Components for Android (Toolbar, TabLayout, FloatingActionButton)

RecyclerView for efficient lists.

ViewPager2 for the tabbed layout.

Build Tool: Gradle

🚀 Getting Started
To get this project up and running on your own machine, follow these steps.

Prerequisites
Android Studio (latest version recommended)

An Android device or emulator running API level 26 or higher.

1. Set Up Firebase
This app requires a Firebase project to handle its backend services.

Create a Firebase Project: Go to the Firebase Console and create a new project.

Register Your App:

In your project's dashboard, click the Android icon to add a new Android app.

Use com.example.ctchat as the Package Name.

Add your SHA-1 certificate key (you can generate this in Android Studio by opening the Gradle tab, going to YourProjectName > app > Tasks > android, and running signingReport).

Download google-services.json:

Download the google-services.json file provided by Firebase.

Place this file directly inside the app/ directory of your project in Android Studio.

Enable Firebase Services:

In the Firebase Console, go to Authentication -> Sign-in method and enable the Email/Password provider.

Go to Firestore Database -> Create database. Start it in test mode for now. The security rules should be set to allow reads and writes:

rules_version = '2';
service cloud.firestore {
  match /databases/{database}/documents {
    match /{document=**} {
      allow read, write: if true;
    }
  }
}

2. Build and Run the App
Clone the Repository:

git clone https://github.com/NKCoder14/ct-chat.git