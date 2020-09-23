package com.dummy.myerp.consumer.dao.impl.db.rowmapper.comptabilite;

import com.dummy.myerp.consumer.ConsumerHelper;
import com.dummy.myerp.consumer.dao.contrat.ComptabiliteDao;
import com.dummy.myerp.consumer.dao.contrat.DaoProxy;
import com.dummy.myerp.consumer.dao.impl.cache.CompteComptableDaoCache;
import com.dummy.myerp.model.bean.comptabilite.CompteComptable;
import com.dummy.myerp.model.bean.comptabilite.LigneEcritureComptable;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest(ConsumerHelper.class)
public class LigneEcritureComptableRMTest {

    @Mock
    ResultSet resultSet;

    @Mock
    CompteComptableDaoCache compteComptableDaoCache;

    @Mock
    DaoProxy daoProxy;

    @Mock
    ComptabiliteDao comptabiliteDao;

    @InjectMocks
    LigneEcritureComptableRM ligneEcritureComptableRM;

    @Test
    public void testLigneEcritureComptablemapRow() throws SQLException {
        //Arrange
        PowerMockito.mockStatic(ConsumerHelper.class);

        doReturn(1).when(resultSet).getObject("compte_comptable_numero", Integer.class);
        CompteComptable compteComptable = new CompteComptable(0, "TU");
        doReturn(compteComptable).when(compteComptableDaoCache).getByNumero(anyInt());
        BigDecimal creditTestValue = BigDecimal.valueOf(123);
        doReturn(creditTestValue).when(resultSet).getBigDecimal("credit");
        BigDecimal debitTestValue = BigDecimal.valueOf(-123);
        doReturn(debitTestValue).when(resultSet).getBigDecimal("debit");
        doReturn("Libelle du ligne comptable").when(resultSet).getString("libelle");

        when(ConsumerHelper.getDaoProxy()).thenReturn(daoProxy);
        when(daoProxy.getComptabiliteDao()).thenReturn(comptabiliteDao);
        //Act
        LigneEcritureComptable ligneEcritureComptable = ligneEcritureComptableRM.mapRow(resultSet, 1);

        //Assert
        assertEquals("Test Ligne Ecriture Comptable Mapping compte", compteComptable, ligneEcritureComptable.getCompteComptable());
        assertEquals("Test Ligne Ecriture Comptable Mapping credit", creditTestValue, ligneEcritureComptable.getCredit());
        assertEquals("Test Ligne Ecriture Comptable Mapping debit", debitTestValue, ligneEcritureComptable.getDebit());
        assertEquals("Test Ligne Ecriture Comptable Mapping libelle", "Libelle du ligne comptable", ligneEcritureComptable.getLibelle());
    }
}