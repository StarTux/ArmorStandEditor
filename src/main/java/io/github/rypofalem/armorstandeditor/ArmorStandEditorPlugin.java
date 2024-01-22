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

package io.github.rypofalem.armorstandeditor;

import com.cavetale.mytems.Mytems;
import io.github.rypofalem.armorstandeditor.language.Language;
import java.io.File;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public final class ArmorStandEditorPlugin extends JavaPlugin {
    private NamespacedKey iconKey;
    private static ArmorStandEditorPlugin instance;
    private CommandEx execute;
    private Language lang;
    protected PlayerEditorManager editorManager;
    protected boolean sendToActionBar = true;
    protected boolean debug = false; //weather or not to broadcast messages via print(String message)
    protected double coarseRot;
    protected double fineRot;

    public ArmorStandEditorPlugin() {
        instance = this;
    }

    @Override
    public void onEnable() {
        //saveResource doesn't accept File.seperator on windows, need to hardcode unix seperator "/" instead
        updateConfig("", "config.yml");
        updateConfig("lang/", "test_NA.yml");
        updateConfig("lang/", "nl_NL.yml");
        updateConfig("lang/", "uk_UA.yml");
        updateConfig("lang/", "zh.yml");
        updateConfig("lang/", "fr_FR.yml");
        updateConfig("lang/", "ro_RO.yml");
        updateConfig("lang/", "ja_JP.yml");
        updateConfig("lang/", "de_DE.yml");
        //English is the default language and needs to be unaltered to so that there is always a backup message string
        saveResource("lang/en_US.yml", true);
        lang = new Language(getConfig().getString("lang"), this);

        coarseRot = getConfig().getDouble("coarse");
        fineRot = getConfig().getDouble("fine");
        debug = getConfig().getBoolean("debug", true);
        sendToActionBar = getConfig().getBoolean("sendMessagesToActionBar", true);

        editorManager = new PlayerEditorManager(this);
        execute = new CommandEx(this);
        getCommand("ase").setExecutor(execute);
        getServer().getPluginManager().registerEvents(editorManager, this);
    }

    private void updateConfig(String folder, String config) {
        if (!new File(getDataFolder() + File.separator + folder + config).exists()) {
            saveResource(folder  + config, false);
        }
    }

    @Override
    public void onDisable() {
        for (Player player : Bukkit.getServer().getOnlinePlayers()) {
            if (player.getOpenInventory().getTopInventory().getHolder() == editorManager.getMenuHolder()) player.closeInventory();
        }
    }

    public static ArmorStandEditorPlugin instance() {
        return instance;
    }

    public Language getLang() {
        return lang;
    }

    public boolean isEditTool(ItemStack item) {
        if (item == null) return false;
        return Mytems.forItem(item) == Mytems.ARMOR_STAND_EDITOR;
    }

    public NamespacedKey getIconKey() {
        if (iconKey == null) iconKey = new NamespacedKey(this, "command_icon");
        return iconKey;
    }
}
