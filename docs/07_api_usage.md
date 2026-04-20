# API Usage

## API: Unsplash

### Endpoint principal

GET https://api.unsplash.com/photos?page=1&per_page=30

### Headers necessários

Authorization: Client-ID {ACCESS_KEY}

### Exemplo de resposta

```json
[
  {
    "id": "abc123",
    "description": "Beautiful sunset",
    "urls": {
      "regular": "https://images.unsplash.com/photo-abc123",
      "thumb": "https://images.unsplash.com/photo-abc123?w=200"
    },
    "user": {
      "name": "John Doe"
    },
    "width": 4000,
    "height": 3000
  }
]
```

### Parâmetros

- page: número da página (para paginação)
- per_page: número de imagens por página (máximo 30)

### Limitações

- 50 requests por hora (plano gratuito)
- Obrigatório crédito ao fotógrafo
