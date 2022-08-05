package kg.geektech.newsapp45.ui.board

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.activity.OnBackPressedCallback
import androidx.core.content.res.ResourcesCompat
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import kg.geektech.newsapp45.Prefs
import kg.geektech.newsapp45.R
import kg.geektech.newsapp45.databinding.FragmentBoardBinding

class BoardFragment : Fragment() {

    private lateinit var binding: FragmentBoardBinding
    private lateinit var listItems: List<ItemBoard>
    private lateinit var listVPDots: List<ImageView>
    private lateinit var viewPager: ViewPager2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBoardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    requireActivity().finish()
                }

            })

        // Я долго мучался делая TextView с наложением на него onClickListener или onTouchListener
        // но он почему то не работал и решил просто сделать кнопку.
        binding.fragmentBoardButtonSkip.setOnClickListener() {
            finish()
        }
        loadData()
        viewPager = binding.fragmentBoardViewPager
        viewPager.adapter = BoardAdapter(listItems) {
            finish()
        }
        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels)
                changeColor()
            }

            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
            }

            override fun onPageScrollStateChanged(state: Int) {
                super.onPageScrollStateChanged(state)
                changeColor()
            }

        })
    }

    private fun changeColor() {
        setColor(viewPager.currentItem)
    }

    private fun setColor(position: Int) {
        for (index in 0..listVPDots.size - 1) {
            if (index == position) listVPDots[index].setBackgroundColor(ResourcesCompat.getColor(resources, R.color.purple_700, null))
            else  listVPDots[index].setBackgroundColor(ResourcesCompat.getColor(resources, R.color.purple_200, null))
        }
    }

    private fun finish() {
        Prefs(requireContext()).saveBoardState()
        findNavController().navigateUp()
    }

    private fun loadData() {
        // Не нашел удобной библиотеки для точек и ViewPager2 и решил просто свои сделать.
        listVPDots = arrayListOf(
            binding.fragmentBoardLinerLayoutIV1,
            binding.fragmentBoardLinerLayoutIV2,
            binding.fragmentBoardLinerLayoutIV3,
            binding.fragmentBoardLinerLayoutIV4
        )

        for ( index in 0..listVPDots.size - 1) listVPDots[index].setOnClickListener(){ viewPager.setCurrentItem(index)}
        listItems = arrayListOf(
            ItemBoard(
                R.raw.robot_hello,
                "Hello",
                "Welcome here"
            ),
            ItemBoard(
                R.raw.robot_helping,
                "Waiting for you",
                "We glad to see you"
            ),
            ItemBoard(
                R.raw.robot_support,
                "We starting",
                "Scroll next page"
            ),
            ItemBoard(
                R.raw.robot_analysis,
                "It's time",
                "Press button below"
            )
        )
    }
}