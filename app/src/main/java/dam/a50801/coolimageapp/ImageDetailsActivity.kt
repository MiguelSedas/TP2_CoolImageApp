package dam.a50801.coolimageapp

import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide

class ImageDetailsActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image_details)

        viewModel = ViewModelProvider(this)[MainViewModel::class.java]

        // Receber dados da MainActivity
        val imageId = intent.getStringExtra("imageId") ?: ""
        val imageUrl = intent.getStringExtra("imageUrl") ?: ""
        val description = intent.getStringExtra("description") ?: ""
        val photographer = intent.getStringExtra("photographer") ?: ""

        // Preencher a UI
        val imageView = findViewById<ImageView>(R.id.detailImage)
        val descriptionView = findViewById<TextView>(R.id.detailDescription)
        val photographerView = findViewById<TextView>(R.id.detailPhotographer)
        val favoriteButton = findViewById<Button>(R.id.favoriteButton)

        Glide.with(this)
            .load(imageUrl)
            .centerCrop()
            .into(imageView)

        descriptionView.text = description.ifEmpty { "Sem descrição" }
        photographerView.text = "📷 $photographer"

        // Estado inicial do botão de favorito
        updateFavoriteButton(favoriteButton, viewModel.isFavorite(imageId))

        // Botão de favorito
        favoriteButton.setOnClickListener {
            val imageItem = ImageItem(
                id = imageId,
                description = description,
                urls = ImageUrls(
                    regular = imageUrl,
                    thumb = intent.getStringExtra("thumbUrl") ?: ""
                ),
                user = UnsplashUser(name = photographer),
                width = 0,
                height = 0
            )
            viewModel.toggleFavorite(imageItem)
            updateFavoriteButton(favoriteButton, viewModel.isFavorite(imageId))
        }

        // Botão de voltar
        findViewById<Button>(R.id.backButton).setOnClickListener {
            finish()
        }
    }

    private fun updateFavoriteButton(button: Button, isFavorite: Boolean) {
        button.text = if (isFavorite) "⭐ Remover Favorito" else "☆ Adicionar Favorito"
    }
}