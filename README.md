# Maxima Tech - Android Challenge

Application that consumes REST API to display clients and orders data with offline support and background synchronization.

[Leia em Português](README.pt.md)

## Tech Stack

**Core**
- Kotlin - Main development language (80%)
- Java - Supporting language (20%)
- Jetpack Compose - UI toolkit
- Material Design 3 - Design system

**Networking**
- Retrofit - HTTP client
- OkHttp - Network interceptor
- Gson - JSON serialization

**Database**
- Room - Local database

**Architecture**
- MVVM - Model-View-ViewModel pattern
- Clean Architecture - Layered architecture
- Coroutines - Asynchronous programming
- Flow - Reactive streams

**Dependency Injection**
- Koin - Dependency injection framework

**Background Tasks**
- WorkManager - Periodic data synchronization
- Connectivity Manager - Network state monitoring

**Resources Management**
- Custom Dimens class - Centralized spacing and sizing values
- String Resources - Text concatenation and localization support

**Testing**
- JUnit - Unit testing framework
- MockK - Mocking framework

## Architecture

The project follows Clean Architecture principles with MVVM pattern:

**Presentation Layer**
- Compose UI screens
- ViewModels with state management
- Navigation component

**Domain Layer**
- Use Cases for business logic
- Repository interfaces
- Domain models

**Data Layer**
- Repository implementations
- Remote data source (API)
- Local data source (Room database)

## Design System Implementation

**Spacing and Dimensions**
- Custom Dimens object with standardized spacing values
- Consistent spacing across all UI components
- Scalable dimension system for different screen sizes

**String Management**
- Centralized string resources for all text content
- String concatenation through resource references
- Prepared for multi-language support
- Dynamic text composition for complex UI elements

## Features

**Client Management**
- View client list
- Client details screen
- Status verification with Snackbar

**Order History**
- Browse order list
- Search orders by RCA number
- Order filtering with debounce (300ms)
- Legend indicators

**Offline Support**
- Automatic fallback to local database
- Background data synchronization every 15 minutes
- Network connectivity monitoring

**UI/UX**
- Modern Compose interface
- Responsive design with consistent spacing
- Loading states and error handling
- Search with real-time filtering
- Centralized design tokens for consistent theming

## Requirements Compliance

**Mandatory Requirements**
- Splash Screen implementation
- Retrofit for API consumption
- Room for local data persistence
- Network-based data source selection
- Koin dependency injection
- SOLID principles with MVVM
- Kotlin (80%) and Java (20%) mix
- Periodic background job (15 minutes)
- Coroutines and Flow usage

**Optional Features Implemented**
- Unit testing
- Centralized design system with custom dimensions
- String resource management for text concatenation

Developed for Maxima Tech Android Challenge