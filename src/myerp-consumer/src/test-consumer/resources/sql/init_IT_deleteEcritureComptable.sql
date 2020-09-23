DELETE FROM myerp.ecriture_comptable WHERE libelle = 'TI comptabiliteDao delete';
INSERT INTO myerp.ecriture_comptable (journal_code,reference,date,libelle) VALUES ('OD','OD-2020/00002', NOW(),'TI comptabiliteDao delete');
