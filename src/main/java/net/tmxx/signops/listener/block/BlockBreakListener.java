package net.tmxx.signops.listener.block;

import lombok.RequiredArgsConstructor;
import net.tmxx.signops.SignOps;
import net.tmxx.signops.sign.OperationSign;
import org.bukkit.block.Sign;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

/**
 * @author tmxx
 * @version 1.0
 */
@RequiredArgsConstructor
public class BlockBreakListener implements Listener {
    /**
     * The sign ops plugin instance.
     */
    private final SignOps signOps;

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
