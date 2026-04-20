package dam.a50801.coolimageapp

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.InputStreamReader
import java.net.URL

/**
 * Serviço responsável por fazer chamadas à API da Unsplash.
 */
class UnsplashApiService {

    companion object {
        private const val ACCESS_KEY = "hE7E7slTstRWqUAhTQKsxOOmCFwl6cjunR4gtCWJWXw"
        private const val BASE_URL = "https://api.unsplash.com"
    }

    /**
     * Obtém uma lista de imagens da API da Unsplash.
     * @param page número da página
     * @param perPage número de imagens por página (máximo 30)
     * @return lista de ImageItem
     */
    fun fetchImages(page: Int = 1, perPage: Int = 30): List<ImageItem> {
        val url = URL("$BASE_URL/photos?page=$page&per_page=$perPage&client_id=$ACCESS_KEY")
        url.openStream().use {
            val type = object : TypeToken<List<ImageItem>>() {}.type
            return Gson().fromJson(InputStreamReader(it, "UTF-8"), type)
        }
    }
}