package com.jpmc.midascore.component;

import com.jpmc.midascore.entity.TransactionRecord;
import com.jpmc.midascore.entity.UserRecord;
import com.jpmc.midascore.foundation.Transaction;
import com.jpmc.midascore.repository.TransactionRepository;
import com.jpmc.midascore.repository.UserRepository;
import com.jpmc.midascore.incentive.IncentiveService;
import com.jpmc.midascore.incentive.Incentive;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DatabaseConduit {
    private static final Logger logger = LoggerFactory.getLogger(DatabaseConduit.class);

    private final UserRepository userRepository;
    private final TransactionRepository transactionRepository;
    private final IncentiveService incentiveService;

    @Autowired
    public DatabaseConduit(UserRepository userRepository,
                           TransactionRepository transactionRepository,
                           IncentiveService incentiveService) {
        this.userRepository = userRepository;
        this.transactionRepository = transactionRepository;
        this.incentiveService = incentiveService;
    }

    public UserRecord save(UserRecord user) {
        return userRepository.save(user);
    }

    @Transactional
    public boolean processTransaction(Transaction transaction) {
        UserRecord senderUser = userRepository.findById(transaction.getSenderId());
        UserRecord recipientUser = userRepository.findById(transaction.getRecipientId());

        if (senderUser == null || recipientUser == null) {
            logger.warn("Invalid transaction - sender or recipient not found");
            return false;
        }

        float transactionAmount = transaction.getAmount();

        if (senderUser.getBalance() < transactionAmount) {
            logger.warn("Invalid transaction - insufficient balance");
            return false;
        }

        // Update sender balance
        float newSenderBalance = senderUser.getBalance() - transactionAmount;
        senderUser.setBalance(newSenderBalance);
        userRepository.save(senderUser);

        // Get incentive amount
        Incentive incentive = incentiveService.getIncentive(transaction);
        float incentiveAmount = (float) incentive.getAmount();

        // Update recipient balance with transaction amount and incentive
        float newRecipientBalance = recipientUser.getBalance() + transactionAmount + incentiveAmount;
        recipientUser.setBalance(newRecipientBalance);
        userRepository.save(recipientUser);

        // Record transaction with incentive
        TransactionRecord record = new TransactionRecord(senderUser, recipientUser, transactionAmount);
        record.setIncentive(incentiveAmount);
        transactionRepository.save(record);

        logger.info("Transaction processed successfully with incentive amount: {}", incentiveAmount);
        return true;
    }

    public UserRecord updateWaldorToWilbur () {
        Iterable<UserRecord> users = userRepository.findAll();
        for (UserRecord user : users) {
            if (user.getName().equals("wilbur")) {
                return user;
            }
        }
        return null;
    }
}