package dam.a50801.coolimageapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

/**
 * Adapter para o RecyclerView que mostra a grelha de imagens.
 * @param onImageClick callback chamado quando o utilizador clica numa imagem
 */
class ImageAdapter(
    private var images: List<ImageItem> = emptyList(),
    private val onImageClick: (ImageItem) -> Unit
) : RecyclerView.Adapter<ImageAdapter.ImageViewHolder>() {

    /**
     * ViewHolder que representa cada item da grelha.
     */
    inner class ImageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.itemImage)

        fun bind(imageItem: ImageItem) {
            Glide.with(itemView.context)
                .load(imageItem.urls.thumb)
                .centerCrop()
                .into(imageView)

            itemView.setOnClickListener { onImageClick(imageItem) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_image, parent, false)
        return ImageViewHolder(view)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        holder.bind(images[position])
    }

    override fun getItemCount(): Int = images.size

    /**
     * Atualiza a lista de imagens e notifica o RecyclerView.
     * @param newImages nova lista de imagens
     */
    fun updateImages(newImages: List<ImageItem>) {
        images = newImages
        notifyDataSetChanged()
    }
}