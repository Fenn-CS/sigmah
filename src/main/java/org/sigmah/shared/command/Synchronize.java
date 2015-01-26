package org.sigmah.shared.command;

import java.util.List;
import org.sigmah.shared.command.base.AbstractCommand;
import org.sigmah.shared.command.base.Command;
import org.sigmah.shared.command.result.VoidResult;

/**
 *
 * @author Raphaël Calabro (rcalabro@ideia.fr)
 */
public class Synchronize extends AbstractCommand<VoidResult> {
    
    private List<Command<?>> commands;
    
    protected Synchronize() {
    }
    
    public Synchronize(List<Command<?>> commands) {
        this.commands = commands;
    }

    public List<Command<?>> getCommands() {
        return commands;
    }
    
}
