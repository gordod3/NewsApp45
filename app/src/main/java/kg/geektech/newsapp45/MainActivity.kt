package kg.geektech.newsapp45

import android.os.Bundle
import android.view.Menu
import android.view.View
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import kg.geektech.newsapp45.databinding.ActivityMainBinding
import kg.geektech.newsapp45.ui.OnSearchAction

class MainActivity : AppCompatActivity() {

    lateinit var onSearch: OnSearchAction
    private lateinit var navController: NavController
    private lateinit var binding: ActivityMainBinding
    private var allActionBarId = arrayListOf(
        R.id.navigation_home,
        R.id.navigation_dashboard,
        R.id.navigation_notifications,
        R.id.navigation_profile,
    )

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.home_search_menu, menu)
        val menuItem = menu?.findItem(R.id.home_menu_action_search)
        if (menuItem != null) {
            val searchView = menuItem.actionView as SearchView
            searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(p0: String?): Boolean {
                    return true
                }

                override fun onQueryTextChange(searchedText: String?): Boolean {
                    onSearch.onSearch(searchedText)
                    return true
                }

            })
        }

        return super.onCreateOptionsMenu(menu)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView

        navController = findNavController(R.id.nav_host_fragment_activity_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home,
                R.id.navigation_dashboard,
                R.id.navigation_notifications,
                R.id.navigation_profile
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        navController.addOnDestinationChangedListener { controller, destination, argument ->
            if (allActionBarId.contains(destination.id)) binding.navView.visibility = View.VISIBLE
            else binding.navView.visibility = View.INVISIBLE

            if (destination.id == R.id.boardFragment) supportActionBar?.hide()
            else supportActionBar?.show()


        }

        if (!Prefs(this).isShown()) navController.navigate(R.id.boardFragment)
    }
}