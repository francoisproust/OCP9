package com.dummy.myerp.technical.exception;

import org.junit.Assert;
import org.junit.Test;

public class NotFoundExceptionTest {
    @Test
    public void contructNotFoundExceptionWithStringMessage() {
        //arrange
        String errorMessage = "String Error Message";

        //act
        NotFoundException notFoundException = new NotFoundException(errorMessage);

        //assert
        Assert.assertNull("Check construct without Throwable", notFoundException.getCause());
        Assert.assertEquals("Check Error Message", errorMessage, notFoundException.getMessage());
    }

    @Test
    public void contructNotFoundExceptionWithThrowableMessage() {
        //arrange
        String thrownMessage = "Throwable Error Message";
        Throwable throwable = new Throwable(thrownMessage);

        //act
        NotFoundException notFoundException = new NotFoundException(throwable);

        //assert
        Assert.assertNotNull("Check construct with Throwable", notFoundException.getCause());
        Assert.assertEquals("Check throwable", throwable, notFoundException.getCause());
        Assert.assertEquals("Check throwable message", thrownMessage, notFoundException.getCause().getMessage());
    }

    @Test
    public void contructNotFoundExceptionWithStringAndThrowableMessage() {
        //arrange
        String errorMessage = "String Error Message";
        String thrownMessage = "Throwable Error Message";
        Throwable throwable = new Throwable(thrownMessage);

        //act
        NotFoundException notFoundException = new NotFoundException(errorMessage, throwable);

        //assert
        Assert.assertEquals("Check Error Message", errorMessage, notFoundException.getMessage());
        Assert.assertNotNull("Check construct with Throwable", notFoundException.getCause());
        Assert.assertEquals("Check throwable", throwable, notFoundException.getCause());
        Assert.assertEquals("Check throwable message", thrownMessage, notFoundException.getCause().getMessage());
    }
}