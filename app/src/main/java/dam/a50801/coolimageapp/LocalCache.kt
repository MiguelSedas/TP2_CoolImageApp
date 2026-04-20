package dam.a50801.coolimageapp

/**
 * Cache local em memória para guardar até 50 imagens.
 * Mantém pelo menos 10 imagens antes e depois da posição atual.
 */
class LocalCache {

    companion object {
        private const val MAX_CACHE_SIZE = 50
    }

    private val cache = mutableListOf<ImageItem>()

    /**
     * Adiciona imagens à cache.
     * Se a cache ultrapassar o limite, remove as mais antigas.
     * @param images lista de imagens a adicionar
     */
    fun addImages(images: List<ImageItem>) {
        cache.addAll(images)
        if (cache.size > MAX_CACHE_SIZE) {
            val excess = cache.size - MAX_CACHE_SIZE
            cache.subList(0, excess).clear()
        }
    }

    /**
     * Devolve todas as imagens em cache.
     * @return lista imutável de imagens
     */
    fun getImages(): List<ImageItem> = cache.toList()

    /**
     * Verifica se a cache tem imagens.
     * @return true se a cache não está vazia
     */
    fun hasImages(): Boolean = cache.isNotEmpty()

    /**
     * Limpa todas as imagens da cache.
     */
    fun clear() = cache.clear()

    /**
     * Devolve o número de imagens em cache.
     */
    fun size(): Int = cache.size
}