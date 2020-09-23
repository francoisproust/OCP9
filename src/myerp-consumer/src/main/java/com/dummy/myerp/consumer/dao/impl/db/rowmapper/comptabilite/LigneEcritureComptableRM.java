package com.dummy.myerp.consumer.dao.impl.db.rowmapper.comptabilite;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.dummy.myerp.model.bean.comptabilite.CompteComptable;
import org.springframework.jdbc.core.RowMapper;
import com.dummy.myerp.consumer.dao.impl.cache.CompteComptableDaoCache;
import com.dummy.myerp.model.bean.comptabilite.LigneEcritureComptable;


/**
 * {@link RowMapper} de {@link LigneEcritureComptable}
 */
public class LigneEcritureComptableRM implements RowMapper<LigneEcritureComptable> {

    /** CompteComptableDaoCache */
    private CompteComptableDaoCache compteComptableDaoCache;

    public LigneEcritureComptableRM() {
        compteComptableDaoCache = new CompteComptableDaoCache();
    }

    @Override
    public LigneEcritureComptable mapRow(ResultSet pRS, int pRowNum) throws SQLException {
        CompteComptable vCompteComptable = compteComptableDaoCache.getByNumero(pRS.getObject("compte_comptable_numero",
                Integer.class));
        BigDecimal vCredit = pRS.getBigDecimal("credit");
        BigDecimal vDebit = pRS.getBigDecimal("debit");
        String vLibelle = pRS.getString("libelle");
        return new LigneEcritureComptable(vCompteComptable, vLibelle, vDebit, vCredit);
    }
}
