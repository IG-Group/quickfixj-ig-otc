/*******************************************************************************
 * Copyright (c) quickfixengine.org  All rights reserved.
 * <p>
 * This file is part of the QuickFIX FIX Engine
 * <p>
 * This file may be distributed under the terms of the quickfixengine.org
 * license as defined by quickfixengine.org and appearing in the file
 * LICENSE included in the packaging of this file.
 * <p>
 * This file is provided AS IS with NO WARRANTY OF ANY KIND, INCLUDING
 * THE WARRANTY OF DESIGN, MERCHANTABILITY AND FITNESS FOR A
 * PARTICULAR PURPOSE.
 * <p>
 * See http://www.quickfixengine.org/LICENSE for licensing information.
 * <p>
 * Contact ask@quickfixengine.org if any conditions of this licensing
 * are not clear to you.
 ******************************************************************************/

package quickfix;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.InputStream;

import org.junit.Test;

public class FileUtilTest {

    @Test
    public void testFileLocation() throws Exception {
        // Assumption: current directory is QFJ project base directory
        InputStream in = FileUtil.open(null, "LICENSE");
        in.close();
        assertNotNull("File not found", in);
    }

    @Test
    public void testClassResourceLocation() throws Exception {
        InputStream in = FileUtil.open(Message.class, "Session.class");
        in.close();
        assertNotNull("Resource not found", in);
    }

    @Test
    public void testClassLoaderResourceLocation() throws Exception {
        InputStream in = FileUtil.open(Message.class, "quickfix/test/acceptance/definitions/client/Normal.def");
        in.close();
        assertNotNull("Resource not found", in);
    }

    @Test
    // QFJ-775
    public void testSessionIDFileName() {
        SessionID sessionID = new SessionID(FixVersions.BEGINSTRING_FIX44, "SENDER???",
                "bla_/--/#()_bla", "!!!TARGET", "foo::bar");
        String sessionIdFileName = FileUtil.sessionIdFileName(sessionID);
        assertEquals("FIX.4.4-SENDER____bla__--_____bla-___TARGET_foo__bar", sessionIdFileName);
        assertTrue(sessionIdFileName.matches("[a-zA-Z0-9-._]*"));

        sessionID = new SessionID(FixVersions.BEGINSTRING_FIX44, "SENDER", "TARGET");
        sessionIdFileName = FileUtil.sessionIdFileName(sessionID);
        assertEquals("FIX.4.4-SENDER-TARGET", sessionIdFileName);
    }
}
