package net.runelite.client.plugins.myplugin;

import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;

@ConfigGroup("myplugin")
public interface MyConfig extends Config {

    // Checkbox for Double Eat
    @ConfigItem(
            keyName = "enableDoubleEat",
            name = "Enable Double Eat",
            description = "Enable double eat mode (cannot be on with triple eat).",
            position = 1
    )
    default boolean enableDoubleEat() {
        return false;
    }

    // Checkbox for Triple Eat
    @ConfigItem(
            keyName = "enableTripleEat",
            name = "Enable Triple Eat",
            description = "Enable triple eat mode (cannot be on with double eat).",
            position = 2
    )
    default boolean enableTripleEat() {
        return false;
    }
    //single spec
    @ConfigItem(
            keyName = "Single Spec Only",
            name = "Enable single spec weapon",
            description = "specs once per call ",
            position = 2
    )
    default boolean enableSingleSpec() {
        return false;
    }
    //double spec
    @ConfigItem(
            keyName = "Double Spec",
            name = "Enable double spec",
            description = "specs back to back per call",
            position = 2
    )
    default boolean enableDoubleSpec() {
        return false;
    }

    // Checkbox for Auto Spec
    @ConfigItem(
            keyName = "enableAutoSpec",
            name = "Enable Auto Spec",
            description = "Enable automatic special attack (cannot be on with keybind spec).",
            position = 3
    )
    default boolean enableAutoSpec() {
        return false;
    }

    // Checkbox for Keybind Spec
    @ConfigItem(
            keyName = "KeybindSpec",
            name = "Enable Keybind Spec",
            description = "Enable keybind-triggered special attack (cannot be on with auto spec).",
            position = 4
    )
    default boolean enableKeybindSpec() {
        return false;
    }

    // Keybind for Spec Attack
    @ConfigItem(
            keyName = "keybindSpecKey",
            name = "Spec Keybind",
            description = "Select the key for triggering the special attack (available only if Keybind Spec is enabled).",
            position = 5
    )
    default SpecKeybind keybindSpecKey() {
        return SpecKeybind.SPACE; // Default to F1
    }

    // Primary Weapon Slot
    @ConfigItem(
            keyName = "primaryWeaponSlot",
            name = "Primary Weapon",
            description = "The currently selected primary weapon.",
            position = 6
    )
    default String primaryWeaponSlot() {
        return "";
    }

    // Primary Shield Slot
    @ConfigItem(
            keyName = "primaryShieldSlot",
            name = "Primary Shield",
            description = "The currently selected primary shield.",
            position = 7
    )
    default String primaryShieldSlot() {
        return "";
    }

    // Primary Spec Weapon
    @ConfigItem(
            keyName = "primarySpecWeapon",
            name = "Primary Spec Weapon",
            description = "The currently selected primary spec weapon.",
            position = 8
    )
    default String primarySpecWeapon() {
        return "";
    }

    // Secondary Spec Weapon
    @ConfigItem(
            keyName = "secondarySpecWeapon",
            name = "Secondary Spec Weapon",
            description = "The currently selected secondary spec weapon.",
            position = 9
    )
    default String secondarySpecWeapon() {
        return "";
    }

    // Enum for Keybind Selection
    enum SpecKeybind {
        SPACE("Spacebar"),
        DOUBLE_SPACE("Double Space"),
        F1("F1"),
        F2("F2"),
        F3("F3"),
        F4("F4"),
        F5("F5"),
        F6("F6"),
        F7("F7"),
        F8("F8"),
        F9("F9"),
        F10("F10"),
        F11("F11"),
        F12("F12"),
        ONE("1"),
        TWO("2"),
        THREE("3"),
        FOUR("4"),
        FIVE("5"),
        SIX("6"),
        SEVEN("7"),
        EIGHT("8"),
        NINE("9"),
        TEN("10");

        private final String displayName;

        SpecKeybind(String displayName) {
            this.displayName = displayName;
        }

        @Override
        public String toString() {
            return displayName;
        }
    }
}
