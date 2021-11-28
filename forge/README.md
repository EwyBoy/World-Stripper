# World-Stripper
![image](https://user-images.githubusercontent.com/5883716/120504697-a7229580-c3c4-11eb-89d5-9ca9a447dc3f.png)


# Info:
World Stripper is a utility mod that allows you to strip away the terrain to view the underground world generation. The mod is small and lightweight with a jar size of 50KB. Super useful if you are a pack developer and need to test world gen for your pack or you just can't seem to find the ores your looking for. Pretty much a must have tool for all map-makers, pack-makers and developers.

# Developers
To add World Stripper to your development environment you need to add the following codeblocks to your projects `build.gralde` file.

You can use https://www.cursemaven.com/ to add World Stripper to your development workspace by:
-----------------------------------
Code to add to `build.gradle`
```groovy
repositories {
    maven {
        url "https://www.cursemaven.com"
        content {
            includeGroup "curse.maven"
        }
    }
}

```
```groovy
dependencies {
    implementation fg.deobf("curse.maven:worldstripper-250603:FileID")
}
```

FileID can be found by going to https://www.curseforge.com/minecraft/mc-mods/world-stripper/files and click the build you are looking for.  
The ID is the numbers in the end of the build URL as seen in the image below:

![Capture](https://user-images.githubusercontent.com/5883716/118098823-b0779e00-b3d4-11eb-976d-f822658d63e4.PNG)

Builds can be found by clicking **_[here](https://www.curseforge.com/minecraft/mc-mods/world-stripper/files)_**!

# Credits
I want to give my friend Moze_Intel a huge shout out for creating the original strippermod_4.20 back in Minecraft 1.6 / 1.7
