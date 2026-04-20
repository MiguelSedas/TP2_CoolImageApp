package dam.a50801.coolimageapp

/**
 * Representa uma imagem obtida da API da Unsplash.
 * @param id identificador único da imagem
 * @param description descrição ou título da imagem
 * @param urls URLs da imagem em diferentes tamanhos
 * @param user informação do fotógrafo
 * @param width largura original da imagem
 * @param height altura original da imagem
 */
data class ImageItem(
    val id: String,
    val description: String?,
    val urls: ImageUrls,
    val user: UnsplashUser,
    val width: Int,
    val height: Int
)

/**
 * URLs da imagem em diferentes tamanhos.
 * @param regular URL da imagem em tamanho normal
 * @param thumb URL da miniatura
 */
data class ImageUrls(
    val regular: String,
    val thumb: String
)

/**
 * Informação do fotógrafo.
 * @param name nome do fotógrafo
 */
data class UnsplashUser(
    val name: String
)

/**
 * Representa um item favorito com timestamp para lógica FIFO.
 * @param imageItem a imagem favorita
 * @param addedAt timestamp de quando foi adicionada
 */
data class FavoriteItem(
    val imageItem: ImageItem,
    val addedAt: Long = System.currentTimeMillis()
)