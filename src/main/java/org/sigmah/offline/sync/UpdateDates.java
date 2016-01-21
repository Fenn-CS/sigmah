package org.sigmah.offline.sync;

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

import com.google.gwt.storage.client.Storage;
import java.util.Date;
import org.sigmah.shared.command.result.Authentication;

/**
 * Handle saving and loading update dates of Offline Mode data.
 * 
 * @author Raphaël Calabro (rcalabro@ideia.fr)
 */
public final class UpdateDates {
    
    private static final String ITEM_DATABASE_UPDATE_DATE = ".database-update-date";
    private static final String ITEM_SIGMAH_UPDATE_DATE = "sigmah.update-date";
    
    private UpdateDates() {
    }
    
    public static Date getDatabaseUpdateDate(Authentication authentication) {
        Date updateDate = null;
        
        final Storage storage = Storage.getLocalStorageIfSupported();
        if(authentication != null && storage != null) {
            final String date = storage.getItem(authentication.getUserEmail() + ITEM_DATABASE_UPDATE_DATE);
            if(date != null) {
                updateDate = new Date(Long.parseLong(date));
            }
        }

        return updateDate;
    }
    
    public static void setDatabaseUpdateDate(Authentication authentication, Date date) {
		if(authentication != null) {
			setDatabaseUpdateDate(authentication.getUserEmail(), date);
		}
	}
	
    public static void setDatabaseUpdateDate(String email, Date date) {
        final Storage storage = Storage.getLocalStorageIfSupported();
        if(storage != null) {
            final String item = email + ITEM_DATABASE_UPDATE_DATE;
            
            if(date != null) {
                storage.setItem(item, Long.toString(date.getTime()));
            } else {
                storage.removeItem(item);
            }
        }
    }
    
    public static Date getSigmahUpdateDate() {
        Date updateDate = null;
        
        final Storage storage = Storage.getLocalStorageIfSupported();
        if(storage != null) {
            final String date = storage.getItem(ITEM_SIGMAH_UPDATE_DATE);
            if(date != null) {
                updateDate = new Date(Long.parseLong(date));
            }
        }
        
        return updateDate;
    }
    
    public static void setSigmahUpdateDate(Date date) {
        final Storage storage = Storage.getLocalStorageIfSupported();
        if(storage != null) {
            storage.setItem(ITEM_SIGMAH_UPDATE_DATE, Long.toString(date.getTime()));
        }
    }
}
