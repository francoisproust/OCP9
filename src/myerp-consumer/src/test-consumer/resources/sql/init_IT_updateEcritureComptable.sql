DELETE FROM myerp.ligne_ecriture_comptable WHERE libelle = 'TI comptabiliteDao update';
DELETE FROM myerp.ecriture_comptable WHERE libelle = 'TI comptabiliteDao update';
DELETE FROM myerp.ecriture_comptable WHERE libelle = 'TI comptabiliteDao update done';
INSERT INTO myerp.ecriture_comptable (journal_code,reference,date,libelle) VALUES ('OD','OD-2020/00001', NOW(),'TI comptabiliteDao update');
