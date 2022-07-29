package kg.geektech.newsapp45.ui.news

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import kg.geektech.newsapp45.Enum
import kg.geektech.newsapp45.databinding.FragmentNewsBinding
import java.time.LocalDateTime

class NewsFragment : Fragment() {
    private lateinit var binding: FragmentNewsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNewsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.fragmentNewsButtonSave.setOnClickListener() {
            save()
        }
        parentFragmentManager.setFragmentResultListener(Enum.RC_FRAGMENT_NEWS_LOAD.name, viewLifecycleOwner){ requestKey, bundle ->
            // Решил добавить возможность смотреть написанные новости и спрятал(GONE) TextView чтобы
            // потом его показать а все остальное спрятать.
            val itemNews = bundle.getSerializable(Enum.BUNDLE_KEY_ITEM_NEWS.name) as ItemNews
            binding.fragmentNewsTextView.setText(itemNews.title)
            binding.fragmentNewsTextView.visibility = View.VISIBLE
            binding.fragmentNewsButtonSave.visibility = View.GONE
            binding.fragmentNewsEditTextTitle.visibility = View.GONE
        }
    }


    private fun save() {
        if (!binding.fragmentNewsEditTextTitle.text.isEmpty()) {
            findNavController().navigateUp()
            val text = binding.fragmentNewsEditTextTitle.text.toString().trimStart().trimEnd()
            val time = LocalDateTime.now()
            val bundle = Bundle()
            bundle.putSerializable(Enum.BUNDLE_KEY_ITEM_NEWS.name, ItemNews(text, time))
            parentFragmentManager.setFragmentResult(Enum.RC_FRAGMENT_NEWS_SAVE.name, bundle)
            findNavController().navigateUp()
        } else binding.fragmentNewsEditTextTitle.setError("напишите что-нибудь")
    }
}