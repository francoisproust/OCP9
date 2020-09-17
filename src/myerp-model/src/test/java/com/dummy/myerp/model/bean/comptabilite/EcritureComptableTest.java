package com.dummy.myerp.model.bean.comptabilite;

import org.apache.commons.lang3.ObjectUtils;
import static org.junit.Assert.*;
import org.junit.Test;

import java.math.BigDecimal;

public class EcritureComptableTest {

    private LigneEcritureComptable createLigne(Integer pCompteComptableNumero, String pDebit, String pCredit) {
        BigDecimal vDebit = pDebit == null ? null : new BigDecimal(pDebit);
        BigDecimal vCredit = pCredit == null ? null : new BigDecimal(pCredit);
        String vLibelle = ObjectUtils.defaultIfNull(vDebit, BigDecimal.ZERO)
                .subtract(ObjectUtils.defaultIfNull(vCredit, BigDecimal.ZERO)).toPlainString();
        return new LigneEcritureComptable(new CompteComptable(pCompteComptableNumero, "TU"),
                vLibelle,
                vDebit, vCredit);
    }

    @Test
    public void getTotalDebitOfEcritureComptableContainingOnlyDebitLigneEcritureComptable() {
        //Arrange
        EcritureComptable ecritureComptable = new EcritureComptable();
        ecritureComptable.getListLigneEcriture().add(this.createLigne(1, "100.00", null));
        ecritureComptable.getListLigneEcriture().add(this.createLigne(2, "50.50", null));
        BigDecimal expectedTotalDebit = BigDecimal.valueOf(150.50).setScale(2, BigDecimal.ROUND_CEILING);

        //Act
        BigDecimal totalDebit = ecritureComptable.getTotalDebit();

        //Assert
        assertEquals(expectedTotalDebit, totalDebit);
    }

    @Test
    public void getTotalDebitOfEcritureComptableContainingOnlyCreditLigneEcritureComptable() {
        //Arrange
        EcritureComptable ecritureComptable = new EcritureComptable();
        ecritureComptable.getListLigneEcriture().add(this.createLigne(1, null, "100.00"));
        ecritureComptable.getListLigneEcriture().add(this.createLigne(2, null, "50.50"));
        BigDecimal expectedTotalDebit = BigDecimal.ZERO;

        //Act
        BigDecimal totalDebit = ecritureComptable.getTotalDebit();

        //Assert
        assertEquals(expectedTotalDebit, totalDebit);
    }

    @Test
    public void getTotalDebitOfEcritureComptableContainingMixLigneEcritureComptable() {
        //Arrange
        EcritureComptable ecritureComptable = new EcritureComptable();
        ecritureComptable.getListLigneEcriture().add(this.createLigne(1, "100.00", null));
        ecritureComptable.getListLigneEcriture().add(this.createLigne(2, null, "50.50"));
        BigDecimal expectedTotalDebit = BigDecimal.valueOf(100.00).setScale(2, BigDecimal.ROUND_CEILING);

        //Act
        BigDecimal totalDebit = ecritureComptable.getTotalDebit();

        //Assert
        assertEquals(expectedTotalDebit, totalDebit);
    }

    @Test
    public void getTotalCrebitOfEcritureComptableContainingOnlyDebitLigneEcritureComptable() {
        //Arrange
        EcritureComptable ecritureComptable = new EcritureComptable();
        ecritureComptable.getListLigneEcriture().add(this.createLigne(1, "100.00", null));
        ecritureComptable.getListLigneEcriture().add(this.createLigne(2, "50.50", null));
        BigDecimal expectedTotalCrebit = BigDecimal.ZERO;

        //Act
        BigDecimal totalCredit = ecritureComptable.getTotalCredit();

        //Assert
        assertEquals(expectedTotalCrebit, totalCredit);
    }

    @Test
    public void getTotalCrebitOfEcritureComptableContainingOnlyCreditLigneEcritureComptable() {
        //Arrange
        EcritureComptable ecritureComptable = new EcritureComptable();
        ecritureComptable.getListLigneEcriture().add(this.createLigne(1, null, "100.00"));
        ecritureComptable.getListLigneEcriture().add(this.createLigne(2, null, "50.50"));
        BigDecimal expectedTotalCrebit = BigDecimal.valueOf(150.50).setScale(2, BigDecimal.ROUND_CEILING);

        //Act
        BigDecimal totalCredit = ecritureComptable.getTotalCredit();

        //Assert
        assertEquals(expectedTotalCrebit, totalCredit);
    }

    @Test
    public void getTotalCrebitOfEcritureComptableContainingMixLigneEcritureComptable() {
        //Arrange
        EcritureComptable ecritureComptable = new EcritureComptable();
        ecritureComptable.getListLigneEcriture().add(this.createLigne(1, "100.00", null));
        ecritureComptable.getListLigneEcriture().add(this.createLigne(2, null, "50.50"));
        BigDecimal expectedTotalCrebit = BigDecimal.valueOf(50.50).setScale(2, BigDecimal.ROUND_CEILING);

        //Act
        BigDecimal totalCredit = ecritureComptable.getTotalCredit();

        //Assert
        assertEquals(expectedTotalCrebit, totalCredit);
    }


    @Test
    public void ecritureComptableIsEquilibree() {
        //Arrange
        EcritureComptable ecritureComptable = new EcritureComptable();
        ecritureComptable.setLibelle("Equilibrée");
        ecritureComptable.getListLigneEcriture().add(this.createLigne(1, "200.50", null));
        ecritureComptable.getListLigneEcriture().add(this.createLigne(1, "100.50", "33"));
        ecritureComptable.getListLigneEcriture().add(this.createLigne(2, null, "301"));
        ecritureComptable.getListLigneEcriture().add(this.createLigne(2, "40", "7"));

        //Act
        boolean checkEquilibree = ecritureComptable.isEquilibree();

        //Arrange
        assertTrue(ecritureComptable.toString(), checkEquilibree);
    }


    @Test
    public void ecritureComptableIsNotEquilibree() {
        //Arrange
        EcritureComptable ecritureComptable = new EcritureComptable();
        ecritureComptable.setLibelle("Non équilibrée");
        ecritureComptable.getListLigneEcriture().add(this.createLigne(1, "10", null));
        ecritureComptable.getListLigneEcriture().add(this.createLigne(1, "20", "1"));
        ecritureComptable.getListLigneEcriture().add(this.createLigne(2, null, "30"));
        ecritureComptable.getListLigneEcriture().add(this.createLigne(2, "1", "2"));

        //Act
        boolean checkEquilibree = ecritureComptable.isEquilibree();

        //Arrange
        assertFalse(ecritureComptable.toString(), checkEquilibree);
    }
}