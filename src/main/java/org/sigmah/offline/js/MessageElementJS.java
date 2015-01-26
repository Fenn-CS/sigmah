package org.sigmah.offline.js;

import org.sigmah.shared.dto.element.MessageElementDTO;

/**
 *
 * @author Raphaël Calabro (rcalabro@ideia.fr)
 */
public final class MessageElementJS extends FlexibleElementJS {
	
	protected MessageElementJS() {
	}
	
	public static MessageElementJS toJavaScript(MessageElementDTO messageElementDTO) {
		return Values.createJavaScriptObject(MessageElementJS.class);
	}
	
	@Override
	protected MessageElementDTO createDTO() {
		return new MessageElementDTO();
	}
}
