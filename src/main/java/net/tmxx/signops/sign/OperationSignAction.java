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
package net.tmxx.signops.sign;

/**
 * <p>
 *     This is an enumeration of all the possible actions for
 *     an operation sign.
 * </p>
 *
 * @author tmxx
 * @version 1.0
 */
public enum OperationSignAction {
    /**
     * Opens a link for a player.
     */
    OPEN_LINK,

    /**
     * Executes a command for a player.
     */
    EXECUTE_COMMAND
}
