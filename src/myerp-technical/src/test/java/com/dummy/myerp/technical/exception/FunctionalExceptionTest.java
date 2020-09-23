package com.dummy.myerp.technical.exception;

import org.junit.Assert;
import org.junit.Test;

public class FunctionalExceptionTest {

    @Test
    public void contructFunctionalExceptionWithStringMessage() {
        //arrange
        String errorMessage = "String Error Message";

        //act
        FunctionalException functionalException = new FunctionalException(errorMessage);

        //assert
        Assert.assertNull("Check construct without Throwable", functionalException.getCause());
        Assert.assertEquals("Check Error Message", errorMessage, functionalException.getMessage());
    }

    @Test
    public void contructFunctionalExceptionWithThrowableMessage() {
        //arrange
        String thrownMessage = "Throwable Error Message";
        Throwable throwable = new Throwable(thrownMessage);

        //act
        FunctionalException functionalException = new FunctionalException(throwable);

        //assert
        Assert.assertNotNull("Check construct with Throwable", functionalException.getCause());
        Assert.assertEquals("Check throwable", throwable, functionalException.getCause());
        Assert.assertEquals("Check throwable message", thrownMessage, functionalException.getCause().getMessage());
    }

    @Test
    public void contructFunctionalExceptionWithStringAndThrowableMessage() {
        //arrange
        String errorMessage = "String Error Message";
        String thrownMessage = "Throwable Error Message";
        Throwable throwable = new Throwable(thrownMessage);

        //act
        FunctionalException functionalException = new FunctionalException(errorMessage, throwable);

        //assert
        Assert.assertEquals("Check Error Message", errorMessage, functionalException.getMessage());
        Assert.assertNotNull("Check construct with Throwable", functionalException.getCause());
        Assert.assertEquals("Check throwable", throwable, functionalException.getCause());
        Assert.assertEquals("Check throwable message", thrownMessage, functionalException.getCause().getMessage());
    }
}