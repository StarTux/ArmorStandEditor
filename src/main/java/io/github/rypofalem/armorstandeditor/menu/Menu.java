/*
 * ArmorStandEditor: Bukkit plugin to allow editing armor stand attributes
 * Copyright (C) 2016-2021 RypoFalem
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */

package io.github.rypofalem.armorstandeditor.menu;

import com.cavetale.core.font.GuiOverlay;
import com.cavetale.mytems.Mytems;
import io.github.rypofalem.armorstandeditor.ArmorStandEditorPlugin;
import io.github.rypofalem.armorstandeditor.PlayerEditor;
import java.util.ArrayList;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import static net.kyori.adventure.text.Component.text;
import static net.kyori.adventure.text.format.NamedTextColor.*;

public final class Menu {
    private Inventory menuInv;
    private PlayerEditor pe;
    private static String name = "Armor Stand Editor Menu";

    public Menu(final PlayerEditor pe) {
        this.pe = pe;
        fillInventory();
    }

    private void fillInventory() {
        ItemStack xAxis = null;
        ItemStack yAxis = null;
        ItemStack zAxis = null;
        ItemStack coarseAdj = null;
        ItemStack fineAdj = null;
        ItemStack rotate  = null;
        ItemStack place  = null;
        ItemStack headPos = null;
        ItemStack rightArmPos = null;
        ItemStack bodyPos = null;
        ItemStack leftArmPos = null;
        ItemStack reset  = null;
        ItemStack showArms = null;
        ItemStack visibility = null;
        ItemStack size = null;
        ItemStack rightLegPos = null;
        ItemStack equipment  = null;
        ItemStack leftLegPos = null;
        ItemStack disableSlots  = null;
        ItemStack gravity = null;
        ItemStack plate = null;
        ItemStack copy = null;
        ItemStack paste = null;
        ItemStack slot1 = null;
        ItemStack slot2 = null;
        ItemStack slot3 = null;
        ItemStack slot4 = null;
        ItemStack help = null;
        xAxis = createIcon(Mytems.LETTER_X.createIcon(),
                           "xaxis", "axis x");
        yAxis = createIcon(Mytems.LETTER_Y.createIcon(),
                           "yaxis", "axis y");
        zAxis = createIcon(Mytems.LETTER_Z.createIcon(),
                           "zaxis", "axis z");
        coarseAdj = createIcon(new ItemStack(Material.COARSE_DIRT, 1),
                               "coarseadj", "adj coarse");
        fineAdj = createIcon(new ItemStack(Material.SANDSTONE),
                             "fineadj", "adj fine");
        reset = createIcon(Mytems.REDO.createIcon(),
                           "reset", "mode reset");
        headPos = createIcon(new ItemStack(Material.LEATHER_HELMET),
                             "head", "mode head");
        bodyPos = createIcon(new ItemStack(Material.LEATHER_CHESTPLATE),
                             "body", "mode body");
        leftLegPos = createIcon(new ItemStack(Material.LEATHER_LEGGINGS),
                                "leftleg", "mode leftleg");
        rightLegPos = createIcon(new ItemStack(Material.LEATHER_LEGGINGS),
                                 "rightleg", "mode rightleg");
        leftArmPos = createIcon(new ItemStack(Material.STICK),
                                "leftarm", "mode leftarm");
        rightArmPos = createIcon(new ItemStack(Material.STICK),
                                 "rightarm", "mode rightarm");
        showArms = createIcon(new ItemStack(Material.STICK),
                              "showarms", "mode showarms");
        if (pe.getPlayer().hasPermission("asedit.invisible")) {
            visibility = new ItemStack(Material.POTION, 1);
            PotionMeta potionMeta = (PotionMeta) visibility.getItemMeta();
            PotionEffect eff1 = new PotionEffect(PotionEffectType.INVISIBILITY, 1, 0);
            potionMeta.addCustomEffect(eff1, true);
            visibility.setItemMeta(potionMeta);
            visibility = createIcon(visibility, "invisible", "mode invisible");
        }
        size = createIcon(new ItemStack(Material.PUFFERFISH, 1),
                          "size", "mode size");
        if (pe.getPlayer().hasPermission("asedit.disableslots")) {
            disableSlots = createIcon(new ItemStack(Material.BARRIER), "disableslots", "mode disableslots");
        }
        gravity = createIcon(new ItemStack(Material.ANVIL), "gravity", "mode gravity");
        plate = createIcon(new ItemStack(Material.STONE_PRESSURE_PLATE, 1),
                           "baseplate", "mode baseplate");
        place = createIcon(new ItemStack(Material.MINECART, 1),
                           "placement", "mode placement");
        rotate = createIcon(new ItemStack(Material.COMPASS, 1),
                            "rotate", "mode rotate");
        equipment = createIcon(new ItemStack(Material.CHEST, 1),
                               "equipment", "mode equipment");
        copy = createIcon(Mytems.MAGNET.createIcon(),
                          "copy", "mode copy");
        paste = createIcon(Mytems.BLACK_PAINTBRUSH.createIcon(),
                           "paste", "mode paste");
        slot1 = createIcon(Mytems.NUMBER_1.createIcon(),
                           "copyslot", "slot 1", "1");
        slot2 = createIcon(Mytems.NUMBER_2.createIcon(),
                           "copyslot", "slot 2", "2");
        slot3 = createIcon(Mytems.NUMBER_3.createIcon(),
                           "copyslot", "slot 3", "3");
        slot4 = createIcon(Mytems.NUMBER_4.createIcon(),
                           "copyslot", "slot 4", "4");
        help = createIcon(Mytems.QUESTION_MARK.createIcon(),
                          "helpgui", "help");
        ItemStack[] items = new ItemStack[] {
            null, xAxis, yAxis, zAxis, null, coarseAdj, fineAdj, null, help,
            null, null, headPos, null, null, rotate, place, null, null,
            null, rightArmPos, bodyPos, leftArmPos, null, showArms, plate, equipment, null,
            null, rightLegPos, null, leftLegPos, null, visibility, size, gravity, null,
            null, null, reset, null, null, null, null, null, null,
            slot1, slot2, slot3, slot4, null, null, null, copy, paste,
        };
        final int invSize = 6 * 9;
        GuiOverlay.Builder titleBuilder = GuiOverlay.BLANK.builder(invSize, TextColor.color(0xFFC0CB))
            .layer(GuiOverlay.TOP_BAR, GRAY)
            .title(text("Armor Stand Editor", BLACK));
        switch (pe.getEMode()) {
        case NONE: break;
        case INVISIBLE: titleBuilder.highlightSlot(indexOf(items, visibility), WHITE); break;
        case SHOWARMS: titleBuilder.highlightSlot(indexOf(items, showArms), WHITE); break;
        case GRAVITY: titleBuilder.highlightSlot(indexOf(items, gravity), WHITE); break;
        case BASEPLATE: titleBuilder.highlightSlot(indexOf(items, plate), WHITE); break;
        case SIZE: titleBuilder.highlightSlot(indexOf(items, size), WHITE); break;
        case COPY: titleBuilder.highlightSlot(indexOf(items, copy), WHITE); break;
        case PASTE: titleBuilder.highlightSlot(indexOf(items, paste), WHITE); break;
        case HEAD: titleBuilder.highlightSlot(indexOf(items, headPos), WHITE); break;
        case BODY: titleBuilder.highlightSlot(indexOf(items, bodyPos), WHITE); break;
        case LEFTARM: titleBuilder.highlightSlot(indexOf(items, leftArmPos), WHITE); break;
        case RIGHTARM: titleBuilder.highlightSlot(indexOf(items, rightArmPos), WHITE); break;
        case LEFTLEG: titleBuilder.highlightSlot(indexOf(items, leftLegPos), WHITE); break;
        case RIGHTLEG: titleBuilder.highlightSlot(indexOf(items, rightLegPos), WHITE); break;
        case PLACEMENT: titleBuilder.highlightSlot(indexOf(items, place), WHITE); break;
        case DISABLESLOTS: titleBuilder.highlightSlot(indexOf(items, disableSlots), WHITE); break;
        case ROTATE: titleBuilder.highlightSlot(indexOf(items, rotate), WHITE); break;
        case EQUIPMENT: titleBuilder.highlightSlot(indexOf(items, equipment), WHITE); break;
        default: break;
        }
        switch (pe.getAxis()) {
        case X: titleBuilder.highlightSlot(indexOf(items, xAxis), AQUA); break;
        case Y: titleBuilder.highlightSlot(indexOf(items, yAxis), AQUA); break;
        case Z: titleBuilder.highlightSlot(indexOf(items, zAxis), AQUA); break;
        default: break;
        }
        switch (pe.getAdjMode()) {
        case COARSE: titleBuilder.highlightSlot(indexOf(items, coarseAdj), AQUA); break;
        case FINE: titleBuilder.highlightSlot(indexOf(items, fineAdj), AQUA); break;
        default: break;
        }
        menuInv = Bukkit.createInventory(pe.getManager().getMenuHolder(), invSize, titleBuilder.build());
        menuInv.setContents(items);
    }

    private static int indexOf(ItemStack[] array, ItemStack item) {
        for (int i = 0; i < array.length; i += 1) {
            if (array[i] == item) return i;
        }
        return -1;
    }

    private ItemStack createIcon(ItemStack icon, String path, String command) {
        return createIcon(icon, path, command, null);
    }

    private ItemStack createIcon(ItemStack icon, String path, String command, String option) {
        ItemMeta meta = icon.getItemMeta();
        meta.getPersistentDataContainer().set(ArmorStandEditorPlugin.instance().getIconKey(), PersistentDataType.STRING, "ase " + command);
        meta.displayName(text(getIconName(path, option)));
        ArrayList<Component> loreList = new ArrayList<>();
        loreList.add(text(getIconDescription(path, option)));
        meta.lore(loreList);
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        meta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
        icon.setItemMeta(meta);
        return icon;
    }

    private String getIconName(String path) {
        return getIconName(path, null);
    }

    private String getIconName(String path, String option) {
        return pe.plugin.getLang().getMessage(path, "iconname", option);
    }

    private String getIconDescription(String path) {
        return getIconDescription(path, null);
    }

    private String getIconDescription(String path, String option) {
        return pe.plugin.getLang().getMessage(path + ".description", "icondescription", option);
    }

    public void openMenu() {
        Player player = pe.getPlayer();
        if (player.hasPermission("asedit.basic")) {
            fillInventory();
            player.openInventory(menuInv);
        }
    }

    public static String getName() {
        return name;
    }
}
