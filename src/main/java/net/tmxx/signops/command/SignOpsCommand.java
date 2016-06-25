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
package net.tmxx.signops.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

/**
 * <p>
 *     The sign ops command. This command manages all the sub
 *     commands of this plugin.
 * </p>
 *
 * @author tmxx
 * @version 1.0
 */
public class SignOpsCommand implements CommandExecutor {
    /**
     * Map of all sub commands of sign ops and their names.
     */
    private final Map< String, SubCommand > subCommandList = new HashMap<>();

    /**
     * Adds a sub command to the sub command map. This will first check
     * if the sub command is already in the map to avoid the injection
     * of wrong commands.
     *
     * @param subCommand    The sub command to add to the map.
     */
    public void addCommand( SubCommand subCommand ) {
        this.subCommandList.putIfAbsent( subCommand.getName(), subCommand );
    }

    /**
     * Invoked when a command sender executes the sign ops command. This will analyze
     * the specified arguments for sub commands, checks the permissions and executes
     * them if there are no issues.
     *
     * @param commandSender The command sender who executed the command.
     * @param command       The executed command as an object.
     * @param commandLabel  The label of the executed command.
     * @param args          The arguments specified by the command sender.
     * @return              Whether the command was successfully executed.
     */
    @Override
    public boolean onCommand( CommandSender commandSender, Command command, String commandLabel, String[] args ) {
        if ( !( commandSender instanceof Player ) ) {
            commandSender.sendMessage( "The SignOps command is only for players" );
            return true;
        }

        Player player = ( Player ) commandSender;
        if ( player.hasPermission( "signops.command.use" ) ) {
            if ( args.length == 0 ) {
                player.sendMessage( "§e§lSignOps commands:" );
                for ( SubCommand subCommand : this.subCommandList.values() ) {
                    subCommand.sendInfo( player );
                }
            } else {
                SubCommand subCommand = this.subCommandList.containsKey( args[ 0 ] ) ? this.subCommandList.get( args[ 0 ] ) : null;

                if ( subCommand == null ) {
                    player.sendMessage( "§c§lThis command doesn't exist" );
                } else {
                    if ( player.hasPermission( subCommand.getPermission() ) ) {
                        Vector<String> stringVector = new Vector<>( Arrays.asList( args ) );
                        stringVector.remove( 0 );
                        args = stringVector.toArray( new String[ stringVector.size() ] );

                        subCommand.execute( player, args );
                    } else {
                        player.sendMessage( "§c§lYou don't have permission to execute this command" );
                    }
                }
            }
        }
        return true;
    }
}
