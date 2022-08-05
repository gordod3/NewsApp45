package kg.geektech.newsapp45.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kg.geektech.newsapp45.databinding.ItemNewsBinding
import kg.geektech.newsapp45.ui.news.ItemNews
import java.time.format.DateTimeFormatter

class HomeAdapter( var list: ArrayList<ItemNews>, private val onClick : (news : ItemNews) -> Unit, private val onLongClickListener : (position : Int) -> Unit) : RecyclerView.Adapter<HomeAdapter.ViewHolder>() {
    var displayList = arrayListOf<ItemNews>()

    inner class ViewHolder(private var binding : ItemNewsBinding) : RecyclerView.ViewHolder(binding.root) {
        private val dtf = DateTimeFormatter.ofPattern("HH:mm, dd MMM yyyy")
        //                                                   20:00, 15 Июл 2022

        fun bind(news : ItemNews){
            if (news.title.length >= 24) binding.itemNewsTitleTV.text = news.title.subSequence(0, 19).toString().plus("...")
            else binding.itemNewsTitleTV.text = news.title
            binding.itemNewsTimeTV.text = dtf.format(news.time)
            binding.root.setOnClickListener(){
                onClick.invoke(news)
            }
            binding.root.setOnLongClickListener(){
                onLongClickListener.invoke(adapterPosition)
                return@setOnLongClickListener true
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemNewsBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(displayList[position])
    }

    override fun getItemCount(): Int {
        return displayList.size
    }

    fun addItem(news: ItemNews){
        list.add(0, news)
        displayList.add(0, news)
        notifyItemChanged(0)
    }

    fun delete(position: Int) {
        list.removeAt(position)
        displayList.removeAt(position)
        notifyItemRemoved(position)
    }
}