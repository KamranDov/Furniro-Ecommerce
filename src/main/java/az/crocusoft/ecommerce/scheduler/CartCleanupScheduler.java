package az.crocusoft.ecommerce.scheduler;

import az.crocusoft.ecommerce.model.Cart;
import az.crocusoft.ecommerce.repository.CartRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
@RequiredArgsConstructor
@Log4j2
public class CartCleanupScheduler {

    private final CartRepository cartRepository;
    @Scheduled(cron = "0 0 0 1 * ?")
    public void performCartCleanup() {
        LocalDate oneMonthAgo = LocalDate.now().minusMonths(1);
        List<Cart> cartsDelete = cartRepository.findCartByCreatedDateBefore(oneMonthAgo);
        int deleteCount = cartsDelete.size();
        cartRepository.deleteAll(cartsDelete);
        if (deleteCount > 0) {
            log.info("{} records deleted in old data cleanup.", deleteCount);
        } else {
            log.info("No records found for old data cleanup.");
        }
    }
}
