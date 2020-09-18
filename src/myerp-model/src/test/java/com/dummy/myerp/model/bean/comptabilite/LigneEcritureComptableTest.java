package com.dummy.myerp.model.bean.comptabilite;

import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.*;

public class LigneEcritureComptableTest {

    @Test
    public void testConstruteurWithAllParamaters(){
        //Arrange
        CompteComptable compteComptable = new CompteComptable(0, "TU");
        String libelleLigneEcritureComptable = "test constructeur";
        int montantDebit = 100;
        int montantCredit = 50;
        BigDecimal expectedDebit = BigDecimal.valueOf(montantDebit).setScale(2,BigDecimal.ROUND_CEILING);
        BigDecimal expectedCredit = BigDecimal.valueOf(montantCredit).setScale(2,BigDecimal.ROUND_CEILING);

        //Act
        LigneEcritureComptable ligneEcritureComptable = new LigneEcritureComptable(compteComptable, libelleLigneEcritureComptable, expectedDebit, expectedCredit);

        //Assert
        assertEquals(compteComptable,ligneEcritureComptable.getCompteComptable());
        assertEquals(libelleLigneEcritureComptable, ligneEcritureComptable.getLibelle());
        assertEquals(expectedDebit, ligneEcritureComptable.getDebit());
        assertEquals(expectedCredit, ligneEcritureComptable.getCredit());
        assertEquals("LigneEcritureComptable{compteComptable=CompteComptable{numero=0, libelle='TU'}, libelle='test constructeur', debit=100.00, credit=50.00}", ligneEcritureComptable.toString());
    }
}