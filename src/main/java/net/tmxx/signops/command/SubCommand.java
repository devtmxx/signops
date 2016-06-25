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

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Player;

/**
 * <p>
 *     The base class of all sub commands of sign ops.
 *     We use this for a much better handling and an
 *     organized code.
 * </p>
 *
 * @author tmxx
 * @version 1.0
 */
@RequiredArgsConstructor
@Getter
public abstract class SubCommand {
    /**
     * The name of the sub command.
     */
    private final String name;

    /**
     * The description of the sub command.
     */
    private final String description;

    /**
     * The usage of the sub command.
     */
    private final String usage;

    /**
     * The permission of the sub command.
     */
    private final String permission;

    /**
     * Invoked when a player executes this sub command.
     *
     * @param player    The executing player.
     * @param args      The arguments specified by the player.
     */
    public abstract void execute( Player player, String[] args );

    /**
     * Sends the command information to the specified player.
     *
     * @param player    The player to send the command information to.
     */
    public void sendInfo( Player player ) {
        player.sendMessage( "§8§l/§6signops " + this.name + " " + this.usage + " §7- " + this.description );
    }
}
