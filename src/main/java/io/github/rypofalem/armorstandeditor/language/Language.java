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

package io.github.rypofalem.armorstandeditor.language;

import io.github.rypofalem.armorstandeditor.ArmorStandEditorPlugin;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import org.bukkit.configuration.file.YamlConfiguration;
import static java.util.Objects.requireNonNull;

public final class Language {
    protected static final String DEFAULTLANG = "en_US.yml";
    private YamlConfiguration langConfig = null;
    private YamlConfiguration defConfig = null;
    private File langFile = null;
    protected final ArmorStandEditorPlugin plugin;

    public Language(final String langFileName, final ArmorStandEditorPlugin plugin) {
        this.plugin = plugin;
        reloadLang(langFileName);
    }

    public void reloadLang(String langFileName) {
        if (langFileName == null) langFileName = DEFAULTLANG;
        File langFolder = new File(plugin.getDataFolder().getPath() + File.separator + "lang");
        langFile = new File(langFolder, langFileName);
        InputStream input = requireNonNull(plugin.getResource("lang" + "/" + DEFAULTLANG));
        Reader defaultLangStream = new InputStreamReader(input, StandardCharsets.UTF_8);
        defConfig = YamlConfiguration.loadConfiguration(defaultLangStream);
        input = null;
        try {
            input = new FileInputStream(langFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return;
        }
        Reader langStream = new InputStreamReader(input, StandardCharsets.UTF_8);
        langConfig = YamlConfiguration.loadConfiguration(langStream);
    }

    //path: yml path to message in language file
    //format: yml path to format in language file
    //option: path-specific variable that may be used
    public String getMessage(String path, String format, String option) {
        if (langConfig == null) reloadLang(langFile.getName());
        if (path == null) return "";
        if (option == null) option = "";

        format = getFormat(format);
        for (int i = 0; i < format.length(); i += 2) { //place formatting symbol before each character
            format = format.substring(0, i) + '\u00A7' + format.substring(i);
        }

        if (getString(path + "." + option) != null) option = getString(path + "." + option);
        String message = format + getString(path + ".msg");
        message = message.replace("<x>", option);
        return message;
    }

    public String getMessage(String path, String format) {
        return getMessage(path, format, null);
    }

    public String getMessage(String path) {
        return getMessage(path, "info");
    }

    private String getFormat(String format) {
        format = getString(format);
        return format == null ? "" : format;
    }

    private String getString(String path) {
        String message = null;
        if (langConfig.contains(path)) {
            message = langConfig.getString(path);
        } else if (defConfig.contains(path)) {
            message = defConfig.getString(path);
        }
        return message;
    }
}
