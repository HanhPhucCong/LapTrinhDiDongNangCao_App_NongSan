package org.agromarket.agro_server.service.customer.Impl;

import lombok.RequiredArgsConstructor;
import org.agromarket.agro_server.exception.CustomException;
import org.agromarket.agro_server.exception.NotFoundException;
import org.agromarket.agro_server.model.dto.response.FavoriteRespone;
import org.agromarket.agro_server.model.entity.Favorite;
import org.agromarket.agro_server.model.entity.Product;
import org.agromarket.agro_server.model.entity.User;
import org.agromarket.agro_server.repositories.customer.FavoriteRepository;
import org.agromarket.agro_server.repositories.customer.ProductRepository;
import org.agromarket.agro_server.service.customer.FavoriteService;
import org.agromarket.agro_server.util.mapper.FavoriteMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class FavoriteServiceImpl implements FavoriteService {
  private final FavoriteRepository favoriteRepository;
  private final ProductRepository productRepository;
  private final FavoriteMapper favoriteMapper;

  @Transactional
  @Override
  public FavoriteRespone addToFavorite(long productId) {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    User currUser = (User) authentication.getPrincipal();

    Favorite favorite = favoriteRepository.getByUserId(currUser.getId());
    if (favorite == null) {
      favorite = new Favorite();
      favorite.setUser(currUser);
      currUser.setFavorite(favorite);
      favoriteRepository.save(favorite);
    }

    Product product =
        productRepository
            .findById(productId)
            .orElseThrow(() -> new NotFoundException("Product not found with id: " + productId));

    if (favorite.getProducts().contains(product)) {
      throw new CustomException("Product is already in favorite!", 409);
    }

    favorite.getProducts().add(product);
    favoriteRepository.save(favorite);

    return favoriteMapper.convertToResponse(favorite);
  }

  @Transactional
  @Override
  public FavoriteRespone removeFromFavorite(long productId) {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    User currUser = (User) authentication.getPrincipal();

    Favorite favorite = favoriteRepository.getByUserId(currUser.getId());
    if (favorite == null) {
      throw new NotFoundException("Favorite is not found with userId: " + currUser.getId());
    }

    Product product =
        productRepository
            .findById(productId)
            .orElseThrow(() -> new NotFoundException("Product not found with id: " + productId));

    if (!favorite.getProducts().contains(product)) {
      throw new NotFoundException("The product is not in your favorites basket.");
    }

    favorite.getProducts().remove(product);

    favoriteRepository.save(favorite);

    return favoriteMapper.convertToResponse(favorite);
  }

  @Override
  public FavoriteRespone myFavorites() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    User currUser = (User) authentication.getPrincipal();

    Favorite favorite = favoriteRepository.getByUserId(currUser.getId());
    if (favorite == null) {
      favorite = new Favorite();
      favorite.setUser(currUser);
      currUser.setFavorite(favorite);
      favoriteRepository.save(favorite);
    }
    return favoriteMapper.convertToResponse(favorite);
  }

  @Override
  public FavoriteRespone clearFavorites() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    User currUser = (User) authentication.getPrincipal();

    Favorite favorite = favoriteRepository.getByUserId(currUser.getId());
    if (favorite == null) {
      favorite = new Favorite();
      favorite.setUser(currUser);
      currUser.setFavorite(favorite);
      favoriteRepository.save(favorite);
    }

    favorite.getProducts().clear();
    favoriteRepository.save(favorite);
    return favoriteMapper.convertToResponse(favorite);
  }
}
