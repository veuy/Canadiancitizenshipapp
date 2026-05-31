# 🇨🇦 Canadian Citizenship Exam Prep App

A modern, offline-first Android application designed to help users study for and pass the Canadian Citizenship Test. The app features a comprehensive 12-chapter study guide, interactive quizzes, and timed practice exams.

## 🚀 Features

- **Full Study Guide**: Contains all 12 chapters of the official study material, formatted for easy reading with rich text and intuitive layouts.
- **Chapter-Specific Quizzes**: Test your knowledge immediately after studying. Each chapter includes a bank of randomized Multiple Choice and True/False questions.
- **Timed Practice Exams**: Simulates the real testing environment with:
  - 20 randomized questions per exam.
  - A 30-minute "Stopwatch" countdown timer.
  - 75% (15/20) passing mark logic.
- **Instant Feedback**: Real-time grading with green/red visual cues and detailed explanations for every question.
- **Progress Tracking**: Visual "Passed" indicators (green check marks) on the dashboard to track your exam history.
- **Review Mode**: Review your performance after each exam to see correct vs. incorrect answers.
- **Offline Access**: Built with a local database so you can study anywhere, anytime—no internet required.

## 🛠️ Technical Stack

- **Language**: Java
- **UI Framework**: XML / Material Design Components
- **Architecture**: MVVM (Model-View-ViewModel)
- **Database**: Room Persistence Library (SQLite)
- **Design Pattern**: Repository Pattern for a Single Source of Truth.
- **UI Logic**: ViewBinding for safe and efficient layout interaction.

## 🏗️ Work in Progress (Roadmap)
- [x] Integrate 12 chapters of study material.
- [x] Implement Practice Exam engine with timer.
- [x] Add detailed question review system.
- [ ] Implement Mock Exam randomized pool.
- [ ] Dark Mode support.

## ⚙️ Installation
1. Clone this repository.
2. Open the project in **Android Studio**.
3. Build and run on an Android device or emulator.
