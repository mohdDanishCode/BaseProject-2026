### Features Implemented

- Clean Architecture with multi-module setup
- Offline-first approach with Room as the single source of truth
- Trending movies fetched from TMDB and synced to local database
- Movie list and movie detail screens built with Jetpack Compose
- Local search powered by database queries (works offline)
- Paging 3 integration for efficient data loading
- Pull-to-refresh support
- Image loading with disk and memory caching
- Centralized design system for colors, typography, spacing, and components
- Separate error handling using sealed error classes
- Proper loading, error (with retry), and empty states
- Keyboard automatically hides on scroll and swipe interactions
- Lazy initialization where applicable

### Setup

Add your TMDB API key in `gradle.properties`:

```properties
TMDB_API_KEY=your_api_key_here
