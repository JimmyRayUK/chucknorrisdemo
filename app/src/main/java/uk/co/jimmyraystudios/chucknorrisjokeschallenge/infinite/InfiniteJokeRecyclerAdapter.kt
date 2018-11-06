package uk.co.jimmyraystudios.chucknorrisjokeschallenge.infinite

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import uk.co.jimmyraystudios.chucknorrisjokeschallenge.R
import uk.co.jimmyraystudios.chucknorrisjokeschallenge.escapeHtml

/**
 * Quick and dirty recycler adapter for infinite loading of data
 * This won't clear old items and will infinitely add new ones, would need to release given time to develop it
 */
class InfiniteJokeRecyclerAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder> {
    val TYPE_JOKE = 1
    val TYPE_LOADING = 2
    val LOAD_THRESHOLD = 6

    val items: ArrayList<String> = arrayListOf()
    val loadMoreListener: () -> Unit
    var isLoading = false

    constructor(loadMoreListener: () -> Unit, view: RecyclerView) : super() {
        this.loadMoreListener = loadMoreListener
        view.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val layoutMan = view.layoutManager as LinearLayoutManager
                val last = layoutMan.findLastVisibleItemPosition()
                if (itemCount - last < LOAD_THRESHOLD && !isLoading) {
                    isLoading = true
                    loadMoreListener.invoke()
                }
            }
        })
        items.add("")
    }

    fun onNewItemsLoaded(newItems: List<String>) {
        isLoading = false
        val oldEnd = items.size
        if (items.isNotEmpty()) {
            items.remove(items.last())
        }
        items.addAll(newItems)
        items.add("")
        notifyItemRangeChanged(oldEnd, newItems.size + 1)
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent?.context)
        if (viewType == TYPE_JOKE) {
            val view = inflater.inflate(R.layout.item_joke, parent, false)
            return JokeViewHolder(view)
        } else {
            val view = inflater.inflate(R.layout.item_loading, parent, false)
            return LoadingViewHolder(view)
        }
    }

    override fun getItemCount(): Int = items.size

    override fun getItemViewType(position: Int): Int = if (position != itemCount - 1) TYPE_JOKE else TYPE_LOADING

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
        if (holder is JokeViewHolder) {
            holder.onBindView(items[position])
        }
    }

    class JokeViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvJoke: TextView = view.findViewById(R.id.tv_joke)

        fun onBindView(joke: String) {
            tvJoke.text = joke.escapeHtml()
        }
    }

    class LoadingViewHolder(view: View) : RecyclerView.ViewHolder(view)
}