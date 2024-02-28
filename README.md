# Cynops Logger

## Usage

```kotlin
val logger = Logger { ... }

logger.i("tag") { "message" }

val log = logger.tag("Tag")

log.i("message")

log.w { "message" }
```
