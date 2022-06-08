/*
 * ArmorStandEditor: Bukkit plugin to allow editing armor stand attributes
 * Copyright (C) 2016  RypoFalem
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

import io.github.rypofalem.armorstandeditor.PlayerEditor;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.ArmorStand;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import static net.kyori.adventure.text.Component.text;

import java.util.ArrayList;

public final class EquipmentMenu {
    Inventory menuInv;
    private PlayerEditor pe;
    private ArmorStand armorstand;
    static String name = "ArmorStand Equipment";
    ItemStack helmet;
    ItemStack chest;
    ItemStack pants;
    ItemStack feetsies;
    ItemStack rightHand;
    ItemStack leftHand;

    public EquipmentMenu(final PlayerEditor pe, final ArmorStand as) {
        this.pe = pe;
        this.armorstand = as;
        name = pe.plugin.getLang().getMessage("equiptitle", "menutitle");
        menuInv = Bukkit.createInventory(pe.getManager().getEquipmentHolder(), 18, text(name));
    }

    private void fillInventory() {
        menuInv.clear();
        EntityEquipment equipment = armorstand.getEquipment();
        assert equipment != null;
        ItemStack theHelmet = equipment.getHelmet();
        ItemStack theChest = equipment.getChestplate();
        ItemStack thePants = equipment.getLeggings();
        ItemStack theFeetsies = equipment.getBoots();
        ItemStack theRightHand = equipment.getItemInMainHand();
        ItemStack theLeftHand = equipment.getItemInOffHand();
        equipment.clear();

        ItemStack disabledIcon = new ItemStack(Material.BARRIER);
        ItemMeta meta = disabledIcon.getItemMeta();
        meta.displayName(text(pe.plugin.getLang().getMessage("disabled", "warn"))); //equipslot.msg <option>
        meta.getPersistentDataContainer().set(pe.plugin.getIconKey(), PersistentDataType.STRING, "ase icon"); // mark as icon
        disabledIcon.setItemMeta(meta);

        ItemStack helmetIcon = createIcon(Material.LEATHER_HELMET, "helm");
        ItemStack chestIcon = createIcon(Material.LEATHER_CHESTPLATE, "chest");
        ItemStack pantsIcon = createIcon(Material.LEATHER_LEGGINGS, "pants");
        ItemStack feetsiesIcon = createIcon(Material.LEATHER_BOOTS, "boots");
        ItemStack rightHandIcon = createIcon(Material.WOODEN_SWORD, "rhand");
        ItemStack leftHandIcon = createIcon(Material.SHIELD, "lhand");
        ItemStack[] items = {
            helmetIcon, chestIcon, pantsIcon, feetsiesIcon, rightHandIcon, leftHandIcon, disabledIcon, disabledIcon, disabledIcon,
            theHelmet, theChest, thePants, theFeetsies, theRightHand, theLeftHand, disabledIcon, disabledIcon, disabledIcon
        };
        menuInv.setContents(items);
    }

    private ItemStack createIcon(Material mat, String slot) {
        ItemStack icon = new ItemStack(mat);
        ItemMeta meta = icon.getItemMeta();
        meta.getPersistentDataContainer().set(pe.plugin.getIconKey(), PersistentDataType.STRING, "ase icon");
        meta.displayName(text(pe.plugin.getLang().getMessage("equipslot", "iconname", slot))); //equipslot.msg <option>
        ArrayList<Component> loreList = new ArrayList<>();
        loreList.add(text(pe.plugin.getLang().getMessage("equipslot.description", "icondescription", slot))); //equioslot.description.msg <option>
        meta.lore(loreList);
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        meta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
        icon.setItemMeta(meta);
        return icon;
    }

    public void open() {
        fillInventory();
        pe.getPlayer().openInventory(menuInv);
    }

    public void equipArmorstand() {
        helmet = menuInv.getItem(9);
        chest = menuInv.getItem(10);
        pants = menuInv.getItem(11);
        feetsies = menuInv.getItem(12);
        rightHand = menuInv.getItem(13);
        leftHand = menuInv.getItem(14);
        armorstand.getEquipment().setHelmet(helmet);
        armorstand.getEquipment().setChestplate(chest);
        armorstand.getEquipment().setLeggings(pants);
        armorstand.getEquipment().setBoots(feetsies);
        armorstand.getEquipment().setItemInMainHand(rightHand);
        armorstand.getEquipment().setItemInOffHand(leftHand);
    }

    public static String getName() {
        return name;
    }
}
