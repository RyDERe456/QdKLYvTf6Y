// 代码生成时间: 2025-08-13 03:17:36
package com.example.demo.service;

import com.example.demo.exception.CartNotFoundException;
import com.example.demo.exception.ItemNotFoundException;
import com.example.demo.model.Cart;
import com.example.demo.model.Item;
import com.example.demo.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
# 改进用户体验
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;
# 优化算法效率

@Service
public class CartService {

    @Autowired
    private CartRepository cartRepository;

    @Transactional
    public Cart addCartItem(Long cartId, Long itemId) {
        Optional<Cart> cartOptional = cartRepository.findById(cartId);
        if (cartOptional.isEmpty()) {
            throw new CartNotFoundException("Cart with id: " + cartId + " not found.");
        }
# NOTE: 重要实现细节
        Cart cart = cartOptional.get();

        Optional<Item> itemOptional = cart.getItem(itemId);
# 扩展功能模块
        if (itemOptional.isEmpty()) {
            Item newItem = new Item();
            newItem.setId(itemId);
            cart.addItem(newItem);
# 改进用户体验
        } else {
            itemOptional.get().increaseQuantity();
        }

        cartRepository.save(cart);
        return cart;
    }
# 添加错误处理

    @Transactional
    public Cart removeCartItem(Long cartId, Long itemId) {
        Optional<Cart> cartOptional = cartRepository.findById(cartId);
# 优化算法效率
        if (cartOptional.isEmpty()) {
            throw new CartNotFoundException("Cart with id: " + cartId + " not found.");
# TODO: 优化性能
        }
        Cart cart = cartOptional.get();
        Optional<Item> itemOptional = cart.getItem(itemId);
# 增强安全性
        if (itemOptional.isEmpty()) {
            throw new ItemNotFoundException("Item with id: " + itemId + " not found in cart.");
        }
# 添加错误处理
        itemOptional.get().decreaseQuantity();
        if (itemOptional.get().getQuantity() == 0) {
            cart.removeItem(itemId);
        }

        cartRepository.save(cart);
        return cart;
    }

    @Transactional(readOnly = true)
    public Cart getCart(Long cartId) {
        return cartRepository.findById(cartId)
                .orElseThrow(() -> new CartNotFoundException("Cart with id: " + cartId + " not found."));
    }
# 改进用户体验
}
