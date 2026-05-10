# Shadow Leveling — Agent Guidelines

> Read this file completely before writing a single line of code. These rules are not suggestions.

---

## 1. Project Identity

**Shadow Leveling** is an Android-only habit tracker styled as a HUD-style anime sci-fi terminal.
Brand axis: **Solo Leveling × Atomic Habits**.

| Game term | App concept |
|---|---|
| Player | The user |
| The System | The app |
| Quest | A habit |
| Rank Up | Level threshold crossed |

Every screen is a HUD overlay. Information is **summoned**, not displayed. The aesthetic is obsidian black, electric blue accents, sharp corners, glow instead of shadow, glitch instead of fade.

Full design spec lives in `DESIGN.md`. This file references it — always read `DESIGN.md` before touching UI.

---

## 2. Architecture — Clean Architecture, Non-Negotiable

The project follows strict Clean Architecture. Violations will be rejected.

```
app/src/main/java/com/mrifkii/habitleveling/
├── domain/          ← Pure Kotlin. Zero Android/framework imports.
│   ├── model/       ← Data classes. No Room annotations here.
│   ├── repository/  ← Interfaces only. No implementation.
│   ├── service/     ← Domain logic services.
│   └── usecase/     ← One public function per class. Calls repository/service.
├── data/            ← Implements domain contracts.
│   ├── local/       ← Room entities, DAOs, Database.
│   │   ├── dao/
│   │   └── entity/
│   ├── remote/      ← API services (Gemini AI, etc.)
│   └── repository/  ← Implements domain/repository interfaces.
├── ui/              ← Jetpack Compose. Talks to ViewModel only.
│   ├── theme/       ← Color.kt, Type.kt, Theme.kt — design tokens.
│   ├── components/  ← Shared Composables (HudPanel, RankBadge, StatBar…).
│   └── {screen}/    ← One folder per screen: Screen.kt + ViewModel.kt.
├── di/              ← Hilt modules only. No business logic here.
└── HabitLevelingApp.kt
```

### Layer rules

| Layer | Allowed dependencies | Forbidden |
|---|---|---|
| `domain` | Kotlin stdlib, coroutines | Android SDK, Room, Retrofit, Hilt |
| `data` | `domain`, Room, Retrofit, Hilt | `ui` |
| `ui` | `domain.model`, ViewModel (which depends on use cases) | `data` directly |
| `di` | All layers | Business logic |

**One use case per file.** Use cases are named `VerbNounUseCase` (e.g., `CompleteQuestUseCase`).

**Repositories are interfaces in `domain`, implemented in `data`.** Never put a `*Impl` class in the domain layer.

**ViewModels never import Room, Retrofit, or any data-layer class directly.** They call use cases only.

---

## 3. Design Rules (enforced, not guidelines)

Read `DESIGN.md` for the full spec. These are the hard constraints you must remember at all times.

### Colors

```kotlin
// ui/theme/Color.kt — use these names everywhere
val BgVoid        = Color(0xFF05070A)   // App background
val BgObsidian    = Color(0xFF0B0F14)   // Sticky chrome
val BgPanel       = Color(0xFF0F1620)   // Panel fill (85% alpha in use)
val BlueCore      = Color(0xFF4ABDFF)   // Brand accent — borders, text, bars
val BlueBright    = Color(0xFF7FD4FF)   // Highlights, hover
val BlueDeep      = Color(0xFF1A6FA8)   // Resting borders
val TextPrimary   = Color(0xFFE6F1FF)
val TextSecondary = Color(0xFF8DA0B8)
val TextMuted     = Color(0xFF4A5A6E)
val TextSystem    = Color(0xFF4ABDFF)
val Success       = Color(0xFF4AFFB5)
val Warning       = Color(0xFFFFB547)
val Danger        = Color(0xFFFF4A6B)
```

**Electric blue is for borders, text accents, glows, and bar fills. Never large flat fills.**

### Shape — HARD RULE

`RoundedCornerShape(2.dp)` everywhere. **Never exceed 2dp radius anywhere.** `CircleShape` is only for user avatar frames.

```kotlin
// Correct
Card(shape = RoundedCornerShape(2.dp)) { … }
// Wrong — never do this
Card(shape = RoundedCornerShape(8.dp)) { … }
Card(shape = RoundedCornerShape(50)) { … }
```

### Glow, not shadow

```kotlin
// Correct: use Modifier.shadow with a glow color
Modifier.shadow(elevation = 0.dp) // then override with drawBehind for glow
// Or expose a glowSoft/glowMedium/glowIntense composable helper
```

Never use `elevation` for depth. Express depth exclusively via glow (`--glow-soft`, `--glow-medium`, `--glow-intense` equivalents).

### Typography

```kotlin
// ui/theme/Type.kt
// Font families: Orbitron (display), Rajdhani (heading), Inter (body), JetBrains Mono (mono)
// Section labels: UPPERCASE, letterSpacing ≥ 0.15.em
// Numbers: fontFamily = JetBrains Mono + fontFeatureSettings = "tnum"
```

Section labels and button text are UPPERCASE with `letterSpacing ≥ 0.15.sp`. Body copy is mixed-case only.

### Components

| Component | Where to put it | Notes |
|---|---|---|
| `HudPanel` | `ui/components/HudPanel.kt` | Corner brackets via `Canvas`, 2dp radius, `BlueDeep` border at rest |
| `QuestCard` | `ui/components/QuestCard.kt` | Extends `HudPanel` |
| `StatBar` | `ui/components/StatBar.kt` | 4dp track, `BlueCore` fill |
| `RankBadge` | `ui/components/RankBadge.kt` | Orbitron 13sp/700, rank color border |
| `PlayerHeader` | `ui/components/PlayerHeader.kt` | Sticky top, **96dp** (M3 LargeTopAppBar height — do not reduce) |
| `BottomNav` | `ui/components/BottomNav.kt` | **80dp intrinsic** (M3 NavigationBar default — do not force a smaller height) |
| `SystemMessage` | `ui/components/SystemMessage.kt` | Igris dialogue panel |

### Ten hard rules — never violate

1. No corner radii > 2dp anywhere.
2. No drop shadows. Use glow only.
3. No gradients except XP-bar shimmer and the optional radial page background.
4. No emoji in system-authored copy.
5. No bright/cheerful colors outside the defined palette.
6. No filled icons. Line only, 1.5dp stroke (Lucide set).
7. No first-person voice from The System. No warmth, no apology, no motivational copy.
8. Numbers always tabular (`JetBrains Mono`, `fontFeatureSettings = "tnum"`).
9. Section labels always UPPERCASE with `letterSpacing ≥ 0.15.sp`.
10. No backdrop blur. Translucency is 85% alpha only.
11. Never use px.

---

## 4. Voice & Microcopy

The System speaks. It does not chat.

### Tone

- Observational, terse, cold.
- Addresses user as `Player` or `Hunter`. Never by name.
- Never first-person (`we`, `us`, `the app`).
- Never breaks the fourth wall.

### System tags — use verbatim

```
[SYSTEM]
[QUEST REGISTERED]
[QUEST CLEARED]
[QUEST FAILED]
[ALERT]
[PENALTY APPLIED]
[RANK UP]
[SHADOW GUIDE]:
```

### Correct examples

```
[QUEST REGISTERED] — Daily run, 5km. Reward: 45 XP, AGI +3.
[QUEST CLEARED] — +45 XP. AGI: 84 → 87.
[ALERT] — Habit dormancy in 1 day. Take action.
[SHADOW GUIDE]: Performance down 15% from yesterday. Recommend completing Running quest.
```

### Rejected examples

```
❌ Hey buddy! Looks like you're slipping! 😊
❌ Great job! You completed your quest!
❌ Don't forget to log your habit today.
❌ Any emoji. Any confetti. Exclamation points (except cinematics).
```

---

## 5. Gemini CLI Tool Mapping

Skills and internal references use Claude Code tool names. When working in Gemini CLI, use these equivalents:

| Reference | Gemini CLI tool |
|---|---|
| `Read` | `read_file` |
| `Write` | `write_file` |
| `Edit` | `replace` |
| `Bash` | `run_shell_command` |
| `Grep` | `grep_search` |
| `Glob` | `glob` |
| `TodoWrite` | `write_todos` |
| `WebSearch` | `google_web_search` |
| `WebFetch` | `web_fetch` |
| Task/subagent dispatch | `@generalist` with full prompt |

Persist cross-session facts via `save_memory`. Use `enter_plan_mode` before making structural changes. Use `tracker_create_task` to track multi-step work.

---

## 6. Kotlin & Android Conventions

- **Kotlin only.** No Java files.
- **Hilt** for DI. No manual service locators.
- **Room** for local persistence. No raw SQLite.
- **Coroutines + Flow** for async. No RxJava, no LiveData (except where bridging legacy is forced by a library).
- **Jetpack Compose** only. No XML layouts.
- **StateFlow** in ViewModel exposed as `val uiState: StateFlow<UiState>`.
- Use `sealed class UiState` for loading/success/error states.
- No business logic in Composables. Composables display state and emit events only.
- Use cases are `suspend fun invoke(...)` or return `Flow<...>`.
- Entities (`*Entity`) live in `data/local/entity`. Domain models (`Player`, `Quest`) live in `domain/model`. Map between them in the `*RepositoryImpl`, never in the use case or ViewModel.

### File naming

| What | Convention |
|---|---|
| Composable | `PascalCase.kt` — same name as the primary composable inside |
| ViewModel | `{Screen}ViewModel.kt` |
| Use case | `VerbNounUseCase.kt` |
| Repository interface | `{Noun}Repository.kt` |
| Repository impl | `{Noun}RepositoryImpl.kt` |
| Room entity | `{Noun}Entity.kt` |
| Hilt module | `{Concern}Module.kt` |

---

## 7. Compose Pitfalls — Things That Break the App

### Fixed height clips content silently

Compose does **not** reflow or shrink children when they exceed a fixed-height container. Content that overflows is drawn outside the bounds and silently clipped by the nearest clipping ancestor. No error, no warning — it just disappears.

**Before setting any fixed height**, verify the intrinsic height of every child:
- Use **`lineHeight`** (not `fontSize`) for text — `headlineSmall` is 24sp but has lineHeight 32sp.
- Account for `Spacer`, padding, and progress bar heights in the same column.
- If `verticalArrangement = Arrangement.SpaceBetween` is used, total content height = sum of all children's heights; SpaceBetween only distributes leftover space and does not shrink children.

### Never force height on M3 components below their token minimum

M3 components measure their children against their own internal token-based height. Constraining to a smaller value clips content without error.

| Component | Intrinsic height | Do not go below |
|---|---|---|
| `NavigationBar` | 80dp (`NavigationBarTokens.ContainerHeight`) | 80dp |
| `TopAppBar` (Small) | 64dp | 64dp |
| `LargeTopAppBar` / `PlayerHeader` | 96dp | 96dp |

If the design spec names a different height (e.g. "64dp bottom nav"), that refers to a **custom implementation**, not a constrained M3 component.

### Modifier order: `padding` vs `height`

Modifier chain order determines what gets what space.

```kotlin
// padding is OUTER, height is INNER
// NavigationBar measures at 80dp; total space consumed = 80 + 24 = 104dp
.padding(12.dp).height(80.dp)

// height is OUTER (total = 80dp), padding eats into it
// NavigationBar measures at 80 - 24 = 56dp → content is clipped
.height(80.dp).padding(12.dp)
```

Rule: if a component needs its full intrinsic height, **do not wrap it with padding before height**.

### `NavigationBar` indicator pill

`NavigationBarItemDefaults.colors(indicatorColor = colors.secondaryContainer)` renders a soft Material3 pill behind the icon. This breaks the HUD aesthetic. Always set `indicatorColor = Color.Transparent`.

---

## 9. Before You Write Code

1. **Read `DESIGN.md`** if any UI is involved.
2. **Identify the correct layer** for each new class.
3. **Check that the domain layer stays pure** — no Android imports creeping in.
4. **Verify microcopy** against the system tag list and tone rules.
5. **Use `enter_plan_mode`** before structural changes; exit only when the plan is confirmed.

---

## 10. What Will Be Rejected

- Corner radius > 2dp anywhere.
- `elevation` used for depth (use glow).
- Warm, emoji-laden, or cheerful system copy.
- Android SDK imports inside `domain/`.
- `data/` classes referenced directly from `ui/`.
- ViewModels that bypass use cases and call repositories directly.
- Filled icons.
- Bright colors outside the defined palette.
- Hallucinated APIs or fabricated method signatures — verify with `read_file` or `grep_search` before calling.

---

## 11. Build & Verify

```bash
# Build debug APK
./gradlew assembleDebug

# Run unit tests
./gradlew test

# Run lint
./gradlew lint

# Install on connected device
./gradlew installDebug
```

All commands run from the repo root. Fix errors before reporting work as done — do not describe what you intended, verify what actually compiled.
