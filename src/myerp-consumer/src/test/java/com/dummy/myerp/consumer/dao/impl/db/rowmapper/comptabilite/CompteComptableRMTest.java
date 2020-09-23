package com.dummy.myerp.consumer.dao.impl.db.rowmapper.comptabilite;

import com.dummy.myerp.model.bean.comptabilite.CompteComptable;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.powermock.modules.junit4.PowerMockRunner;

import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;

@RunWith(PowerMockRunner.class)
public class CompteComptableRMTest {

    @Mock
    ResultSet resultSet;

    @InjectMocks
    CompteComptableRM compteComptableRM;

    @Test
    public void testCompteComptableRowMapping() throws SQLException {
        //Arrange
        doReturn(123).when(resultSet).getInt(anyString());
        doReturn("Libelle du compte").when(resultSet).getString(anyString());

        //Act
        CompteComptable compteComptable = compteComptableRM.mapRow(resultSet, 1);

        //Assert
        assertEquals("Test Compte Comptable Mapping Numero", Integer.valueOf(123), compteComptable.getNumero());
        assertEquals("Test Compte Comptable Mapping Libelle", "Libelle du compte", compteComptable.getLibelle());
    }
}