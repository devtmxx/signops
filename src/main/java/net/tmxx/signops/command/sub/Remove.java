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

import net.tmxx.signops.SignOps;
import net.tmxx.signops.command.SubCommand;
import org.bukkit.entity.Player;

/**
 * <p>
 *     The remove command removes the sign the user is looking at
 *     from the operation sign list.
 * </p>
 *
 * @author tmxx
 * @version 1.0
 */
public class Remove extends SubCommand {
    /**
     * The sign ops plugin instance.
     */
    private final SignOps signOps;

    /**
     * Constructs a new remove command by specifying the sign ops plugin instance.
     *
     * @param signOps   The sign ops plugin instance.
     */
    public Remove( SignOps signOps ) {
        super( "remove", "Removes an operation sign", "", "signops.command.remove" );

        this.signOps = signOps;
    }

    /**
     * Invoked when a player executes this sub command.
     *
     * @param player    The executing player.
     * @param args      The arguments specified by the player.
     */
    @Override
    public void execute( Player player, String[] args ) {
        if ( this.signOps.getCurrentRemovals().contains( player.getUniqueId() ) ) {
            player.sendMessage( "§c§lYou are currently removing signs" );
        } else {
            this.signOps.getCurrentRemovals().add( player.getUniqueId() );
            player.sendMessage( "§e§lYou are now removing signs" );
            player.sendMessage( "§e§lPunch a sign to remove all operations from it" );
        }
    }
}
