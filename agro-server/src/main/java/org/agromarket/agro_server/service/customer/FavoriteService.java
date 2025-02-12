package org.agromarket.agro_server.service.customer;

import org.agromarket.agro_server.model.dto.response.FavoriteRespone;

public interface FavoriteService {
  public FavoriteRespone addToFavorite(long productId);

  public FavoriteRespone removeFromFavorite(long productId);

  public FavoriteRespone myFavorites();

  public FavoriteRespone clearFavorites();
}
