package com.example.vaxtrack

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavHostController
import androidx.navigation.compose.*
import androidx.room.Room
import com.example.vaxtrack.data.local.AppDatabase
import com.example.vaxtrack.data.repository.SyncRepository
import com.example.vaxtrack.data.repository.PhotoRepository
import com.example.vaxtrack.ui.screens.*
import com.example.vaxtrack.ui.theme.VaxTrackTheme
import com.example.vaxtrack.viewmodel.VaccinationViewModel
import com.example.vaxtrack.remote.SupabaseClient
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.storage.storage
import kotlinx.coroutines.launch

data class DrawerItem(
    val route: String,
    val label: String,
    val icon: ImageVector
)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)

        val db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "vaxtrack_db"
        ).build()

        val syncRepository = SyncRepository(db.syncDao())

        // Lancer la synchronisation au démarrage
        lifecycleScope.launch {
            syncRepository.processPendingOperations()
        }

        setContent {
            VaxTrackTheme {
                // ton NavHost habituel
            }
        }
        setContent {
            VaxTrackTheme {
                val navController = rememberNavController()

                NavHost(navController = navController, startDestination = "splash") {
                    composable("splash") {
                        SplashScreen { nextRoute ->
                            navController.navigate(nextRoute) {
                                popUpTo("splash") { inclusive = true }
                            }
                        }
                    }
                    composable("login") {
                        LoginScreen(onLoginSuccess = {
                            navController.navigate("dashboard") {
                                popUpTo("login") { inclusive = true }
                            }
                        })
                    }
                    composable("register") {
                        RegisterScreen(onRegisterSuccess = {
                            navController.navigate("login") {
                                popUpTo("register") { inclusive = true }
                            }
                        })
                    }
                    composable("dashboard") {
                        // Navigation principale de l’app
                        AppNavigation(navController)
                    }
                }
            }
        }
    }
}


@Composable
fun AuthNavigation(navController: NavHostController) {
    NavHost(navController = navController, startDestination = "login") {
        composable("login") {
            LoginScreen(onLoginSuccess = {
                navController.navigate("app") {
                    popUpTo("login") { inclusive = true }
                }
            })
        }
        composable("register") {
            RegisterScreen(onRegisterSuccess = {
                navController.navigate("login") {
                    popUpTo("register") { inclusive = true }
                }
            })
        }
    }
}

@Composable
fun AppNavigation(navController: NavHostController) {
    val context = androidx.compose.ui.platform.LocalContext.current
    val db = AppDatabase.getInstance(context)
    val menageRepository = com.example.vaxtrack.data.repository.MenageRepository(db.menageDao())
    val photoRepository = PhotoRepository(SupabaseClient.client.storage)
    
    val viewModel = VaccinationViewModel(
        menageRepository = menageRepository,
        photoRepository = photoRepository
    )

    NavHost(navController = navController, startDestination = "dashboard") {
        composable("dashboard") { DashboardScreen(viewModel) }
        composable("menages") { MenageListScreen(viewModel) }
        composable("formulaire") { FormulaireMenageScreen(viewModel) }
        composable("profil") { Text("Écran Profil Agent") }
        composable("parametres") { Text("Écran Paramètres") }
    }
}

@Composable
fun DrawerContent(
    navController: NavHostController,
    drawerState: DrawerState,
    scope: kotlinx.coroutines.CoroutineScope
) {
    val items = listOf(
        DrawerItem("dashboard", "Dashboard", Icons.Default.Home),
        DrawerItem("menages", "Ménages", Icons.Default.List),
        DrawerItem("formulaire", "Formulaire", Icons.Default.Add),
        DrawerItem("profil", "Profil", Icons.Default.Person),
        DrawerItem("parametres", "Paramètres", Icons.Default.Settings)
    )

    ModalDrawerSheet {
        Text("Menu", style = MaterialTheme.typography.titleLarge, modifier = Modifier.padding(16.dp))
        items.forEach { item ->
            NavigationDrawerItem(
                icon = { Icon(item.icon, contentDescription = item.label) },
                label = { Text(item.label) },
                selected = false,
                onClick = {
                    navController.navigate(item.route)
                    scope.launch { drawerState.close() }
                }
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Bouton Logout
        NavigationDrawerItem(
            icon = { Icon(Icons.Default.Settings, contentDescription = "Déconnexion") },
            label = { Text("Déconnexion") },
            selected = false,
            onClick = {
                scope.launch {
                    // Supabase signOut
                    SupabaseClient.client.auth.signOut()
                    // Redirection vers Login
                    navController.navigate("login") {
                        popUpTo("dashboard") { inclusive = true }
                    }
                    scope.launch { drawerState.close() }
                }
            }
        )
    }
}
