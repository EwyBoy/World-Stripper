# Changelog

## 6.0.0 (Minecraft 26.3 - Fabric, NeoForge)

First release for Minecraft 26.3, with everything from 6.0.0 for 1.21 below. Forge is no longer built for 26.x.
On 26.3 the strip/dress benchmark gives the same results as on 1.21 (r=24: strip ~160 ms, r=64: ~0.7 s on the server thread),
and Fabric and NeoForge produce identical stripped worlds.

## 6.0.0 (Minecraft 1.21 / 1.21.1 - Fabric, NeoForge, Forge)

### Faster stripping
Measured on a dedicated server, seed `worldstripper`, default strip list, Y -64..256
(time on the server thread; lighting continues on the light thread):

| Radius (blocks) | 5.0.1 strip | 6.0.0 strip | 5.0.1 dress | 6.0.0 dress | 5.0.1 undo memory | 6.0.0 undo memory |
|---|---|---|---|---|---|---|
| 24 (default, 49x49) | 730 ms | ~130-170 ms | 471 ms | ~95 ms | 20 MB | < 1 MB |
| 64 | 5.1 s | ~0.8 s | 9.4 s | ~0.4 s | 161 MB | ~3 MB |
| 128 | 26.2 s | ~3.2 s | 76 s | ~1.7 s | 646 MB (3.7 GB peak) | ~7 MB |
| 256 | out of memory | 15.6 s | - | 7.3 s | - | 22 MB |

- The strip box is walked one chunk section at a time; sections whose palette holds nothing strippable are skipped.
- Blocks are written straight into the chunk sections (heightmaps, lighting, block entities, POIs and client updates are still handled).
- Light checks go to the light engine in one batch per section.
- Undo data is a compact palette copy per section instead of a map entry per block.

### Fixes
- Dedicated servers: pressing Page Up / Page Down no longer crashes the server (#26).
- Forge (1.21): client-only event handlers are no longer loaded on dedicated servers (#26).
- The mod's network channel is optional: a client with World Stripper can join servers without it, and vice versa (#26).
- Keybinds have readable names again under "World Stripper" in Controls (#31).
- New `/worldstripper strip [radius]` and `/worldstripper dress [radius]` commands (#31).
- Mod icon is back (#33).
- Dressing restores the stripped blocks exactly: gravel and sand no longer fall into caves, fluids no longer start flowing.
- Undo data is kept per dimension and cleared when the server stops (dressing in another dimension or world used to paste the wrong blocks).
- `/worldstripper edit` works (it always failed with "No such argument").
- Default strip list: `dirt_path` instead of the old `grass_path`, removed the non-existent `flowing_water`/`flowing_lava` and a duplicate, added mangrove, cherry and azalea logs/leaves. Existing `strippables.json` files are not changed.
- No more empty `worldstripper` folder in the game directory.
- Errors while reading/writing the config are logged with their stack trace.

### Notes
- With the default block-update settings, mobs that are already pathing through the stripped area don't re-plan their path immediately.
