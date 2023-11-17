package release.release_proj.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import release.release_proj.repository.CartImpl;

@SpringBootTest
@Transactional
class CartServiceImplTest {

    @Autowired CartServiceImpl cartService;
    @Autowired CartImpl cartRepository;
}