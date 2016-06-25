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
package net.tmxx.signops.config.converter;

import net.cubespace.Yamler.Config.Converter.Converter;
import net.cubespace.Yamler.Config.InternalConverter;
import net.tmxx.signops.sign.OperationSign;
import net.tmxx.signops.sign.OperationSignAction;

import java.lang.reflect.ParameterizedType;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 *     Converts an operation sign to a valid configuration object
 *     and the other way around.
 * </p>
 *
 * @author tmxx
 * @version 1.0
 */
public class OperationSignConverter implements Converter {
    /**
     * Constructs a new operation sign converter by specifying an
     * internal converter to use.
     *
     * @param internalConverter The internal converter.
     */
    public OperationSignConverter( InternalConverter internalConverter ) {}

    /**
     * Converts the operation sign to a configuration object.
     *
     * @param type              The operation sign class type.
     * @param obj               The operation sign object.
     * @param parameterizedType The parameterized type of the operation sign.
     * @return                  The configuration object.
     * @throws Exception        If an error occurs.
     */
    @Override
    public Object toConfig( Class< ? > type, Object obj, ParameterizedType parameterizedType ) throws Exception {
        OperationSign operationSign = ( OperationSign ) obj;
        Map< String, Object > map = new HashMap<>();

        map.put( "world", operationSign.getWorld() );
        map.put( "x", operationSign.getX() );
        map.put( "y", operationSign.getY() );
        map.put( "z", operationSign.getZ() );
        map.put( "action", operationSign.getOperationSignAction().name() );
        map.put( "value", operationSign.getValue() );

        return map;
    }

    /**
     * Converts a configuration object to a valid operation sign.
     *
     * @param type              The class type to which the converter converts the configuration object to.
     * @param obj               The configuration object to convert to the class type.
     * @param parameterizedType The parameterized type of the class type.
     * @return                  The valid operation sign.
     * @throws Exception        If an error occurs.
     */
    @Override
    public Object fromConfig( Class< ? > type, Object obj, ParameterizedType parameterizedType ) throws Exception {
        if ( obj instanceof HashMap ) {
            Map map = ( HashMap ) obj;

            OperationSign operationSign = new OperationSign();

            operationSign.setWorld( ( String ) map.get( "world" ) );
            operationSign.setX( ( Integer ) map.get( "x" ) );
            operationSign.setY( ( Integer ) map.get( "y" ) );
            operationSign.setZ( ( Integer ) map.get( "z" ) );
            operationSign.setOperationSignAction( OperationSignAction.valueOf( ( String ) map.get( "action" ) ) );
            operationSign.setValue( ( String ) map.get( "value" ) );

            return operationSign;
        }

        return null;
    }

    /**
     * Checks whether this converter supports the specified class type.
     *
     * @param type  The class type to check.
     * @return      Whether this converter supports the class type.
     */
    @Override
    public boolean supports( Class< ? > type ) {
        return type.isAssignableFrom( OperationSign.class );
    }
}
