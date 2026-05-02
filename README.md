# CTChat - Android Chat Application
# 💬 Real-time Communication Platform for Career Tribe
A modern, native Android application built using Kotlin that allows internal team members to communicate through one-on-one and group chats. Developed to enhance and streamline team collaboration at Career Tribe.

This project was developed for the internal team at Career Tribe to facilitate better communication.

## 🚀 Features
- 🔐 Secure Authentication: Separate, dedicated screens for user registration and login.
- 💬 Real-time Messaging: Instant one-on-one chat with message history loaded on open.
- 👥 Group Chat Creation: A dedicated screen with a user-selection list to create new group chats.
- 🔍 Live User Search: An integrated toolbar search to filter the user list in real-time.
- 🕓 Message Timestamps: Every message includes a formatted timestamp for clear context.
- 🎨 Custom UI: A polished interface with a custom color theme, distinct message bubbles, and a Material Design FloatingActionButton.
- 📱 Fully Native & Responsive: Built with Kotlin and XML for the highest performance and a responsive layout that adapts to different screen sizes.

## 🛠️ Tech Stack
- Kotlin
- Android SDK
- XML for Layouts
- Firebase
- Firebase Authentication
- Cloud Firestore
- AndroidX Libraries
- Material Components for Android
- Data Binding Library
- Gradle

## 📁 Folder Structure

```bash

.
├── app/
│   ├── src/main/
│   │   ├── java/com/example/ctchat/
│   │   │   ├── activities/    # UI controllers for screens
│   │   │   ├── adapters/      # RecyclerView adapters for lists
│   │   │   ├── fragments/     # UI for tabs (Chats, Status, Calls)
│   │   │   └── models/        # Kotlin data classes (User, ChatMessage)
│   │   ├── res/
│   │   │   ├── layout/        # XML layout files
│   │   │   ├── drawable/      # Icons and custom backgrounds
│   │   │   └── menu/          # Toolbar menu definitions
│   │   └── AndroidManifest.xml # App manifest
│   ├── build.gradle           # App-level Gradle script
├── build.gradle               # Project-level Gradle script
└── README.md                  # This file

CTChat/app/src/main/
├── AndroidManifest.xml
│
├── java/com/example/ctchat/          ← 🧠 LOGIC (Kotlin)
│   ├── activities/                    ← Screens (Frontend + Firebase calls)
│   │   ├── SplashActivity.kt            Splash screen
│   │   ├── LoginActivity.kt             Login (Firebase Auth)
│   │   ├── RegisterActivity.kt          Register (Firebase Auth)
│   │   ├── MainActivity.kt              Home (tabs: Chats, Status, Calls)
│   │   ├── ChatActivity.kt              1-on-1 chat (Firestore read/write)
│   │   ├── GroupChatActivity.kt          Group chat (Firestore read/write)
│   │   ├── CreateGroupActivity.kt        Create group (Firestore write)
│   │   └── ProfileActivity.kt           Profile edit (Firestore read/write)
│   │
│   ├── adapters/                      ← RecyclerView data binding
│   │   ├── ChatAdapter.kt               Chat message list
│   │   ├── ConversationAdapter.kt        User/group conversation list
│   │   ├── SelectUserAdapter.kt          User selection (group creation)
│   │   └── ViewPagerAdapter.kt           Tab navigation
│   │
│   ├── fragments/                     ← Tab content
│   │   ├── ChatsFragment.kt             Chats tab (user + group list)
│   │   ├── StatusFragment.kt            Status tab (placeholder)
│   │   └── CallsFragment.kt             Calls tab (placeholder)
│   │
│   └── models/                        ← Data classes
│       ├── User.kt                       User model
│       ├── ChatMessage.kt                Message model
│       ├── ChatSession.kt                1-on-1 session metadata
│       ├── Conversation.kt               Unified conversation model
│       └── Group.kt                      Group model
│
└── res/                               ← 🎨 UI (Frontend)
    ├── layout/                        ← XML screens (15 files)
    │   ├── activity_*.xml                Activity layouts (7)
    │   ├── fragment_*.xml                Fragment layouts (3)
    │   └── item_*.xml                    List item layouts (5)
    │
    ├── drawable/                      ← Shape/icon resources (15 files)
    ├── menu/                          ← Toolbar menu
    │   └── main_menu.xml
    ├── values/                        ← Light theme
    │   ├── colors.xml, themes.xml, strings.xml
    └── values-night/                  ← Dark theme
        ├── colors.xml, themes.xml
