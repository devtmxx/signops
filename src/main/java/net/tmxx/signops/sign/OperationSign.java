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

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.tmxx.signops.util.PotionEffectUtil;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

/**
 * <p>
 *     An operation sign represents a sign in the world which is able
 *     to execute a specified action.
 * </p>
 *
 * @author tmxx
 * @version 1.0
 */
@Data
@EqualsAndHashCode( callSuper = false )
@ToString
public class OperationSign {
    /**
     * The name of the world of this operation sign.
     */
    private String world;

    /**
     * The x coordinate of this operation sign.
     */
    private int x;

    /**
     * The y coordinate of this operation sign.
     */
    private int y;

    /**
     * The z coordinate of this operation sign.
     */
    private int z;

    /**
     * The operation sign action.
     */
    private OperationSignAction operationSignAction;

    /**
     * The value of this operation sign action.
     */
    private String value;

    /**
     * Checks if the specified location equals the location of this sign.
     *
     * @param location  The location to check for.
     * @return          Whether the specified location equals this signs location.
     */
    public boolean equalsLocation( Location location ) {
        return location.getWorld().getName().equals( this.world ) &&
                location.getBlockX() == this.x &&
                location.getBlockY() == this.y &&
                location.getBlockZ() == this.z;
    }

    /**
     * Performs the action of this operation sign for the specified player.
     *
     * @param player    The player to perform the action for.
     */
    public void performAction( Player player ) {
        switch ( this.operationSignAction ) {
            case OPEN_LINK: {
                TextComponent textComponent = new TextComponent( "§e§lClick here to open link!" );
                textComponent.setClickEvent( new ClickEvent(
                        ClickEvent.Action.OPEN_URL,
                        ( this.value.startsWith( "http://" ) ||
                                this.value.startsWith( "https://" ) ) ?
                                this.value : "http://" + this.value )
                );
                textComponent.setHoverEvent( new HoverEvent( HoverEvent.Action.SHOW_TEXT, new TextComponent[] {
                        new TextComponent( "§e§l" + this.value )
                } ) );
                player.spigot().sendMessage( textComponent );
                break;
            }
            case EXECUTE_COMMAND: {
                PlayerCommandPreprocessEvent playerCommandPreprocessEvent = new PlayerCommandPreprocessEvent( player, this.value );
                Bukkit.getPluginManager().callEvent( playerCommandPreprocessEvent );
                if ( !playerCommandPreprocessEvent.isCancelled() ) {
                    player.performCommand( this.value );
                }
                break;
            }
            case VECTOR: {
                String[] split = this.value.split( ";" );
                double x = Double.parseDouble( split[ 0 ] );
                double y = Double.parseDouble( split[ 1 ] );
                double z = Double.parseDouble( split[ 2 ] );
                player.setVelocity( new Vector( x, y, z ) );
            }
            /*case POTION_EFFECT: {
                if ( this.value.equalsIgnoreCase( "clear" ) ) {
                    for ( PotionEffect potionEffect : player.getActivePotionEffects() ) {
                        player.removePotionEffect( potionEffect.getType() );
                    }
                } else {
                    String[] split = this.value.split( ";" );
                    PotionEffectType potionEffectType = PotionEffectUtil.getTypeByName( split[ 0 ] );
                    int duration = Integer.parseInt( split[ 1 ] );
                    int amplifier = Integer.parseInt( split[ 2 ] );
                    player.addPotionEffect( new PotionEffect( potionEffectType, duration, amplifier ) );
                }
            }*/
            default:
                break;
        }
    }
}
