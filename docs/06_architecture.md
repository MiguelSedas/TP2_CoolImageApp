# Architecture

## Padrão: MVVM (Model-View-ViewModel)

## Camadas

### UI (View)

- MainActivity
- ImageDetailsActivity
- RecyclerView Adapters

### ViewModel

- MainViewModel — gere o estado da lista de imagens, favoritos e cache

### Repository

- ImageRepository — intermédio entre ViewModel e fontes de dados

### Data Sources

- UnsplashApiService — chamadas à API remota
- LocalCache — cache local em memória (até 50 imagens)
- FavoritesManager — gestão dos favoritos (FIFO, máximo 5)

## Diagrama

UI → ViewModel → Repository → ApiService
→ LocalCache
→ FavoritesManager
