package com.dummy.myerp.consumer.db.helper;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.modules.junit4.PowerMockRunner;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.powermock.api.mockito.PowerMockito.doReturn;

@RunWith(PowerMockRunner.class)
public class ResultSetHelperTest {
    @Mock
    ResultSet resultSet;

    @Test
    public void testGetIntegerWithNotNullResultSet() throws SQLException {
        //Arrange
        doReturn(false).when(resultSet).wasNull();
        doReturn(1).when(resultSet).getInt(anyString());

        //Act
        Integer result = ResultSetHelper.getInteger(resultSet, "colonne");

        //Assert
        assertEquals("Test ResultSetHelper getInteger from column name in a resultset", Integer.valueOf(1), result);
    }

    @Test
    public void testGetIntegerWithNullResultSet() throws SQLException {
        //Arrange
        doReturn(true).when(resultSet).wasNull();
        doReturn(1).when(resultSet).getInt(anyString());

        //Act
        Integer result = ResultSetHelper.getInteger(resultSet, "colonne");

        //Assert
        assertNull("Test ResultSetHelper getInteger with empty resultset", result);
    }


    @Test
    public void testGetLongWithNotNullResultSet() throws SQLException {
        //Arrange
        doReturn(false).when(resultSet).wasNull();
        doReturn(1L).when(resultSet).getLong(anyString());

        //Act
        Long result = ResultSetHelper.getLong(resultSet, "colonne");

        //Assert
        assertEquals("Test ResultSetHelper getLong from column name in a resultset", Long.valueOf(1), result);
    }

    @Test
    public void testGetLongWithNullResultSet() throws SQLException {
        //Arrange
        doReturn(true).when(resultSet).wasNull();
        doReturn(1L).when(resultSet).getLong(anyString());

        //Act
        Long result = ResultSetHelper.getLong(resultSet, "colonne");

        //Assert
        assertNull("Test ResultSetHelper getInteger with empty resultset", result);
    }


    @Test
    public void testGetDateWithNotNullResultSet() throws SQLException {
        //Arrange
        java.sql.Date date = java.sql.Date.valueOf(LocalDate.now());
        date.setTime(System.currentTimeMillis());
        doReturn(date).when(resultSet).getDate(anyString());
        DateFormat formatter = new SimpleDateFormat("HH:mm:ss");

        //Act
        Date result = ResultSetHelper.getDate(resultSet, "colonne");

        //Assert
        assertNotNull("Test ResultSetHelper getDate from column name in a resultset", result);
        assertEquals("Test ResultSetHelper getDate from column name in a resultset truncate time", "00:00:00", formatter.format(result));
    }

    @Test
    public void testGetDateWithNullResultSet() throws SQLException {
        //Arrange
        doReturn(null).when(resultSet).getDate(anyString());

        //Act
        Date result = ResultSetHelper.getDate(resultSet, "colonne");

        //Assert
        assertNull("Test ResultSetHelper getDate with empty resultset", result);
    }
}