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
package net.tmxx.signops.listener.player;

import lombok.RequiredArgsConstructor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.tmxx.signops.SignOps;
import net.tmxx.signops.sign.OperationSign;
import net.tmxx.signops.util.URLUtil;
import org.bukkit.ChatColor;
import org.bukkit.block.Sign;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *     Listener listening for the {@link org.bukkit.event.player.PlayerInteractEvent}
 *     and checks if the clicked sign contains a link.
 * </p>
 *
 * @author tmxx
 * @version 1.0
 */
@RequiredArgsConstructor
public class PlayerInteractListener implements Listener {
    /**
     * The sign ops plugin instance.
     */
    private final SignOps signOps;

    /**
     * Invoked when a user interact with something. This will check if
     * the user performed a right click on a block and if the block is a sign,
     * it will analyze this sign for urls.
     *
     * @param event The posted event.
     */
    @EventHandler
    public void onPlayerInteract( PlayerInteractEvent event ) {
        if ( event.getAction().equals( Action.RIGHT_CLICK_BLOCK ) ) {
            if ( event.getClickedBlock().getState() instanceof Sign ) {
                for ( OperationSign operationSign : this.signOps.getMainConfig().getOperationSigns() ) {
                    if ( operationSign.equalsLocation( event.getClickedBlock().getLocation() ) ) {
                        operationSign.performAction( event.getPlayer() );
                    }
                }

                Sign sign = ( Sign ) event.getClickedBlock().getState();
                StringBuilder stringBuilder = new StringBuilder();

                for ( int i = 0; i < 4; i++ ) {
                    stringBuilder.append( ChatColor.stripColor( sign.getLine( i ) ) );
                }

                String url = URLUtil.extractURL( stringBuilder.toString() );

                if ( url != null ) {
                    url = url.trim();
                    TextComponent textComponent = new TextComponent( "§e§lClick here to open link!" );
                    textComponent.setClickEvent( new ClickEvent( ClickEvent.Action.OPEN_URL, ( url.startsWith( "http://" ) || url.startsWith( "https://" ) ) ? url : "http://" + url ) );
                    textComponent.setHoverEvent( new HoverEvent( HoverEvent.Action.SHOW_TEXT, new TextComponent[] {
                            new TextComponent( "§e§l" + url )
                    } ) );
                    event.getPlayer().spigot().sendMessage( textComponent );
                }
            }
        } else if ( event.getAction().equals( Action.LEFT_CLICK_BLOCK ) ) {
            if ( event.getClickedBlock().getState() instanceof Sign ) {
                if ( this.signOps.getCurrentRemovals().contains( event.getPlayer().getUniqueId() ) ) {
                    List<OperationSign> operationSignList = new ArrayList<>();

                    for ( OperationSign operationSign : this.signOps.getMainConfig().getOperationSigns() ) {
                        if ( operationSign.equalsLocation( event.getClickedBlock().getLocation() ) ) {
                            operationSignList.add( operationSign );
                        }
                    }

                    if ( operationSignList.isEmpty() ) {
                        event.getPlayer().sendMessage( "§c§lNo operations to remove here" );
                        this.signOps.getCurrentRemovals().remove( event.getPlayer().getUniqueId() );
                        event.setCancelled( true );
                    } else {
                        for ( OperationSign operationSign : operationSignList ) {
                            this.signOps.getMainConfig().getOperationSigns().remove( operationSign );
                        }
                        this.signOps.getMainConfig().saveConfig();
                        this.signOps.getCurrentRemovals().remove( event.getPlayer().getUniqueId() );
                        event.getPlayer().sendMessage( "§e§lRemoved sign operations" );
                        event.setCancelled( true );
                    }
                } else {
                    OperationSign operationSign = this.signOps.getCurrentSetups().get( event.getPlayer().getUniqueId() );
                    if ( operationSign != null ) {
                        operationSign.setWorld( event.getClickedBlock().getWorld().getName() );
                        operationSign.setX( event.getClickedBlock().getX() );
                        operationSign.setY( event.getClickedBlock().getY() );
                        operationSign.setZ( event.getClickedBlock().getZ() );

                        this.signOps.getMainConfig().getOperationSigns().add( operationSign );
                        this.signOps.getMainConfig().saveConfig();
                        this.signOps.getCurrentSetups().remove( event.getPlayer().getUniqueId() );

                        event.getPlayer().sendMessage( "§e§lSuccessfully applied values to operation sign" );
                        event.setCancelled( true );
                    }
                }
            }
        }
    }
}
