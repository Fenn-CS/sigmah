package org.sigmah.offline.presenter;

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

import com.extjs.gxt.ui.client.data.BaseModelData;
import java.util.List;
import org.sigmah.client.util.DateUtils;
import org.sigmah.shared.dto.ProjectDTO;
import org.sigmah.shared.dto.base.EntityDTO;
import org.sigmah.shared.dto.element.FilesListElementDTO;
import org.sigmah.shared.dto.orgunit.OrgUnitDTO;
import org.sigmah.shared.dto.value.FileVersionDTO;

/**
 *
 * @author Raphaël Calabro (rcalabro@ideia.fr)
 */
public class TreeGridFileModel extends BaseModelData {
	
	public static final String NAME = "name";
	public static final String LAST_MODIFICATION = "lastModification";
	public static final String AUTHOR = "author";
	public static final String SIZE = "size";
	public static final String CHILDREN = "children";
	
	private EntityDTO<Integer> dto;

	protected TreeGridFileModel() {
	}
	
	public TreeGridFileModel(EntityDTO<Integer> dto) {
		this.dto = dto;
		
		if(dto instanceof ProjectDTO) {
			setProperties((ProjectDTO) dto);
			
		} else if(dto instanceof OrgUnitDTO) {
			setProperties((OrgUnitDTO) dto);
			
		} else if(dto instanceof FileVersionDTO) {
			setProperties((FileVersionDTO) dto);
		}
	}
	
	private void setProperties(ProjectDTO project) {
		set(NAME, project.getName() + " - " + project.getFullName());
	}
	
	private void setProperties(OrgUnitDTO orgUnit) {
		set(NAME, orgUnit.getName() + " - " + orgUnit.getFullName());
	}
	
	private void setProperties(FileVersionDTO fileVersion) {
		set(NAME, fileVersion.getName() + '.' + fileVersion.getExtension());
		set(LAST_MODIFICATION, DateUtils.DATE_SHORT.format(fileVersion.getAddedDate()));
		set(AUTHOR, fileVersion.getAuthorFirstName() + ' ' + fileVersion.getAuthorName());
		set(SIZE, sizeToString(fileVersion.getSize()));
	}

	public EntityDTO<Integer> getDTO() {
		return dto;
	}
	
	public List<FileVersionDTO> getChildren() {
		return (List<FileVersionDTO>) get(CHILDREN);
	}
	
	public void setChildren(List<FileVersionDTO> children) {
		set(CHILDREN, children);
	}
	
	private String sizeToString(long size) {
		final FilesListElementDTO.Size converter = FilesListElementDTO.Size.convertToBestUnit(new FilesListElementDTO.Size(size, FilesListElementDTO.Size.SizeUnit.BYTE));
		return Math.round(converter.getSize()) + " " + FilesListElementDTO.Size.SizeUnit.getTranslation(converter.getUnit());
	}
	
}
