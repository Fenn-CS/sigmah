package org.sigmah.shared.command;

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

import org.sigmah.shared.command.base.Command;
import org.sigmah.shared.dto.value.FileVersionDTO;
import org.sigmah.shared.file.FileSlice;

/**
 *
 * @author Raphaël Calabro (rcalabro@ideia.fr)
 */
public class DownloadSlice implements Command<FileSlice> {
	
	private int fileVersionId;
	private long offset;
	private int size;

	public DownloadSlice() {
	}

	public DownloadSlice(FileVersionDTO fileVersion, long offset, int size) {
		this.fileVersionId = fileVersion.getId();
		this.offset = offset;
		this.size = size;
	}

	public int getFileVersionId() {
		return fileVersionId;
	}

	public void setFileVersionId(int fileVersionId) {
		this.fileVersionId = fileVersionId;
	}
	
	public long getOffset() {
		return offset;
	}

	public void setOffset(long offset) {
		this.offset = offset;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}
}
