DELETE FROM myerp.sequence_ecriture_comptable WHERE journal_code = 'OD' AND annee = '2000';
INSERT INTO myerp.sequence_ecriture_comptable (journal_code,annee,derniere_valeur) VALUES ('OD','2000', '0');
