package net.runelite.client.plugins.myplugin;

import net.runelite.api.Client;
import net.runelite.api.Item;
import net.runelite.api.MenuAction;
import net.runelite.api.widgets.Widget;
import net.runelite.api.widgets.WidgetInfo;
import net.runelite.client.game.ItemManager;
import net.runelite.client.game.ItemStats;
import net.runelite.client.config.ConfigManager;


//import javax.inject.Inject;
import com.google.inject.Inject;
import java.util.ArrayList;
import java.util.List;


public class InventoryHelper {

    private final Client client;
    private final ItemManager itemManager;


    private final MyConfig config;

    @Inject
    public InventoryHelper(Client client, ItemManager itemManager, MyConfig config) {
        this.client = client;
        this.itemManager = itemManager;
        this.config = config;
    }



    /**
     * Retrieves all items in the player's inventory.
     *
     * @return A list of items currently in the inventory.
     */
    public List<Item> getInventoryItems() {
        List<Item> inventoryItems = new ArrayList<>();

        // Get the inventory widget
        Widget inventoryWidget = client.getWidget(WidgetInfo.INVENTORY);
        if (inventoryWidget != null && inventoryWidget.getChildren() != null) {
            for (Widget itemWidget : inventoryWidget.getChildren()) {
                if (itemWidget != null && itemWidget.getItemId() > 0) {
                    // Add items to the list with their ID and quantity
                    inventoryItems.add(new Item(itemWidget.getItemId(), itemWidget.getItemQuantity()));
                }
            }
        }
        return inventoryItems;
    }

    /**
     * Retrieves detailed stats for an item.
     *
     * @param itemId The ID of the item to get stats for.
     * @return An ItemStats object containing the item's stats, or null if not available.
     */
    public ItemStats getItemStats(int itemId) {
        // Use the non-deprecated `getItemStats(int itemId)` method
        return itemManager.getItemStats(itemId);
    }

    public List<String> getAvailableSpecWeapons() {
        List<String> specWeapons = new ArrayList<>();
        List<Item> inventoryItems = getInventoryItems();

        for (Item item : inventoryItems) {
            String itemName = itemManager.getItemComposition(item.getId()).getName();
            // Add weapons capable of special attacks (you can extend this list)
            if (itemName.equals("Abyssal Whip") || itemName.equals("Dragon Dagger") || itemName.equals("Armadyl Godsword")) {
                specWeapons.add(itemName);
            }
        }
        return specWeapons;
    }



    public boolean wieldItem(int itemId) {
        Widget inventoryWidget = client.getWidget(WidgetInfo.INVENTORY);

        if (inventoryWidget == null || inventoryWidget.getChildren() == null) {
            return false; // Inventory widget not available
        }

        for (Widget itemWidget : inventoryWidget.getChildren()) {
            if (itemWidget != null && itemWidget.getItemId() == itemId) {
                // Simulate the "Wield" menu action for the item
                client.menuAction(
                        itemWidget.getIndex(),                       // p0: Index of the item in the inventory
                        WidgetInfo.INVENTORY.getId(),                // p1: Widget ID for the inventory
                        MenuAction.WIDGET_TARGET,                   // action: MenuAction type for interacting with widgets
                        itemWidget.getId(),                         // id: Widget ID for the specific item
                        itemId,                                     // itemId: The ID of the item being interacted with
                        "Wield",                                    // option: The action to perform (e.g., "Wield")
                        itemManager.getItemComposition(itemId).getName() // target: The item's name
                );
                return true; // Successfully queued the wield action
            }
        }

        return false; // Item not found in inventory
    }



    /**
     * Prints stats for all equippable items in the inventory.
     */
    public void printInventoryItemStats() {
        List<Item> inventoryItems = getInventoryItems();

        for (Item item : inventoryItems) {
            ItemStats stats = getItemStats(item.getId());
            if (stats != null && stats.getEquipment() != null) {
                System.out.println("Item: " + itemManager.getItemComposition(item.getId()).getName());
                System.out.println("  Stab: " + stats.getEquipment().getAstab());
                System.out.println("  Slash: " + stats.getEquipment().getAslash());
                System.out.println("  Crush: " + stats.getEquipment().getAcrush());
                System.out.println("  Magic: " + stats.getEquipment().getAmagic());
                System.out.println("  Ranged: " + stats.getEquipment().getArange());
            } else {
                System.out.println("No stats available for item ID: " + item.getId());
            }
        }
    }

    /**
     * Represents an inventory item with stats.
     */
    public static class InventoryItem {
        private final String name;
        private final int stab;
        private final int slash;
        private final int crush;
        private final int magic;
        private final int ranged;

        public InventoryItem(String name, int stab, int slash, int crush, int magic, int ranged) {
            this.name = name;
            this.stab = stab;
            this.slash = slash;
            this.crush = crush;
            this.magic = magic;
            this.ranged = ranged;
        }

        @Override
        public String toString() {
            return String.format(
                    "Item: %s\n  Stab: %d\n  Slash: %d\n  Crush: %d\n  Magic: %d\n  Ranged: %d",
                    name, stab, slash, crush, magic, ranged
            );
        }
    }
}
