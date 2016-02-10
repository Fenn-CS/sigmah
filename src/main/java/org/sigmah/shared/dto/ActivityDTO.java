package org.sigmah.shared.dto;

/*
 * #%L
 * Sigmah
 * %%
 * Copyright (C) 2010 - 2016 URD
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-3.0.html>.
 * #L%
 */

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.sigmah.client.util.ToStringBuilder;
import org.sigmah.shared.dto.base.AbstractModelDataEntityDTO;

/**
 * One-to-one DTO for the {@link org.sigmah.server.domain.Activity} domain object.
 *
 * @author Alex Bertram
 * @author Denis Colliot (dcolliot@ideia.fr)
 */
public final class ActivityDTO extends AbstractModelDataEntityDTO<Integer> {

	/**
	 * Serial version UID.
	 */
	private static final long serialVersionUID = -3319562645487148050L;

	/**
	 * DTO corresponding domain entity name.
	 */
	public static final String ENTITY_NAME = "Activity";

	public static final int REPORT_ONCE = 0;
	public static final int REPORT_MONTHLY = 1;

	private UserDatabaseDTO database;

	private List<IndicatorDTO> indicators = new ArrayList<IndicatorDTO>(0);
	private List<AttributeGroupDTO> attributeGroups = new ArrayList<AttributeGroupDTO>(0);

	public ActivityDTO() {
		setAssessment(false);
		setReportingFrequency(REPORT_ONCE);
	}

	/**
	 * Constructs a DTO with the given properties
	 */
	public ActivityDTO(Map<String, Object> properties) {
		super(properties);
	}

	/**
	 * Creates a shallow clone
	 *
	 * @param model
	 */
	public ActivityDTO(ActivityDTO model) {
		super(model.getProperties());
		this.database = model.getDatabase();
		this.setIndicators(model.getIndicators());
		this.setAttributeGroups(model.getAttributeGroups());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getEntityName() {
		return ENTITY_NAME;
	}

	/**
	 * @param id
	 *          the Activity's id
	 * @param name
	 *          the Activity's name
	 */
	public ActivityDTO(int id, String name) {
		this();
		setId(id);
		setName(name);
	}

	/**
	 * @param db
	 *          the UserDatabaseDTO to which this Activity belongs
	 */
	public ActivityDTO(UserDatabaseDTO db) {
		setDatabase(db);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void appendToString(final ToStringBuilder builder) {
		builder.append("name", getName());
		builder.append("assessment", isAssessment());
		builder.append("reportingFrequency", getReportingFrequency());
		builder.append("locationTypeId", getLocationTypeId());
		builder.append("category", getCategory());
	}

	/**
	 * Sets this Activity's name
	 */
	public void setName(String value) {
		set("name", value);
	}

	/**
	 * @return this Activity's name
	 */
	public String getName() {
		return get("name");
	}

	/**
	 * @return the database to which this Activity belongs
	 */
	public UserDatabaseDTO getDatabase() {
		return database;
	}

	/**
	 * Sets the database to which this Activity belongs
	 */
	public void setDatabase(UserDatabaseDTO database) {
		this.database = database;
	}

	/**
	 * @return a list of this Activity's indicators
	 */
	public List<IndicatorDTO> getIndicators() {
		return indicators;
	}

	/**
	 * Sets this Activity's Indicator
	 */
	public void setIndicators(List<IndicatorDTO> indicators) {
		this.indicators = indicators;
	}

	public List<AttributeGroupDTO> getAttributeGroups() {
		return attributeGroups;
	}

	public void setAttributeGroups(List<AttributeGroupDTO> attributes) {
		this.attributeGroups = attributes;
	}

	public void setAssessment(boolean value) {
		set("assessment", value);
	}

	public boolean isAssessment() {
		return (Boolean) get("assessment");
	}

	/**
	 * Sets the ReportingFrequency of this Activity, either <code>REPORT_ONCE</code> or <code>REPORT_MONTHLY</code>
	 */
	public void setReportingFrequency(int frequency) {
		set("reportingFrequency", frequency);
	}

	/**
	 * @return the ReportingFrequency of this Activity, either <code>REPORT_ONCE</code> or <code>REPORT_MONTHLY</code>
	 */
	public int getReportingFrequency() {
		return (Integer) get("reportingFrequency");
	}

	/**
	 * Sets the id of the LocationType of the Location to which this Site belongs.
	 */
	public void setLocationTypeId(int locationId) {
		set("locationTypeId", locationId);

	}

	/**
	 * @return the id of the LocationType of the Location to which this Site belongs
	 */
	public int getLocationTypeId() {
		return (Integer) get("locationTypeId");
	}

	/**
	 * @return the
	 */
	public LocationTypeDTO getLocationType() {
		return getDatabase().getCountry().getLocationTypeById(getLocationTypeId());
	}

	/**
	 * Searches the list of Attributes for the AttributeDTO with the given id
	 *
	 * @return the AttributeDTO matching the given id, or null if no such AttributeDTO was found.
	 */
	public AttributeDTO getAttributeById(int id) {
		for (AttributeGroupDTO group : attributeGroups) {
			AttributeDTO attribute = SchemaDTO.getById(group.getAttributes(), id);
			if (attribute != null) {
				return attribute;
			}
		}
		return null;
	}

	/**
	 * Searches this Activity's list of Indicators for the IndicatorDTO with the given id.
	 *
	 * @return the matching IndicatorDTO or null if nothing was found
	 */
	public IndicatorDTO getIndicatorById(int indicatorId) {
		for (IndicatorDTO indicator : indicators) {
			if (indicator.getId().equals(indicatorId)) {
				return indicator;
			}
		}
		return null;
	}

	/**
	 * @return this Activity's category
	 */
	public String getCategory() {
		return get("category");
	}

	/**
	 * Sets this Activity's category
	 */
	public void setCategory(String category) {
		set("category", category);
	}

	/**
	 * Convenience method that creates a list of IndicatorGroups from this Activity's list of Indicators, based on the
	 * Indicator's category property.
	 */
	public List<IndicatorGroup> groupIndicators() {
		List<IndicatorGroup> groups = new ArrayList<IndicatorGroup>();
		Map<String, IndicatorGroup> map = new HashMap<String, IndicatorGroup>();

		for (IndicatorDTO indicator : indicators) {
			String category = indicator.getCategory();
			IndicatorGroup group = map.get(category);
			if (group == null) {
				group = new IndicatorGroup(category);
				map.put(category, group);
				groups.add(group);
			}
			group.addIndicator(indicator);
		}
		return groups;
	}

	/**
	 * @return the id of the MapIcon associated with this Activity
	 */
	public String getMapIcon() {
		return get("mapIcon");
	}

	/**
	 * Sets the id of the MapIcon to be associated with this Activity
	 */
	public void setMapIcon(String mapIcon) {
		set("mapIcon", mapIcon);
	}

	/**
	 * @return The list of admin levels that are relevant for the level of aggregation of this activity
	 */
	public List<AdminLevelDTO> getAdminLevels() {
		if (getLocationType().isAdminLevel()) {

			// if this activity is bound to an administative
			// level, then we need only as far down as this goes

			return getDatabase().getCountry().getAdminLevelAncestors(getLocationType().getBoundAdminLevelId());
		} else {

			// all admin levels

			return getDatabase().getCountry().getAdminLevels();
		}
	}

	/**
	 * Searches this Activity's list of AttributeGroups for an AttributeGroupDTO with the given id
	 * 
	 * @return the matching AttributeGroupDTO or null if there are no matches
	 */
	public AttributeGroupDTO getAttributeGroupById(int id) {
		for (AttributeGroupDTO group : attributeGroups) {
			if (group.getId().equals(id)) {
				return group;
			}
		}
		return null;
	}
}
