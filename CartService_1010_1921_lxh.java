// 代码生成时间: 2025-10-10 19:21:19
package com.example.demo.service;

import com.example.demo.exception.CartNotFoundException;
import com.example.demo.model.Cart;
import com.example.demo.model.Item;
import com.example.demo.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class CartService {
    
    @Autowired
    private CartRepository cartRepository;
    
    /*
     * 添加一个新的购物车项
     */
    @Transactional
    public Cart addItemToCart(String cartId, Item item) {
        return cartRepository.findById(cartId)
                .map(cart -> {
                    cart.addItem(item);
                    return cartRepository.save(cart);
                }).orElseThrow(() -> new CartNotFoundException("Cart not found with id: " + cartId));
    }
    
    /*
     * 从购物车中删除一个项
     */
    @Transactional
    public Cart removeItemFromCart(String cartId, String itemId) {
        return cartRepository.findById(cartId)
                .map(cart -> {
                    cart.removeItem(itemId);
                    return cartRepository.save(cart);
                }).orElseThrow(() -> new CartNotFoundException("Cart not found with id: " + cartId));
    }
    
    /*
     * 获取购物车内容
     */
    public Cart getCart(String cartId) {
        return cartRepository.findById(cartId)
                .orElseThrow(() -> new CartNotFoundException("Cart not found with id: " + cartId));
    }

    /*
     * 清空购物车
     */
    @Transactional
    public void clearCart(String cartId) {
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new CartNotFoundException("Cart not found with id: " + cartId));
        cart.clear();
        cartRepository.save(cart);
    }
}
