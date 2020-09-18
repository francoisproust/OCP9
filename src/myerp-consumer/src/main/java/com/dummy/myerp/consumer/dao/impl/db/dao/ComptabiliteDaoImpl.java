package com.dummy.myerp.consumer.dao.impl.db.dao;

import java.sql.Types;
import java.util.List;

import com.dummy.myerp.consumer.dao.impl.db.rowmapper.comptabilite.*;
import com.dummy.myerp.model.bean.comptabilite.*;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import com.dummy.myerp.consumer.dao.contrat.ComptabiliteDao;
import com.dummy.myerp.consumer.db.AbstractDbConsumer;
import com.dummy.myerp.consumer.db.DataSourcesEnum;
import com.dummy.myerp.technical.exception.NotFoundException;


/**
 * Implémentation de l'interface {@link ComptabiliteDao}
 */
public class ComptabiliteDaoImpl extends AbstractDbConsumer implements ComptabiliteDao {

    // ==================== Constructeurs ====================
    /** Instance unique de la classe (design pattern Singleton) */
    private static final ComptabiliteDaoImpl INSTANCE = new ComptabiliteDaoImpl();

    /**
     * Renvoie l'instance unique de la classe (design pattern Singleton).
     *
     * @return {@link ComptabiliteDaoImpl}
     */
    public static ComptabiliteDaoImpl getInstance() {
        return ComptabiliteDaoImpl.INSTANCE;
    }

    /**
     * Constructeur.
     */
    protected ComptabiliteDaoImpl() {
        super();
    }

    // ========= Attributs nom des colonnes =============
    private static String id = "id";
    private static String ecritureId = "ecriture_id";
    private static String ligneId = "ligne_id";
    private static String libelle = "libelle";
    private static String journalCode = "journal_code";
    private static String reference = "reference";
    private static String date = "date";
    private static String annee = "annee";
    private static String sequence = "sequence";
    private static String compteComptableNumero = "compte_comptable_numero";
    private static String credit = "credit";
    private static String debit = "debit";

    // ==================== Méthodes ====================
    /** SQLgetListCompteComptable */
    private static String reqSQLgetListCompteComptable;
    public static void setReqSQLgetListCompteComptable(String pSQLgetListCompteComptable) {
        reqSQLgetListCompteComptable = pSQLgetListCompteComptable;
    }
    @Override
    public List<CompteComptable> getListCompteComptable() {
        JdbcTemplate vJdbcTemplate = new JdbcTemplate(this.getDataSource(DataSourcesEnum.MYERP));
        CompteComptableRM vRM = new CompteComptableRM();
        return vJdbcTemplate.query(reqSQLgetListCompteComptable, vRM);
    }


    /** SQLgetListJournalComptable */
    private static String reqSQLgetListJournalComptable;
    public static void setReqSQLgetListJournalComptable(String pSQLgetListJournalComptable) {
        reqSQLgetListJournalComptable = pSQLgetListJournalComptable;
    }
    @Override
    public List<JournalComptable> getListJournalComptable() {
        JdbcTemplate vJdbcTemplate = new JdbcTemplate(this.getDataSource(DataSourcesEnum.MYERP));
        JournalComptableRM vRM = new JournalComptableRM();
        return vJdbcTemplate.query(reqSQLgetListJournalComptable, vRM);
    }

    // ==================== EcritureComptable - GET ====================

    /** SQLgetListEcritureComptable */
    private static String reqSQLgetListEcritureComptable;
    public static void setReqSQLgetListEcritureComptable(String pSQLgetListEcritureComptable) {
        reqSQLgetListEcritureComptable = pSQLgetListEcritureComptable;
    }
    @Override
    public List<EcritureComptable> getListEcritureComptable() {
        JdbcTemplate vJdbcTemplate = new JdbcTemplate(this.getDataSource(DataSourcesEnum.MYERP));
        EcritureComptableRM vRM = new EcritureComptableRM();
        return vJdbcTemplate.query(reqSQLgetListEcritureComptable, vRM);
    }


    /** SQLgetEcritureComptable */
    private static String reqSQLgetEcritureComptable;
    public static void setReqSQLgetEcritureComptable(String pSQLgetEcritureComptable) {
        reqSQLgetEcritureComptable = pSQLgetEcritureComptable;
    }
    @Override
    public EcritureComptable getEcritureComptable(Integer pId) throws NotFoundException {
        NamedParameterJdbcTemplate vJdbcTemplate = new NamedParameterJdbcTemplate(getDataSource(DataSourcesEnum.MYERP));
        MapSqlParameterSource vSqlParams = new MapSqlParameterSource();
        vSqlParams.addValue(id, pId);
        EcritureComptableRM vRM = new EcritureComptableRM();
        EcritureComptable vBean;
        try {
            vBean = vJdbcTemplate.queryForObject(reqSQLgetEcritureComptable, vSqlParams, vRM);
        } catch (EmptyResultDataAccessException vEx) {
            throw new NotFoundException("EcritureComptable non trouvée : id=" + pId);
        }
        return vBean;
    }


    /** SQLgetEcritureComptableByRef */
    private static String reqSQLgetEcritureComptableByRef;
    public static  void setReqSQLgetEcritureComptableByRef(String pSQLgetEcritureComptableByRef) {
        reqSQLgetEcritureComptableByRef = pSQLgetEcritureComptableByRef;
    }
    @Override
    public EcritureComptable getEcritureComptableByRef(String pReference) throws NotFoundException {
        NamedParameterJdbcTemplate vJdbcTemplate = new NamedParameterJdbcTemplate(getDataSource(DataSourcesEnum.MYERP));
        MapSqlParameterSource vSqlParams = new MapSqlParameterSource();
        vSqlParams.addValue(reference, pReference);
        EcritureComptableRM vRM = new EcritureComptableRM();
        EcritureComptable vBean;
        try {
            vBean = vJdbcTemplate.queryForObject(reqSQLgetEcritureComptableByRef, vSqlParams, vRM);
        } catch (EmptyResultDataAccessException vEx) {
            throw new NotFoundException("EcritureComptable non trouvée : reference=" + pReference);
        }
        return vBean;
    }


    /** SQLloadListLigneEcriture */
    private static String reqSQLloadListLigneEcriture;
    public static  void setReqSQLloadListLigneEcriture(String pSQLloadListLigneEcriture) {
        reqSQLloadListLigneEcriture = pSQLloadListLigneEcriture;
    }
    @Override
    public void loadListLigneEcriture(EcritureComptable pEcritureComptable) {
        NamedParameterJdbcTemplate vJdbcTemplate = new NamedParameterJdbcTemplate(getDataSource(DataSourcesEnum.MYERP));
        MapSqlParameterSource vSqlParams = new MapSqlParameterSource();
        vSqlParams.addValue(ecritureId, pEcritureComptable.getId());
        LigneEcritureComptableRM vRM = new LigneEcritureComptableRM();
        List<LigneEcritureComptable> vList = vJdbcTemplate.query(reqSQLloadListLigneEcriture, vSqlParams, vRM);
        pEcritureComptable.getListLigneEcriture().clear();
        pEcritureComptable.getListLigneEcriture().addAll(vList);
    }


    // ==================== EcritureComptable - INSERT ====================

    /** SQLinsertEcritureComptable */
    private static String reqSQLinsertEcritureComptable;
    public static  void setReqSQLinsertEcritureComptable(String pSQLinsertEcritureComptable) {
        reqSQLinsertEcritureComptable = pSQLinsertEcritureComptable;
    }
    @Override
    public void insertEcritureComptable(EcritureComptable pEcritureComptable) {
        // ===== Ecriture Comptable
        NamedParameterJdbcTemplate vJdbcTemplate = new NamedParameterJdbcTemplate(getDataSource(DataSourcesEnum.MYERP));
        MapSqlParameterSource vSqlParams = new MapSqlParameterSource();
        vSqlParams.addValue(journalCode, pEcritureComptable.getJournal().getCode());
        vSqlParams.addValue(reference, pEcritureComptable.getReference());
        vSqlParams.addValue(date, pEcritureComptable.getDate(), Types.DATE);
        vSqlParams.addValue(libelle, pEcritureComptable.getLibelle());

        vJdbcTemplate.update(reqSQLinsertEcritureComptable, vSqlParams);

        // ----- Récupération de l'id
        Integer vId = this.queryGetSequenceValuePostgreSQL(DataSourcesEnum.MYERP, "myerp.ecriture_comptable_id_seq",
                Integer.class);
        pEcritureComptable.setId(vId);

        // ===== Liste des lignes d'écriture
        this.insertListLigneEcritureComptable(pEcritureComptable);
    }

    /** SQLinsertListLigneEcritureComptable */
    private static String reqSQLinsertListLigneEcritureComptable;
    public static void setReqSQLinsertListLigneEcritureComptable(String pSQLinsertListLigneEcritureComptable) {
        reqSQLinsertListLigneEcritureComptable = pSQLinsertListLigneEcritureComptable;
    }
    /**
     * Insert les lignes d'écriture de l'écriture comptable
     * @param pEcritureComptable l'écriture comptable
     */
    protected void insertListLigneEcritureComptable(EcritureComptable pEcritureComptable) {
        NamedParameterJdbcTemplate vJdbcTemplate = new NamedParameterJdbcTemplate(getDataSource(DataSourcesEnum.MYERP));
        MapSqlParameterSource vSqlParams = new MapSqlParameterSource();
        vSqlParams.addValue("ecriture_id", pEcritureComptable.getId());

        int vLigneId = 0;
        for (LigneEcritureComptable vLigne : pEcritureComptable.getListLigneEcriture()) {
            vLigneId++;
            vSqlParams.addValue(ligneId, vLigneId);
            vSqlParams.addValue(compteComptableNumero, vLigne.getCompteComptable().getNumero());
            vSqlParams.addValue(libelle, vLigne.getLibelle());
            vSqlParams.addValue(debit, vLigne.getDebit());

            vSqlParams.addValue(credit, vLigne.getCredit());

            vJdbcTemplate.update(reqSQLinsertListLigneEcritureComptable, vSqlParams);
        }
    }


    // ==================== EcritureComptable - UPDATE ====================

    /** SQLupdateEcritureComptable */
    private static String reqSQLupdateEcritureComptable;
    public static  void setReqSQLupdateEcritureComptable(String pSQLupdateEcritureComptable) {
        reqSQLupdateEcritureComptable = pSQLupdateEcritureComptable;
    }
    @Override
    public void updateEcritureComptable(EcritureComptable pEcritureComptable) {
        // ===== Ecriture Comptable
        NamedParameterJdbcTemplate vJdbcTemplate = new NamedParameterJdbcTemplate(getDataSource(DataSourcesEnum.MYERP));
        MapSqlParameterSource vSqlParams = new MapSqlParameterSource();
        vSqlParams.addValue(id, pEcritureComptable.getId());
        vSqlParams.addValue(journalCode, pEcritureComptable.getJournal().getCode());
        vSqlParams.addValue(reference, pEcritureComptable.getReference());
        vSqlParams.addValue(date, pEcritureComptable.getDate(), Types.DATE);
        vSqlParams.addValue(libelle, pEcritureComptable.getLibelle());

        vJdbcTemplate.update(reqSQLupdateEcritureComptable, vSqlParams);

        // ===== Liste des lignes d'écriture
        this.deleteListLigneEcritureComptable(pEcritureComptable.getId());
        this.insertListLigneEcritureComptable(pEcritureComptable);
    }


    // ==================== EcritureComptable - DELETE ====================

    /** SQLdeleteEcritureComptable */
    private static String reqSQLdeleteEcritureComptable;
    public static void setReqSQLdeleteEcritureComptable(String pSQLdeleteEcritureComptable) {
        reqSQLdeleteEcritureComptable = pSQLdeleteEcritureComptable;
    }
    @Override
    public void deleteEcritureComptable(Integer pId) {
        // ===== Suppression des lignes d'écriture
        this.deleteListLigneEcritureComptable(pId);

        // ===== Suppression de l'écriture
        NamedParameterJdbcTemplate vJdbcTemplate = new NamedParameterJdbcTemplate(getDataSource(DataSourcesEnum.MYERP));
        MapSqlParameterSource vSqlParams = new MapSqlParameterSource();
        vSqlParams.addValue(id, pId);
        vJdbcTemplate.update(reqSQLdeleteEcritureComptable, vSqlParams);
    }

    /** SQLselectDerniereValeurSequenceJournalComptable */
    private static String reqSQLselectDerniereValeurSequenceJournalComptable;
    public static void setReqSQLselectDerniereValeurSequenceJournalComptable(String pSQLselectDerniereValeurSequenceJournalComptable) {
        reqSQLselectDerniereValeurSequenceJournalComptable = pSQLselectDerniereValeurSequenceJournalComptable;
    }
    /**
     * Renvoie la dernière valeur de la séquence pour un journal et une année donnés
     *
     * @param pCodeJournal code du Journal
     * @param pAnnee       année de la séquence
     * @return Dernière valeur
     */
    @Override
    public Integer getDernierNumeroSequenceComptableByJournalAndAnnee(String pCodeJournal, Integer pAnnee) {
        NamedParameterJdbcTemplate vJdbcTemplate = new NamedParameterJdbcTemplate(getDataSource(DataSourcesEnum.MYERP));
        MapSqlParameterSource vSqlParams = new MapSqlParameterSource();
        vSqlParams.addValue(journalCode, pCodeJournal);
        vSqlParams.addValue(annee, pAnnee);
        SequenceEcritureComptableRM vRM = new SequenceEcritureComptableRM();
        List<SequenceEcritureComptable> vSequenceList = vJdbcTemplate.query(reqSQLselectDerniereValeurSequenceJournalComptable, vSqlParams, vRM);
        if (vSequenceList.isEmpty()) {
            return null;
        } else {
            return vSequenceList.get(0).getDerniereValeur();
        }
    }

    /** SQLinsertDerniereValeurSequenceJournalComptable */
    private static String reqSQLinsertSequenceJournalComptable;
    public static void setReqSQLinsertSequenceJournalComptable(String pSQLinsertSequenceJournalComptable) {
        reqSQLinsertSequenceJournalComptable = pSQLinsertSequenceJournalComptable;
    }
    /**
     * Insert une nouvelle séquence pour un journal et une année donnés
     *
     * @param pSequenceEcritureComptable Sequence à inserer
     */
    @Override
    public void insertSequenceEcritureComptable(SequenceEcritureComptable pSequenceEcritureComptable) {
        NamedParameterJdbcTemplate vJdbcTemplate = new NamedParameterJdbcTemplate(getDataSource(DataSourcesEnum.MYERP));
        MapSqlParameterSource vSqlParams = new MapSqlParameterSource();
        vSqlParams.addValue(journalCode, pSequenceEcritureComptable.getJournalCode());
        vSqlParams.addValue(annee, pSequenceEcritureComptable.getAnnee());
        vSqlParams.addValue(sequence, pSequenceEcritureComptable.getDerniereValeur());
        vJdbcTemplate.update(reqSQLinsertSequenceJournalComptable, vSqlParams);
    }

    /** SQLinsertDerniereValeurSequenceJournalComptable */
    private static String reqSQLupdateSequenceJournalComptable;
    public static void setReqSQLupdateSequenceJournalComptable(String pSQLupdateSequenceJournalComptable) {
        reqSQLupdateSequenceJournalComptable = pSQLupdateSequenceJournalComptable;
    }
    /**
     * Mets à jour une séquence pour un journal et une année donnés
     *
     * @param pSequenceEcritureComptable Sequence à updater
     */
    @Override
    public void updateSequenceEcritureComptable(SequenceEcritureComptable pSequenceEcritureComptable) {
        NamedParameterJdbcTemplate vJdbcTemplate = new NamedParameterJdbcTemplate(getDataSource(DataSourcesEnum.MYERP));
        MapSqlParameterSource vSqlParams = new MapSqlParameterSource();
        vSqlParams.addValue(journalCode, pSequenceEcritureComptable.getJournalCode());
        vSqlParams.addValue(annee, pSequenceEcritureComptable.getAnnee());
        vSqlParams.addValue(sequence, pSequenceEcritureComptable.getDerniereValeur());
        vJdbcTemplate.update(reqSQLupdateSequenceJournalComptable, vSqlParams);
    }

    /** SQLdeleteListLigneEcritureComptable */
    private static String reqSQLdeleteListLigneEcritureComptable;
    public static void setReqSQLdeleteListLigneEcritureComptable(String pSQLdeleteListLigneEcritureComptable) {
        reqSQLdeleteListLigneEcritureComptable = pSQLdeleteListLigneEcritureComptable;
    }
    /**
     * Supprime les lignes d'écriture de l'écriture comptable d'id {@code pEcritureId}
     * @param pEcritureId id de l'écriture comptable
     */
    protected void deleteListLigneEcritureComptable(Integer pEcritureId) {
        NamedParameterJdbcTemplate vJdbcTemplate = new NamedParameterJdbcTemplate(getDataSource(DataSourcesEnum.MYERP));
        MapSqlParameterSource vSqlParams = new MapSqlParameterSource();
        vSqlParams.addValue(ecritureId, pEcritureId);
        vJdbcTemplate.update(reqSQLdeleteListLigneEcritureComptable, vSqlParams);
    }
}