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
import net.tmxx.signops.sign.OperationSignAction;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;

import java.util.Set;

/**
 * <p>
 *     The add command adds the sign the user is looking at as an operation
 *     sign with a specified action and a value.
 * </p>
 *
 * @author tmxx
 * @version 1.0
 */
public class Add extends SubCommand {
    /**
     * The sign ops plugin instance.
     */
    private final SignOps signOps;

    /**
     * Constructs a new add command by specifiying the sign ops plugin instance.
     *
     * @param signOps   The sign ops plugin instance.
     */
    public Add( SignOps signOps ) {
        super( "add", "Adds an operation sign", "<Action>", "signops.command.add" );

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
            if ( args.length < 2 ) {
                this.sendInfo( player );
            } else {
                try {
                    OperationSignAction operationSignAction = OperationSignAction.valueOf( args[ 0 ] );
                    StringBuilder stringBuilder = new StringBuilder();

                    for ( int i = 1; i < args.length; i++ ) {
                        stringBuilder.append( args[ i ] ).append( " " );
                    }

                    OperationSign operationSign = null;

                    for ( OperationSign sign : this.signOps.getMainConfig().getOperationSigns() ) {
                        if ( sign.equalsLocation( block.getLocation() ) ) {
                            operationSign = sign;
                        }
                    }

                    if ( operationSign == null ) {
                        operationSign = new OperationSign();
                        operationSign.setWorld( block.getWorld().getName() );
                        operationSign.setX( block.getX() );
                        operationSign.setY( block.getY() );
                        operationSign.setZ( block.getZ() );

                        this.signOps.getMainConfig().getOperationSigns().add( operationSign );
                    }

                    if ( !operationSignAction.check( stringBuilder.toString().trim() ) ) {
                        player.sendMessage( "§c§lThe input value is not correct§8§l: §e§l" + operationSignAction.getValueTemplate() );
                        this.signOps.getMainConfig().getOperationSigns().remove( operationSign );
                        this.signOps.getMainConfig().saveConfig();
                        return;
                    }
                    operationSign.setOperationSignAction( operationSignAction );
                    operationSign.setValue( stringBuilder.toString().trim() );

                    this.signOps.getMainConfig().saveConfig();
                    player.sendMessage( "§e§lOperation sign saved with action §c§l" + operationSignAction.name() + " §e§land value §c§l" + stringBuilder.toString() );
                } catch ( Exception e ) {
                    this.sendInfo( player );
                    player.sendMessage( "§e§lValid operation sign actions:" );
                    for ( OperationSignAction action : OperationSignAction.values() ) {
                        player.sendMessage( "   §7- §c§l" + action.name() + " §8§l[§e§l" + action.getValueTemplate() + "§8§l]" );
                    }
                }
            }
        } else {
            player.sendMessage( "§c§lThis is no sign" );
        }
    }
}
