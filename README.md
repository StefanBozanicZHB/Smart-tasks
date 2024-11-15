# Smart Tasks

Smart Tasks is a task management app designed to make organizing your daily tasks simple and efficient. Built using Jetpack Compose and following Clean Architecture principles, Smart Tasks provides a seamless user experience with features like task sorting, theme selection, and an intuitive date picker.

## 👶 Special Note

During the development of Smart Tasks, the author became a father for the second time. This milestone made the project even more special, symbolizing growth and new beginnings, both personally and professionally.

## 🏗️ Architecture

The project is structured based on Clean Architecture, dividing the code into three main layers to maintain separation of concerns and facilitate easy testing and scaling:

- **Presentation**: This layer handles the UI using Jetpack Compose. It interacts with ViewModels to display data and manage user interactions.
- **Domain**: Contains the business logic of the app, including use cases and model definitions. It is independent of other layers, making it easy to test.
- **Data**: Manages the data sources, which include local storage (Room database) and remote API calls (using Retrofit). It implements repository interfaces defined in the Domain layer.

## 🔧 Setup Instructions

Follow these steps to set up and run the project on your local machine:

1. Clone the repository.
2. Open the project in [Android Studio](https://developer.android.com/studio).
3. Make sure you have the latest version of Android Studio with Jetpack Compose support.
4. Sync the Gradle files and let the dependencies download.
5. Build and run the app on an emulator or physical device.

## 🌟 Features

- **Task Management**: Create, update tasks with ease.
- **Sorting Options**: Sort tasks by priority or due date to stay organized.
- **Theme Selection**: Choose between light and dark themes based on your preference. [In progress...]
- **Date Picker**: Easily select a date to view tasks scheduled for that day.
- **Responsive UI**: A beautiful and responsive UI built using Jetpack Compose.

## 📚 Libraries and Tools Used

The app leverages modern Android libraries and tools to ensure a smooth and maintainable development process:

- **Jetpack Compose**: Android’s modern toolkit for building native UI.
- **Koin**: A lightweight dependency injection framework for Kotlin.
- **Room**: A database library for efficient local data storage.
- **Retrofit**: A type-safe HTTP client for making API requests.
- **DataStore**: Used for storing user preferences, such as theme and sorting options.
- **Timber**: A logging utility to help with debugging and monitoring.

## 📌 Issues Management

I used the GitHub Issues feature as a ticket management system for tracking progress on Smart Tasks. Here's how it works:

- **Closed Issues**: Represent completed tickets or features that have been implemented.
- **Open Issues**: Represent suggestions or "nice to have" features that are planned for future implementation.

## 🤝 Contribution Guidelines

I welcome contributions from the community! To contribute:

1. Fork the repository.
2. Clone your forked repository.
3. Create a new branch for your feature or bug fix.
4. Make your changes and commit them with a descriptive commit message.
5. Push your changes to your forked repository.
6. Open a pull request on the main repository and describe your changes in detail.

---

Thank you for your interest in Smart Tasks! If you have any questions or suggestions, feel free to open an issue or reach out.
