package dam.a50801.coolimageapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

/**
 * ViewModel que gere o estado da UI principal.
 * Expõe LiveData para imagens, favoritos, loading e erros.
 */
class MainViewModel : ViewModel() {

    private val repository = ImageRepository.getInstance()

    /** Lista de imagens a mostrar na UI */
    private val _images = MutableLiveData<List<ImageItem>>()
    val images: LiveData<List<ImageItem>> = _images

    /** Lista de favoritos */
    private val _favorites = MutableLiveData<List<FavoriteItem>>()
    val favorites: LiveData<List<FavoriteItem>> = _favorites

    /** Estado de carregamento */
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    /** Mensagem de erro */
    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> = _error

    init {
        loadImages()
    }

    /**
     * Carrega imagens da API numa thread separada.
     */
    fun loadImages(page: Int = 1) {
        _isLoading.value = true
        _error.value = null

        Thread {
            try {
                val images = repository.getImages(page)
                _images.postValue(images)
            } catch (e: Exception) {
                _error.postValue("Erro ao carregar imagens: ${e.message}")
            } finally {
                _isLoading.postValue(false)
            }
        }.start()
    }

    /**
     * Atualiza a lista de favoritos.
     */
    fun refreshFavorites() {
        _favorites.postValue(repository.getFavorites())
    }

    /**
     * Adiciona ou remove uma imagem dos favoritos.
     */
    fun toggleFavorite(imageItem: ImageItem) {
        if (repository.isFavorite(imageItem.id)) {
            repository.removeFavorite(imageItem.id)
        } else {
            repository.addFavorite(imageItem)
        }
        refreshFavorites()
    }

    /**
     * Verifica se uma imagem é favorita.
     */
    fun isFavorite(imageId: String): Boolean = repository.isFavorite(imageId)
}