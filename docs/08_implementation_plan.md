# Implementation Plan

## Step 1

Criar o projeto Android com Kotlin e XML Views.
Adicionar dependências: Gson, Glide (para carregar imagens), RecyclerView.

## Step 2

Criar o modelo de dados ImageItem e FavoriteItem.

## Step 3

Implementar o UnsplashApiService para fazer chamadas à API.

## Step 4

Criar o LocalCache para guardar até 50 imagens em memória.

## Step 5

Criar o FavoritesManager com lógica FIFO (máximo 5 favoritos).

## Step 6

Criar o ImageRepository que coordena ApiService, Cache e Favoritos.

## Step 7

Criar o MainViewModel com LiveData para expor imagens e favoritos à UI.

## Step 8

Criar o RecyclerView Adapter para mostrar as imagens.

## Step 9

Desenhar o layout activity_main.xml com RecyclerView, ProgressBar e barra de favoritos.

## Step 10

Conectar a MainActivity ao ViewModel e observar LiveData.

## Step 11

Criar o ImageDetailsActivity com layout e lógica de favoritos.

## Step 12

Implementar o botão de refresh e SwipeRefreshLayout.

## Step 13

Adicionar tratamento de erros da API com mensagens ao utilizador.

## Step 14

Testar toda a aplicação e corrigir problemas encontrados.
