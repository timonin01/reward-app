package org.javaguru.reward.payment.service;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Component
class RewardPaymentHashGenerator {

    String generateHash(Long employeeId,
                        Long rewardId,
                        BigDecimal amount) {
        String xml = getXmlString(employeeId, rewardId, amount);
        String hash = generateHash(xml);
        if (hash.length() > 200) {  // column length
            throw new RuntimeException("Hash function length > 200!!!");
        }
        return hash;
    }

    private String getXmlString(Long employeeId,
                                Long rewardId,
                                BigDecimal amount) {
        return "<payment>"
                + "<employeeId>" + employeeId + "</employeeId>"
                + "<rewardId>" + rewardId + "</rewardId>"
                + "<amount>" + amount + "</amount>"
                + "</payment>";
    }

    private String generateHash(String content) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-512");
            byte[] hash = md.digest(content.getBytes());
            return String.format("%064x", new BigInteger(1, hash)); // Convert to hex
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("SHA-512 algorithm not found", e);
        }
    }

}
