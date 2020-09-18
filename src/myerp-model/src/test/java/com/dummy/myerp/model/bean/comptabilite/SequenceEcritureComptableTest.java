package com.dummy.myerp.model.bean.comptabilite;

import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.*;

public class SequenceEcritureComptableTest {
    @Test
    public void testConstruteurWithoutParameters(){
        //Arrange
        String journalCode = "TI";
        Integer annee = 2020;
        Integer derniereValeur = 50;

        //Act
        SequenceEcritureComptable sequenceEcritureComptable = new SequenceEcritureComptable();
        sequenceEcritureComptable.setJournalCode(journalCode);
        sequenceEcritureComptable.setAnnee(annee);
        sequenceEcritureComptable.setDerniereValeur(derniereValeur);

        //Assert
        assertEquals(journalCode, sequenceEcritureComptable.getJournalCode());
        assertEquals(annee,sequenceEcritureComptable.getAnnee());
        assertEquals(derniereValeur, sequenceEcritureComptable.getDerniereValeur());
        assertEquals("SequenceEcritureComptable{journalCode='TI', annee=2020, derniereValeur=50}", sequenceEcritureComptable.toString());
    }

    @Test
    public void testConstruteurWithAllParamaters(){
        //Arrange
        String journalCode = "TI";
        Integer annee = 2020;
        Integer derniereValeur = 50;

        //Act
        SequenceEcritureComptable sequenceEcritureComptable = new SequenceEcritureComptable(journalCode, annee, derniereValeur);

        //Assert
        assertEquals(journalCode, sequenceEcritureComptable.getJournalCode());
        assertEquals(annee,sequenceEcritureComptable.getAnnee());
        assertEquals(derniereValeur, sequenceEcritureComptable.getDerniereValeur());
        assertEquals("SequenceEcritureComptable{journalCode='TI', annee=2020, derniereValeur=50}", sequenceEcritureComptable.toString());
    }
}