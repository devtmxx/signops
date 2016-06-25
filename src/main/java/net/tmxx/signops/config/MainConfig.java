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
package net.tmxx.signops.config;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import net.cubespace.Yamler.Config.Comments;
import net.cubespace.Yamler.Config.Config;
import net.cubespace.Yamler.Config.InvalidConfigurationException;
import net.cubespace.Yamler.Config.InvalidConverterException;
import net.tmxx.signops.SignOps;
import net.tmxx.signops.config.converter.OperationSignConverter;
import net.tmxx.signops.sign.OperationSign;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *     The main configuration of the sign ops plugin.
 * </p>
 *
 * @author tmxx
 * @version 1.0
 */
@Data
@EqualsAndHashCode( callSuper = false )
@ToString
public class MainConfig extends Config {
    /**
     * List of operation signs.
     */
    @Comments( {
            "List of operation signs"
    } )
    private List<OperationSign> operationSigns = new ArrayList<>();

    /**
     * Constructs a new main configuration by specifying the sign ops plugin instance.
     *
     * @param signOps   The sign ops plugin instance.
     */
    public MainConfig( SignOps signOps ) {
        this.CONFIG_FILE = new File( signOps.getDataFolder(), "config.yml" );

        try {
            this.addConverter( OperationSignConverter.class );
        } catch ( InvalidConverterException e ) {
            e.printStackTrace();
        }
    }

    /**
     * Saves this configuration.
     */
    public void saveConfig() {
        try {
            this.save();
        } catch ( InvalidConfigurationException e ) {
            e.printStackTrace();
        }
    }
}
