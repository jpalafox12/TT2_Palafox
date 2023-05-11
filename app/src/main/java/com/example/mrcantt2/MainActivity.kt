package com.example.mrcantt2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import android.widget.Toolbar
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.example.mrcantt2.Citas.CitasFragment
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity() {

    /*Creamos las variables*/
    lateinit var toggle: ActionBarDrawerToggle
    lateinit var drawerLayout: DrawerLayout
    private lateinit var toolbar: Toolbar


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()

        val misMascotasFragment = MisMascotasFragment()

        fragmentTransaction.replace(R.id.fragment_container, misMascotasFragment)
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()

        val drawerLayout : DrawerLayout = findViewById(R.id.drawerLayout)
        val navView : NavigationView = findViewById(R.id.navigation_view)

        toggle = ActionBarDrawerToggle(this, drawerLayout, R.string.open,R.string.close )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)


        navView.setNavigationItemSelectedListener {

            it.isChecked = true

            when(it.itemId){
                R.id.nav_mascotas -> {
                    Toast.makeText(
                        applicationContext,
                        "Vas ingresar a la parte de mascotas",
                        Toast.LENGTH_SHORT
                    ).show()
                    replaceFragment(MisMascotasFragment(), it.title.toString())
                    drawerLayout.closeDrawer(GravityCompat.START)

                }
                R.id.nav_citas -> {
                    Toast.makeText(
                        applicationContext,
                        "Vas ingresar a la parte de citas",
                        Toast.LENGTH_SHORT
                    ).show()
                    replaceFragment(CitasFragment(), it.title.toString())
                    drawerLayout.closeDrawer(GravityCompat.START)
                }
                R.id.nav_perfil -> {
                    Toast.makeText(
                        applicationContext,
                        "Vas a entrar a MI PERFIL",
                        Toast.LENGTH_SHORT
                    ).show()
                    replaceFragment(PerfilUsuarioFragment(), it.title.toString())
                    drawerLayout.closeDrawer(GravityCompat.START)
                }

                R.id.nav_logout -> {
                    Toast.makeText(
                        applicationContext,
                        "Vas a cerrar sesión",
                        Toast.LENGTH_SHORT
                    ).show()
                    val intent = Intent(this, LogIn::class.java)
                    startActivity(intent)
                    finish()
                }
            }
            true
        }

    }

    private fun replaceFragment(fragment: Fragment, title : String){
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()

        fragmentTransaction.replace(R.id.fragment_container, fragment)
        fragmentTransaction.commit()
        setTitle(title)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if (toggle.onOptionsItemSelected(item)){
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}