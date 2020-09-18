package java.com.dummy.myerp.consumer.dao.impl.db.dao;

import com.dummy.myerp.consumer.dao.impl.db.dao.ComptabiliteDaoImpl;
import com.dummy.myerp.model.bean.comptabilite.*;
import com.dummy.myerp.technical.exception.NotFoundException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.AFTER_TEST_METHOD;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;

@RunWith(SpringRunner.class)
@ContextConfiguration("classpath:applicationContextIT.xml")
public class ComptabiliteDaoImplIT {

    @Autowired
    ComptabiliteDaoImpl comptabiliteDaoImpl;

    @Test
    public void testGetListCompteComptable() {
        //Act
        List<CompteComptable> compteComptableList = comptabiliteDaoImpl.getListCompteComptable();

        //Assert
        assertEquals("TI consumer list compte comptable", 7, compteComptableList.size());
    }

    @Test
    public void testGetListJournalComptable() {
        //Act
        List<JournalComptable> journalComptableList = comptabiliteDaoImpl.getListJournalComptable();

        //Assert
        assertEquals("TI consumer list journal comptable", 4, journalComptableList.size());
    }

    @Test
    public void testGetListEcritureComptable() {
        //Act
        List<EcritureComptable> ecritureComptableList = comptabiliteDaoImpl.getListEcritureComptable();

        //Assert
        assertFalse("TI consumer list ecriture comptable vide", ecritureComptableList.isEmpty());
    }

    @Test
    public void testGetEcritureComptableWithExistingId() {
        //Arrange
        Integer idEcritureComptable = -1;

        //Act
        EcritureComptable ecritureComptable = null;
        NotFoundException notFoundException = null;
        try {
            ecritureComptable = comptabiliteDaoImpl.getEcritureComptable(idEcritureComptable);
        } catch (NotFoundException e) {
            notFoundException = e;
        }

        //Assert
        assertNotNull("TI consumer get ecriture comptable by id", ecritureComptable);
        assertNull("TI consumer find ecriture comptable by id", notFoundException);
        String expectedLibelle = "AC-2016/00001";
        assertEquals("TI consumer get ecriture comptable reference", expectedLibelle, ecritureComptable.getReference());
    }

    @Test
    public void testGetEcritureComptableWithUnExistingId() {
        //Arrange
        Integer idEcritureComptable = 200;

        //Act
        EcritureComptable ecritureComptable = null;
        NotFoundException notFoundException = null;
        try {
            ecritureComptable = comptabiliteDaoImpl.getEcritureComptable(idEcritureComptable);
        } catch (NotFoundException e) {
            notFoundException = e;
        }

        //Assert
        assertNull("TI consumer not found ecriture comptable by ID", ecritureComptable);
        assertNotNull("TI consumer exception not found ecriture comptable by ID", notFoundException);
    }

    @Test
    public void testGetEcritureComptableWithExistingRef() {
        //Arrange
        String referenceComptable = "AC-2016/00001";

        //Act
        EcritureComptable ecritureComptable = null;
        NotFoundException notFoundException = null;
        try {
            ecritureComptable = comptabiliteDaoImpl.getEcritureComptableByRef(referenceComptable);
        } catch (NotFoundException e) {
            notFoundException = e;
        }

        //Assert
        assertNotNull("TI consumer get ecriture comptable by ref", ecritureComptable);
        assertNull("TI consumer find ecriture comptable by ref", notFoundException);
        String expectedLibelle = "AC-2016/00001";
        assertEquals("TI consumer get ecriture comptable ref", expectedLibelle, ecritureComptable.getReference());
    }

    @Test
    public void testGetEcritureComptableWithUnExistingRef() {
        //Arrange
        String referenceComptable = "Unexist";

        //Act
        EcritureComptable ecritureComptable = null;
        NotFoundException notFoundException = null;
        try {
            ecritureComptable = comptabiliteDaoImpl.getEcritureComptableByRef(referenceComptable);
        } catch (NotFoundException e) {
            notFoundException = e;
        }

        //Assert
        assertNull("TI consumer not found ecriture comptable by ref", ecritureComptable);
        assertNotNull("TI consumer exception not found ecriture comptable by ref", notFoundException);
    }

    @Test
    @Sql(executionPhase = BEFORE_TEST_METHOD, scripts = "classpath:/sql/clean_IT_insertEcritureComptable.sql")
    @Sql(executionPhase = AFTER_TEST_METHOD, scripts = "classpath:/sql/clean_IT_insertEcritureComptable.sql")
    public void testInsertEcritureComptable() throws NotFoundException {
        //Arrange
        EcritureComptable ecritureComptable = new EcritureComptable();
        ecritureComptable.setDate(new Date());
        ecritureComptable.setLibelle("TI comptabiliteDao insert");
        ecritureComptable.setReference("AC-2020/00001");
        ecritureComptable.setJournal(comptabiliteDaoImpl.getListJournalComptable().get(0));

        //Act
        comptabiliteDaoImpl.insertEcritureComptable(ecritureComptable);
        EcritureComptable insertedEcritureComptable = comptabiliteDaoImpl.getEcritureComptableByRef("AC-2020/00001");

        //Assert
        assertNotNull("TI consumer insert ecriture OK", insertedEcritureComptable);
        assertEquals("TI consumer insert ecriture check ref", "AC-2020/00001", insertedEcritureComptable.getReference());
        assertEquals("TI consumer insert ecriture check libelle", "TI comptabiliteDao insert", insertedEcritureComptable.getLibelle());
    }

    @Test
    @Sql(executionPhase = BEFORE_TEST_METHOD, scripts = "classpath:/sql/init_IT_updateEcritureComptable.sql")
    @Sql(executionPhase = AFTER_TEST_METHOD, scripts = "classpath:/sql/clean_IT_updateEcritureComptable.sql")
    public void testUpdateEcritureComptable() throws NotFoundException {
        //Arrange
        EcritureComptable ecritureComptable = comptabiliteDaoImpl.getEcritureComptableByRef("OD-2020/00001");
        ecritureComptable.setLibelle("TI comptabiliteDao update done");
        CompteComptable compteComptable = comptabiliteDaoImpl.getListCompteComptable().get(0);
        ecritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(compteComptable, "TI comptabiliteDao update", BigDecimal.ZERO, BigDecimal.valueOf(1)));
        ecritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(compteComptable, "TI comptabiliteDao update", BigDecimal.valueOf(1), BigDecimal.ZERO));

        //Act
        comptabiliteDaoImpl.updateEcritureComptable(ecritureComptable);
        EcritureComptable updatedtedEcritureComptable = comptabiliteDaoImpl.getEcritureComptableByRef("OD-2020/00001");

        //Assert
        assertNotNull("TI consumer update ecriture check update", updatedtedEcritureComptable);
        assertEquals("TI consumer update ecriture check reference", "OD-2020/00001", updatedtedEcritureComptable.getReference());
        assertEquals("TI consumer update ecriture check libelle", "TI comptabiliteDao update done", updatedtedEcritureComptable.getLibelle());
        assertEquals("TI consumer update ecriture check nombre ligne", 2, updatedtedEcritureComptable.getListLigneEcriture().size());
    }

    @Test(expected = NotFoundException.class)
    @Sql(executionPhase = BEFORE_TEST_METHOD, scripts = "classpath:/sql/init_IT_deleteEcritureComptable.sql")
    @Sql(executionPhase = AFTER_TEST_METHOD, scripts = "classpath:/sql/clean_IT_deleteEcritureComptable.sql")
    public void testDeleteEcritureComptable() throws NotFoundException {
        //Arrange
        EcritureComptable ecritureComptable = comptabiliteDaoImpl.getEcritureComptableByRef("OD-2020/00002");

        //Act
        comptabiliteDaoImpl.deleteEcritureComptable(ecritureComptable.getId());

        //Assert
        EcritureComptable deletedEcritureComptable = comptabiliteDaoImpl.getEcritureComptableByRef("OD-2020/00002");
        assertNull("TI consumer delete ecriture check delete", deletedEcritureComptable);
    }

    @Test
    @Sql(executionPhase = BEFORE_TEST_METHOD, scripts = "classpath:/sql/init_IT_lastValueSequenceEcritureComptable.sql")
    @Sql(executionPhase = AFTER_TEST_METHOD, scripts = "classpath:/sql/clean_IT_lastValueSequenceEcritureComptable.sql")
    public void testGetDernierValeurSequenceComptableForJounalAndAnneeWhenExists(){
        //Arrange
        String journalCode = "OD";
        Integer annee = 2000;

        //Act
        Integer derniere_valeur = comptabiliteDaoImpl.getDernierNumeroSequenceComptableByJournalAndAnnee(journalCode, annee);

        //Assert
        assertEquals("TI consumer sequence ecriture get dernire valeur pour un journal existant", Integer.valueOf(40),derniere_valeur);

    }

    @Test
    public void testGetDernierValeurSequenceComptableForJounalAndAnneeWhenNotExists(){
        //Arrange
        String journalCode = "NOT EXITS";
        Integer annee = 2000;

        //Act
        Integer derniere_valeur = comptabiliteDaoImpl.getDernierNumeroSequenceComptableByJournalAndAnnee(journalCode, annee);

        //Assert
        assertNull("TI consumer sequence ecriture get dernire valeur null pour un journal inexistant", derniere_valeur);

    }

    @Test
    @Sql(executionPhase = BEFORE_TEST_METHOD, scripts = "classpath:/sql/init_IT_insertSequenceEcritureComptable.sql")
    @Sql(executionPhase = AFTER_TEST_METHOD, scripts = "classpath:/sql/clean_IT_insertSequenceEcritureComptable.sql")
    public void testInsertSequenceEcritureComptable(){
        //Arrange
        String journalCode = "OD";
        Integer annee = 2000;
        assertNull(comptabiliteDaoImpl.getDernierNumeroSequenceComptableByJournalAndAnnee(journalCode, annee));
        SequenceEcritureComptable sequenceEcritureComptable = new SequenceEcritureComptable(journalCode, annee, 1);

        //Act
        comptabiliteDaoImpl.insertSequenceEcritureComptable(sequenceEcritureComptable);

        //Assert
        assertEquals("TI consumer sequence ecriture insert sequence journal", Integer.valueOf(1), comptabiliteDaoImpl.getDernierNumeroSequenceComptableByJournalAndAnnee(journalCode, annee));

    }

    @Test
    @Sql(executionPhase = BEFORE_TEST_METHOD, scripts = "classpath:/sql/init_IT_updateSequenceEcritureComptable.sql")
    @Sql(executionPhase = AFTER_TEST_METHOD, scripts = "classpath:/sql/clean_IT_updateSequenceEcritureComptable.sql")
    public void testUpdateSequenceEcritureComptable(){
        //Arrange
        String journalCode = "OD";
        Integer annee = 2000;
        assertNotEquals(Integer.valueOf(15), comptabiliteDaoImpl.getDernierNumeroSequenceComptableByJournalAndAnnee(journalCode, annee));
        SequenceEcritureComptable sequenceEcritureComptable = new SequenceEcritureComptable(journalCode, annee, 15);

        //Act
        comptabiliteDaoImpl.updateSequenceEcritureComptable(sequenceEcritureComptable);

        //Assert
        assertEquals("TI consumer sequence ecriture update sequence journal",Integer.valueOf(15) , comptabiliteDaoImpl.getDernierNumeroSequenceComptableByJournalAndAnnee(journalCode, annee));

    }
}