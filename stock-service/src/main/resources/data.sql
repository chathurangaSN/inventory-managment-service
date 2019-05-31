insert into DraftLog (date, reason, userId) values ("2019-01-01 09:00:00", "2019 jan openstock", 1)
        
insert into DraftDetails (brandId, OSid, itemId, quantity, uomId) values (1, 1, 1, 24, 1)
insert into DraftDetails (brandId, OSid, itemId, quantity, uomId) values (2, 1, 2, 24, 1)
insert into DraftDetails (brandId, OSid, itemId, quantity, uomId) values (3, 1, 1, 24, 2)
insert into DraftDetails (brandId, OSid, itemId, quantity, uomId) values (4, 1, 1, 24, 1)

insert into DraftLog (date, reason, userId) values ("2019-02-01 09:00:00", "2019  feb openstock", 1)
        
insert into DraftDetails (brandId, OSid, itemId, quantity, uomId) values (1, 2, 1, 23, 1)
insert into DraftDetails (brandId, OSid, itemId, quantity, uomId) values (2, 2, 1, 100, 2)
insert into DraftDetails (brandId, OSid, itemId, quantity, uomId) values (3, 2, 1, 5, 2)
insert into DraftDetails (brandId, OSid, itemId, quantity, uomId) values (4, 2, 1, 12, 1)

-- transactionlog and transaction details
--1
insert into TransactionLog (date, type, userId) values ("2019-01-10 10:00:00", "issue", 1)
        
insert into TransactionDetails (brandId, itemId, uomId, quantity, transactionLogId) values (1, 1, 1, 50, 1)
insert into TransactionDetails (brandId, itemId, uomId, quantity, transactionLogId) values (1, 1, 2, 75, 1)
insert into TransactionDetails (brandId, itemId, uomId, quantity, transactionLogId) values (2, 1, 1, 40, 1)
insert into TransactionDetails (brandId, itemId, uomId, quantity, transactionLogId) values (2, 2, 1, 10, 1)

insert into CurrentStock (brandId, itemId, uomId, quantity) values (1, 1, 1, 50)
insert into CurrentStock (brandId, itemId, uomId, quantity) values (1, 1, 2, 75)
insert into CurrentStock (brandId, itemId, uomId, quantity) values (2, 1, 1, 40)
insert into CurrentStock (brandId, itemId, uomId, quantity) values (2, 2, 1, 10)

--2
insert into TransactionLog (date, type, userId) values ("2019-02-02 09:00:00", "issue", 1)
        
insert into TransactionDetails (brandId, itemId, uomId, quantity, transactionLogId) values (2, 2, 1, 20, 2)
insert into TransactionDetails (brandId, itemId, uomId, quantity, transactionLogId) values (3, 1, 1, 45, 2)
insert into TransactionDetails (brandId, itemId, uomId, quantity, transactionLogId) values (4, 2, 2, 90, 2)
insert into TransactionDetails (brandId, itemId, uomId, quantity, transactionLogId) values (1, 2, 1, 50, 2)

insert into CurrentStock (brandId, itemId, uomId, quantity) values (2, 2, 1, 20)
insert into CurrentStock (brandId, itemId, uomId, quantity) values (3, 1, 1, 45)
insert into CurrentStock (brandId, itemId, uomId, quantity) values (4, 2, 2, 90)
insert into CurrentStock (brandId, itemId, uomId, quantity) values (1, 2, 1, 50)

--3
insert into TransactionLog (date, type, userId) values ("2019-02-03 09:00:00", "issue", 1)
        
insert into TransactionDetails (brandId, itemId, uomId, quantity, transactionLogId) values (2, 2, 2, 20, 3)
insert into TransactionDetails (brandId, itemId, uomId, quantity, transactionLogId) values (3, 2, 1, 45, 3)
insert into TransactionDetails (brandId, itemId, uomId, quantity, transactionLogId) values (1, 2, 2, 90, 3)
insert into TransactionDetails (brandId, itemId, uomId, quantity, transactionLogId) values (1, 4, 1, 50, 3)

insert into CurrentStock (brandId, itemId, uomId, quantity) values (2, 2, 2, 20)
insert into CurrentStock (brandId, itemId, uomId, quantity) values (3, 2, 1, 45)
insert into CurrentStock (brandId, itemId, uomId, quantity) values (1, 2, 2, 90)
insert into CurrentStock (brandId, itemId, uomId, quantity) values (1, 4, 1, 50)

--4
insert into TransactionLog (date, type, userId) values ("2019-02-04 09:00:00", "issue", 1)
        
insert into TransactionDetails (brandId, itemId, uomId, quantity, transactionLogId) values (2, 2, 1, 20, 4)
insert into TransactionDetails (brandId, itemId, uomId, quantity, transactionLogId) values (3, 1, 1, 45, 4)
insert into TransactionDetails (brandId, itemId, uomId, quantity, transactionLogId) values (4, 2, 2, 90, 4)
insert into TransactionDetails (brandId, itemId, uomId, quantity, transactionLogId) values (1, 2, 1, 50, 4)



--5
insert into TransactionLog (date, type, userId) values ("2019-03-02 09:00:00", "issue", 1)
        
insert into TransactionDetails (brandId, itemId, uomId, quantity, transactionLogId) values (2, 2, 1, 20, 5)
insert into TransactionDetails (brandId, itemId, uomId, quantity, transactionLogId) values (3, 1, 1, 45, 5)
insert into TransactionDetails (brandId, itemId, uomId, quantity, transactionLogId) values (4, 2, 2, 90, 5)
insert into TransactionDetails (brandId, itemId, uomId, quantity, transactionLogId) values (1, 2, 1, 50, 5)


