package com.dummy.myerp.technical.exception;

import org.junit.Assert;
import org.junit.Test;

public class TechnicalExceptionTest {
    @Test
    public void contructTechnicalExceptionWithStringMessage() {
        //arrange
        String errorMessage = "String Error Message";

        //act
        TechnicalException technicalException = new TechnicalException(errorMessage);

        //assert
        Assert.assertNull("Check construct without Throwable", technicalException.getCause());
        Assert.assertEquals("Check Error Message", errorMessage, technicalException.getMessage());
    }

    @Test
    public void contructTechnicalExceptionWithThrowableMessage() {
        //arrange
        String thrownMessage = "Throwable Error Message";
        Throwable throwable = new Throwable(thrownMessage);

        //act
        TechnicalException technicalException = new TechnicalException(throwable);

        //assert
        Assert.assertNotNull("Check construct with Throwable", technicalException.getCause());
        Assert.assertEquals("Check throwable", throwable, technicalException.getCause());
        Assert.assertEquals("Check throwable message", thrownMessage, technicalException.getCause().getMessage());
    }

    @Test
    public void contructTechnicalExceptionWithStringAndThrowableMessage() {
        //arrange
        String errorMessage = "String Error Message";
        String thrownMessage = "Throwable Error Message";
        Throwable throwable = new Throwable(thrownMessage);

        //act
        TechnicalException technicalException = new TechnicalException(errorMessage, throwable);

        //assert
        Assert.assertEquals("Check Error Message", errorMessage, technicalException.getMessage());
        Assert.assertNotNull("Check construct with Throwable", technicalException.getCause());
        Assert.assertEquals("Check throwable", throwable, technicalException.getCause());
        Assert.assertEquals("Check throwable message", thrownMessage, technicalException.getCause().getMessage());
    }
}