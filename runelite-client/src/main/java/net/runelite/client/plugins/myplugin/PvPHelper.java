package net.runelite.client.plugins.myplugin;

import net.runelite.api.*;
import net.runelite.api.coords.WorldPoint;
import net.runelite.api.events.InteractingChanged;
import net.runelite.api.widgets.Widget;
import net.runelite.api.widgets.WidgetInfo;

//import javax.inject.Inject;
import com.google.inject.Inject;

import java.util.*;
import java.util.stream.Collectors;

import net.runelite.api.Client;
import net.runelite.api.MenuAction;

import java.util.function.Consumer;

import net.runelite.api.*;
import net.runelite.api.widgets.Widget;
import net.runelite.api.widgets.WidgetInfo;
import net.runelite.cache.definitions.ItemDefinition;
import net.runelite.client.game.ItemManager;
import net.runelite.client.plugins.Plugin;



import java.util.List;
import java.util.stream.Collectors;


public class PvPHelper {

    private final Client client;

    // PvP state
    private Player targetPlayer;
    private boolean isInPvPArea;
    Player myPlayer;
    Actor opponent;

    @Inject
    private ItemManager itemManager;

    @Inject
    public PvPHelper(Client client) {
        this.client = client;
        this.itemManager = itemManager;
    }

    // New: Print Opponent Equipment
    public void printOpponentEquipment() {
        if (opponent instanceof Player) {
            Player playerOpponent = (Player) opponent;

            // Get the equipment IDs from the opponent's PlayerComposition
            int[] equipmentIds = playerOpponent.getPlayerComposition().getEquipmentIds();

            if (equipmentIds != null) {
                StringBuilder gearInfo = new StringBuilder("Opponent's Gear:\n");

                for (int i = 0; i < equipmentIds.length; i++) {
                    int itemId = equipmentIds[i];

                    // Skip if the item ID is -1 (indicates no item equipped in that slot)
                    if (itemId == -1) {
                        continue;
                    }

                    // Remove extra metadata from item ID (if present)
                    int actualItemId = itemId >= 512 ? itemId - 512 : itemId;

                    // Get the item's name
                    String itemName = itemManager.getItemComposition(actualItemId).getName();

                    // Append the slot name and item name to the output
                    gearInfo.append(slotName(i)).append(": ").append(itemName).append("\n");
                }

                // Log or handle the gear information
                System.out.println(gearInfo.toString());
            }
        }
    }


    // New: Map slot indices to equipment slot names
    private String slotName(int slot) {
        switch (slot) {
            case 0: return "Head";
            case 1: return "Cape";
            case 2: return "Amulet";
            case 3: return "Weapon";
            case 4: return "Body";
            case 5: return "Shield";
            case 7: return "Legs"; // Slot 6 is sometimes unused
            case 9: return "Gloves"; // Slot 8 is sometimes unused
            case 10: return "Boots";
            case 12: return "Ring"; // Slot 11 is sometimes unused
            case 13: return "Ammo";
            default: return "Unknown Slot";
        }
    }

    // Hook into existing event: Handle opponent changes
    public void handleInteractingChanged(InteractingChanged event) {
        if (event.getSource() == client.getLocalPlayer()) {
            opponent = event.getTarget();
        }
    }

    public Actor getOppenent() {
        return opponent;
    }

    // Hook into existing event: On Game Tick
    public void handleGameTick() {
        if (opponent != null) {
            printOpponentEquipment();
        }
    }

    public int getPlayerPrayer(){

        return client.getBoostedSkillLevel(Skill.PRAYER);
    }
    public int getPlayerHP(){
        return client.getBoostedSkillLevel(Skill.HITPOINTS);
    }


    public boolean isInPvPArea() {
        return isInPvPArea;
    }

    public void setInPvPArea(boolean inPvPArea) {
        isInPvPArea = inPvPArea;
    }

    /**
     * Determines if a player is in the PvP combat bracket.
     *
     * @param player The player to check.
     * @return True if the player is in the bracket, false otherwise.
     */
    public boolean isInPvPBracket(Player player) {
        Player myPlayer = client.getLocalPlayer();
        if (myPlayer == null || player == null) {
            return false; // Can't determine bracket without players
        }

        int myCombatLevel = myPlayer.getCombatLevel();
        int otherCombatLevel = player.getCombatLevel();

        // Determine if both players are within each other's PvP bracket
        return Math.abs(myCombatLevel - otherCombatLevel) <= 15;
    }










    /**
     * Follow a player by name.
     */
    public void followPlayerByName(String playerName) {
        Player player = findPlayerByName(playerName);
        if (player != null) {
            followPlayer(player);
        }
    }

    /**
     * Attack a player by name.
     */
    public void attackPlayerByName(String playerName) {
        Player player = findPlayerByName(playerName);
        if (player != null) {
            attackPlayer(player);
        }
    }

    /**
     * Follow a specific player.
     */
    public void followPlayer(Player player) {
        if (player != null) {
            createAndSetMenuEntry("Follow", player, MenuAction.PLAYER_SECOND_OPTION);
        }
    }

    /**
     * Attack a specific player.
     */
    public void attackPlayer(Player player) {
        if (player != null) {
            createAndSetMenuEntry("Attack", player, MenuAction.PLAYER_FIRST_OPTION);
        }
    }

    /**
     * Find a player by name.
     */
    public Player findPlayerByName(String playerName) {
        return client.getPlayers().stream()
                .filter(player -> player.getName().equalsIgnoreCase(playerName))
                .findFirst()
                .orElse(null);
    }

    /**
     * Get a list of nearby players.
     */
    public List<Player> getNearbyPlayers() {
        WorldPoint localPoint = client.getLocalPlayer().getWorldLocation();
        return client.getPlayers().stream()
                .filter(player -> player.getWorldLocation().distanceTo(localPoint) < 15) // Example range
                .collect(Collectors.toList());
    }

    /**
     * Get a list of nearby NPCs.
     */
    public List<NPC> getNearbyNPCs() {
        WorldPoint localPoint = client.getLocalPlayer().getWorldLocation();
        return client.getNpcs().stream()
                .filter(npc -> npc.getWorldLocation().distanceTo(localPoint) < 15) // Example range
                .collect(Collectors.toList());
    }

    /**
     * Check if the player is in a PvP area.
     */
    public boolean checkIfInPvPArea() {
        // Use the IN_WILDERNESS varbit to determine if the player is in the Wilderness
        int inWilderness = client.getVarbitValue(Varbits.IN_WILDERNESS);
        return inWilderness == 1;
    }


    /**
     * Utility to create and set a menu entry for interaction.
     */
    private void createAndSetMenuEntry(String option, Player player, MenuAction action) {
        MenuEntry entry = client.createMenuEntry(-1);
        entry.setOption(option);
        entry.setTarget("<col=ffffff>" + player.getName());
        entry.setIdentifier(player.getId());
        entry.setType(action);
        entry.setParam0(0);
        entry.setParam1(0);
        client.setMenuEntries(new MenuEntry[]{entry});
    }



    /**
     * Applies the given action to each element in the collection.
     *
     * @param <T> The type of elements in the collection.
     * @param collection The collection to iterate over.
     * @param action The action to perform on each element.
     */
    public static <T> void each(Collection<T> collection, Consumer<T> action) {
        if (collection == null || action == null) {
            throw new IllegalArgumentException("Collection and action cannot be null.");
        }

        for (T element : collection) {
            action.accept(element);
        }
    }

    public boolean PlayerINPVP() {
        // Define the PvP and High-Risk worlds using a HashSet
        Set<Integer> pvpAndHighRiskWorlds = new HashSet<>();

        // PvP Worlds
        pvpAndHighRiskWorlds.add(308); // Wilderness PK - Free
        pvpAndHighRiskWorlds.add(316); // Wilderness PK - Free
        pvpAndHighRiskWorlds.add(369); // Wilderness PK - Members
        pvpAndHighRiskWorlds.add(392); // PvP World
        pvpAndHighRiskWorlds.add(539); // PvP World
        pvpAndHighRiskWorlds.add(548); // PvP World - High Risk
        pvpAndHighRiskWorlds.add(559); // LMS Competitive
        pvpAndHighRiskWorlds.add(577); // PvP World - Free
        pvpAndHighRiskWorlds.add(560); // PvP World (Rotation B)
        pvpAndHighRiskWorlds.add(561); // PvP World - Free (Rotation B)
        pvpAndHighRiskWorlds.add(579); // PvP World - High Risk (Rotation B)

        boolean wild = this.isInPvPArea;
        return (wild || pvpAndHighRiskWorlds.contains(client.getWorld()));

    }





}
