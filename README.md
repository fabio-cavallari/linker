# Linker

**Linker** is an Android application that provides URL shortening functionality with a clean and intuitive interface.  
The project focuses on demonstrating a solid architecture based on **Clean Architecture** principles.

## 🎯 Objective

The application consists of a single screen where users can input long URLs and receive shortened versions. Users can then share these shortened links or access the original websites directly from the app's history list.

Each shortened URL is displayed with its alias and provides quick actions for sharing and opening in a browser. The communication with the URL shortening API is handled through domain models and follows a clear separation of concerns.

## 🛠️ Development Stages

Thinking about how this app could behave in a more robust scenario, the development was structured in stages:

1. **API Integration and Error Handling**  
   A solid foundation was created for API communication, with proper error handling and result wrapping using sealed classes.

2. **Input Validation**  
   URL validation was implemented to ensure only valid URLs are sent to the API, providing immediate feedback to users.

3. **State Management**  
   A unidirectional data flow was established using MVI pattern with Intents, ensuring predictable state changes.

4. **UI/UX Polish**  
   Material Design 3 components were integrated with custom theming, providing both light and dark mode support with proper contrast variants.

## ⚙️ Architecture Decisions

The project follows Clean Architecture principles with clear separation between layers:

- **Data Layer**: Handles external dependencies (API, validators)
- **Domain Layer**: Contains business logic and entity models
- **Presentation Layer**: Manages UI state and user interactions

## 🧰 Technologies and Concepts Used

- **Clean Architecture**
- **MVVM + MVI**
- **Jetpack Compose**
- **Koin** (dependency injection)
- **Retrofit** (API communication)
- **MockK** (testing)
- **Kotlin Coroutines** (asynchronous operations)
- **StateFlow** (state management)

## 🧪 Testing

- **Unit tests** added mainly in the data layer (repository, remote provider, utilities).
- **UI tests** (instrumented) to validate compose components behavior and user interactions.

## 📝 Future Improvements

- Add Room Database for persistent storage