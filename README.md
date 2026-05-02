# Accurate User App

## How to Use
1. **View Users** — User list loads automatically from API, cached locally for offline use
2. **Search** — Type in the search bar, results filter in real-time (300ms debounce)
3. **Sort** — Tap the ↑↓ button to toggle A-Z / Z-A sorting
4. **Filter by City** — Tap the filter icon, select a city from the bottom sheet. Active filter shows as a chip
5. **Add User** — Tap the + FAB, fill the form, tap Save

## Tech Stack
| Category | Library |
|---|---|
| UI | Jetpack Compose + Material 3 |
| Architecture | MVVM + Clean Architecture |
| DI | Dagger Hilt |
| Networking | Retrofit + OkHttp |
| JSON | Moshi (with KSP codegen) |
| Local DB | Room Database |
| Reactive | Kotlin Coroutines + Flow |
| Background Sync | WorkManager (every 15 min) |
| Analytics | Firebase Analytics |
| Adaptive Layout | Material3 Adaptive |

## Design Decisions
- **Offline-first**: Room DB is the single source of truth. Data is always read from local DB, synced from API in background
- **Search debounce 300ms**: Prevents excessive DB queries while user is typing
- **Bottom sheet filter**: Less intrusive than a dialog, shows all city options clearly
- **Avatar with initial + gender color**: Quick visual identification without photos (blue = male, pink = female)
- **WorkManager periodic sync**: Keeps data fresh every 15 minutes when device is connected

## Commit Convention
Following [Karma commit message format](http://karma-runner.github.io/4.0/dev/git-commit-msg.html):
- `feat:` new feature
- `fix:` bug fix  
- `chore:` build/config changes
- `refactor:` code restructure
