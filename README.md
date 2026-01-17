# 🎬 Movie Demo App

A modern Android application built with **Kotlin** and **Jetpack Compose** that demonstrates trending movie discovery using an **offline-first architecture**, **clean architecture principles**, and a **polished production-ready UI**.

---

## 📹 App Demo

https://drive.google.com/file/d/1QK7EQMSbYLIKlHALfJNc8GiDKbBPm85N/view?usp=sharing

---

## ✨ Features

- Browse trending movies  
- Movie detail screen  
- Offline-first architecture (local database as source of truth)  
- Local movie search (works completely offline)  
- Pull-to-refresh support  
- Paging 3 for efficient data loading  
- Image caching (memory & disk)  
- Centralized design system  
- Structured error handling  
- Loading, error (with retry), and empty states  
- Keyboard auto-hide on scroll and swipe  
- Lazy initialization for better performance  

---

## 🧱 Architecture

The app follows **Clean Architecture** with a **multi-module setup**:

### Layers

- **Data layer**
  - Remote API
  - Local database
  - Paging & RemoteMediator
  - Mappers

- **Domain layer**
  - Business logic
  - Use cases

- **UI layer**
  - Jetpack Compose screens
  - ViewModel & State management

### Data Flow

Remote API → Local Database → UI

> UI observes data **only from the database**, ensuring full offline support.

---

## 🛠 Tech Stack

- Kotlin  
- Jetpack Compose  
- Compose Navigation  
- Retrofit + OkHttp  
- Room  
- Paging 3  
- Coil  
- Hilt  
- StateFlow & ViewModel  

---

## ⚙️ Setup

1. Clone the repository  
2. Generate a TMDB API key  
3. Add the key to `gradle.properties`:

```properties
TMDB_API_KEY=your_api_key_here


