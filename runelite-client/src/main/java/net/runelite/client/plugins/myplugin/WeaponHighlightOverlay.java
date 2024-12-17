package net.runelite.client.plugins.myplugin;

import net.runelite.api.Client;
import net.runelite.api.Item;
import net.runelite.api.widgets.Widget;
import net.runelite.api.widgets.WidgetInfo;
import net.runelite.client.ui.overlay.Overlay;
import net.runelite.client.ui.overlay.OverlayLayer;
import net.runelite.client.ui.overlay.OverlayPosition;

import javax.inject.Inject;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class WeaponHighlightOverlay extends Overlay {

    private final Client client;
    private final MyConfig config;

    // Catppuccin-inspired colors
    private static final Color CATPPUCCIN_GREEN = new Color(166, 227, 161, 255);  // Primary Weapon
    private static final Color CATPPUCCIN_YELLOW = new Color(249, 226, 175, 255); // Shield
    private static final Color CATPPUCCIN_MAUVE = new Color(203, 166, 247, 255);  // Primary Spec
    private static final Color CATPPUCCIN_BLUE = new Color(137, 220, 235, 255);   // Secondary Spec

    @Inject
    public WeaponHighlightOverlay(Client client, MyConfig config) {
        this.client = client;
        this.config = config;
        setPosition(OverlayPosition.DYNAMIC);
        setLayer(OverlayLayer.ABOVE_WIDGETS);
    }

    @Override
    public Dimension render(Graphics2D graphics) {
        var inventoryWidget = client.getWidget(WidgetInfo.INVENTORY);
        if (inventoryWidget != null && inventoryWidget.getChildren() != null) {
            for (var itemWidget : inventoryWidget.getChildren()) {
                if (itemWidget == null || itemWidget.getItemId() <= 0) continue;

                String itemName = client.getItemDefinition(itemWidget.getItemId()).getName();
                Rectangle bounds = itemWidget.getBounds();

                if (itemName.equalsIgnoreCase(config.primaryWeaponSlot())) {
                    drawHighlight(graphics, bounds, CATPPUCCIN_GREEN);
                } else if (itemName.equalsIgnoreCase(config.primaryShieldSlot())) {
                    drawHighlight(graphics, bounds, CATPPUCCIN_YELLOW);
                } else if (itemName.equalsIgnoreCase(config.primarySpecWeapon())) {
                    drawHighlight(graphics, bounds, CATPPUCCIN_MAUVE);
                } else if (itemName.equalsIgnoreCase(config.secondarySpecWeapon())) {
                    drawHighlight(graphics, bounds, CATPPUCCIN_BLUE);
                }
            }
        }
        //renderInventory for debugging
//        renderInventoryCoordinates(graphics);
        return null;
    }

//    private void renderInventoryCoordinates(Graphics2D graphics) {
//        // Get inventory items and their positions
//        Widget inventoryWidget = client.getWidget(WidgetInfo.INVENTORY); // Inventory widget
//
//        if (inventoryWidget != null && inventoryWidget.getDynamicChildren() != null) {
//            graphics.setFont(new Font("Arial", Font.PLAIN, 10));
//            graphics.setColor(Color.YELLOW);
//
//            for (Widget item : inventoryWidget.getDynamicChildren()) {
//
//                if (item != null && item.getItemId() > 0) {
//                    Rectangle bounds = item.getBounds();
//                    if (bounds != null) {
//                        String xCoordinate = "X: " + bounds.x;
//                        String yCoordinate = "Y: " + bounds.y;
//                        graphics.drawString(xCoordinate, bounds.x, bounds.y);
//                        graphics.drawString(yCoordinate, bounds.x, bounds.y + 12); // Adjust Y position for better visibility
//                    }
//                }
//            }
//        }
//    }
    private void drawHighlight(Graphics2D graphics, Rectangle bounds, Color color) {
        if (bounds != null) {
            graphics.setColor(color);
            graphics.setStroke(new BasicStroke(3)); // 3px border
            graphics.drawRect(bounds.x, bounds.y, bounds.width, bounds.height);
        }
    }


}
