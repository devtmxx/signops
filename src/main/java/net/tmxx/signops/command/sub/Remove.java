package net.tmxx.signops.command.sub;

import net.tmxx.signops.SignOps;
import net.tmxx.signops.command.SubCommand;
import net.tmxx.signops.sign.OperationSign;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;

import java.util.Set;

/**
 * @author tmxx
 * @version 1.0
 */
public class Remove extends SubCommand {
    private final SignOps signOps;
    public Remove( SignOps signOps ) {
        super( "remove", "Removes an operation sign", "", "signops.command.remove" );

        this.signOps = signOps;
    }

    @Override
    public void execute( Player player, String[] args ) {
        Block block = player.getTargetBlock( ( Set< Material > ) null, 5 );
        if ( block != null && block instanceof Sign ) {
            OperationSign operationSign = null;

            for ( OperationSign sign : this.signOps.getMainConfig().getOperationSigns() ) {
                if ( sign.equalsLocation( block.getLocation() ) ) {
                    operationSign = sign;
                }
            }

            if ( operationSign == null ) {
                player.sendMessage( "§c§lNo operation found for this sign" );
            } else {
                this.signOps.getMainConfig().getOperationSigns().remove( operationSign );
                this.signOps.getMainConfig().saveConfig();
                player.sendMessage( "§e§lOperation sign removed" );
            }
        }
    }
}
