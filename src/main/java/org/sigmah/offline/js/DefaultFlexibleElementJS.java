package org.sigmah.offline.js;

import org.sigmah.shared.dto.element.BudgetElementDTO;
import org.sigmah.shared.dto.element.DefaultFlexibleElementDTO;
import org.sigmah.shared.dto.referential.DefaultFlexibleElementType;

/**
 *
 * @author Raphaël Calabro (rcalabro@ideia.fr)
 */
public class DefaultFlexibleElementJS extends FlexibleElementJS {
	
	protected DefaultFlexibleElementJS() {
	}
	
	public static DefaultFlexibleElementJS toJavaScript(DefaultFlexibleElementDTO defaultFlexibleElementDTO) {
		final DefaultFlexibleElementJS defaultFlexibleElementJS;
		
		if(defaultFlexibleElementDTO.getType() == DefaultFlexibleElementType.BUDGET
				// FIXME: the following test should not be mandatory
				&& defaultFlexibleElementDTO.getClass() == BudgetElementDTO.class) {
			defaultFlexibleElementJS = BudgetElementJS.toJavaScript((BudgetElementDTO) defaultFlexibleElementDTO);
		} else {
			defaultFlexibleElementJS = Values.createJavaScriptObject(DefaultFlexibleElementJS.class);
		}
		
		defaultFlexibleElementJS.setType(defaultFlexibleElementDTO.getType());
		
		return defaultFlexibleElementJS;
	}
	
	public final DefaultFlexibleElementDTO toDefaultFlexibleElementDTO() {
		final DefaultFlexibleElementDTO defaultFlexibleElementDTO;
		
		if(getTypeEnum() == DefaultFlexibleElementType.BUDGET) {
			defaultFlexibleElementDTO = ((BudgetElementJS)this).toBudgetElementDTO();
		} else {
			defaultFlexibleElementDTO = new DefaultFlexibleElementDTO();
		}
		
		defaultFlexibleElementDTO.setType(getTypeEnum());
		return defaultFlexibleElementDTO;
	}

	public final native String getType() /*-{
		return this.type;
	}-*/;

	public final DefaultFlexibleElementType getTypeEnum() {
		if(getType() != null) {
			return DefaultFlexibleElementType.valueOf(getType());
		}
		return null;
	}

	public final void setType(DefaultFlexibleElementType type) {
		if(type != null) {
			setType(type.name());
		}
	}
	
	public final native void setType(String type) /*-{
		this.type = type;
	}-*/;
}
