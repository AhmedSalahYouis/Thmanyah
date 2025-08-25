# Thmanyah - Android Challenge App

A modern Android application built with cutting-edge technologies, showcasing best practices in mobile development and designed with future Kotlin Multiplatform (KMP) capabilities in mind.

## 🚀 Features

### Core Functionality
- **Home Feed**: Dynamic content sections with different layout types (square, two-lines grid, big square, queue)
- **Search**: Real-time search with debouncing and network-based results
- **Content Management**: Podcasts, audio articles, audio books, and episodes
- **Offline Support**: Robust offline handling with proper error states

### User Experience
- **Responsive Design**: Adaptive layouts for different screen sizes
- **Localization**: Full Arabic and English language support
- **Dark/Light Themes**: Material 3 design system with dynamic color support
- **Smooth Animations**: Compose-based animations and transitions

### Technical Features
- **Real-time Network Monitoring**: Automatic offline/online state detection
- **Image Loading**: Efficient image caching and loading with Coil
- **Pagination**: Smooth content loading with Paging 3
- **Error Handling**: Comprehensive error handling with user-friendly messages

## 🛠 Tech Stack

### Core Technologies
- **Kotlin**: Modern Kotlin with latest language features
- **Jetpack Compose**: Declarative UI toolkit for Android
- **Material 3**: Latest Material Design components
- **Android Architecture Components**: Lifecycle, ViewModel, Navigation

### Architecture & Design
- **Clean Architecture**: Separation of concerns with domain, data, and presentation layers
- **MVVM Pattern**: Model-View-ViewModel architecture
- **Repository Pattern**: Data abstraction layer
- **Use Case Pattern**: Business logic encapsulation

### Dependency Injection
- **Koin**: Lightweight dependency injection framework
  - **KMP Ready**: Designed for future Kotlin Multiplatform development
  - **DSL-based**: Kotlin DSL for dependency configuration
  - **Performance**: Fast startup and minimal runtime overhead
  - **Modular**: Easy to organize dependencies by feature

### Networking & Data
- **Retrofit**: Type-safe HTTP client
- **OkHttp**: HTTP client with interceptors
- **Room Database**: Local data persistence
- **Paging 3**: Efficient data loading and pagination
- **Kotlinx Serialization**: JSON serialization

### UI & Design
- **Jetpack Compose**: Modern declarative UI
- **Material 3**: Latest design system
- **Custom Typography**: IBM Plex Sans Arabic font family
- **Responsive Layouts**: Adaptive design for different screen sizes

### Testing & Quality
- **JUnit**: Unit testing framework
- **MockK**: Mocking library for Kotlin
- **Turbine**: Flow testing utilities
- **Detekt**: Static code analysis
- **Roborazzi**: Screenshot testing

### Build & Development
- **Gradle**: Build system with Kotlin DSL
- **KSP**: Kotlin Symbol Processing
- **Version Catalogs**: Centralized dependency management

### Dependency Management
- **Dependabot**: Automated dependency updates and security alerts
    - **Automated PRs**: Automatic pull requests for outdated dependencies
    - **Security Scanning**: Vulnerability detection and updates
    - **Version Management**: Keeps dependencies up-to-date with minimal manual intervention
    - **Multi-Platform**: Supports Android, Gradle, and other dependency ecosystems

## 🏗 Project Structure

```
Thmanyah/
├── app/                          # Main application module
├── feature/                      # Feature modules
│   ├── home/                     # Home feed functionality
│   └── search/                   # Search functionality
├── core/                         # Core modules
│   ├── data/                     # Data layer (repositories, data sources)
│   ├── domain/                   # Domain layer (use cases, entities)
│   ├── network/                  # Networking layer
│   ├── ui/                       # Shared UI components
│   ├── model/                    # Data models
│   ├── error/                    # Error handling
│   └── logger/                   # Logging utilities
└── gradle/                       # Gradle configuration
```

## 🔧 Architecture Overview

### Clean Architecture Layers

1. **Presentation Layer** (`feature/`, `core/ui/`)
   - Compose UI components
   - ViewModels
   - Navigation

2. **Domain Layer** (`core/domain/`)
   - Use cases
   - Business logic
   - Domain models

3. **Data Layer** (`core/data/`)
   - Repositories
   - Data sources
   - Local database

4. **Infrastructure Layer** (`core/network/`, `core/logger/`)
   - Network communication
   - Logging
   - External services

### Dependency Injection with Koin

```kotlin
// Feature module configuration
val searchModule = module {
    includes(domainModule)
    viewModel { SearchViewModel(get()) }
}

// Domain module configuration
val domainModule = module {
    single<SearchUseCase> { 
        SearchUseCaseImpl(get(), Dispatchers.IO)
    }
}
```

## 🚀 Getting Started

### Prerequisites
- Android Studio Hedgehog or later
- Android SDK 34+
- Kotlin 2.2.10+
- JDK 11+

### Installation

1. **Clone the repository**
   ```bash
   git clone https://github.com/yourusername/thmanyah.git
   cd thmanyah
   ```

2. **Open in Android Studio**
   - Open Android Studio
   - Select "Open an existing project"
   - Navigate to the cloned directory

3. **Sync and Build**
   - Wait for Gradle sync to complete
   - Build the project (Build → Make Project)

4. **Run on Device/Emulator**
   - Connect a device or start an emulator
   - Click the Run button

### Build Variants
- **Debug**: Development build with debugging enabled
- **Release**: Production build with optimizations

## 🔮 KMP (Kotlin Multiplatform) Readiness

### Why Koin for KMP?

1. **Platform Agnostic**: Koin works seamlessly across all KMP targets
2. **Kotlin Native Support**: Full support for iOS, macOS, and other native platforms
3. **Shared Code**: Dependency injection can be shared between platforms
4. **Performance**: Lightweight and fast, perfect for mobile applications

### KMP Migration Path

The current architecture is designed to easily migrate to KMP:

1. **Shared Modules**: Core modules can be converted to `commonMain`
2. **Platform-Specific**: UI and platform-specific code in `androidMain`/`iosMain`
3. **Dependencies**: Koin modules can be shared across platforms
4. **Testing**: Common test code in `commonTest`

### Example KMP Structure (Future)
```
shared/
├── commonMain/
│   ├── kotlin/
│   │   ├── domain/           # Shared business logic
│   │   ├── data/             # Shared data layer
│   │   └── di/               # Shared Koin modules
│   └── resources/            # Shared resources
├── androidMain/              # Android-specific code
└── iosMain/                  # iOS-specific code
```

## 🧪 Testing

### Running Tests

```bash
# Unit tests
./gradlew test

# Instrumented tests
./gradlew connectedAndroidTest

# All tests
./gradlew check
```

### Test Structure
- **Unit Tests**: Business logic and data layer testing
- **UI Tests**: Compose UI testing with Roborazzi
- **Integration Tests**: Repository and network layer testing

## 📱 Screenshots

*[Add screenshots of your app here]*

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

## 📄 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## 🙏 Acknowledgments

- **Jetpack Compose** team for the amazing UI toolkit
- **Koin** team for lightweight dependency injection
- **Material Design** team for the design system
- **Android Architecture Components** team for the architecture guidance

## 📞 Support

If you have any questions or need help:
- Create an issue in the repository
- Contact the development team
- Check the documentation

---

**Built with ❤️ using modern Android development practices**
