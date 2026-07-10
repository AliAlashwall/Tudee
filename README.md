# Tudee

Tudee is a modern Android task management application built with the latest Android development tools and best practices.

## Features

- **Task Management**: Create, update, and organize your daily tasks.
- **Modern UI**: Built entirely with Jetpack Compose for a smooth and responsive user interface.
- **Local Persistence**: Uses Room database for reliable offline storage.
- **Clean Architecture**: Follows SOLID principles and Clean Architecture (Data, Domain, Presentation layers).
- **Animations**: Integrated Lottie animations for an engaging user experience.

## Screens
<img width="208" height="580" alt="image" src="https://github.com/user-attachments/assets/12dbf28b-3672-4bba-a48d-05538d0c02f4" />

<img width="208" height="580" alt="image" src="https://github.com/user-attachments/assets/d42d3029-2e81-434b-945b-b73f2c178b74" />

<img width="208" height="580" alt="image" src="https://github.com/user-attachments/assets/f7d2b8c4-cee9-4c76-bc4e-cd8e5c3c0685" />

<img width="208" height="580" alt="image" src="https://github.com/user-attachments/assets/666f97a3-9a21-4112-a945-7a8c13117f38" />

<img width="208" height="580" alt="image" src="https://github.com/user-attachments/assets/aeebc942-0e0b-494c-b899-cd051f3f80c9" />

<img width="208" height="580" alt="image" src="https://github.com/user-attachments/assets/bf429c38-2cad-4e25-991c-977ba6a5fa5f" />









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

