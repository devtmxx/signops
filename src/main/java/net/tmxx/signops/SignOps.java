/**
 * SignOps adds new sign operations to the game.
 * Copyright (C) 2016  tmxx
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package net.tmxx.signops;

import lombok.Getter;
import net.cubespace.Yamler.Config.InvalidConfigurationException;
import net.tmxx.signops.command.SignOpsCommand;
import net.tmxx.signops.command.sub.Add;
import net.tmxx.signops.command.sub.Remove;
import net.tmxx.signops.command.sub.SetLine;
import net.tmxx.signops.config.MainConfig;
import net.tmxx.signops.listener.block.BlockBreakListener;
import net.tmxx.signops.listener.player.PlayerInteractListener;
import net.tmxx.signops.listener.block.SignChangeListener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * <p>
 *     The main entry point of sign ops.
 * </p>
 *
 * @author tmxx
 * @version 1.0
 */
public class SignOps extends JavaPlugin {
    /**
     * The main configuration of sign ops.
     */
    @Getter private MainConfig mainConfig;

    /**
     * Invoked when this plugin is being enabled.
     */
    @Override
    public void onEnable() {
        this.mainConfig = new MainConfig( this );
        try {
            this.mainConfig.init();
        } catch ( InvalidConfigurationException e ) {
            e.printStackTrace();
        }

        this.setupCommands();
        this.setupListeners();

        this.getLogger().info( "SignOps adds new sign operations to the game.\n" +
                "    Copyright (C) 2016  tmxx\n" +
                "\n" +
                "    This program is free software: you can redistribute it and/or modify\n" +
                "    it under the terms of the GNU General Public License as published by\n" +
                "    the Free Software Foundation, either version 3 of the License, or\n" +
                "    (at your option) any later version.\n" +
                "\n" +
                "    This program is distributed in the hope that it will be useful,\n" +
                "    but WITHOUT ANY WARRANTY; without even the implied warranty of\n" +
                "    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the\n" +
                "    GNU General Public License for more details.\n" +
                "\n" +
                "    You should have received a copy of the GNU General Public License\n" +
                "    along with this program.  If not, see <http://www.gnu.org/licenses/>." );
    }

    /**
     * Sets up all commands of sign ops.
     */
    private void setupCommands() {
        SignOpsCommand signOpsCommand = new SignOpsCommand();
        signOpsCommand.addCommand( new SetLine() );
        signOpsCommand.addCommand( new Add( this ) );
        signOpsCommand.addCommand( new Remove( this ) );

        this.getCommand( "signops" ).setExecutor( signOpsCommand );
    }

    /**
     * Sets up all listeners of sign ops.
     */
    private void setupListeners() {
        PluginManager pluginManager = this.getServer().getPluginManager();

        pluginManager.registerEvents( new PlayerInteractListener( this ), this );
        pluginManager.registerEvents( new BlockBreakListener( this ), this );
        pluginManager.registerEvents( new SignChangeListener(), this );
    }
}
