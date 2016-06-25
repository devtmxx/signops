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

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;

/**
 * <p>
 *     Listener listening for the {@link org.bukkit.event.block.SignChangeEvent}
 *     and translates color codes of the lines of the sign.
 * </p>
 *
 * @author tmxx
 * @version 1.0
 */
public class SignChangeListener implements Listener {
    /**
     * Invoked when a user changes a sign. This will analyze the lines of the sign
     * and translate alternate color codes of the lines.
     *
     * @param event The posted event.
     */
    @EventHandler
    public void onSignChange( SignChangeEvent event ) {
        for ( int i = 0 ; i < event.getLines().length; i++ ) {
            event.setLine( i, ChatColor.translateAlternateColorCodes( '&', event.getLine( i ) ) );
        }
    }
}
