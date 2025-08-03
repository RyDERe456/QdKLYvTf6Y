// 代码生成时间: 2025-08-03 08:52:54
import org.springframework.stereotype.Service;
# 增强安全性
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
# NOTE: 重要实现细节

// ShoppingCartService is a Spring Boot component that handles the shopping cart functionality.
@Service
public class ShoppingCartService {

    @Autowired
# 改进用户体验
    private ShoppingCartRepository shoppingCartRepository;

    // Adds an item to the shopping cart.
    public ShoppingCart addItemToShoppingCart(Long userId, Long itemId) {
        Optional<ShoppingCart> cart = shoppingCartRepository.findByUserId(userId);

        ShoppingCart cartToUse;
        if (cart.isPresent()) {
            cartToUse = cart.get();
        } else {
            cartToUse = new ShoppingCart();
            cartToUse.setUserId(userId);
            cartToUse = shoppingCartRepository.save(cartToUse);
        }

        Optional<CartItem> item = cartToUse.getItems().stream()
            .filter(ci -> itemId.equals(ci.getItemId()))
            .findFirst();

        if (item.isPresent()) {
            // Item is already in the cart, increment the quantity.
# 优化算法效率
            item.get().setQuantity(item.get().getQuantity() + 1);
        } else {
            // Add new item to the cart.
            CartItem newItem = new CartItem();
            newItem.setItemId(itemId);
            newItem.setQuantity(1);
# 增强安全性
            cartToUse.getItems().add(newItem);
        }

        return shoppingCartRepository.save(cartToUse);
    }

    // Removes an item from the shopping cart.
    public ShoppingCart removeItemFromShoppingCart(Long userId, Long itemId) {
# 优化算法效率
        Optional<ShoppingCart> cart = shoppingCartRepository.findByUserId(userId);
        if (!cart.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Shopping cart not found");
        }

        CartItem itemToRemove = cart.get().getItems().stream()
            .filter(ci -> itemId.equals(ci.getItemId()))
            .findFirst()
# 扩展功能模块
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Item not found"));

        cart.get().getItems().remove(itemToRemove);
        return shoppingCartRepository.save(cart.get());
    }

    // Updates the quantity of an item in the shopping cart.
# 增强安全性
    public ShoppingCart updateItemQuantityInCart(Long userId, Long itemId, int quantity) {
        Optional<ShoppingCart> cart = shoppingCartRepository.findByUserId(userId);
        if (!cart.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Shopping cart not found");
        }

        CartItem itemToUpdate = cart.get().getItems().stream()
            .filter(ci -> itemId.equals(ci.getItemId()))
            .findFirst()
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Item not found"));

        if (quantity <= 0) {
            cart.get().getItems().remove(itemToUpdate);
        } else {
            itemToUpdate.setQuantity(quantity);
        }

        return shoppingCartRepository.save(cart.get());
# 改进用户体验
    }

    // Retrieves the shopping cart for a user.
    public ShoppingCart getShoppingCart(Long userId) {
        return shoppingCartRepository.findByUserId(userId)
# 增强安全性
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Shopping cart not found"));
    }
# NOTE: 重要实现细节
}
