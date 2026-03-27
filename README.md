# Tudee

Tudee is a modern Android task management application built with the latest Android development tools and best practices.

## Features

- **Task Management**: Create, update, and organize your daily tasks.
- **Modern UI**: Built entirely with Jetpack Compose for a smooth and responsive user interface.
- **Local Persistence**: Uses Room database for reliable offline storage.
- **Clean Architecture**: Follows SOLID principles and Clean Architecture (Data, Domain, Presentation layers).
- **Animations**: Integrated Lottie animations for an engaging user experience.

## Screens
<img width="208" height="580" alt="image" src="https://github.com/user-attachments/assets/a1a93482-7d3a-4493-99f2-6fe0de1439bc" />
<img width="208" height="580" alt="image" src="https://github.com/user-attachments/assets/ffba5f78-8dca-45e6-a8b2-9eb8b38c565d" />
<img width="208" height="580" alt="image" src="https://github.com/user-attachments/assets/4bf9178e-3869-4b7f-9bac-7b3ac6b790f1" />
<img width="208" height="580" alt="image" src="https://github.com/user-attachments/assets/2da4e835-60ba-40aa-80f3-da636d34ef7a" />
<img width="208" height="580" alt="image" src="https://github.com/user-attachments/assets/af1774f7-158c-4ce2-8ffe-d8ed0e7adc54" />
<img width="208" height="580" alt="image" src="https://github.com/user-attachments/assets/e61b28c8-2374-41d2-8bba-343250bb4e95" />









## Tech Stack

- **Language**: [Kotlin](https://kotlinlang.org/)
- **UI Framework**: [Jetpack Compose](https://developer.android.com/jetpack/compose)
- **Dependency Injection**: [Hilt](https://developer.android.com/training/dependency-injection/hilt-android)
- **Database**: [Room](https://developer.android.com/training/data-storage/room)
- **Navigation**: [Navigation Compose](https://developer.android.com/jetpack/compose/navigation)
- **Asynchronous Programming**: [Coroutines](https://kotlinlang.org/docs/coroutines-overview.html) & [Flow](https://kotlinlang.org/docs/flow.html)
- **Image Loading**: [Coil](https://coil-kt.github.io/coil/)
- **Animations**: [Lottie for Android](https://github.com/airbnb/lottie-android)
- **Data Storage**: [Preferences DataStore](https://developer.android.com/topic/libraries/architecture/datastore)

## Project Structure

The project is organized following Clean Architecture principles:

- **`data`**: Contains implementation of repositories, database definitions (Room), and data sources.
- **`domain`**: Contains business logic, models, and repository interfaces.
- **`presentation`**: Contains the UI layer (Compose screens, ViewModels, and UI components).
- **`di`**: Dependency Injection modules using Hilt.
- **`navigation`**: Navigation logic and route definitions.

## Getting Started

### Prerequisites

- Android Studio Koala | 2024.1.1 or newer
- JDK 17
- Android SDK 24+

### Installation

1. Clone the repository:
   ```bash
   git clone https://github.com/AliAlashwall/Tudee
   ```
2. Open the project in Android Studio.
3. Sync the project with Gradle files.
4. Run the app on an emulator or a physical device.

## Contributing

Contributions are welcome! Please feel free to submit a Pull Request.

