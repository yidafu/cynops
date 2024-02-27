# Cynops Logger

## Usage

```kotlin
val logger = Logger { ... }

val log = logger.tag("Tag")

log.i("message")

log.w { "message" }
```
