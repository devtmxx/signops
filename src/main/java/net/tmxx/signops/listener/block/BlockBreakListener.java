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
package net.tmxx.signops.listener.block;

import lombok.RequiredArgsConstructor;
import net.tmxx.signops.SignOps;
import net.tmxx.signops.sign.OperationSign;
import org.bukkit.block.Sign;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

/**
 * <p>
 *     Listener listening for the {@link org.bukkit.event.block.BlockBreakEvent}
 *     and removes (if necessary) the operation sign.
 * </p>
 *
 * @author tmxx
 * @version 1.0
 */
@RequiredArgsConstructor
public class BlockBreakListener implements Listener {
    /**
     * The sign ops plugin instance.
     */
    private final SignOps signOps;

    /**
     * Invoked when a player breaks a block.
     *
     * @param event The posted event.
     */
    @EventHandler
    public void onBlockBreak( BlockBreakEvent event ) {
        if ( event.getBlock().getState() instanceof Sign ) {
            OperationSign operationSign = null;

            for ( OperationSign sign : this.signOps.getMainConfig().getOperationSigns() ) {
                if ( sign.equalsLocation( event.getBlock().getLocation() ) ) {
                    operationSign = sign;
                }
            }

            if ( operationSign != null ) {
                this.signOps.getMainConfig().getOperationSigns().remove( operationSign );
                this.signOps.getMainConfig().saveConfig();
                event.getPlayer().sendMessage( "§e§lRemoved operation sign" );
            }
        }
    }
}
