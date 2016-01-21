package org.sigmah.shared.computation;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import org.sigmah.shared.dto.element.FlexibleElementDTO;

/**
 * Parse and creates <code>Computation</code> objects.
 * 
 * @author Raphaël Calabro (raphael.calabro@netapsys.fr)
 * @since 2.1
 */
public final class Computations {
	
	/**
	 * Parse the given rule.
	 * 
	 * @param rule Rule to read.
	 * @param allElements Flexible element that can be used in the rule.
	 * @return A new <code>Computation</code> object.
	 */
	public static Computation parse(String rule, Collection<FlexibleElementDTO> allElements) {
		final ParserEnvironment environment = new ParserEnvironment();
		environment.setElements(createReferenceMap(allElements));
		
		final char[] array = rule.toCharArray();
		int index = 0;
		while (index < array.length) {
			index = environment.getState().execute(index, array, environment);
		}
		
		environment.popEverythingFromStackToInstructions();
		
		return new Computation(environment.getInstructions());
	}
	
	/**
	 * Creates a map associating the identifier and the code of every element
	 * to its element.
	 * 
	 * @param allElements Flexible element that can be used in the rule.
	 * @return A map of id/code to its flexible element.
	 */
	private static Map<String, FlexibleElementDTO> createReferenceMap(Collection<FlexibleElementDTO> allElements) {
		final HashMap<String, FlexibleElementDTO> references = new HashMap<String, FlexibleElementDTO>();
		
		for (final FlexibleElementDTO element : allElements) {
			references.put('#' + element.getId().toString(), element);
			references.put(element.getCode(), element);
		}
		
		return references;
	}
	
	/**
	 * Private constructor.
	 */
	private Computations() {
		// Nothing.
	}
	
}
