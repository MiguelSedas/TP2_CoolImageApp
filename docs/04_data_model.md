# Data Model

## ImageItem

- id: String — identificador único da imagem
- url: String — URL da imagem em tamanho normal
- thumbUrl: String — URL da miniatura
- title: String — descrição ou título
- photographer: String — nome do fotógrafo
- width: Int — largura original
- height: Int — altura original

## FavoriteItem

- imageItem: ImageItem — imagem favorita
- addedAt: Long — timestamp de quando foi adicionada (para FIFO)
