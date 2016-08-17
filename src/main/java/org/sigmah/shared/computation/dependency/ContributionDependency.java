package org.sigmah.shared.computation.dependency;

import com.google.gwt.core.client.GWT;
import org.sigmah.shared.computation.instruction.Instructions;
import org.sigmah.shared.util.ValueResultUtils;

/**
 * Dependency to the given or received contribution throught funding links.
 * 
 * @author Raphaël Calabro (raphael.calabro@netapsys.fr)
 * @since 2.2
 */
public class ContributionDependency implements Dependency {
	
	public static final String REFERENCE = "@contribution";
	
	private Scope scope;
	
	private Integer projectModelId;

	/**
	 * Empty constructor, required for serialization.
	 */
	public ContributionDependency() {
		// Empty.
	}

	public ContributionDependency(final Scope scope) {
		this.scope = scope;
		
		final String modelName = scope.getModelName();
		if (modelName != null && modelName.length() > 1 && modelName.charAt(0) == Instructions.ID_PREFIX) {
			final String[] parts = modelName.split(ValueResultUtils.BUDGET_VALUE_SEPARATOR);
			if (parts.length == 2) {
				try {
					projectModelId = Integer.parseInt(parts[0].substring(1));
					scope.setModelName(parts[1]);
				} catch (NumberFormatException e) {
					GWT.log("Given model name starts by the identifier prefix but is not an identifier: " + modelName, e);
				}
			}
		}
	}

	public Scope getScope() {
		return scope;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isResolved() {
		return scope.getModelName() == null || projectModelId != null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		final StringBuilder stringBuilder = new StringBuilder()
				.append(scope.getLinkedProjectTypeName())
				.append(ValueResultUtils.DEFAULT_VALUE_SEPARATOR);
		
		if (projectModelId != null) {
			stringBuilder.append(Instructions.ID_PREFIX)
					.append(projectModelId)
					.append(ValueResultUtils.BUDGET_VALUE_SEPARATOR);
		}
		stringBuilder.append(scope.getModelName())
				.append(ValueResultUtils.DEFAULT_VALUE_SEPARATOR)
				.append(REFERENCE);
		
		return stringBuilder.toString();
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toHumanReadableString() {
		return new StringBuilder()
				.append(scope.getLinkedProjectTypeName())
				.append(ValueResultUtils.DEFAULT_VALUE_SEPARATOR)
				.append(scope.getModelName())
				.append(ValueResultUtils.DEFAULT_VALUE_SEPARATOR)
				.append(REFERENCE)
				.toString();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void accept(final DependencyVisitor visitor) {
		visitor.visit(this);
	}

	public void setProjectModelId(final Integer projectModelId) {
		this.projectModelId = projectModelId;
	}

	public Integer getProjectModelId() {
		return projectModelId;
	}
	
}
