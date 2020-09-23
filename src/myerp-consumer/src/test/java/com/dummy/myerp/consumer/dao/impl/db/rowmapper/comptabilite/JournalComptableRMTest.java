package com.dummy.myerp.consumer.dao.impl.db.rowmapper.comptabilite;

import com.dummy.myerp.model.bean.comptabilite.JournalComptable;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.powermock.modules.junit4.PowerMockRunner;

import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doReturn;

@RunWith(PowerMockRunner.class)
public class JournalComptableRMTest {

    @Mock
    ResultSet resultSet;

    @InjectMocks
    JournalComptableRM journalComptableRM;

    @Test
    public void testJournalComptableRowMapping() throws SQLException {
        //Arrange
        doReturn("Code du journal").when(resultSet).getString("code");
        doReturn("Libelle du journal").when(resultSet).getString("libelle");

        //Act
        JournalComptable journalComptable = journalComptableRM.mapRow(resultSet, 1);

        //Assert
        assertEquals("Test Journal Comptable Mapping Code", "Code du journal", journalComptable.getCode());
        assertEquals("Test Journal Comptable Mapping libelle", "Libelle du journal", journalComptable.getLibelle());
    }
}