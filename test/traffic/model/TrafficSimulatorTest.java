/*******************************************************************************
 * Copyright (c) 2011 - Jochen Wuttke.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Jochen Wuttke (wuttkej@gmail.com) - initial API and implementation
 ********************************************************************************
 *
 * Created: Nov 29, 2011
 */

package traffic.model;

import java.io.FileNotFoundException;

import org.junit.Test;
import static org.junit.Assert.*;


/**
 * @author Jochen Wuttke - wuttkej@gmail.com
 *
 */
public class TrafficSimulatorTest {
	
	@Test(expected=FileNotFoundException.class)
	public void nonExistingFileThrows() {
		new TrafficSimulator( "bla" );
		fail( "Test should have thrown a FileNotFoundException" );
	}

}
