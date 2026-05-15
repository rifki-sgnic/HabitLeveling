# 🌑 Shadow Leveling — Habit Tracker System

[![Kotlin](https://img.shields.io/badge/Kotlin-2.0-blue.svg)](https://kotlinlang.org/)
[![Compose](https://img.shields.io/badge/Jetpack-Compose-green.svg)](https://developer.android.com/jetpack/compose)
[![Hilt](https://img.shields.io/badge/Dagger-Hilt-orange.svg)](https://dagger.dev/hilt/)
[![Gemini](https://img.shields.io/badge/AI-Gemini_2.5_Flash-purple.svg)](https://aistudio.google.com/)

> **"Arise."** — This is not just a habit tracker. It is a HUD-style terminal system designed to push your limits, styled after the "Solo Leveling" interface.

---

## 🖥️ System Overview

**Shadow Leveling** transforms your daily habits into an RPG Quest. Every real-world action provides stats and level-ups within the application. If you fail to complete your daily quests, be prepared to face the **Penalty**.

### Key Features
*   **Dynamic HUD Interface**: A sci-fi themed dark UI with *Electric Blue* accents.
*   **Player Status System**: Monitor Level, HP, MP, Fatigue, and Stats (Strength, Agility, Intelligence, etc.).
*   **AI Architect (Gemini 2.5 Flash)**: The system creates unique quests every day based on your latest progress and stats.
*   **Penalty System**: Skipped training? Fatigue will rise, and "The System" will issue a punishment.
*   **Clean Architecture**: Neat and modular code following industry standards (Domain, Data, UI layers).

---

## 🛠️ Tech Stack
*   **Language**: Kotlin (Modern & Safe)
*   **UI Framework**: Jetpack Compose (Declarative UI)
*   **Dependency Injection**: Hilt (Dagger-based)
*   **Local Database**: Room Persistence (SQLite wrapper)
*   **Asynchronous**: Coroutines & Flow
*   **AI Integration**: Google Generative AI SDK (Gemini API)
*   **Architecture**: Clean Architecture + MVVM

---

## 🏗️ Project Structure
```text
app/src/main/java/com/mrifkii/habitleveling/
├── domain/          ← Pure Kotlin (Business Logic, Model, Repository Interface)
├── data/            ← Implementation (Room, Remote API, Repository Impl)
├── ui/              ← Jetpack Compose (Screens, Components, Theme)
├── di/              ← Hilt Modules
└── MainActivity.kt  ← Application Entry Point
```

---

## 🚀 Getting Started

### 1. Prerequisites
*   Android Studio Ladybug (or newer).
*   Google AI Studio API Key (Get it for free at [Google AI Studio](https://aistudio.google.com/)).

### 2. API Key Configuration
For security reasons, the API Key is stored in `local.properties`. Add the following line to your `local.properties` file:

```properties
GEMINI_API_KEY=AIzaSyA... (Replace with your actual API Key)
```

### 3. Build & Run
1.  Open the project in Android Studio.
2.  Perform a **Gradle Sync**.
3.  Click **Rebuild Project** (Crucial for `BuildConfig` to detect the API Key).
4.  Run on an Emulator or Real Device.

---

## 📜 System Rules
1.  **Daily Quests** must be completed before 00:00.
2.  Each **Level Up** grants **Stat Points** that can be allocated in the Status Screen.
3.  Use the **✨ (AI)** button in the Quests tab to summon "The Architect" and receive new challenges.

---

## 🤝 Contribution
Want to help develop "The System"? Feel free to open an *Issue* or submit a *Pull Request*.

*Developed by [rifki-sgnic](https://github.com/rifki-sgnic) and [setyobagus01](https://github.com/setyobagus01)*
