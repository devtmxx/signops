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

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.tmxx.signops.util.URLUtil;

import java.util.function.Function;

/**
 * <p>
 *     This is an enumeration of all the possible actions for
 *     an operation sign.
 * </p>
 *
 * @author tmxx
 * @version 1.0
 */
@RequiredArgsConstructor
public enum OperationSignAction {
    /**
     * Opens a link for a player.
     */
    OPEN_LINK( new Function< String, Boolean >() {
        @Override
        public Boolean apply( String input ) {
            return URLUtil.containsURL( input );
        }
    }, "<URL>" ),

    /**
     * Executes a command for a player.
     */
    EXECUTE_COMMAND( new Function< String, Boolean >() {
        @Override
        public Boolean apply( String input ) {
            return true;
        }
    }, "<Command> [Arguments]" ),

    /**
     * Applies a vector to a player.
     */
    VECTOR( new Function< String, Boolean >() {
        @Override
        public Boolean apply( String input ) {
            String[] split = input.split( ";" );
            if ( split.length != 3 ) {
                return false;
            }

            for ( String string : split ) {
                try {
                    Double.parseDouble( string );
                } catch ( NumberFormatException e ) {
                    return false;
                }
            }

            return true;
        }
    }, "<X>;<Y>;<Z>" );

    /**
     * The function to use when checking the value of an operation sign.
     */
    private final Function< String, Boolean > checkFunction;

    /**
     * The template of the value to insert.
     */
    @Getter private final String valueTemplate;

    /**
     * Checks whether the value of an operation sign is correct.
     *
     * @param input The input value of an operation sign.
     * @return      Whether the value is correct.
     */
    public boolean check( String input ) {
        return this.checkFunction.apply( input );
    }
}
