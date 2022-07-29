package kg.geektech.newsapp45.ui.board
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kg.geektech.newsapp45.databinding.ItemBoardBinding

class BoardAdapter(private var list : List<ItemBoard>, private val onClick: () -> Unit) : RecyclerView.Adapter<BoardAdapter.ViewHolder>() {
    inner class ViewHolder(private var binding : ItemBoardBinding) :
        RecyclerView.ViewHolder(binding.root) {
            fun bind(board: ItemBoard){
                binding.itemBoardMButtonStart.setOnClickListener(){
                    onClick.invoke()
                }
                if (adapterPosition == list.size - 1) binding.itemBoardMButtonStart.visibility = View.VISIBLE
                else binding.itemBoardMButtonStart.visibility = View.GONE
                Glide.with(itemView).load(board.titleImage).into(binding.itemBoardTitleImage)
                binding.itemBoardTitleText.text = board.titleText
                binding.itemBoardDescriptionText.text = board.descriptionText
            }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BoardAdapter.ViewHolder {
        return ViewHolder(ItemBoardBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: BoardAdapter.ViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }
}
