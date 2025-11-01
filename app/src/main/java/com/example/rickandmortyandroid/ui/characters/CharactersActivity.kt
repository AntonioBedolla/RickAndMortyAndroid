package com.example.rickandmortyandroid.ui.characters

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.rickandmortyandroid.ui.theme.RickAndMortyAndroidTheme
import com.example.rickandmortyandroid.viewmodels.CharactersViewModel
import com.example.rickandmortyandroid.models.Character

/**
 * Activity principal de la lista de personajes.
 * Ahora usa Jetpack Compose en lugar de XML + RecyclerView.
 */class CharactersActivity : ComponentActivity() {
    // 🔹 ViewModel (inyectado automáticamente con viewModels)
    private val viewModel: CharactersViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // ✅ Compose reemplaza setContentView()
        setContent {
            RickAndMortyAndroidTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    CharactersScreen(viewModel = viewModel)
                }
            }
        }
    }
}

/**
 * Pantalla principal que muestra la lista de personajes.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CharactersScreen(viewModel: CharactersViewModel) {
    // 🔹 Observa el estado del ViewModel usando collectAsStateWithLifecycle
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    // 🔹 Scaffold nos da estructura básica (snackbar, FAB, etc.)
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Personajes") }
            )
        }
    ) { padding ->
        // 🔹 LazyColumn reemplaza al RecyclerView
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(8.dp)
        ) {
            items(uiState.characters) { character ->
                CharacterItem(character = character)
            }

            // 🔹 Indicador simple de carga (paginación o primera carga)
            if (uiState.isLoading) {
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        contentAlignment = androidx.compose.ui.Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }
            }
        }
    }

    // 🔹 Efecto que carga la primera página cuando la pantalla aparece
    LaunchedEffect(Unit) {
        viewModel.loadFirstPage()
    }
}

/**
 * Item individual del personaje (antes lo manejaba el RecyclerView.Adapter)
 */
@Composable
fun CharacterItem(character: com.example.rickandmortyandroid.models.Character) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(12.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // 🖼 Imagen del personaje usando Coil
            Image(
                painter = rememberAsyncImagePainter(character.imageUrl),
                contentDescription = character.name,
                modifier = Modifier
                    .size(80.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.width(16.dp))

            // 🔤 Datos del personaje
            Column {
                Text(text = character.name, style = MaterialTheme.typography.titleMedium)
                Text(text = character.species, style = MaterialTheme.typography.bodyMedium)
                Text(text = "Estado: ${character.status}", style = MaterialTheme.typography.bodySmall)
            }
        }
    }
}