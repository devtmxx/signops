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
package net.tmxx.signops.command.sub;

import net.tmxx.signops.command.SubCommand;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;

import java.util.Set;

/**
 * <p>
 *     The set line command sets the specified line of the sign the
 *     user looks at to the specified value.
 * </p>
 *
 * @author tmxx
 * @version 1.0
 */
public class SetLine extends SubCommand {
    /**
     * Constructs a new set line sub command.
     */
    public SetLine() {
        super( "setline", "Sets the line of the target sign to the specified value", "<Line> <Value>", "signops.command.setline" );
    }

    /**
     * Invoked when a player executes this sub command.
     *
     * @param player    The executing player.
     * @param args      The arguments specified by the player.
     */
    @Override
    public void execute( Player player, String[] args ) {
        Block block = player.getTargetBlock( ( Set<Material> ) null, 5 );
        if ( block != null && block.getState() instanceof Sign ) {
            Sign sign = ( Sign ) block.getState();
            if ( args.length <= 1 ) {
                this.sendInfo( player );
            } else {
                try {
                    int line = Integer.parseInt( args[ 0 ] ) - 1;

                    if ( line >= 0 && line <= 3 ) {
                        StringBuilder stringBuilder = new StringBuilder();
                        for ( int i = 1; i < args.length; i++ ) {
                            stringBuilder.append( args[ i ] ).append( " " );
                        }

                        String value = stringBuilder.toString();

                        if ( value.length() > 16 ) {
                            player.sendMessage( "§c§lSign lines can only by 16 characters long" );
                        } else {
                            sign.setLine( line, ChatColor.translateAlternateColorCodes( '&', value ) );
                            sign.update();
                            player.sendMessage( "§e§lSet line " + ( line + 1 ) + " to §r" + ChatColor.translateAlternateColorCodes( '&', value ) );
                        }
                    } else {
                        player.sendMessage( "§c§lPlease use line numbers 1 - 4. Signs only have 4 lines" );
                    }
                } catch ( NumberFormatException e ) {
                    this.sendInfo( player );
                }
            }
        } else {
            player.sendMessage( "§c§lYour target is not a sign" );
        }
    }
}
