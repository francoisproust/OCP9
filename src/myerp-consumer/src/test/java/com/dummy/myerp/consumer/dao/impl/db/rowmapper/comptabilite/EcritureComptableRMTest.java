package com.dummy.myerp.consumer.dao.impl.db.rowmapper.comptabilite;

import com.dummy.myerp.consumer.ConsumerHelper;
import com.dummy.myerp.consumer.dao.contrat.ComptabiliteDao;
import com.dummy.myerp.consumer.dao.contrat.DaoProxy;
import com.dummy.myerp.consumer.dao.impl.cache.JournalComptableDaoCache;
import com.dummy.myerp.model.bean.comptabilite.EcritureComptable;
import com.dummy.myerp.model.bean.comptabilite.JournalComptable;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest(ConsumerHelper.class)
public class EcritureComptableRMTest {

    @Mock
    ResultSet resultSet;

    @Mock
    DaoProxy daoProxy;

    @Mock
    ComptabiliteDao comptabiliteDao;
    @Mock
    JournalComptableDaoCache journalComptableDaoCache;

    @InjectMocks
    EcritureComptableRM ecritureComptableRM;

    @Test
    public void testEcritureComptableRowMapping() throws SQLException {
        //Arrange
        PowerMockito.mockStatic(ConsumerHelper.class);

        doReturn(123).when(resultSet).getInt(anyString());
        doReturn("ref test").when(resultSet).getString("reference");
        Date date = Date.valueOf("2020-04-28");
        doReturn(date).when(resultSet).getDate("date");
        doReturn("Libelle test").when(resultSet).getString("libelle");

        doReturn("journal_code").when(resultSet).getString("journal_code");
        JournalComptable journalComptable = new JournalComptable("TU", "Test Unitaire");
        doReturn(journalComptable).when(journalComptableDaoCache).getByCode(anyString());

        when(ConsumerHelper.getDaoProxy()).thenReturn(daoProxy);
        when(daoProxy.getComptabiliteDao()).thenReturn(comptabiliteDao);

        //Act
        EcritureComptable ecritureComptable = ecritureComptableRM.mapRow(resultSet, 1);

        //Assert
        assertEquals("Test Ecriture Comptable Mapping Id", Integer.valueOf(123), ecritureComptable.getId());
        assertEquals("Test Ecriture Comptable Mapping journal", journalComptable, ecritureComptable.getJournal());
        assertEquals("Test Ecriture Comptable Mapping reference", "ref test", ecritureComptable.getReference());
        assertEquals("Test Ecriture Comptable Mapping date", date, ecritureComptable.getDate());
        assertEquals("Test Ecriture Comptable Mapping libelle", "Libelle test", ecritureComptable.getLibelle());
    }
}