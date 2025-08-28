# Home Feature Components

This package contains all the composables for the home feature, organized by content type and layout.

## Structure

### Content Type Composables

Each content type has 4 layout-specific composables:

#### Podcast Content Items
- `QueuePodcastItem` - Queue layout wrapper for CoreCard
- `SquarePodcastItem` - Square layout wrapper for CoreCard  
- `BigSquarePodcastItem` - Big Square layout wrapper for CoreCard
- `TwoLinesGridPodcastItem` - Two Lines Grid layout wrapper for CoreCardTwoLines

#### Episode Content Items
- `QueueEpisodeItem` - Queue layout wrapper for CoreCard
- `SquareEpisodeItem` - Square layout wrapper for CoreCard
- `BigSquareEpisodeItem` - Big Square layout wrapper for CoreCard
- `TwoLinesGridEpisodeItem` - Two Lines Grid layout wrapper for CoreCardTwoLines

#### Audio Book Content Items
- `QueueAudioBookItem` - Queue layout wrapper for CoreCard
- `SquareAudioBookItem` - Square layout wrapper for CoreCard
- `BigSquareAudioBookItem` - Big Square layout wrapper for CoreCard
- `TwoLinesGridAudioBookItem` - Two Lines Grid layout wrapper for CoreCardTwoLines

#### Audio Article Content Items
- `QueueAudioArticleItem` - Queue layout wrapper for CoreCard
- `SquareAudioArticleItem` - Square layout wrapper for CoreCard
- `BigSquareAudioArticleItem` - Big Square layout wrapper for CoreCard
- `TwoLinesGridAudioArticleItem` - Two Lines Grid layout wrapper for CoreCardTwoLines

### Factory Composables

#### ContentItemFactory
Automatically creates the appropriate content item based on the item type and section layout. This is the recommended way to display content items as it handles all the type checking and layout selection automatically.

### UI Components

#### CategoryChipsRow
Content type filtering chips for the home screen.

## Usage

### Direct Usage (Specific Layouts)
```kotlin
// For specific podcast layouts
SquarePodcastItem(
    podcast = podcastItem,
    onClick = { /* handle click */ }
)

// For specific episode layouts
TwoLinesGridEpisodeItem(
    episode = episodeItem,
    onClick = { /* handle click */ }
)
```

### Factory Usage (Recommended)
```kotlin
// The factory automatically selects the appropriate composable
ContentItemFactory(
    item = homeItem,
    layout = sectionLayout,
    onClick = { /* handle click */ }
)
```

### In SectionItem
The `SectionItem` composable now uses `ContentItemFactory` internally, so you don't need to change your existing code.

## Benefits

1. **Type Safety**: Each composable is strongly typed to its specific content type
2. **Layout Consistency**: Each layout has a consistent implementation across content types
3. **Maintainability**: Easy to modify specific layouts without affecting others
4. **Reusability**: Composables can be used independently or through the factory
5. **Extensibility**: Easy to add new content types or layouts

## Migration

If you were previously using `CoreCard` or `CoreCardTwoLines` directly, you can:

1. **Keep using them directly** - they still work
2. **Use specific layout composables** - for type-safe, layout-specific rendering
3. **Use ContentItemFactory** - for automatic selection (recommended)

## Preview Support

All composables include preview functions that demonstrate different layouts and content types. You can view these in Android Studio's preview pane.
