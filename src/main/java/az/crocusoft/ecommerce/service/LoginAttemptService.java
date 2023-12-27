package az.crocusoft.ecommerce.service;

import az.crocusoft.ecommerce.model.FailedLoginAttempt;
import az.crocusoft.ecommerce.model.User;
import az.crocusoft.ecommerce.model.UserStatus;
import az.crocusoft.ecommerce.repository.FailedLoginAttemptRepository;
import az.crocusoft.ecommerce.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LoginAttemptService {
    private final FailedLoginAttemptRepository failedLoginAttemptRepository;
    private final UserRepository userRepository;
    private static final int MAX_FAILED_ATTEMPTS = 3;
    private static final int BLOCK_DURATION_HOURS = 1;



    public boolean isUserBlocked(String username) {
        Optional<FailedLoginAttempt> attemptOptional =
                failedLoginAttemptRepository.findByUsername(username);

        if (attemptOptional.isPresent()) {
            FailedLoginAttempt attempt = attemptOptional.get();
            LocalDateTime blockEndTime = attempt.getLastAttempt().plusHours(BLOCK_DURATION_HOURS);

            return attempt.getCount() >= MAX_FAILED_ATTEMPTS &&
                    blockEndTime.isAfter(LocalDateTime.now());
        }

        return false;
    }

    public void incrementFailedLoginAttempts(String username) {
        Optional<FailedLoginAttempt> attemptOptional =
                failedLoginAttemptRepository.findByUsername(username);
        FailedLoginAttempt attempt;

        if (attemptOptional.isPresent()) {
            attempt = attemptOptional.get();
            attempt.setCount(attempt.getCount() + 1);
        } else {
            attempt = new FailedLoginAttempt();
            attempt.setUsername(username);
            attempt.setCount(1);
        }

        attempt.setLastAttempt(LocalDateTime.now());

        failedLoginAttemptRepository.save(attempt);

        if (attempt.getCount() >= MAX_FAILED_ATTEMPTS) {
            blockUser(username);
            attempt.setBlockedUntil(attempt.getLastAttempt().plusHours(BLOCK_DURATION_HOURS));
        }
    }

    public void resetFailedLoginAttempts(String username) {
        failedLoginAttemptRepository.deleteByUsername(username);
    }


    public void blockUser(String username) {
        Optional<User> userOptional = userRepository.findByUsername(username);

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.setStatus(UserStatus.BLOCKED);

            // Set blockedUntil to the current time plus BLOCK_DURATION_HOURS
            LocalDateTime blockedUntil = LocalDateTime.now().plusHours(BLOCK_DURATION_HOURS);

            // Save the blockedUntil time in the FailedLoginAttempt entity
            saveBlockedUntil(username, blockedUntil);

            userRepository.save(user);

            // Schedule the unblock task
            scheduleUnblockTask(username);
        }
    }

    private void saveBlockedUntil(String username, LocalDateTime blockedUntil) {
        Optional<FailedLoginAttempt> attemptOptional = failedLoginAttemptRepository.findByUsername(username);

        if (attemptOptional.isPresent()) {
            FailedLoginAttempt attempt = attemptOptional.get();
            attempt.setBlockedUntil(blockedUntil);
            failedLoginAttemptRepository.save(attempt);
        } else {
            // Create a new FailedLoginAttempt if not exists (this may happen if the first login attempt is a failure)
            FailedLoginAttempt attempt = new FailedLoginAttempt();
            attempt.setUsername(username);
            attempt.setBlockedUntil(blockedUntil);
            failedLoginAttemptRepository.save(attempt);
        }
    }


    private void scheduleUnblockTask(String username) {
        // Bu görevi çalıştırmak için bir görev zamanlayıcısı kullanın (örneğin, Spring'in @Scheduled'ı)
        // Bu örnekte, gecikmeyi simüle etmek için basit bir Thread.sleep kullanıyoruz
        new Thread(() -> {
            try {
                Thread.sleep(BLOCK_DURATION_HOURS * 60 * 60 * 1000);  // Saatleri milisaniyeye çevir
//                Thread.sleep( 60 * 1000);  // Saatleri milisaniyeye çevir
                unblockUser(username);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }).start();
    }

    public void unblockUser(String username) {
        Optional<User> userOptional = userRepository.findByUsername(username);

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.setStatus(UserStatus.ACTIVE);  // UserStatus, ACTIVE, BLOCKED gibi değerlere sahip bir enum varsayılsın.
            userRepository.save(user);

            // İsteğe bağlı olarak, kullanıcıyı engelini kaldırdıktan sonra başarısız giriş denemelerini sıfırlayabilirsiniz
            resetFailedLoginAttempts(username);
        }
    }
    }
