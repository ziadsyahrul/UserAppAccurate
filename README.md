# Accurate User App

## How to Use
1. **View Users** — User list loads automatically from API, cached locally for offline use
2. **Search** — Type in the search bar, results filter in real-time (300ms debounce)
3. **Sort** — Tap the ↑↓ button to toggle A-Z / Z-A sorting
4. **Filter by City** — Tap the filter icon, select a city from the bottom sheet. Active filter shows as a chip
5. **Add User** — Tap the + FAB, fill the form, tap Save

## Screenshots

<p>
  <img src="https://github.com/user-attachments/assets/d2c3f272-c3a7-4e9a-8536-09ab21e6bf47" width="280"/>
  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
  <img src="https://github.com/user-attachments/assets/298da3a2-ff58-4750-8342-8a1a04ea9853" width="280"/>
</p>


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

## Why This UI/UX
- **Search bar always visible at top** — User tidak perlu cari tombol search, langsung terlihat saat buka app. Mengurangi friction untuk fitur yang paling sering dipakai.
- **Sort & Filter as icon buttons next to search** — Diletakkan berdekatan dengan search bar karena ketiganya adalah fitur discovery. User yang mau cari user spesifik biasanya butuh ketiganya sekaligus.
- **Active filter shown as chip** — Saat filter aktif, chip muncul di bawah search bar agar user selalu tahu filter apa yang sedang aktif. Tap chip untuk hapus filter — lebih intuitif daripada harus buka bottom sheet lagi.
- **Bottom sheet for city filter** — Lebih natural di mobile dibanding dialog/dropdown. User bisa lihat semua pilihan kota sekaligus dan dismiss dengan swipe down.
- **FAB for Add User** — Floating Action Button adalah pattern standar Material Design untuk primary action. Selalu visible di corner kanan bawah tanpa mengganggu konten list.
- **Avatar with initials + gender color** — Karena tidak ada foto user, avatar dengan huruf pertama nama memberikan visual identity yang cukup. Warna berbeda untuk gender membantu user scan list lebih cepat tanpa harus baca detail.
- **Snackbar for offline notification** — Non-intrusive, tidak memblokir konten. User tetap bisa lihat data cached sambil tahu kondisi offline.

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

## Setup
1. Clone repository
```
   git clone https://github.com/ziadsyahrul/UserAppAccurate.git
```
2. Add `google-services.json` from your Firebase project into `app/` folder
3. Build and run
