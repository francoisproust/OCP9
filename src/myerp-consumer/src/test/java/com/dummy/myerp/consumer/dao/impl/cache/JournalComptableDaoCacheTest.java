package com.dummy.myerp.consumer.dao.impl.cache;

import com.dummy.myerp.consumer.ConsumerHelper;
import com.dummy.myerp.consumer.dao.contrat.ComptabiliteDao;
import com.dummy.myerp.consumer.dao.contrat.DaoProxy;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(PowerMockRunner.class)
@PrepareForTest(ConsumerHelper.class)
public class JournalComptableDaoCacheTest {
    @Mock
    DaoProxy daoProxy;

    @Mock
    ComptabiliteDao comptabiliteDao;

    @InjectMocks
    JournalComptableDaoCache journalComptableDaoCache;

    @Test
    public void testGetByCodeUseCacheAfterFisrtCall() {
        //Arrange
        PowerMockito.mockStatic(ConsumerHelper.class);
        when(ConsumerHelper.getDaoProxy()).thenReturn(daoProxy);
        when(daoProxy.getComptabiliteDao()).thenReturn(comptabiliteDao);
        PowerMockito.spy(journalComptableDaoCache);
        int nbAppel = 2;

        //Act
        int countAppel = 0;
        for (int i = 0; i < nbAppel; i++) {
            journalComptableDaoCache.getByCode("code");
            countAppel++;
        }

        //Assert
        assertEquals("Test nombre d'appel au cache Journal Comptable", nbAppel, countAppel);
        verify(comptabiliteDao, times(1)).getListJournalComptable();
    }
}