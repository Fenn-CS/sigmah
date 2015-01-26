package org.sigmah.server.handler;

import org.sigmah.server.dispatch.impl.UserDispatch.UserExecutionContext;
import org.sigmah.server.domain.Project;
import org.sigmah.server.domain.logframe.LogFrame;
import org.sigmah.server.domain.logframe.LogFrameCopyContext;
import org.sigmah.server.handler.base.AbstractCommandHandler;
import org.sigmah.server.mapper.Mapper;
import org.sigmah.shared.command.CopyLogFrame;
import org.sigmah.shared.dispatch.CommandException;
import org.sigmah.shared.dto.logframe.LogFrameDTO;

import com.google.inject.Inject;
import com.google.inject.persist.Transactional;

/**
 * Handler for the CopyLogFrame command.
 * 
 * @author Raphaël Calabro (rcalabro@ideia.fr)
 * @author Maxime Lombard (mlombard@ideia.fr)
 */
public class CopyLogFrameHandler extends AbstractCommandHandler<CopyLogFrame, LogFrameDTO> {

	private final Mapper mapper;

	@Inject
	public CopyLogFrameHandler(Mapper mapper) {

		this.mapper = mapper;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public LogFrameDTO execute(final CopyLogFrame cmd, final UserExecutionContext context) throws CommandException {
		final Project project = em().find(Project.class, cmd.getDestinationId());
		final LogFrame logFrame = em().find(LogFrame.class, cmd.getSourceId());

		final LogFrame copy = replaceLogFrame(project, logFrame, cmd);
		return mapper.map(copy, LogFrameDTO.class);
	}

	@Transactional
	private LogFrame replaceLogFrame(Project project, LogFrame source, CopyLogFrame cmd) {
		final LogFrame previousLogFrame = project.getLogFrame();
		if (previousLogFrame != null) {
			em().remove(previousLogFrame);
		}

		final LogFrame copy = source.copy(LogFrameCopyContext.toProject(project).withStrategy(cmd.getIndicatorCopyStrategy()));
		copy.setParentProject(project);
		project.setLogFrame(copy);

		em().merge(project);

		return project.getLogFrame();
	}
}
