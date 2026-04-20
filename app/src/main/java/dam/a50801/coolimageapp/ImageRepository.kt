package dam.a50801.coolimageapp

/**
 * Repositório que coordena as fontes de dados:
 * API remota, cache local e favoritos.
 */
class ImageRepository {

    companion object {
        // Instância única partilhada por toda a app
        @Volatile
        private var instance: ImageRepository? = null

        fun getInstance(): ImageRepository {
            return instance ?: synchronized(this) {
                instance ?: ImageRepository().also { instance = it }
            }
        }
    }

    private val apiService = UnsplashApiService()
    private val cache = LocalCache()
    private val favoritesManager = FavoritesManager()

    /**
     * Obtém imagens — primeiro tenta a API, se falhar usa a cache.
     * @param page número da página
     * @return lista de imagens
     */
    fun getImages(page: Int = 1): List<ImageItem> {
        return try {
            val images = apiService.fetchImages(page)
            cache.addImages(images)
            images
        } catch (e: Exception) {
            // Se a API falhar, devolve as imagens da cache
            if (cache.hasImages()) {
                cache.getImages()
            } else {
                throw e
            }
        }
    }

    /**
     * Devolve as imagens em cache.
     */
    fun getCachedImages(): List<ImageItem> = cache.getImages()

    /**
     * Adiciona uma imagem aos favoritos.
     */
    fun addFavorite(imageItem: ImageItem) = favoritesManager.addFavorite(imageItem)

    /**
     * Remove uma imagem dos favoritos.
     */
    fun removeFavorite(imageId: String) = favoritesManager.removeFavorite(imageId)

    /**
     * Verifica se uma imagem é favorita.
     */
    fun isFavorite(imageId: String): Boolean = favoritesManager.isFavorite(imageId)

    /**
     * Devolve a lista de favoritos.
     */
    fun getFavorites(): List<FavoriteItem> = favoritesManager.getFavorites()
}