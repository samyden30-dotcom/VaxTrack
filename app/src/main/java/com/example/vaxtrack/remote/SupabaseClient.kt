package com.example.vaxtrack.remote

import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.auth.Auth
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.storage.Storage

object SupabaseClient {
    val client = createSupabaseClient(
        supabaseUrl = "https://vujatqsqnlxihjwoodeu.supabase.co",
        supabaseKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6InZ1amF0cXNxbmx4aWhqd29vZGV1Iiwicm9sZSI6ImFub24iLCJpYXQiOjE3Nzc2MjI5OTAsImV4cCI6MjA5MzE5ODk5MH0.z3gCaoUDt2UFEkEDZk3ztUQbiwX6hkct8FRc6yaBD38"
    ) {
        install(Auth)
        install(Postgrest)
        install(Storage)
    }
}
