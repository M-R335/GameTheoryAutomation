
# GameTheoryAutomation Internal RuneLite Plugin

## Overview
This is a custom RuneLite internal plugin that leverages **Game Theory** to make optimal combat decisions during PvP scenarios. The plugin is designed to:

- **Perfectly execute attacks, eats, and switches** based on available combat options.
- Gather opponent gear and calculate the **best openings** with statistical probabilities for securing a confirmed kill.
- Fully customizable to work with any melee combat setup.
- Include quality-of-life enhancements like **keybind settings** and **right-click weapon menu options**.

This project contains **over 1,500 lines of code** and will be working to optimize and reduce this number in my free time and focuses on creating seamless automation while remaining fully configurable for personal preference.

## Features

### 1. **Game Theory Integration**
- Analyzes real-time combat data.
- Determines the most optimal moves (e.g., spec, eat, or attack) based on calculated probabilities for securing a successful outcome.
- Considers the opponent's gear data and predicts statistical kill chances.

### 2. **Customizable Combat Settings**
You can customize combat setups directly within the plugin interface:

- **Primary Weapon**
- **Primary Spec Weapon**
- **Secondary Spec Weapon**
- **Primary Shield**
- Keybind for spec attacks.

> Example Settings:
> - Primary Weapon: Abyssal Tentacle
> - Primary Shield: Toktz-ket-xil
> - Primary Spec Weapon: Armadyl Godsword
> - Secondary Spec Weapon: Granite Maul

![Customizable Settings](assets/Screenshot-2024-12-17-092940.png)

### 3. **Keybind Support**
- Custom **keybind settings** allow you to trigger special attacks with a personal hotkey.
- Current Keybind: `Double Space` for spec weapon activation.

![Keybind Options](assets/Screenshot-2024-12-17-092932.png)

### 4. **Right-Click Weapon Menu Options**
Added right-click menu options for weapons:

- **Set as Primary Spec Weapon**
- **Set as Secondary Spec Weapon**
- **Set as Primary Weapon or Shield**

![Right-Click Menu](assets/Screenshot-2024-12-17-094155.png)

### 5. **Automated Execution**
- The plugin will automatically:
  - Execute special attacks.
  - Time eating and healing perfectly.
  - Switch between primary and spec weapons efficiently.

Example in action:
-Fully done based on game engines tick of 600ms so each hit is perfect
![Auto Execution](assets/runescapeAuto.gif)

## Ethical Notes
- **Removed Files**: `MyPlugin.java` and `MyPluginOverlay.java` were excluded as they are against both game rules and RuneLite's policies.
- The plugin was designed for research purposes and should not be used for rule-breaking activities.

## Value
Implementations like these, while not directly sold or distributed, are valued highly in online markets due to their advanced automation features and statistical optimization. Some producing high income due to game popularity
