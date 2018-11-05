package uk.co.jimmyraystudios.chucknorrisjokeschallenge

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import uk.co.jimmyraystudios.chucknorrisjokeschallenge.home.HomeFragment
import uk.co.jimmyraystudios.chucknorrisjokeschallenge.inifite.InfiniteJokesFragment
import uk.co.jimmyraystudios.chucknorrisjokeschallenge.search.SearchFragment

class MainActivity : AppCompatActivity(), MainActivityInteractor {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        loadMainFragment()
    }

    override fun openSearch() {
        val searchFragment = SearchFragment.newInstance()
        switchFragment(searchFragment)
    }

    override fun showJokeDialog(joke: String) {
        AlertDialog.Builder(this@MainActivity)
                .setCancelable(true)
                .setMessage(joke.escapeHtml())
                .setPositiveButton("Ok") { dialog, which -> dialog.dismiss() }
                .show()
    }

    override fun showErrorDialog(title: String, message: String) {
        AlertDialog.Builder(this@MainActivity)
                .setCancelable(true)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("Ok") { dialog, which -> dialog.dismiss() }
                .show()
    }

    private fun loadMainFragment() {
        supportFragmentManager
                .beginTransaction()
                .replace(R.id.main_fragment_container, HomeFragment())
                .addToBackStack("")
                .commit()
    }

    override fun showInfiniteJokes() {
        switchFragment(InfiniteJokesFragment.newInstance())
    }

    private fun switchFragment(newFragment: Fragment) {
        supportFragmentManager
                .beginTransaction()
                .replace(R.id.main_fragment_container, newFragment)
                .addToBackStack("")
                .setCustomAnimations(android.R.anim.slide_in_left, 0)
                .commit()
    }
}
