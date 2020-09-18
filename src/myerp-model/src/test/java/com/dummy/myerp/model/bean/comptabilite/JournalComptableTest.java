package com.dummy.myerp.model.bean.comptabilite;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static com.dummy.myerp.model.bean.comptabilite.JournalComptable.getByCode;
import static org.junit.Assert.*;

public class JournalComptableTest {

    @Test
    public void testConstruteurWithAllParamaters(){
        //Arrange
        String codeJournalComptable = "1";
        String libelleJournalComptable = "test constructeur";

        //Act
        JournalComptable journalComptable = new JournalComptable(codeJournalComptable, libelleJournalComptable);

        //Assert
        assertEquals(codeJournalComptable,journalComptable.getCode());
        assertEquals(libelleJournalComptable, journalComptable.getLibelle());
        assertEquals("JournalComptable{code='1', libelle='test constructeur'}", journalComptable.toString());
    }

    @Test
    public void getJournalComptableWhenGetByCodeInListContainingTheExpectedJournal(){
        //Arrange
        List<JournalComptable> journalComptableList = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            journalComptableList.add(new JournalComptable("AC-"+i, "Journal Comptable "+i));
        }
        String expectedCodeJournalComptable = "AC-4";

        //Act
        JournalComptable resultJournalComptable = getByCode(journalComptableList, expectedCodeJournalComptable);

        //Assert
        assertEquals("JournalComptable{code='AC-4', libelle='Journal Comptable 4'}", resultJournalComptable.toString());
    }

    @Test
    public void getJournalComptableWhenGetByCodeNotInListContainingTheExpectedJournal(){
        //Arrange
        List<JournalComptable> journalComptableList = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            journalComptableList.add(new JournalComptable("AC-"+i, "Journal Comptable "+i));
        }
        String expectedCodeJournalComptable = "AC-50";

        //Act
        JournalComptable resultJournalComptable = getByCode(journalComptableList, expectedCodeJournalComptable);

        //Assert
        assertNull(resultJournalComptable);
    }
}