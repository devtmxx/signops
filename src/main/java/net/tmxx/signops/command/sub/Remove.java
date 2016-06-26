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
import net.tmxx.signops.sign.OperationSign;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;

import java.util.Set;

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
        Block block = player.getTargetBlock( ( Set< Material > ) null, 5 );
        if ( block != null && block.getState() instanceof Sign ) {
            OperationSign operationSign = null;

            for ( OperationSign sign : this.signOps.getMainConfig().getOperationSigns() ) {
                if ( sign.equalsLocation( block.getLocation() ) ) {
                    operationSign = sign;
                }
            }

            if ( operationSign == null ) {
                player.sendMessage( "§c§lNo operation found for this sign" );
            } else {
                this.signOps.getMainConfig().getOperationSigns().remove( operationSign );
                this.signOps.getMainConfig().saveConfig();
                player.sendMessage( "§e§lOperation sign removed" );
            }
        } else {
            player.sendMessage( "§c§lThis is no sign" );
        }
    }
}
