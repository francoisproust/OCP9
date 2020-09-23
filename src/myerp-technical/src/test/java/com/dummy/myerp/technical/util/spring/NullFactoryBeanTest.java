package com.dummy.myerp.technical.util.spring;

import org.junit.Assert;
import org.junit.Test;

public class NullFactoryBeanTest {

    @Test
    public void getInstanceNull(){
        //Arrange
        class NullBeanTest {}

        //act
        NullFactoryBean<NullBeanTest> nullBeanTest = new NullFactoryBean<>(NullBeanTest.class);

        //Act
        try {
            Assert.assertNull(nullBeanTest.getObject());
            Assert.assertNotNull(nullBeanTest.getObjectType());
            Assert.assertFalse(nullBeanTest.isSingleton());
        } catch (Exception e) {
            Assert.fail();
        }
    }
}