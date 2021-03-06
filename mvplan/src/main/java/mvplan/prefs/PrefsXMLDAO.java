/*
 * PrefsDAO.java
 * 
 *   Provides Data Access to Prefs objects. Persists Prefs object to XML using the
 *   XMLEncoder objects.
 *
 *   @author Guy Wittig
 *   @version 18-Jun-2006
 *
 *   This program is part of MV-Plan
 *   Copywrite 2006 Guy Wittig
 *
 *   This program is free software; you can redistribute it and/or modify
 *   it under the terms of the GNU General Public License as published by
 *   the Free Software Foundation; either version 2 of the License, or
 *   (at your option) any later version.
 *
 *   This program is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of 
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU General Public License for more details.
 *
 *   The GNU General Public License can be read at http://www.gnu.org/licenses/licenses.html
 */
package mvplan.prefs;

import java.io.*;
import java.beans.*;
import mvplan.prefs.Prefs;

/**
 * This implementation of Prefs dao is saving preferences to xml file , it uses
 * XMLEncoder and XMLDecoder to save preferences .  
 * 
 * 
 * @author Guy
 */
public class PrefsXMLDAO implements PrefsDAO {
	private final String fileName;

	/**
	 * Creates a new instance of PrefsXMLDAO 
	 * @param filename filename where preferences will be saved or loaded
	 */
	public PrefsXMLDAO(String filename) {
		this.fileName = filename;
	}

	/**
	 * Save Prefs object to XML file using XMLEncoder
	 */
	public void savePrefs(Prefs p) throws PrefsException {
		if (fileName == null) {
			throw new PrefsException("Could not save preferences");
		}
		try {
			XMLEncoder encoder = new XMLEncoder(new BufferedOutputStream(
					new FileOutputStream(fileName)));
			encoder.writeObject(p);
			encoder.close();
		} catch (FileNotFoundException e) {
			throw new PrefsException("Could not save preferences", e);
		} catch (SecurityException e) {
			throw new PrefsException("Could not save preferences", e);
		}

	}

	/**
	 * Load Prefs object from XML file using XMLDecoder
	 */
	public Prefs loadPrefs() throws PrefsException {
		if (fileName == null) {
			throw new PrefsException("Could not save preferences");
		}
		try {
			XMLDecoder decoder = new XMLDecoder(new BufferedInputStream(
					new FileInputStream(fileName)));
			Prefs p = (Prefs) decoder.readObject();
			decoder.close();
			p.validatePrefs(); // Check that all is within bounds
			return p;

		} catch (FileNotFoundException e) {
			throw new PrefsException("Could not save preferences", e);
		}
	}

}
