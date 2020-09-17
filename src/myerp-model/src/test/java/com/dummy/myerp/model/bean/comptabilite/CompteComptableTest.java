package com.dummy.myerp.model.bean.comptabilite;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static com.dummy.myerp.model.bean.comptabilite.CompteComptable.getByNumero;
import static org.junit.Assert.*;

public class CompteComptableTest {

    @Test
    public void testConstruteurWithAllParamaters(){
        //Arrange
        Integer numeroCompteCompatble = 1;
        String libelleCompteComptable = "test constructeur";

        //Act
        CompteComptable compteComptable = new CompteComptable(numeroCompteCompatble, libelleCompteComptable);

        //Assert
        assertEquals(numeroCompteCompatble,compteComptable.getNumero());
        assertEquals(libelleCompteComptable, compteComptable.getLibelle());
        assertEquals("CompteComptable{numero=1, libelle='test constructeur'}", compteComptable.toString());
    }

    @Test
    public void getCompteComptableWhenGetByNumeroInListContainingTheExpectedCompte(){
        //Arrange
        List<CompteComptable> compteComptableList = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            compteComptableList.add(new CompteComptable(i, "Compte Comptable "+i));
        }
        Integer expectedNumeroCompteComptable = 4;

        //Act
        CompteComptable resultCompteComptable = getByNumero(compteComptableList, expectedNumeroCompteComptable);

        //Assert
        assertEquals("CompteComptable{numero=4, libelle='Compte Comptable 4'}", resultCompteComptable.toString());
    }

    @Test
    public void getNullWhenGetByNumeroInListNotContainingTheExpectedCompte(){
        //Arrange
        List<CompteComptable> compteComptableList = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            compteComptableList.add(new CompteComptable(i, "Compte Comptable "+i));
        }
        Integer expectedNumeroCompteComptable = 50;

        //Act
        CompteComptable resultCompteComptable = getByNumero(compteComptableList, expectedNumeroCompteComptable);

        //Assert
        assertNull(resultCompteComptable);
    }
}