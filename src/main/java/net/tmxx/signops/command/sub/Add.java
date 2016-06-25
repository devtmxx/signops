package net.tmxx.signops.command.sub;

import net.tmxx.signops.SignOps;
import net.tmxx.signops.command.SubCommand;
import net.tmxx.signops.sign.OperationSign;
import net.tmxx.signops.sign.OperationSignAction;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;

import java.util.Set;

/**
 * @author tmxx
 * @version 1.0
 */
public class Add extends SubCommand {
    private final SignOps signOps;
    public Add( SignOps signOps ) {
        super( "add", "Adds an operation sign", "<Action>", "signops.command.add" );

        this.signOps = signOps;
    }

    @Override
    public void execute( Player player, String[] args ) {
        Block block = player.getTargetBlock( ( Set< Material > ) null, 5 );
        if ( block != null && block.getState() instanceof Sign ) {
            if ( args.length < 2 ) {
                this.sendInfo( player );
            } else {
                try {
                    OperationSignAction operationSignAction = OperationSignAction.valueOf( args[ 0 ] );
                    StringBuilder stringBuilder = new StringBuilder();

                    for ( int i = 1; i < args.length; i++ ) {
                        stringBuilder.append( args[ i ] ).append( " " );
                    }

                    OperationSign operationSign = null;

                    for ( OperationSign sign : this.signOps.getMainConfig().getOperationSigns() ) {
                        if ( sign.equalsLocation( block.getLocation() ) ) {
                            operationSign = sign;
                        }
                    }

                    if ( operationSign == null ) {
                        operationSign = new OperationSign();
                        operationSign.setWorld( block.getWorld().getName() );
                        operationSign.setX( block.getX() );
                        operationSign.setY( block.getY() );
                        operationSign.setZ( block.getZ() );

                        this.signOps.getMainConfig().getOperationSigns().add( operationSign );
                    }

                    operationSign.setOperationSignAction( operationSignAction );
                    operationSign.setValue( stringBuilder.toString() );

                    this.signOps.getMainConfig().saveConfig();
                    player.sendMessage( "§e§lOperation sign saved with action §c§l" + operationSignAction.name() + " §e§land value §c§l" + stringBuilder.toString() );
                } catch ( Exception e ) {
                    this.sendInfo( player );
                }
            }
        }
    }
}
