package org.sigmah.server.servlet.importer;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.sigmah.shared.command.AutomatedImport;
import org.sigmah.shared.command.UpdateProject;
import org.sigmah.shared.dispatch.CommandException;
import org.sigmah.shared.dto.ElementExtractedValue;
import org.sigmah.shared.dto.ImportDetails;
import org.sigmah.shared.dto.base.EntityDTO;
import org.sigmah.shared.dto.element.event.ValueEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Wrap an importer to perform automatic and silent importation.
 * 
 * @author Raphaël Calabro (raphael.calabro@netapsys.fr)
 */
public class AutomatedImporter {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(AutomatedImporter.class);
	
	/**
	 * Importer used to retrieve the data of the file.
	 */
	private final Importer importer;

	/**
	 * Creates a new automated importer with the given <code>importer</code>.
	 * <p>
	 * The importer must be initialized.
	 * 
	 * @param importer 
	 *          Importer to use.
	 * @see Importer#initialize() 
	 */
	public AutomatedImporter(Importer importer) {
		this.importer = importer;
	}
	
	/**
	 * Importation the data with the given configuration.
	 * 
	 * @param configuration
	 *          Import configuration.
	 */
	public void importCorrespondances(AutomatedImport configuration) {
		
		while (importer.hasNext()) {
			final ImportDetails details = importer.next();
			
			switch (details.getEntityStatus()) {
			case PROJECT_FOUND_CODE:
				final Map.Entry<EntityDTO<Integer>, List<ElementExtractedValue>> singleEntry = details.getEntitiesToImport().entrySet().iterator().next();
				updateContainerWithDetails(singleEntry.getKey(), singleEntry.getValue(), configuration.getFileName());
				break;
			}
		}
	}
	
	/**
	 * Update the given container with given elements.
	 * 
	 * @param container
	 *          Container to update (can be a project or an org unit).
	 * @param extractedValues
	 *          Extracted values to set.
	 * @param fileName 
	 *          Name of the imported file.
	 */
	private void updateContainerWithDetails(final EntityDTO<Integer> container, final List<ElementExtractedValue> extractedValues, final String fileName) {
		
		final ArrayList<ValueEvent> values = new ArrayList<>();
		for (final ElementExtractedValue value : extractedValues) {
			final ValueEvent event = value != null ? value.toValueEvent() : null;
			
			if (event != null) {
				values.add(event);
			}
		};
		
		final UpdateProject updateProject = new UpdateProject(container.getId(), values, "Imported from file '" + fileName + "'.");
		try {
			importer.getExecutionContext().execute(updateProject);
		} catch (CommandException ex) {
			LOGGER.error("An error occured while importing values for the project #" + container.getId() + ".", ex);
		}
	}
	
}
