package com.dummy.myerp.business.impl.manager;

import com.dummy.myerp.model.bean.comptabilite.CompteComptable;
import com.dummy.myerp.model.bean.comptabilite.EcritureComptable;
import com.dummy.myerp.model.bean.comptabilite.JournalComptable;
import com.dummy.myerp.model.bean.comptabilite.LigneEcritureComptable;
import com.dummy.myerp.technical.exception.FunctionalException;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Date;


public class ComptabiliteManagerImplTest {

    private ComptabiliteManagerImpl manager = new ComptabiliteManagerImpl();

    @Test
    public void checkEcritureComptableUnit() throws Exception {
        //Arrange
        EcritureComptable vEcritureComptable = getEcritureComptableOK();

        //Act - //Assert
        manager.checkEcritureComptableUnit(vEcritureComptable);
    }

    @Test(expected = FunctionalException.class)
    public void checkEcritureComptableUnitViolation() throws Exception {
        //Arrange
        EcritureComptable vEcritureComptable;
        vEcritureComptable = new EcritureComptable();

        //Act - //Assert
        manager.checkEcritureComptableUnit(vEcritureComptable);
    }

    @Test(expected = FunctionalException.class)
    public void checkEcritureComptableUnitRG2Violation() throws Exception {
        //Arrange
        EcritureComptable vEcritureComptable = getEcritureComptableOK();
        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(2, "TU"),
                null, null,
                new BigDecimal(1234)));

        //Act - Assert
        manager.checkEcritureComptableUnit(vEcritureComptable);
    }

    @Test(expected = FunctionalException.class)
    public void checkEcritureComptableUnitRG3Violation() throws Exception {
        //Arrange
        EcritureComptable vEcritureComptable = getEcritureComptableOK();
        vEcritureComptable.getListLigneEcriture().get(1).setDebit(BigDecimal.valueOf(-123));
        vEcritureComptable.getListLigneEcriture().get(1).setCredit(BigDecimal.ZERO);

        //Act - Assert
        manager.checkEcritureComptableUnit(vEcritureComptable);
    }

    @Test(expected = FunctionalException.class)
    public void checkEcritureComptableUnitRG5AnneeViolation() throws Exception {
        //Arrange
        EcritureComptable vEcritureComptable = getEcritureComptableOK();
        vEcritureComptable.setReference("AC-2021/00001");

        //Act - Assert
        manager.checkEcritureComptableUnit(vEcritureComptable);
    }

    @Test(expected = FunctionalException.class)
    public void checkEcritureComptableUnitRG5JournalCodeViolation() throws Exception {
        //Arrange
        EcritureComptable vEcritureComptable = getEcritureComptableOK();
        vEcritureComptable.setReference("OD-2020/00001");

        //Act - Assert
        manager.checkEcritureComptableUnit(vEcritureComptable);
    }

    private EcritureComptable getEcritureComptableOK() {
        EcritureComptable vEcritureComptable;
        vEcritureComptable = new EcritureComptable();
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
        return vEcritureComptable;
    }
}