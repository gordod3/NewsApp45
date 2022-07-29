package kg.geektech.newsapp45.ui.home

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import kg.geektech.newsapp45.Enum
import kg.geektech.newsapp45.R
import kg.geektech.newsapp45.databinding.FragmentHomeBinding
import kg.geektech.newsapp45.ui.news.ItemNews

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: HomeAdapter
    private lateinit var builder : AlertDialog.Builder

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        builder = AlertDialog.Builder(requireContext())
        // По моему я случайон нарушил какой-то законы вселенной но мне получилось засунуть
        // в один конструктор два Unit для onClick и onLongClick логики...
        adapter = HomeAdapter(arrayListOf(), {
            loadItem(it)
        }) {
            deleteItem(it)
        }
    }

    private fun loadItem(item : ItemNews) {
        val bundle = Bundle()
        bundle.putSerializable(Enum.BUNDLE_KEY_ITEM_NEWS.name, item)
        parentFragmentManager.setFragmentResult(Enum.RC_FRAGMENT_NEWS_LOAD.name, bundle)
        findNavController().navigate(R.id.newsFragment)
    }

    private fun deleteItem(position: Int) {
        builder.setTitle("Удалить?")
        builder.setPositiveButton("Да"){dialog, which ->
            adapter.delete(position)
        }
        builder.setNegativeButton("Нет"){dialog, which ->

        }
        builder.show()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        parentFragmentManager.setFragmentResultListener(
            Enum.RC_FRAGMENT_NEWS_SAVE.name,
            viewLifecycleOwner
        ) { requestKey, bundle ->
            val news = bundle.getSerializable(Enum.BUNDLE_KEY_ITEM_NEWS.name) as ItemNews
            adapter.addItem(news)
        }

        binding.fragmentHomeFab.setOnClickListener() {
            findNavController().navigate(R.id.newsFragment)
        }

        binding.fragmentHomeRecyclerView.adapter = adapter
    }
}