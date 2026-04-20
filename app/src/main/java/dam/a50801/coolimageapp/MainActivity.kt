package dam.a50801.coolimageapp

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.bumptech.glide.Glide
import dam.a50801.coolimageapp.ImageAdapter
import dam.a50801.coolimageapp.ImageDetailsActivity
import dam.a50801.coolimageapp.MainViewModel
import dam.a50801.coolimageapp.R

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel
    private lateinit var adapter: ImageAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var swipeRefresh: SwipeRefreshLayout

    // Slots de favoritos
    private lateinit var favoriteSlots: List<ImageView>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Inicializar views
        recyclerView = findViewById(R.id.recyclerView)
        progressBar = findViewById(R.id.progressBar)
        swipeRefresh = findViewById(R.id.swipeRefresh)

        // Inicializar slots de favoritos
        favoriteSlots = listOf(
            findViewById(R.id.favorite1),
            findViewById(R.id.favorite2),
            findViewById(R.id.favorite3),
            findViewById(R.id.favorite4),
            findViewById(R.id.favorite5)
        )

        // Configurar RecyclerView com grelha de 2 colunas
        adapter = ImageAdapter { imageItem ->
            // Navegar para o ecrã de detalhes
            val intent = Intent(this, ImageDetailsActivity::class.java)
            intent.putExtra("imageId", imageItem.id)
            intent.putExtra("imageUrl", imageItem.urls.regular)
            intent.putExtra("thumbUrl", imageItem.urls.thumb)
            intent.putExtra("description", imageItem.description ?: "")
            intent.putExtra("photographer", imageItem.user.name)
            startActivity(intent)
        }
        recyclerView.layoutManager = GridLayoutManager(this, 2)
        recyclerView.adapter = adapter

        // Inicializar ViewModel
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]

        // Observar imagens
        viewModel.images.observe(this) { images ->
            adapter.updateImages(images)
        }

        // Observar favoritos
        viewModel.favorites.observe(this) { favorites ->
            updateFavoritesBar(favorites)
        }

        // Observar estado de carregamento
        viewModel.isLoading.observe(this) { isLoading ->
            progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
            swipeRefresh.isRefreshing = isLoading
        }

        // Observar erros
        viewModel.error.observe(this) { error ->
            error?.let {
                Toast.makeText(this, it, Toast.LENGTH_LONG).show()
            }
        }

        // Botão de refresh
        findViewById<Button>(R.id.refreshButton).setOnClickListener {
            viewModel.loadImages()
        }

        // SwipeRefresh
        swipeRefresh.setOnRefreshListener {
            viewModel.loadImages()
        }
    }

    /**
     * Atualiza a barra de favoritos com as imagens favoritas.
     */
    private fun updateFavoritesBar(favorites: List<FavoriteItem>) {
        favoriteSlots.forEachIndexed { index, imageView ->
            if (index < favorites.size) {
                val favorite = favorites[index]
                Glide.with(this)
                    .load(favorite.imageItem.urls.thumb)
                    .centerCrop()
                    .into(imageView)

                // Clique num favorito abre os detalhes
                imageView.setOnClickListener {
                    val intent = Intent(this, ImageDetailsActivity::class.java)
                    intent.putExtra("imageId", favorite.imageItem.id)
                    intent.putExtra("imageUrl", favorite.imageItem.urls.regular)
                    intent.putExtra("thumbUrl", favorite.imageItem.urls.thumb)
                    intent.putExtra("description", favorite.imageItem.description ?: "")
                    intent.putExtra("photographer", favorite.imageItem.user.name)
                    startActivity(intent)
                }
            } else {
                // Slot vazio
                imageView.setImageResource(android.R.drawable.ic_menu_gallery)
                imageView.setOnClickListener(null)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        // Recarregar favoritos quando voltamos a esta activity
        viewModel.refreshFavorites()
    }
}