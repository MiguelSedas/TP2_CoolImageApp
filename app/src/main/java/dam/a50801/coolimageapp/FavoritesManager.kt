package dam.a50801.coolimageapp

/**
 * Gere os favoritos com lógica FIFO (First In, First Out).
 * Máximo de 5 favoritos em simultâneo.
 */
class FavoritesManager {

    companion object {
        private const val MAX_FAVORITES = 5
    }

    private val favorites = mutableListOf<FavoriteItem>()

    /**
     * Adiciona uma imagem aos favoritos.
     * Se já existirem 5 favoritos, remove o mais antigo (FIFO).
     * @param imageItem imagem a adicionar
     */
    fun addFavorite(imageItem: ImageItem) {
        // Verificar se já é favorito
        if (isFavorite(imageItem.id)) return

        // FIFO — remove o mais antigo se chegou ao limite
        if (favorites.size >= MAX_FAVORITES) {
            favorites.removeAt(0)
        }

        favorites.add(FavoriteItem(imageItem))
    }

    /**
     * Remove uma imagem dos favoritos.
     * @param imageId id da imagem a remover
     */
    fun removeFavorite(imageId: String) {
        favorites.removeIf { it.imageItem.id == imageId }
    }

    /**
     * Verifica se uma imagem é favorita.
     * @param imageId id da imagem
     * @return true se é favorita
     */
    fun isFavorite(imageId: String): Boolean {
        return favorites.any { it.imageItem.id == imageId }
    }

    /**
     * Devolve a lista de favoritos ordenada por data de adição.
     * @return lista imutável de FavoriteItem
     */
    fun getFavorites(): List<FavoriteItem> = favorites.toList()

    /**
     * Devolve o número de favoritos.
     */
    fun size(): Int = favorites.size
}