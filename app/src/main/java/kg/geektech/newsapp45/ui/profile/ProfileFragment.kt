package kg.geektech.newsapp45.ui.profile

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import kg.geektech.newsapp45.Prefs
import kg.geektech.newsapp45.databinding.FragmentProfileBinding


class ProfileFragment : Fragment() {

    var getContent = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        Prefs(requireContext()).saveAvatarURI(uri.toString())
        binding.fragmentImageViewProfile.setImageURI(uri)
        binding.fragmentImageViewProfileBackground.setImageURI(uri)
    }

    private lateinit var binding : FragmentProfileBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (!Prefs(requireContext()).getAvatarUri().equals(null)){
            Glide.with(this).load(Prefs(requireContext()).getAvatarUri()?.toUri()).centerCrop().into(binding.fragmentImageViewProfile)
            Glide.with(this).load(Prefs(requireContext()).getAvatarUri()?.toUri()).centerCrop().into(binding.fragmentImageViewProfileBackground)
        }
        binding.fragmentImageViewProfile.setOnClickListener{
            getContent.launch("image/*")
        }

    }
}