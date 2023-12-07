package release.release_proj.service;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import release.release_proj.domain.Cart;
import release.release_proj.domain.Order;
import release.release_proj.repository.CartRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor //생성자 주입을 대신 해줌
public class CartServiceImpl implements CartService {

    //@Autowired
    //private CartRepository cartRepository; //필드주입

    private final CartRepository cartRepository;
    private final ItemService itemService;
    private final OrderService orderService;

    /*@Autowired //생성자 주입
    public CartServiceImpl(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }*/

    @Override
    public Optional<List<Cart>> readMemberCarts(String memberId) {
        //return cartRepository.findByMemberId(member.getMemberId());
        return cartRepository.findByMemberId(memberId);
    }

    @Override
    public Optional<Cart> readMemberCartItems(String memberId, Long itemId) {
        //return cartRepository.findByMemberIdAndItemId(member.getMemberId(), item.getItemId());
        return cartRepository.findByMemberIdAndItemId(memberId, itemId);
    }

    @Override
    public int addCartItem(Cart cart) {
        try {
            Optional<Cart> isCartExist = cartRepository.findByMemberIdAndItemId(cart.getMemberId(), cart.getItemId());
            if (isCartExist.isPresent()){
                Cart currentCart = isCartExist.get();
                //currentCart.setAmount(currentCart.getAmount() + cart.getAmount());
                //return cartRepository.updateCartAmount(currentCart.getCartId(), currentCart.getAmount());
                return cartRepository.updateCartAmount(currentCart.getCartId(), cart.getAmount());
            }
            else {
                return cartRepository.save(cart);
            }
        } catch (DataIntegrityViolationException e) {
            // 외래 키 제약 조건 위배로 인한 예외 처리
            throw new IllegalArgumentException("Failed to create cart. 해당하는 itemId나 memberId가 존재하지 않습니다", e);
        }
    }

    @Override
    public int deleteCartItem(String memberId, Long itemId) {
        //String memberId = member.getMemberId();
        //Long itemId = item.getItemId();
        Optional<Cart> isCartItemExist = cartRepository.findByMemberIdAndItemId(memberId, itemId);

        if (isCartItemExist.isPresent()) {
            return cartRepository.deleteByMemberIdAndItemId(memberId, itemId);
        } else {
            return 0;
        }
    }

    @Override
    public int deleteCart(String memberId) {
        //String memberId = member.getMemberId();
        Optional<List<Cart>> memberCarts = cartRepository.findByMemberId(memberId);

        if (memberCarts.isPresent()) {
            return cartRepository.deleteByMemberId(memberId);
        } else {
            return 0;
        }
    }

    @Override
    public int decreaseCartItem(Long cartId) {
        int amount = -1;
        int result = cartRepository.updateCartAmount(cartId, amount);
        cartRepository.deleteCartIfAmountIsZero(cartId); //이게 제대로 작동하지 않았을 때의 예외처리를 해주어야 할까?

        return result;
    }

    @Override
    public int increaseCartItem(Long cartId, int amount) { //증가의 경우 상품페이지에서 바로 담을 수 있으므로 한번에 여러개의 상품이 증가할 수 있음

        return cartRepository.updateCartAmount(cartId, amount);
    }

    @Override
    public void payAllCart(String memberId, String memo) {
        Optional<List<Cart>> carts = cartRepository.findByMemberId(memberId);
        if (carts.isPresent()) {
            for (Cart cart : carts.get()) {
                /*int currentStock = itemService.getStock(cart.getItemId());
                if (currentStock < cart.getAmount()) {
                    throw new IllegalStateException(cart.getItemId() + " 상품의 재고가 부족합니다.");
                }
                else {
                    itemService.updateStock(cart.getItemId(), cart.getAmount());
                    itemService.updateCount(cart.getItemId(), cart.getAmount());
                }*/

                //order 기록 저장- 구매내역으로 무언가를 할 경우(user의 등급을 매길 경우 user에 paymentPrice 항목도 추가를 해야하나?)
                //order 기록 저장하면 동시에 주문해도 db에 다른 행으로 들어가는데 괜찮을까?
                Order order = new Order();
                order.setMemberId(cart.getMemberId());
                order.setItemId(cart.getItemId());
                order.setCount(cart.getAmount());
                order.setPrice(cart.getAmount() * itemService.getPrice(cart.getItemId()));
                order.setMemo(memo != null ? memo : ""); //controller에서 memo를 받아와서 order에 set해줌
                orderService.save(order);
                cartRepository.deleteByMemberIdAndItemId(memberId, cart.getItemId());
            }
        }
    }


    @Override
    public void paySomeCart(String memberId, List<Long> itemsId, String memo) {
        for (Long itemId : itemsId) {
            Optional<Cart> cart = cartRepository.findByMemberIdAndItemId(memberId, itemId);
            if (cart.isPresent()) {
                Cart existCart = cart.get();
                /*int currentStock = itemService.getStock(existCart.getItemId());
                if (currentStock < existCart.getAmount()) {
                    throw new IllegalStateException(existCart.getItemId() + " 상품의 재고가 부족합니다.");
                }
                else {
                    itemService.updateStock(existCart.getItemId(), existCart.getAmount());
                     itemService.updateCount(existCart.getItemId(), existCart.getAmount());
                }*/

                Order order = new Order();
                order.setMemberId(existCart.getMemberId());
                order.setItemId(existCart.getItemId());
                order.setCount(existCart.getAmount());
                order.setPrice(existCart.getAmount() * itemService.getPrice(existCart.getItemId()));
                order.setMemo(memo != null ? memo : ""); //controller에서 memo를 받아와서 order에 set해줌
                orderService.save(order);
                cartRepository.deleteByMemberIdAndItemId(memberId, itemId);
            }
        }
    }
}