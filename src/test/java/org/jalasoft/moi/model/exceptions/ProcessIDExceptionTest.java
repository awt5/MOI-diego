/*
 *  Copyright (c) 2020 Jalasoft.
 *
 *  This software is the confidential and proprietary information of Jalasoft.
 *  ("Confidential Information"). You shall not disclose such Confidential
 *  Information and shall use it only in accordance with the terms of the
 *  license agreement you entered into with Jalasoft.
 */

package org.jalasoft.moi.model.exceptions;

import org.jalasoft.moi.model.core.Executer;
import org.jalasoft.moi.model.interaction.ProcessCacheTest;
import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;

public class ProcessIDExceptionTest {

    private static ProcessCacheTest processCache;

    @BeforeAll
    static void initAll() {
        processCache = new ProcessCacheTest();
    }
/*
    @Test(expected = ProcessIDException.class)
    public void throwsExceptionWhenCommandNullTest() throws CommandBuildException, ResultException, ProcessIDException {
        Executer executer = new Executer(processCache);
        executer.execute(null);
    }

    @Test(expected = ProcessIDException.class)
    public void throwsExceptionWhenCommandInvalidTest() throws CommandBuildException, ResultException, ProcessIDException {
        Executer executer = new Executer(processCache);
        executer.execute("wrong");
    }*/
}
