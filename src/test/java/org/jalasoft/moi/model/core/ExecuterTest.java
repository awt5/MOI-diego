/*
 *  Copyright (c) 2020 Jalasoft.
 *
 *  This software is the confidential and proprietary information of Jalasoft.
 *  ("Confidential Information"). You shall not disclose such Confidential
 *  Information and shall use it only in accordance with the terms of the
 *  license agreement you entered into with Jalasoft.
 */

package org.jalasoft.moi.model.core;

import org.jalasoft.moi.model.core.parameters.Result;
import org.jalasoft.moi.model.exceptions.CommandBuildException;
import org.jalasoft.moi.model.exceptions.ProcessIDException;
import org.jalasoft.moi.model.exceptions.ResultException;
import org.jalasoft.moi.model.interaction.ProcessCacheTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class ExecuterTest {
    private static ProcessCacheTest processCache;

    @BeforeAll
    static void initAll() {
        processCache = new ProcessCacheTest();
    }
/*
    @Test
    public void givenTestParamAndHandlerWhenExecuteParamThenReceiveTheExpectedOutput() throws CommandBuildException, ResultException, ProcessIDException {
        //given
        String expectedResult = "Tested\r\n";
        Result currentResult;
        Executer testExecute = new Executer(processCache);
        //when
        currentResult = testExecute.execute("echo Tested");
        //then
        assertEquals(expectedResult, currentResult.getValue());
    }

    @org.junit.Test(expected = ProcessIDException.class)
    public void throwsExceptionWhenCommandNullTest() throws CommandBuildException, ResultException, ProcessIDException {
        Executer executer = new Executer(processCache);
        executer.execute(null);
    }*/
}
