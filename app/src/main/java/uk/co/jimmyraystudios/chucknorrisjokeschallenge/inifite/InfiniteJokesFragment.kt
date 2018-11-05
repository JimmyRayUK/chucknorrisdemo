package uk.co.jimmyraystudios.chucknorrisjokeschallenge.inifite

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import uk.co.jimmyraystudios.chucknorrisjokeschallenge.MainActivityInteractor
import uk.co.jimmyraystudios.chucknorrisjokeschallenge.R
import uk.co.jimmyraystudios.chucknorrisjokeschallenge.data.Joke
import uk.co.jimmyraystudios.chucknorrisjokeschallenge.data.NorrisDataSource
import uk.co.jimmyraystudios.chucknorrisjokeschallenge.data.NorrisDataSourceProvider


class InfiniteJokesFragment : Fragment(), NorrisDataSource.ResponseListener {

    private var interactor: MainActivityInteractor? = null

    private lateinit var dataSource: NorrisDataSource

    private lateinit var recycler: RecyclerView

    private lateinit var adapter : InfiniteJokeRecyclerAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_infinite_jokes, container, false)

        initViews(view)
        dataSource = NorrisDataSourceProvider.getDataSource()
        onLoadJokesRequested()
        return view
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is MainActivityInteractor) {
            interactor = context
        } else {
            throw RuntimeException(context.toString() + " must implement MainActivityInteractor")
        }
    }

    override fun onDetach() {
        super.onDetach()
        interactor = null
    }

    override fun onResponse(jokes: List<Joke>) {
        adapter.onNewItemsLoaded(jokes.map { it.joke })
    }

    override fun onError(message: String) {
        interactor?.showErrorDialog("Oops", "Something went wrong loading more jokes, try slowing down!")
    }

    fun initViews(view: View) {
        recycler = view.findViewById(R.id.recycler_inifinte_jokes)
        recycler.layoutManager = LinearLayoutManager(view.context)
        adapter = InfiniteJokeRecyclerAdapter({ onLoadJokesRequested() }, recycler)
        recycler.adapter = adapter
    }

    private fun onLoadJokesRequested() {
        dataSource.getMultipleJokes(this , 20)
    }

    class InfiniteJokeRecyclerAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder> {
        val TYPE_JOKE = 1
        val TYPE_LOADING = 2
        val LOAD_THRESHOLD = 6

        val items : ArrayList<String> = arrayListOf()
        val loadMoreListener : () -> Unit
        var isLoading = false

        constructor(loadMoreListener : () -> Unit, view : RecyclerView) : super() {
            this.loadMoreListener = loadMoreListener
            view.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    val layoutMan = view.layoutManager as LinearLayoutManager
                    val last = layoutMan.findLastVisibleItemPosition()
                    if(itemCount - last < LOAD_THRESHOLD && !isLoading) {
                        isLoading = true
                        loadMoreListener.invoke()
                    }
                }
            })
        }

        fun onNewItemsLoaded(newItems : List<String>) {
            isLoading = false
            val oldEnd = items.size
            if(items.isNotEmpty()) { items.remove(items.last()) }
            items.addAll(newItems)
            items.add("")
            notifyItemRangeChanged(oldEnd, newItems.size + 1)
        }

        override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder {
            val inflater = LayoutInflater.from(parent?.context)
            if(viewType == TYPE_JOKE) {
                val view = inflater.inflate(R.layout.item_joke, parent, false)
                return JokeViewHolder(view)
            } else {
                val view = inflater.inflate(R.layout.item_loading, parent, false)
                return LoadingViewHolder(view)
            }
        }

        override fun getItemCount(): Int = items.size

        override fun getItemViewType(position: Int): Int = if(position != itemCount -1) TYPE_JOKE else TYPE_LOADING

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
            if(holder is JokeViewHolder) {
                holder.onBindView(items[position])
            }
        }
    }

    class JokeViewHolder(view : View) : RecyclerView.ViewHolder(view) {
        val tvJoke : TextView

        init{
            tvJoke = view.findViewById(R.id.tv_joke)
        }

        fun onBindView(joke : String) {
            tvJoke.setText(joke)
        }
    }

    class LoadingViewHolder(view : View) : RecyclerView.ViewHolder(view)

    companion object {
        @JvmStatic
        fun newInstance() = InfiniteJokesFragment()
    }
}
