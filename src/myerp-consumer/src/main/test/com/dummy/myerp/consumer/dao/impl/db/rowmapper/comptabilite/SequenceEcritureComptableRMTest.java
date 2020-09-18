package com.dummy.myerp.consumer.dao.impl.db.rowmapper.comptabilite;

import com.dummy.myerp.model.bean.comptabilite.SequenceEcritureComptable;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.powermock.modules.junit4.PowerMockRunner;

import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.Assert.*;
import static org.powermock.api.mockito.PowerMockito.doReturn;

@RunWith(PowerMockRunner.class)
public class SequenceEcritureComptableRMTest {

    @Mock
    ResultSet resultSet;

    @InjectMocks
    SequenceEcritureComptableRM sequenceEcritureComptableRM;

    @Test
    public void testSequenceEcritureComptablemapRow() throws SQLException {
        //Arrange
        doReturn("TI").when(resultSet).getString("journal_code");
        doReturn(2020).when(resultSet).getInt("annee");
        doReturn(25).when(resultSet).getInt("derniere_valeur");

        //Act
        SequenceEcritureComptable sequenceEcritureComptable = sequenceEcritureComptableRM.mapRow(resultSet, 1);

        //Assert
        assertEquals("Test Sequence Ecriture Comptable Mapping journal_code", "TI", sequenceEcritureComptable.getJournalCode());
        assertEquals("Test Sequence Ecriture Comptable Mapping annee", Integer.valueOf(2020), sequenceEcritureComptable.getAnnee());
        assertEquals("Test Sequence Ecriture Comptable Mapping derniere_valeur", Integer.valueOf(25), sequenceEcritureComptable.getDerniereValeur());
    }
}