package com.dummy.myerp.testbusiness.business;

import com.dummy.myerp.business.contrat.manager.ComptabiliteManager;
import com.dummy.myerp.business.impl.BusinessProxyImpl;
import com.dummy.myerp.business.impl.TransactionManager;
import com.dummy.myerp.consumer.dao.contrat.DaoProxy;
import com.dummy.myerp.model.bean.comptabilite.CompteComptable;
import com.dummy.myerp.model.bean.comptabilite.EcritureComptable;
import com.dummy.myerp.model.bean.comptabilite.JournalComptable;
import com.dummy.myerp.model.bean.comptabilite.LigneEcritureComptable;
import com.dummy.myerp.technical.exception.FunctionalException;
import com.dummy.myerp.technical.exception.NotFoundException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.AFTER_TEST_METHOD;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;

@RunWith(SpringRunner.class)
@ContextConfiguration("classpath:bootstrapContext.xml")
public class BusinessIT {

    @Autowired
    BusinessProxyImpl businessProxy;
    @Autowired
    DaoProxy daoProxy;
    @Autowired
    TransactionManager transactionManager;
    private ComptabiliteManager comptabiliteManager;

    @Before
    public void setUp() {
        comptabiliteManager = businessProxy.getComptabiliteManager();
    }

    @Test
    public void testGetListCompteComptable() {
        //Arrange - //Act
        List<CompteComptable> compteComptableList = comptabiliteManager.getListCompteComptable();

        //Assert
        assertFalse("TI Business list Compte Comptable", compteComptableList.isEmpty());
    }

    @Test
    public void testGetListJournalComptable() {
        //Arrange - //Act
        List<JournalComptable> journalComptableList = comptabiliteManager.getListJournalComptable();

        //Assert
        assertFalse("TI Business list Journal Comptable", journalComptableList.isEmpty());
    }

    @Test
    public void testGetListEcritureComptable() {
        //Arrange - //Act
        List<EcritureComptable> ecritureComptableList = comptabiliteManager.getListEcritureComptable();

        //Assert
        assertFalse("TI Business list Ecriture Comptable", ecritureComptableList.isEmpty());
    }

    @Test
    @Sql(executionPhase = BEFORE_TEST_METHOD, scripts = "sql/init_IT_addReferenceAndInsertSequence.sql")
    @Sql(executionPhase = AFTER_TEST_METHOD, scripts = "sql/clean_IT_addReferenceAndInsertSequence.sql")
    public void testAddReferenceAndInsertSequence() {
        //Arrange
        EcritureComptable ecritureComptable = new EcritureComptable();
        ecritureComptable.setDate(new Date());
        ecritureComptable.setJournal(comptabiliteManager.getListJournalComptable().get(0));
        assertNull(daoProxy.getComptabiliteDao().getDernierNumeroSequenceComptableByJournalAndAnnee(ecritureComptable.getJournal().getCode(), Integer.valueOf(new SimpleDateFormat("yyyy").format(ecritureComptable.getDate()))));

        //Act
        comptabiliteManager.addReference(ecritureComptable);

        //Assert
        assertNotNull("TI business add reference with new sequence", ecritureComptable.getReference());
        assertEquals("TI business add reference with new sequence value 1", Integer.valueOf(1), daoProxy.getComptabiliteDao().getDernierNumeroSequenceComptableByJournalAndAnnee(ecritureComptable.getJournal().getCode(), Integer.valueOf(new SimpleDateFormat("yyyy").format(ecritureComptable.getDate()))));
    }

    @Test
    @Sql(executionPhase = BEFORE_TEST_METHOD, scripts = "sql/init_IT_addReferenceAndUpdateSequence.sql")
    @Sql(executionPhase = AFTER_TEST_METHOD, scripts = "sql/clean_IT_addReferenceAndUpdateSequence.sql")
    public void testAddReferenceAndUpdateSequence() {
        //Arrange
        EcritureComptable ecritureComptable = new EcritureComptable();
        ecritureComptable.setDate(new Date());
        ecritureComptable.setJournal(comptabiliteManager.getListJournalComptable().get(0));
        assertEquals(Integer.valueOf(225), daoProxy.getComptabiliteDao().getDernierNumeroSequenceComptableByJournalAndAnnee(ecritureComptable.getJournal().getCode(), Integer.valueOf(new SimpleDateFormat("yyyy").format(ecritureComptable.getDate()))));


        //Act
        comptabiliteManager.addReference(ecritureComptable);

        //Assert
        assertNotNull("TI business add reference and update sequence", ecritureComptable.getReference());
        assertEquals("TI business add reference with new sequence value 226", Integer.valueOf(226), daoProxy.getComptabiliteDao().getDernierNumeroSequenceComptableByJournalAndAnnee(ecritureComptable.getJournal().getCode(), Integer.valueOf(new SimpleDateFormat("yyyy").format(ecritureComptable.getDate()))));
    }

    @Test
    public void testCheckEcritureComptableContextOK() throws FunctionalException {
        //Arrange
        EcritureComptable vEcritureComptable = new EcritureComptable();
        vEcritureComptable.setJournal(new JournalComptable("AC", "Achat"));
        vEcritureComptable.setDate(new Date());
        vEcritureComptable.setLibelle("Libelle");
        vEcritureComptable.setReference("AC-2020/00001");
        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(1, "TU"),
                null, new BigDecimal(123),
                null));
        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(2, "TU"),
                null, null,
                new BigDecimal(123)));

        //Act - //Assert
        comptabiliteManager.checkEcritureComptable(vEcritureComptable);
    }

    @Test(expected = FunctionalException.class)
    public void testCheckEcritureComptableContextIdNullRefExists() throws FunctionalException {
        //Arrange
        EcritureComptable vEcritureComptable = comptabiliteManager.getListEcritureComptable().get(0);
        vEcritureComptable.setId(null);

        //Act - //Assert
        comptabiliteManager.checkEcritureComptable(vEcritureComptable);
    }

    @Test(expected = FunctionalException.class)
    public void testCheckEcritureComptableContextIdNotNullRefExists() throws FunctionalException {
        //Arrange
        EcritureComptable vEcritureComptable = comptabiliteManager.getListEcritureComptable().get(0);
        vEcritureComptable.setId(vEcritureComptable.getId() + 1);

        //Act - //Assert
        comptabiliteManager.checkEcritureComptable(vEcritureComptable);
    }

    @Test
    @Sql(executionPhase = BEFORE_TEST_METHOD, scripts = "sql/init_IT_insertEcritureComptable.sql")
    @Sql(executionPhase = AFTER_TEST_METHOD, scripts = "sql/clean_IT_insertEcritureComptable.sql")
    public void testInsertEcritureComptable() throws FunctionalException {
        //Arrange
        EcritureComptable vEcritureComptable = generateEcritureForTestWithParamLibelle("TI business insert");

        //Act
        comptabiliteManager.insertEcritureComptable(vEcritureComptable);

        //Assert
        try {
            EcritureComptable ecritureComptable = daoProxy.getComptabiliteDao().getEcritureComptableByRef(vEcritureComptable.getReference());
            assertEquals("TI Business insert Ecriture libelle", "TI business insert", ecritureComptable.getLibelle());
            assertEquals("TI Business insert Ecriture reference", "AC-2020/00001", ecritureComptable.getReference());
            assertEquals("TI Business insert Ecriture lignes libelle", "TI business insert", ecritureComptable.getListLigneEcriture().get(0).getLibelle());
        } catch (NotFoundException e) {
            fail("L'ecriture comptable n'a pas été parsistée");
        }
    }

    @Test(expected = NotFoundException.class)
    @Sql(executionPhase = BEFORE_TEST_METHOD, scripts = "sql/init_IT_insertEcritureComptable.sql")
    @Sql(executionPhase = AFTER_TEST_METHOD, scripts = "sql/clean_IT_insertEcritureComptable.sql")
    public void testFailedInsertEcritureComptable() throws NotFoundException {
        //Arrange
        EcritureComptable vEcritureComptable = generateEcritureForTestWithParamLibelle("TI business insert");
        vEcritureComptable.getListLigneEcriture().get(0).setCompteComptable(new CompteComptable(0, "TU"));

        //Act - //Arrange
        try {
            comptabiliteManager.insertEcritureComptable(vEcritureComptable);
        } catch (Exception e) {
            EcritureComptable ecritureComptable = daoProxy.getComptabiliteDao().getEcritureComptableByRef(vEcritureComptable.getReference());
            assertNull("TI business insert ecriteure fail", ecritureComptable);
        }
    }

    @Test
    @Sql(executionPhase = BEFORE_TEST_METHOD, scripts = "sql/init_IT_updateEcritureComptable.sql")
    @Sql(executionPhase = AFTER_TEST_METHOD, scripts = "sql/clean_IT_updateEcritureComptable.sql")
    public void testUpdateEcritureComptable() throws FunctionalException {
        //Arrange
        EcritureComptable vEcritureComptable = generateEcritureForTestWithParamLibelle("TI business not update");
        comptabiliteManager.insertEcritureComptable(vEcritureComptable);
        vEcritureComptable.setLibelle("TI business updated");
        for (LigneEcritureComptable l : vEcritureComptable.getListLigneEcriture()) {
            l.setLibelle("TI business updated");
        }

        //Act
        comptabiliteManager.updateEcritureComptable(vEcritureComptable);

        //Assert
        try {
            EcritureComptable ecritureComptable = daoProxy.getComptabiliteDao().getEcritureComptableByRef(vEcritureComptable.getReference());
            assertEquals("TI Business update Ecriture libelle", "TI business updated", ecritureComptable.getLibelle());
            assertEquals("TI Business update Ecriture reference", "AC-2020/00001", ecritureComptable.getReference());
            for (LigneEcritureComptable l : ecritureComptable.getListLigneEcriture()) {
                assertEquals("TI Business update Ecriture lignes libelle", "TI business updated", l.getLibelle());
            }
        } catch (NotFoundException e) {
            fail("L'ecriture comptable n'a pas été deleté à tort");
        }
    }

    @Test
    @Sql(executionPhase = BEFORE_TEST_METHOD, scripts = "sql/init_IT_updateEcritureComptable.sql")
    @Sql(executionPhase = AFTER_TEST_METHOD, scripts = "sql/clean_IT_updateEcritureComptable.sql")
    public void testFailUpdateEcritureComptable() throws FunctionalException, NotFoundException {
        //Arrange
        EcritureComptable vEcritureComptable = generateEcritureForTestWithParamLibelle("TI business not update");
        comptabiliteManager.insertEcritureComptable(vEcritureComptable);
        vEcritureComptable.setLibelle("TI business updated");
        for (LigneEcritureComptable l : vEcritureComptable.getListLigneEcriture()) {
            l.setLibelle("TI business updated");
            l.setCompteComptable(new CompteComptable(0, "TU"));
        }

        //Act - //Assert
        try {
            comptabiliteManager.updateEcritureComptable(vEcritureComptable);
        } catch (Exception e) {
            EcritureComptable ecritureComptable = daoProxy.getComptabiliteDao().getEcritureComptableByRef(vEcritureComptable.getReference());
            assertEquals("TI Business update Ecriture libelle not modified", "TI business not update", ecritureComptable.getLibelle());
            assertEquals("TI Business update Ecriture reference not modified", "AC-2020/00001", ecritureComptable.getReference());
            for (LigneEcritureComptable l : ecritureComptable.getListLigneEcriture()) {
                assertEquals("TI Business update Ecriture lignes libelle not modified", "TI business not update", l.getLibelle());
            }
        }
    }

    @Test
    @Sql(executionPhase = BEFORE_TEST_METHOD, scripts = "sql/init_IT_deleteEcritureComptable.sql")
    @Sql(executionPhase = AFTER_TEST_METHOD, scripts = "sql/clean_IT_deleteEcritureComptable.sql")
    public void testDeleteEcritureComptable() throws FunctionalException {
        //Arrange
        EcritureComptable vEcritureComptable = generateEcritureForTestWithParamLibelle("TI business delete");
        comptabiliteManager.insertEcritureComptable(vEcritureComptable);
        EcritureComptable ecritureComptable = null;
        try {
            ecritureComptable = daoProxy.getComptabiliteDao().getEcritureComptableByRef(vEcritureComptable.getReference());
        } catch (Exception e) {
            fail("L'ecriture test n'a pas été initalisée");
        }

        //Act
        comptabiliteManager.deleteEcritureComptable(ecritureComptable.getId());

        //Arrange
        try {
            assertNull(daoProxy.getComptabiliteDao().getEcritureComptableByRef(vEcritureComptable.getReference()));
        } catch (NotFoundException ex) {
            assertEquals("TI businees delete ecriture not found", "EcritureComptable non trouvée : reference=AC-2020/00001", ex.getMessage());
        }
    }

    private EcritureComptable generateEcritureForTestWithParamLibelle(String pLibelle) {
        EcritureComptable vEcritureComptable = new EcritureComptable();
        CompteComptable compteComptable = daoProxy.getComptabiliteDao().getListCompteComptable().get(0);
        vEcritureComptable.setJournal(new JournalComptable("AC", "Achat"));
        vEcritureComptable.setDate(new Date());
        vEcritureComptable.setLibelle(pLibelle);
        vEcritureComptable.setReference("AC-2020/00001");
        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(compteComptable,
                pLibelle, new BigDecimal(123),
                null));
        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(compteComptable,
                pLibelle, null,
                new BigDecimal(123)));
        return vEcritureComptable;
    }
}